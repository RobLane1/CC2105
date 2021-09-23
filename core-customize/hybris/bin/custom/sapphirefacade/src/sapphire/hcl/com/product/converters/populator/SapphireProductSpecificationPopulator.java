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
package sapphire.hcl.com.product.converters.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;


/**
 * Populator for product specification
 */
public class SapphireProductSpecificationPopulator implements Populator<ProductModel, ProductData>
{

	@Override
	public void populate(final ProductModel source, final ProductData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		populateProductSpecification(source, target);
	}

	private void populateProductSpecification(final ProductModel source, final ProductData target)
	{
		final Collection<MediaModel> mediaCollection = source.getData_sheet();
		if (null != mediaCollection && !mediaCollection.isEmpty())
		{

			final MediaModel media = mediaCollection.iterator().next();
			if (media != null)
			{
				final String url = media.getURL();
				target.setSpecifications(StringUtils.isEmpty(url) ? "" : url);
			}
		}
	}
}
