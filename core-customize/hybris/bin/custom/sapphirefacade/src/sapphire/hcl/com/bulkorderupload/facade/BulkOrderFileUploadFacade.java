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
package sapphire.hcl.com.bulkorderupload.facade;

import de.hybris.platform.core.model.order.CartModel;

import java.io.InputStream;


/**
 * This interface supports asynchronous processing of file upload to create a SavedCart.
 */
public interface BulkOrderFileUploadFacade
{
	/**
	 * Creates SavedCart from the file uploaded in asynchronous fashion. This will create a @
	 * {@link de.hybris.platform.acceleratorservices.cartfileupload.events.SavedCartFileUploadEvent} event which is
	 * captured by event listener @
	 * {@link de.hybris.platform.acceleratorservices.cartfileupload.events.SavedCartFileUploadEventListener} to trigger a
	 * business process.
	 *
	 * @param fileStream
	 *           Input stream of the file
	 * @param fileName
	 *           File Name
	 * @param fileFormat
	 *           File Format
	 */
	CartModel createBulkCartFromFileUpload(final InputStream fileInputStream, final String fileName, final String fileFormat);
}
