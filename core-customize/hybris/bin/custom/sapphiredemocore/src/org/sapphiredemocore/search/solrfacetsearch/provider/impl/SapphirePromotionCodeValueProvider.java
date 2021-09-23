package org.sapphiredemocore.search.solrfacetsearch.provider.impl;


import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.PromotionCodeValueProvider;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.promotions.model.AbstractPromotionModel;
import de.hybris.platform.promotions.model.PromotionGroupModel;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * This ValueProvider will provide the product's promotion code.
 */
public class SapphirePromotionCodeValueProvider extends PromotionCodeValueProvider
{
	private TimeService timeService;

	@Override
	protected List<FieldValue> createFieldValue(final ProductModel product, final IndexConfig indexConfig,
			final IndexedProperty indexedProperty)
	{
		if ("Y5003116".equals(product.getCode()))
		{
			System.out.println("found");
		}
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final BaseSiteModel baseSiteModel = indexConfig.getBaseSite();
		if (baseSiteModel != null && baseSiteModel.getDefaultPromotionGroup() != null)
		{
			final PromotionGroupModel defaultPromotionGroup = baseSiteModel.getDefaultPromotionGroup();
			final Date currentTimeRoundedToMinute = DateUtils.round(getTimeService().getCurrentTime(), Calendar.MINUTE);
			if (defaultPromotionGroup != null)
			{
				final List<AbstractPromotionModel> promotions = (List<AbstractPromotionModel>) getPromotionsService()
						.getAbstractProductPromotions(Collections.singletonList(defaultPromotionGroup), product, true,
								currentTimeRoundedToMinute);
				for (final AbstractPromotionModel promotion : promotions)
				{
					addFieldValues(fieldValues, indexedProperty, null, promotion.getCode());
					break;
				}
			}
		}
		return fieldValues;
	}

	@Override
	protected List<FieldValue> createFieldValues(final ProductModel product, final IndexConfig indexConfig,
			final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		final BaseSiteModel baseSiteModel = indexConfig.getBaseSite();
		if (baseSiteModel != null && baseSiteModel.getDefaultPromotionGroup() != null)
		{
			final PromotionGroupModel defaultPromotionGroup = baseSiteModel.getDefaultPromotionGroup();
			final Date currentTimeRoundedToMinute = DateUtils.round(getTimeService().getCurrentTime(), Calendar.MINUTE);
			if (defaultPromotionGroup != null)
			{
				final List<AbstractPromotionModel> promotions = (List<AbstractPromotionModel>) getPromotionsService()
						.getAbstractProductPromotions(Collections.singletonList(defaultPromotionGroup), product, true,
								currentTimeRoundedToMinute);
				for (final AbstractPromotionModel promotion : promotions)
				{
					addFieldValues(fieldValues, indexedProperty, null, promotion.getCode());
					break;
				}
			}
		}
		return fieldValues;
	}

	/**
	 * Getter
	 *
	 * @return TimeService
	 */
	protected TimeService getTimeService()
	{
		return timeService;
	}

	/**
	 * Setter
	 *
	 * @param timeService
	 */
	@Required
	public void setTimeService(final TimeService timeService)
	{
		this.timeService = timeService;
	}
}
