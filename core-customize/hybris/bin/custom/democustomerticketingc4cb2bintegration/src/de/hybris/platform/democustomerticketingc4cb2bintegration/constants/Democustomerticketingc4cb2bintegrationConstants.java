/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 SAP SE
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * Hybris ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with SAP Hybris.
 */
package de.hybris.platform.democustomerticketingc4cb2bintegration.constants;

import de.hybris.platform.util.Config;



/**
 * Global class for all Democustomerticketingc4cb2bintegration constants. You can add global constants for your
 * extension into this class.
 */
public final class Democustomerticketingc4cb2bintegrationConstants extends
		GeneratedDemocustomerticketingc4cb2bintegrationConstants
{
	public static final String EXTENSIONNAME = "democustomerticketingc4cb2bintegration";

	private Democustomerticketingc4cb2bintegrationConstants()
	{
		//empty to avoid instantiating this constant class
	}

	// implement here constants used by this extension

	public static final String PLATFORM_LOGO_CODE = "democustomerticketingc4cb2bintegrationPlatformLogo";
	public static final String URL = Config.getParameter("customerticketingc4cintegration.c4c-get-url");
	public static final String URL_SUFFIX = Config.getParameter("customerticketingc4cintegration.c4c-ticket-get-suffix");

}
