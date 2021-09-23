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
package de.hybris.platform.democustomerticketingc4cb2bintegration.converters.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.customerticketingc4cintegration.constants.Customerticketingc4cintegrationConstants;
import de.hybris.platform.customerticketingc4cintegration.data.DemoServiceRequestData;
import de.hybris.platform.customerticketingc4cintegration.data.Name;
import de.hybris.platform.customerticketingc4cintegration.data.Note;
import de.hybris.platform.customerticketingfacades.data.StatusData;
import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;


/**
 *
 * Populator for B2B external customer id
 *
 */
public class DemoY2C4CB2BTicketPopulator<SOURCE extends TicketData, TARGET extends DemoServiceRequestData>
		implements Populator<SOURCE, TARGET>
{

	@Resource
	private Map<String, StatusData> statusMapping;

	@Override
	public void populate(final TicketData source, final DemoServiceRequestData target) throws ConversionException
	{
		final String SALES_ORDER_VALUE = Config.getParameter("democustomerticketingc4cb2bintegration.service.order.value");
		final String PROCESSIONG_TYPE_CODE_VALUE = Config
				.getParameter("democustomerticketingc4cb2bintegration.processing.type.code.value");

		if ((source.getStatus() == null || source.getStatus().getId() == null)) // creating
		{
			target.setCustomerID(source.getCustomerId());
			target.setProcessingTypeCode(PROCESSIONG_TYPE_CODE_VALUE);
			target.setProcessingTypeCodeText(SALES_ORDER_VALUE);
			target.setProductID(source.getProductId());
			target.setAssignedTo("JACK");

			final Name subject = new Name();
			subject.setContent(source.getSubject());
			subject.setLanguageCode(Customerticketingc4cintegrationConstants.LANGUAGE);
			target.setName(subject);

			if (!StringUtils.isEmpty(source.getMessage()))
			{
				final Note textData = new Note();
				textData.setText(source.getMessage());
				textData.setLanguageCode(Customerticketingc4cintegrationConstants.LANGUAGE);
				textData.setTypeCode(Customerticketingc4cintegrationConstants.TYPECODE_10004);
				target.setNotes(Arrays.asList(textData));
			}

		}
		else
		// updating
		{
			for (final String id : statusMapping.keySet())
			{
				if (statusMapping.get(id).getId().equalsIgnoreCase(source.getStatus().getId()))
				{
					target.setStatusCode(id);
				}
			}
		}
	}
}