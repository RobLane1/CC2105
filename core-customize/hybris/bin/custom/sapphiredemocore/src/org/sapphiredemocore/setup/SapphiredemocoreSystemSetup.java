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
package org.sapphiredemocore.setup;

import static org.sapphiredemocore.constants.SapphiredemocoreConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import org.sapphiredemocore.constants.SapphiredemocoreConstants;
import org.sapphiredemocore.service.SapphiredemocoreService;


@SystemSetup(extension = SapphiredemocoreConstants.EXTENSIONNAME)
public class SapphiredemocoreSystemSetup
{
	private final SapphiredemocoreService sapphiredemocoreService;

	public SapphiredemocoreSystemSetup(final SapphiredemocoreService sapphiredemocoreService)
	{
		this.sapphiredemocoreService = sapphiredemocoreService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		sapphiredemocoreService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SapphiredemocoreSystemSetup.class.getResourceAsStream("/sapphiredemocore/sap-hybris-platform.png");
	}
}
