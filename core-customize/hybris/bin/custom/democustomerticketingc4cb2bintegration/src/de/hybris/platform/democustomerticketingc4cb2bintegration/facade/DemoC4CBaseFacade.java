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
package de.hybris.platform.democustomerticketingc4cb2bintegration.facade;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.customerticketingc4cintegration.facade.C4CBaseFacade;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.List;


/**
 *
 */
public class DemoC4CBaseFacade extends C4CBaseFacade
{
	@Override
	protected <S, T> SearchPageData<T> convertPageData(final List<S> source, final Converter<S, T> converter,
			final PageableData pageableData, final int total)
	{
		// YTODO Auto-generated method stub
		return super.convertPageData(source, converter, pageableData, total);
	}

	@Override
	protected List<SortData> getSupportedSortsForC4C(final PageableData pageableData)
	{
		// YTODO Auto-generated method stub
		return super.getSupportedSortsForC4C(pageableData);
	}
}
