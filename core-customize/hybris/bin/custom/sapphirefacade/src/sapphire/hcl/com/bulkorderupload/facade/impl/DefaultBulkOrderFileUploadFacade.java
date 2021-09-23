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
package sapphire.hcl.com.bulkorderupload.facade.impl;

import de.hybris.platform.acceleratorservices.cartfileupload.events.SavedCartFileUploadEvent;
import de.hybris.platform.acceleratorservices.enums.ImportStatus;
import de.hybris.platform.acceleratorservices.model.process.SavedCartFileUploadProcessModel;
import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import sapphire.hcl.com.bulkorderupload.action.BulkCartFromUploadFileAction;
import sapphire.hcl.com.bulkorderupload.facade.BulkOrderFileUploadFacade;


/**
 *
 */
public class DefaultBulkOrderFileUploadFacade implements BulkOrderFileUploadFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultBulkOrderFileUploadFacade.class);

	private CommonI18NService commonI18NService;
	private BaseStoreService baseStoreService;
	private UserService userService;
	private EventService eventService;
	private MediaService mediaService;
	private ModelService modelService;
	private BaseSiteService baseSiteService;
	private TimeService timeService;
	private KeyGenerator guidKeyGenerator;
	private BusinessProcessService businessProcessService;
	@Resource(name = "defaultBulkCartFromUploadFileAction")
	private BulkCartFromUploadFileAction bulkCartFromUploadFileAction;


	@Override
	public CartModel createBulkCartFromFileUpload(final InputStream fileInputStream, final String fileName,
			final String fileFormat)
	{
		final CatalogUnawareMediaModel mediaModel = getModelService().create(CatalogUnawareMediaModel.class);
		mediaModel.setCode(System.currentTimeMillis() + "_" + fileName);
		getModelService().save(mediaModel);
		getMediaService().setStreamForMedia(mediaModel, fileInputStream, fileName, fileFormat);
		getModelService().refresh(mediaModel);

		final SavedCartFileUploadEvent savedCartFileUploadEvent = new SavedCartFileUploadEvent();
		savedCartFileUploadEvent.setFileMedia(mediaModel);
		savedCartFileUploadEvent.setCurrency(getCommonI18NService().getCurrentCurrency());
		savedCartFileUploadEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		savedCartFileUploadEvent.setCustomer((CustomerModel) getUserService().getCurrentUser());
		savedCartFileUploadEvent.setBaseStore(getBaseStoreService().getCurrentBaseStore());
		savedCartFileUploadEvent.setSite(getBaseSiteService().getCurrentBaseSite());
		final CartModel cartFileUploadProcessModel = publishEvent(savedCartFileUploadEvent);

		/*
		 * final Collection<CartModel> cartModel = savedCartFileUploadEvent.getCustomer().getCarts();
		 *
		 * final Iterator<CartModel> itr = cartModel.iterator(); CartModel lastElement = itr.next();
		 *
		 * while (itr.hasNext()) { lastElement = itr.next(); }
		 *
		 * System.out.println("Last element in the iterator is ---------" + lastElement);
		 */
		return cartFileUploadProcessModel;
	}

	protected CartModel publishEvent(final SavedCartFileUploadEvent event)
	{
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Received SavedCartFileUploadEvent..");
		}
		final SavedCartFileUploadProcessModel cartFileUploadProcessModel = (SavedCartFileUploadProcessModel) getBusinessProcessService()
				.createProcess("savedCartFileUploadProcess" + "-" + event.getBaseStore().getUid() + "-" + System.currentTimeMillis(),
						"savedCartFileUploadProcess");
		cartFileUploadProcessModel.setUploadedFile(event.getFileMedia());
		cartFileUploadProcessModel.setUser(event.getCustomer());
		cartFileUploadProcessModel.setStore(event.getBaseStore());
		cartFileUploadProcessModel.setCurrency(event.getCurrency());
		cartFileUploadProcessModel.setLanguage(event.getLanguage());
		cartFileUploadProcessModel.setSite(event.getSite());
		cartFileUploadProcessModel.setSavedCart(createSavedCartForProcess(event));
		getModelService().save(cartFileUploadProcessModel);
		getModelService().refresh(cartFileUploadProcessModel);
		final CartModel cartModel = bulkCartFromUploadFileAction.executeAction(cartFileUploadProcessModel);
		return cartModel;
	}

	protected CartModel createSavedCartForProcess(final SavedCartFileUploadEvent event)
	{
		final CartModel cartModel = getModelService().create(CartModel.class);
		cartModel.setSaveTime(getTimeService().getCurrentTime());
		cartModel.setName(String.valueOf(System.currentTimeMillis()));
		cartModel.setUser(event.getCustomer());
		cartModel.setCurrency(event.getCurrency());
		cartModel.setDate(getTimeService().getCurrentTime());
		cartModel.setSite(event.getSite());
		cartModel.setSavedBy(event.getCustomer());
		cartModel.setImportStatus(ImportStatus.PROCESSING);
		cartModel.setStore(event.getBaseStore());
		cartModel.setGuid(getGuidKeyGenerator().generate().toString());
		getModelService().save(cartModel);
		getModelService().refresh(cartModel);
		return cartModel;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	@Required
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	protected EventService getEventService()
	{
		return eventService;
	}

	@Required
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	protected MediaService getMediaService()
	{
		return mediaService;
	}

	@Required
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the timeService
	 */
	public TimeService getTimeService()
	{
		return timeService;
	}

	/**
	 * @param timeService
	 *           the timeService to set
	 */
	public void setTimeService(final TimeService timeService)
	{
		this.timeService = timeService;
	}

	/**
	 * @return the guidKeyGenerator
	 */
	public KeyGenerator getGuidKeyGenerator()
	{
		return guidKeyGenerator;
	}

	/**
	 * @param guidKeyGenerator
	 *           the guidKeyGenerator to set
	 */
	public void setGuidKeyGenerator(final KeyGenerator guidKeyGenerator)
	{
		this.guidKeyGenerator = guidKeyGenerator;
	}

	/**
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *           the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}
}

