/**
 *
 */
package sapphire.hcl.com.order.facade.impl;

import static de.hybris.platform.util.localization.Localization.getLocalizedString;

import de.hybris.platform.b2b.model.B2BCostCenterModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.B2BCostCenterService;
import de.hybris.platform.b2bacceleratorfacades.exception.EntityValidationException;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BPaymentTypeData;
import de.hybris.platform.b2bacceleratorfacades.order.impl.DefaultB2BCheckoutFacade;
import de.hybris.platform.b2bacceleratorservices.enums.CheckoutPaymentType;
import de.hybris.platform.b2bacceleratorservices.order.B2BCommerceCartService;
import de.hybris.platform.b2bcommercefacades.company.data.B2BCostCenterData;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commerceservices.delivery.DeliveryService;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.CommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.servicelayer.internal.dao.GenericDao;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import sapphire.hcl.com.order.facade.BulkOrderCheckoutFacade;


/**
 *
 */
public class DefaultBulkOrderCheckoutFacade extends DefaultB2BCheckoutFacade implements BulkOrderCheckoutFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultBulkOrderCheckoutFacade.class);
	private CommerceCheckoutService commerceCheckoutService;
	private B2BCommerceCartService commerceCartService;
	private B2BCostCenterService<B2BCostCenterModel, B2BCustomerModel> b2bCostCenterService;
	private ModelService modelService;
	private CartFacade cartFacade;
	private DeliveryService deliveryService;
	private CartService cartService;
	private GenericDao<AbstractOrderModel> abstractOrderGenericDao;
	private PriceDataFactory priceDataFactory;
	private static final String CART_CHECKOUT_DELIVERYADDRESS_INVALID = "cart.deliveryAddress.invalid";
	private static final String CART_CHECKOUT_DELIVERYMODE_INVALID = "cart.deliveryMode.invalid";
	private static final String CART_CHECKOUT_NOT_CALCULATED = "cart.not.calculated";
	private static final String CART_CHECKOUT_PAYMENTINFO_EMPTY = "cart.paymentInfo.empty";


	@Override
	public OrderModel placeOrder(final CartModel cartModel, final String costCenterId, final String purchaseOrderNo)
			throws InvalidCartException
	{
		final CartData cartData = getCheckoutCart();
		final CartModel uploadedCart = getCart();
		uploadedCart.setPaymentType(CheckoutPaymentType.ACCOUNT);

		if (CheckoutPaymentType.ACCOUNT.getCode().equals(uploadedCart.getPaymentType().getCode()))
		{
			final B2BCostCenterData costCenter = new B2BCostCenterData();
			costCenter.setCode(costCenterId);

			cartData.setCostCenter(costCenter);
		}


		if (cartData.getCostCenter() != null)
		{
			setCostCenterForCart(costCenterId, uploadedCart);
			getModelService().save(uploadedCart);
		}

		uploadedCart.setPurchaseOrderNumber(purchaseOrderNo);

		uploadedCart.setPaymentInfo(getCommerceCartService().createInvoicePaymentInfo(uploadedCart));
		getCommerceCartService().calculateCartForPaymentTypeChange(uploadedCart);

		B2BCostCenterModel costCenterModel = null;
		if (costCenterId != null)
		{
			costCenterModel = getB2bCostCenterService().getCostCenterForCode(costCenterId);
		}
		uploadedCart.setDeliveryAddress(costCenterModel.getUnit().getShippingAddress());

		final DeliveryModeModel deliveryModeModel = getDeliveryService().getDeliveryModeForCode("standard-gross");
		uploadedCart.setDeliveryMode(deliveryModeModel);

		//uploadedCart.setDeliveryCost(8.99);
		getModelService().save(uploadedCart);
		getCommerceCheckoutService().calculateCart(createCommerceCheckoutParameter(uploadedCart, true));

		uploadedCart.setStatus(OrderStatus.WAITING_FOR_IMMEDIATE_CANCEL);


		final CommerceCheckoutParameter parameter = new CommerceCheckoutParameter();
		parameter.setEnableHooks(false);
		parameter.setCart(uploadedCart);
		parameter.setSalesApplication(SalesApplication.WEB);


		if (isValidCheckoutCart())
		{
			final OrderModel orderModel = getCommerceCheckoutService().placeOrder(parameter).getOrder();

			if (orderModel != null)
			{
				getCartService().removeSessionCart();
				//getModelService().refresh(orderModel);
				final String orderCode = orderModel.getCode();
				final AbstractOrderModel abstractOrderModel = getAbstractOrderForCode(orderCode);

				costCenterModel = getB2bCostCenterService().getCostCenterForCode(costCenterId);
				for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries())
				{
					abstractOrderEntry.setCostCenter(costCenterModel);
					getModelService().save(abstractOrderEntry);
				}
				getModelService().save(abstractOrderModel);
				getModelService().refresh(orderModel);
			}

			return orderModel;
		}


		return null;
	}


	protected <T extends AbstractOrderModel> T getAbstractOrderForCode(final String code)
	{
		final List<AbstractOrderModel> orders = getAbstractOrderGenericDao()
				.find(Collections.singletonMap(AbstractOrderModel.CODE, code));
		return orders.iterator().hasNext() ? (T) orders.iterator().next() : null;
	}

	protected boolean isValidCheckoutCart()
	{
		final CartData cartData = getCheckoutCart();
		final boolean valid = true;

		if (!cartData.isCalculated())
		{
			throw new EntityValidationException(getLocalizedString(CART_CHECKOUT_NOT_CALCULATED));
		}

		if (cartData.getDeliveryAddress() == null)
		{
			throw new EntityValidationException(getLocalizedString(CART_CHECKOUT_DELIVERYADDRESS_INVALID));
		}

		if (cartData.getDeliveryMode() == null)
		{
			throw new EntityValidationException(getLocalizedString(CART_CHECKOUT_DELIVERYMODE_INVALID));
		}

		final boolean accountPaymentType = CheckoutPaymentType.ACCOUNT.getCode().equals(cartData.getPaymentType().getCode());
		if (!accountPaymentType && cartData.getPaymentInfo() == null)
		{
			throw new EntityValidationException(getLocalizedString(CART_CHECKOUT_PAYMENTINFO_EMPTY));
		}

		return valid;
	}

	protected void setCostCenterForCart(final String costCenterCode, final CartModel cartModel)
	{
		final B2BPaymentTypeData paymentType = getCheckoutCart().getPaymentType();

		B2BCostCenterModel costCenterModel = null;
		if (costCenterCode != null)
		{
			costCenterModel = getB2bCostCenterService().getCostCenterForCode(costCenterCode);
		}

		for (final AbstractOrderEntryModel abstractOrderEntry : cartModel.getEntries())
		{
			if (abstractOrderEntry.getCostCenter() != costCenterModel)
			{
				abstractOrderEntry.setCostCenter(costCenterModel);
				getModelService().save(abstractOrderEntry);
			}
		}

		// if set cost center, delivery address and mode need to be cleared
		removeDeliveryAddress();
		removeDeliveryMode();
	}

	public CartData getCheckoutCart()
	{
		final CartData cartData = getCartFacade().getSessionCart();
		return cartData;
	}

	protected CartModel getCart()
	{
		return hasCheckoutCart() ? getCartService().getSessionCart() : null;
	}

	public boolean hasCheckoutCart()
	{
		return getCartFacade().hasSessionCart();
	}

	public boolean removeDeliveryAddress()
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final CommerceCheckoutParameter parameter = createCommerceCheckoutParameter(cartModel, true);
			parameter.setAddress(null);
			parameter.setIsDeliveryAddress(false);
			return getCommerceCheckoutService().setDeliveryAddress(parameter);
		}
		return false;
	}

	public boolean removeDeliveryMode()
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			return getCommerceCheckoutService().removeDeliveryMode(createCommerceCheckoutParameter(cartModel, true));
		}
		return false;
	}

	protected CommerceCheckoutParameter createCommerceCheckoutParameter(final CartModel cart, final boolean enableHooks)
	{
		final CommerceCheckoutParameter parameter = new CommerceCheckoutParameter();
		parameter.setEnableHooks(enableHooks);
		parameter.setCart(cart);
		return parameter;
	}


	/**
	 * @return the commerceCheckoutService
	 */
	public CommerceCheckoutService getCommerceCheckoutService()
	{
		return commerceCheckoutService;
	}

	/**
	 * @param commerceCheckoutService
	 *           the commerceCheckoutService to set
	 */
	public void setCommerceCheckoutService(final CommerceCheckoutService commerceCheckoutService)
	{
		this.commerceCheckoutService = commerceCheckoutService;
	}


	protected <T extends B2BCommerceCartService> T getCommerceCartService()
	{
		return (T) commerceCartService;
	}

	@Required
	public <T extends B2BCommerceCartService> void setCommerceCartService(final T _commerceCartService)
	{
		this.commerceCartService = _commerceCartService;
	}


	protected B2BCostCenterService<B2BCostCenterModel, B2BCustomerModel> getB2bCostCenterService()
	{
		return b2bCostCenterService;
	}

	@Required
	public void setB2bCostCenterService(final B2BCostCenterService<B2BCostCenterModel, B2BCustomerModel> b2bCostCenterService)
	{
		this.b2bCostCenterService = b2bCostCenterService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	protected <T extends CartService> T getCartService()
	{
		return (T) cartService;
	}

	@Required
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	@Required
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	protected DeliveryService getDeliveryService()
	{
		return deliveryService;
	}

	@Required
	public void setDeliveryService(final DeliveryService deliveryService)
	{
		this.deliveryService = deliveryService;
	}

	protected PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	@Required
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	protected GenericDao<AbstractOrderModel> getAbstractOrderGenericDao()
	{
		return abstractOrderGenericDao;
	}

	@Required
	public void setAbstractOrderGenericDao(final GenericDao<AbstractOrderModel> abstractOrderGenericDao)
	{
		this.abstractOrderGenericDao = abstractOrderGenericDao;
	}
}

