package org.sapphiredemocore.search.solrfacetsearch.provider.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;


/**
 * This ValueProvider will provide the product's pdf/doc url.
 */
public class PdfDocValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private static final Logger LOG = Logger.getLogger(PdfDocValueProvider.class);

	private FieldNameProvider fieldNameProvider;

	protected FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			Collection<MediaModel> mediaCollection=((ProductModel) model).getData_sheet();
			if (null!= mediaCollection && ! mediaCollection.isEmpty())
			{
				
				final MediaModel media = mediaCollection.iterator().next();
				if (media != null)
				{
					return createFieldValues(indexedProperty, media);
				}
				if (LOG.isDebugEnabled())
				{
					LOG.debug("No PDF/Doc found for product [" + ((ProductModel) model).getCode() + "]");
				}
			}
		}
		return Collections.emptyList();
	}

	protected Collection<FieldValue> createFieldValues(final IndexedProperty indexedProperty, final MediaModel media)
	{
		return createFieldValues(indexedProperty, media.getURL());
	}

	protected Collection<FieldValue> createFieldValues(final IndexedProperty indexedProperty, final String value)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();

		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}

		return fieldValues;
	}
}
