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
package sapphire.hcl.com.order.facade;

import de.hybris.platform.commercefacades.order.data.OrderData;

import sapphire.hcl.com.order.data.OrdercancelResultData;


/**
 *
 */
public interface SapphireOrderCancelFacade
{
	OrdercancelResultData cancelOrder(OrderData order);
}
