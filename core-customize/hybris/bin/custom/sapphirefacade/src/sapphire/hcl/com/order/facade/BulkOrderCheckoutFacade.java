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

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;


/**
 *
 */
public interface BulkOrderCheckoutFacade
{
	OrderModel placeOrder(final CartModel uploadedCart, final String costCenterId, final String purchaseOrderNo)
			throws InvalidCartException;

}
