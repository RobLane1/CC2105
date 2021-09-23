/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2017 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package sapphire.hcl.com.order.facade.impl;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.ordercancel.CancelDecision;
import de.hybris.platform.ordercancel.DefaultOrderCancelDenialReason;
import de.hybris.platform.ordercancel.OrderCancelDenialReason;
import de.hybris.platform.ordercancel.OrderCancelException;
import de.hybris.platform.ordercancel.OrderCancelRequest;
import de.hybris.platform.ordercancel.OrderCancelService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.store.services.BaseStoreService;

import org.apache.log4j.Logger;

import sapphire.hcl.com.order.data.OrdercancelResultData;
import sapphire.hcl.com.order.facade.SapphireOrderCancelFacade;


/**
 *
 */
public class SapphireOrderCancelFacadeImpl implements SapphireOrderCancelFacade
{

	private static final Logger LOG = Logger.getLogger(SapphireOrderCancelFacadeImpl.class);
	private OrderCancelService orderCancelService;
	private CustomerAccountService customerAccountService;
	private UserService userService;
	private BaseStoreService baseStoreService;
	private ModelService modelService;

	@Override
	public OrdercancelResultData cancelOrder(final OrderData order)
	{
		ServicesUtil.validateParameterNotNull(order, "order must not be null");
		final CustomerModel currentUser = (CustomerModel) getUserService().getCurrentUser();
		final OrderModel orderModel = getCustomerAccountService().getOrderForCode(currentUser, order.getCode(),
				getBaseStoreService().getCurrentBaseStore());

		if (null == orderModel)
		{
			throw new UnknownIdentifierException("order with code " + order.getCode()
					+ " not found for current user in current BaseStore");
		}

		final CancelDecision cancelDecision = orderCancelService.isCancelPossible(orderModel, currentUser, false, false);
		final OrdercancelResultData result = new OrdercancelResultData();
		result.setOrderId(order.getCode());
		if (cancelDecision.isAllowed())
		{
			try
			{
				final OrderCancelRequest orderCancelRequest = new OrderCancelRequest(orderModel);
				orderCancelService.requestOrderCancel(orderCancelRequest, currentUser);
				orderModel.setStatus(OrderStatus.CANCELLED);
				modelService.save(orderModel);
				result.setSuccess(true);
			}
			catch (final OrderCancelException e)
			{
				LOG.error("Cancel failed for Order [ " + orderModel + " ]", e);
				result.setSuccess(false);
				result.setFailReason(e.getMessage());
			}
		}
		else
		{
			result.setSuccess(false);
			final StringBuilder denialReasonDescriptionBuilder = new StringBuilder();
			for (final OrderCancelDenialReason reason : cancelDecision.getDenialReasons())
			{
				if (reason instanceof DefaultOrderCancelDenialReason)
				{
					final DefaultOrderCancelDenialReason defaultOrderCancelDenialReason = (DefaultOrderCancelDenialReason) reason;
					denialReasonDescriptionBuilder.append(defaultOrderCancelDenialReason.getDescription()).append("</br>");
				}
			}
			result.setFailReason(denialReasonDescriptionBuilder.toString());
		}

		return result;
	}

	public OrderCancelService getOrderCancelService()
	{
		return orderCancelService;
	}

	public void setOrderCancelService(final OrderCancelService orderCancelService)
	{
		this.orderCancelService = orderCancelService;
	}

	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


}
