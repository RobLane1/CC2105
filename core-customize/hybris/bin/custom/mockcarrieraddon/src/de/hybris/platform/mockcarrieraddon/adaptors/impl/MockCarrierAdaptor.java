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
package de.hybris.platform.mockcarrieraddon.adaptors.impl;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.mockcarrieraddon.trustmanager.ConsignmentX509ExtendedTrustManager;
import de.hybris.platform.mockcarrieraddon.utils.BufferReaderUtils;
import de.hybris.platform.consignmenttrackingservices.adaptors.CarrierAdaptor;
import de.hybris.platform.consignmenttrackingservices.delivery.data.ConsignmentEventData;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.site.BaseSiteService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.databind.ObjectMapper;




/**
 * A mock implementation of CarrierAdaptor.
 */
public class MockCarrierAdaptor implements CarrierAdaptor
{
	private static final Logger LOG = LoggerFactory.getLogger(MockCarrierAdaptor.class);
	private static final String TRACKING_URL_KEY = "default.carrier.tracking.url";
	private static final String DELIVERY_LEAD_TIME_KEY = "default.delivery.lead.time";
	private ConfigurationService configurationService;
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;
	private BaseSiteService baseSiteService;

	@Override
	public List<ConsignmentEventData> getConsignmentEvents(final String trackingId)
	{

		List<ConsignmentEventData> events = new ArrayList<>();
		URL url = getTrackingUrl(trackingId);

		if (url != null)
		{
			HttpsURLConnection connection = null;
			BufferedReader reader = null;
			try
			{
				url = new URL(url.toString() + trackingId);
				final SSLContext sslcontext = SSLContext.getInstance("TLS");
				sslcontext.init(null, new TrustManager[]
				{ getTrustManager() }, new SecureRandom());

				connection = (HttpsURLConnection) url.openConnection();
				connection.setSSLSocketFactory(sslcontext.getSocketFactory());

				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				final StringBuilder result = BufferReaderUtils.getStringBuiderByBufferedReader(reader);
				if (StringUtils.isNotBlank(result))
				{
					final ObjectMapper mapper = new ObjectMapper();
					ConsignmentEventData[] array = mapper.readValue(result.toString(), ConsignmentEventData[].class);
					events = Arrays.asList(array);
				}
			}
			catch (IOException | KeyManagementException | NoSuchAlgorithmException e)
			{
				LOG.error("Get consignment events error", e);
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (IOException e)//NOSONAR
					{
						LOG.error("Close reader error");
					}
				}

				if (connection != null)
				{
					connection.disconnect();
				}
			}
		}

		return events;
	}

	@Override
	public URL getTrackingUrl(final String trackingID)
	{
		final String baseUrl = siteBaseUrlResolutionService.getWebsiteUrlForSite(baseSiteService.getCurrentBaseSite(), true,
				StringUtils.EMPTY);
		final Configuration config = getConfigurationService().getConfiguration();
		final String trackingUrl = config.getString(TRACKING_URL_KEY, StringUtils.EMPTY);
		try
		{
			return new URL(baseUrl + trackingUrl);
		}
		catch (final MalformedURLException e)
		{
			LOG.error("Invalid Tracking URL", e);
		}
		return null;
	}

	@Override
	public int getDeliveryLeadTime(final ConsignmentModel consignment)
	{
		final Configuration config = getConfigurationService().getConfiguration();
		return config.getInt(DELIVERY_LEAD_TIME_KEY, 0);
	}

	protected TrustManager getTrustManager()
	{
		return new ConsignmentX509ExtendedTrustManager();
	}

	protected ConfigurationService getConfigurationService()
	{
		return configurationService;
	}

	@Required
	public void setConfigurationService(final ConfigurationService configurationService)
	{
		this.configurationService = configurationService;
	}

	protected SiteBaseUrlResolutionService getSiteBaseUrlResolutionService()
	{
		return siteBaseUrlResolutionService;
	}

	@Required
	public void setSiteBaseUrlResolutionService(SiteBaseUrlResolutionService siteBaseUrlResolutionService)
	{
		this.siteBaseUrlResolutionService = siteBaseUrlResolutionService;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	@Required
	public void setBaseSiteService(BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}
}
