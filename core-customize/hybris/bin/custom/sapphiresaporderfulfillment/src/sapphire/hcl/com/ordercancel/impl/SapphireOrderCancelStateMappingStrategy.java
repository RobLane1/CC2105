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
package sapphire.hcl.com.ordercancel.impl;

import de.hybris.platform.basecommerce.enums.OrderCancelState;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordercancel.OrderCancelStateMappingStrategy;
import de.hybris.platform.servicelayer.util.ServicesUtil;


/**
 *
 */
public class SapphireOrderCancelStateMappingStrategy implements OrderCancelStateMappingStrategy
{

	@Override
	public OrderCancelState getOrderCancelState(final OrderModel order)
	{
		ServicesUtil.validateParameterNotNull(order, "order must not be null");
		if (OrderStatus.WAITING_FOR_IMMEDIATE_CANCEL.equals(order.getStatus()))
		{
			return OrderCancelState.PENDINGORHOLDINGAREA;
		}
		return OrderCancelState.CANCELIMPOSSIBLE;
	}

}
