/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package sapphire.hcl.com.order.converters.populator;

import de.hybris.platform.commercefacades.order.data.OrderHistoryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.ordercancel.CancelDecision;
import de.hybris.platform.ordercancel.OrderCancelService;
import de.hybris.platform.servicelayer.user.UserService;

import org.springframework.util.Assert;


public class SapphireOrderHistoryCancelPopulator implements Populator<OrderModel, OrderHistoryData>
{
	private OrderCancelService orderCancelService;
	private UserService userService;

	@Override
	public void populate(final OrderModel source, final OrderHistoryData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setCancellable(isOrderCancellable(source));
	}

	private boolean isOrderCancellable(final OrderModel source)
	{
		final PrincipalModel user = userService.getCurrentUser();
		final CancelDecision cancelDecision = orderCancelService.isCancelPossible(source, user, false, false);
		return cancelDecision.isAllowed();
	}

	public OrderCancelService getOrderCancelService()
	{
		return orderCancelService;
	}

	public void setOrderCancelService(final OrderCancelService orderCancelService)
	{
		this.orderCancelService = orderCancelService;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

}