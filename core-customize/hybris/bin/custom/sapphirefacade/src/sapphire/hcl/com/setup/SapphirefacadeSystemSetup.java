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
package sapphire.hcl.com.setup;

import static sapphire.hcl.com.constants.SapphirefacadeConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import sapphire.hcl.com.constants.SapphirefacadeConstants;
import sapphire.hcl.com.service.SapphirefacadeService;


@SystemSetup(extension = SapphirefacadeConstants.EXTENSIONNAME)
public class SapphirefacadeSystemSetup
{
	private final SapphirefacadeService sapphirefacadeService;

	public SapphirefacadeSystemSetup(final SapphirefacadeService sapphirefacadeService)
	{
		this.sapphirefacadeService = sapphirefacadeService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		sapphirefacadeService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return SapphirefacadeSystemSetup.class.getResourceAsStream("/sapphirefacade/sap-hybris-platform.png");
	}
}
