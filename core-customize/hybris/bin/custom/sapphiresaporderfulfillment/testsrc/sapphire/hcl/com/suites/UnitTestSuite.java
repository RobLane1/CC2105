/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package sapphire.hcl.com.suites;


import sapphire.hcl.com.actions.SendOrderToDataHubActionTest;
import sapphire.hcl.com.actions.SetCompletionStatusActionTest;
import sapphire.hcl.com.actions.SetConfirmationStatusActionTest;
import sapphire.hcl.com.actions.UpdateERPOrderStatusActionTest;
import sapphire.hcl.com.jobs.OrderCancelRepairJobTest;
import sapphire.hcl.com.jobs.OrderExchangeRepairJobTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@SuppressWarnings("javadoc")
@RunWith(Suite.class)
@SuiteClasses(
{ UpdateERPOrderStatusActionTest.class, SetConfirmationStatusActionTest.class, SendOrderToDataHubActionTest.class,
		SetCompletionStatusActionTest.class, OrderExchangeRepairJobTest.class, OrderCancelRepairJobTest.class })
public class UnitTestSuite
{
	// Intentionally left blank
}
