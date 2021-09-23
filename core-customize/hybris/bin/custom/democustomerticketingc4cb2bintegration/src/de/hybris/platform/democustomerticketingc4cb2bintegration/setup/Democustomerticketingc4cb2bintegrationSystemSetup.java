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
package de.hybris.platform.democustomerticketingc4cb2bintegration.setup;

import static de.hybris.platform.democustomerticketingc4cb2bintegration.constants.Democustomerticketingc4cb2bintegrationConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import de.hybris.platform.democustomerticketingc4cb2bintegration.constants.Democustomerticketingc4cb2bintegrationConstants;
import de.hybris.platform.democustomerticketingc4cb2bintegration.service.Democustomerticketingc4cb2bintegrationService;


@SystemSetup(extension = Democustomerticketingc4cb2bintegrationConstants.EXTENSIONNAME)
public class Democustomerticketingc4cb2bintegrationSystemSetup
{
	private final Democustomerticketingc4cb2bintegrationService democustomerticketingc4cb2bintegrationService;

	public Democustomerticketingc4cb2bintegrationSystemSetup(final Democustomerticketingc4cb2bintegrationService democustomerticketingc4cb2bintegrationService)
	{
		this.democustomerticketingc4cb2bintegrationService = democustomerticketingc4cb2bintegrationService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		democustomerticketingc4cb2bintegrationService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return Democustomerticketingc4cb2bintegrationSystemSetup.class.getResourceAsStream("/democustomerticketingc4cb2bintegration/sap-hybris-platform.png");
	}
}
