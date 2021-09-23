/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 SAP SE or an SAP affiliate company.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 *
 *
 */
package de.hybris.platform.b2b.occ.v2.constants;

/**
 * Global class for all Demob2boccaddon web constants. You can add global constants for your extension into this class.
 */
public final class Demob2boccaddonWebConstants
{
	//Dummy field to avoid pmd error - delete when you add the first real constant!
	public static final String deleteThisDummyField = "DELETE ME";

	private Demob2boccaddonWebConstants()
	{
		//empty to avoid instantiating this constant class
	}

	public static final String ROLE_GUEST = "ROLE_GUEST";
	public static final String ROLE_CUSTOMERGROUP = "ROLE_CUSTOMERGROUP";
	public static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
	public static final String ROLE_CUSTOMERMANAGERGROUP = "ROLE_CUSTOMERMANAGERGROUP";
}
