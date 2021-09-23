package org.sapphiredemocore.search.converters.populator;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.PromotionData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantOptionsProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;


/**
 * This class is used for search populator for Sapphire
 */
public class SapphireSearchResultVariantOptionsProductPopulator extends SearchResultVariantOptionsProductPopulator
{

	final String DOC_PDF_SPECIFICATION = "doc_pdf";
	final String PROMOTION_DESCRIPTION = "primaryPromotionDesc";

	/**
	 * Populate the target instance with values from the source instance.
	 *
	 * @param source
	 * @param target
	 * @throws de.hybris.platform.servicelayer.dto.converter.ConversionException
	 *            if an error occurs
	 */
	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);
		populateProductSpecificationDocument(source, target);
	}

	/**
	 * This method is used to populate the document associated to a product
	 *
	 * @param source
	 * @param target
	 */
	private void populateProductSpecificationDocument(final SearchResultValueData source, final ProductData target)
	{
		final String url = (String) getValue(source, DOC_PDF_SPECIFICATION);
		target.setSpecifications(StringUtils.isEmpty(url) ? "" : url);
	}

	@Override
	protected void populatePromotions(final SearchResultValueData source, final ProductData target)
	{
		final String promotionCode = this.<String> getValue(source, "primaryPromotionCode");
		if (StringUtils.isNotEmpty(promotionCode))
		{
			final String primaryPromotionBannerUrl = this.<String> getValue(source, "primaryPromotionBanner");
			final PromotionData promotionData = createPromotionData(promotionCode, primaryPromotionBannerUrl);
			final String promotionDescription = (String) getValue(source, PROMOTION_DESCRIPTION);
			promotionData.setDescription(StringUtils.isEmpty(promotionDescription) ? "" : promotionDescription);
			target.setPotentialPromotions(Collections.singletonList(promotionData));
		}
	}

}
