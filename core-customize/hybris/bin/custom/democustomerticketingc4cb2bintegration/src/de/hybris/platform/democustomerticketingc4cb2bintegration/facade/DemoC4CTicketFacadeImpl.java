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
package de.hybris.platform.democustomerticketingc4cb2bintegration.facade;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerticketingc4cintegration.SitePropsHolder;
import de.hybris.platform.customerticketingc4cintegration.constants.Customerticketingc4cintegrationConstants;
import de.hybris.platform.customerticketingc4cintegration.data.DemoODataSingleResponseData;
import de.hybris.platform.customerticketingc4cintegration.data.DemoServiceRequestData;
import de.hybris.platform.customerticketingc4cintegration.data.Note;
import de.hybris.platform.customerticketingc4cintegration.data.ODataListResponseData;
import de.hybris.platform.customerticketingc4cintegration.data.ODataListResultsData;
import de.hybris.platform.customerticketingc4cintegration.data.ServiceRequestData;
import de.hybris.platform.customerticketingc4cintegration.facade.C4CTicketFacadeImpl;
import de.hybris.platform.customerticketingfacades.data.StatusData;
import de.hybris.platform.customerticketingfacades.data.TicketAssociatedData;
import de.hybris.platform.customerticketingfacades.data.TicketCategory;
import de.hybris.platform.customerticketingfacades.data.TicketData;
import de.hybris.platform.democustomerticketingc4cb2bintegration.constants.Democustomerticketingc4cb2bintegrationConstants;
import de.hybris.platform.democustomerticketingc4cb2bintegration.data.D;
import de.hybris.platform.democustomerticketingc4cb2bintegration.data.Result;
import de.hybris.platform.democustomerticketingc4cb2bintegration.data.TestServiceRequestData;
import de.hybris.platform.democustomerticketingc4cb2bintegration.service.impl.DefaultDemocustomerticketingc4cb2bintegrationService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * TicketFacade for c4c integration
 */
public class DemoC4CTicketFacadeImpl extends C4CTicketFacadeImpl
{
	private final static Logger LOGGER = Logger.getLogger(C4CTicketFacadeImpl.class);

	private ObjectMapper jacksonObjectMapper;
	private ObjectMapper jacksonObjectMapperForCreate;

	private Converter<ServiceRequestData, TicketData> ticketConverter;
	private Converter<TicketData, ServiceRequestData> defaultC4CTicketConverter;
	private Converter<DemoServiceRequestData, TicketData> demoTicketConverter;
	private Converter<Result, TicketData> newTicketConverter;
	private Converter<TicketData, DemoServiceRequestData> demoDefaultC4CTicketConverter;
	private Converter<TicketData, Note> updateMessageConverter;

	private RestTemplate restTemplate;

	@Resource
	private SitePropsHolder sitePropsHolder;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "ticket_completed")
	private StatusData completedStatus;

	@Resource(name = "ticket_open")
	private StatusData openStatus;

	private DefaultDemocustomerticketingc4cb2bintegrationService c4cIntegrationService;
	private UserService userService;

	@Override
	public TicketData createTicket(final TicketData ticket)
	{
		Assert.isTrue(StringUtils.isNotBlank(ticket.getSubject()), "Subject can't be empty");
		Assert.isTrue(ticket.getSubject().length() <= 255, "Subject can't be longer than 255 chars");

		LOGGER.info("Sending request to: " + Customerticketingc4cintegrationConstants.URL
				+ Customerticketingc4cintegrationConstants.TICKETING_SUFFIX);

		try
		{
			final String message = ticket.getMessage();
			ticket.setMessage("");
			//setting customerId explicitly and override the customerUid set from the addon
			ticket.setCustomerId(customerFacade.getCurrentCustomer().getCustomerId());

			final HttpHeaders headers = getHttpHeaderUtil().getEnrichedHeaders();
			final HttpEntity<String> entity = new HttpEntity<>(
					jacksonObjectMapperForCreate.writeValueAsString(demoDefaultC4CTicketConverter.convert(ticket)), headers);
			final ResponseEntity<String> result = restTemplate.postForEntity(
					Customerticketingc4cintegrationConstants.URL + Customerticketingc4cintegrationConstants.TICKETING_SUFFIX, entity,
					String.class);

			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			final DemoODataSingleResponseData responseData = jacksonObjectMapperForCreate.readValue(result.getBody(),
					DemoODataSingleResponseData.class);

			final TicketData ticketData = demoTicketConverter.convert(responseData.getD().getResults());
			final TicketData updateTicket = beforeUpdateTicket(message, ticketData);
			updateTicket(updateTicket);
			return ticketData;
		}
		catch (final IOException e)
		{
			LOGGER.warn("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.warn("Can't send request " + e);
		}

		return null; // or throw
	}


	/**
	 *
	 */
	private TicketData beforeUpdateTicket(final String message, final TicketData ticketData)
	{
		final TicketData updateTicket = new TicketData();
		updateTicket.setMessage(message);
		updateTicket.setId(ticketData.getId());
		updateTicket.setObjectID(ticketData.getObjectID());
		updateTicket.setStatus(openStatus);
		return updateTicket;
	}


	@Override
	public TicketData updateTicket(final TicketData ticket)
	{

		Assert.isTrue(StringUtils.isNotBlank(ticket.getMessage()), "Message can't be empty");

		try
		{
			//setting customerId explicitly and override the customerUid set from the addon
			ticket.setCustomerId(customerFacade.getCurrentCustomer().getCustomerId());

			final HttpHeaders updateTicketHeaders = getHttpHeaderUtil().getEnrichedHeaders();

			updateTicketHeaders.set(HttpHeaders.CONTENT_TYPE, Customerticketingc4cintegrationConstants.MULTIPART_MIXED_MODE);

			final HttpHeaders statusUpdateHeaders = getHttpHeaderUtil()
					.addBatchHeaders("PATCH ServiceTicketCollection('" + ticket.getObjectID() + "') HTTP/1.1");

			final HttpHeaders messageUpdateHeaders = getHttpHeaderUtil()
					.addBatchHeaders("POST ServiceTicketCollection('" + ticket.getObjectID() + "')/Notes HTTP/1.1");


			final MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();

			LOGGER.info(demoDefaultC4CTicketConverter.convert(ticket));

			final HttpEntity<String> statusEntity = new HttpEntity<>(
					jacksonObjectMapper.writeValueAsString(demoDefaultC4CTicketConverter.convert(ticket)), statusUpdateHeaders);


			LOGGER.info(updateMessageConverter.convert(ticket));

			final HttpEntity<String> messageEntity = new HttpEntity<>(
					jacksonObjectMapper.writeValueAsString(updateMessageConverter.convert(ticket)), messageUpdateHeaders);
			if (null != ticket.getStatus() && completedStatus.getId().equalsIgnoreCase(ticket.getStatus().getId()))
			{
				if (getTicket(ticket.getId()).getStatus().getId().equals(ticket.getStatus().getId())) // so status doesn't changed
				{
					throw new IllegalArgumentException("You can not add a message to a completed ticket. Please, reopen the ticket");
				}

				parts.add("message", messageEntity);
				parts.add("status", statusEntity);
			}
			else
			{
				parts.add("status", statusEntity);
				parts.add("message", messageEntity);
			}

			final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(parts, updateTicketHeaders);

			final URI uri = UriComponentsBuilder
					.fromHttpUrl(
							Democustomerticketingc4cb2bintegrationConstants.URL + Customerticketingc4cintegrationConstants.BATCH_SUFFIX)
					.build().encode().toUri();


			LOGGER.info("Result uri for status update: " + uri);


			final ResponseEntity<MultiValueMap> result = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
					MultiValueMap.class);


			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			if (result.getBody().containsKey(Customerticketingc4cintegrationConstants.MULTIPART_HAS_ERROR))
			{
				LOGGER.error("Error happend!");
				if (null != result.getBody().get(Customerticketingc4cintegrationConstants.MULTIPART_ERROR_MESSAGE))
				{
					LOGGER.error(result.getBody().get(Customerticketingc4cintegrationConstants.MULTIPART_ERROR_MESSAGE));
				}

				return null;
			}

			return getTicket(ticket.getId());

		}
		catch (final IOException e)
		{
			LOGGER.warn("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.warn("Can't send request " + e);
		}

		return null; // or throw ?

	}


	@Override
	public TicketData getTicket(final String ticketId)
	{
		return getTicketFromNewUrl(ticketId);

	}

	private TicketData getTicketFromOldUrl(final String ticketId)
	{
		validateParameterNotNullStandardMessage("ticketId", ticketId);

		final UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(Democustomerticketingc4cb2bintegrationConstants.URL
						+ Democustomerticketingc4cb2bintegrationConstants.URL_SUFFIX)
				.queryParam(Customerticketingc4cintegrationConstants.FILETR_SUFFIX,
						String.format("CustomerID eq '%s'", customerFacade.getCurrentCustomer().getCustomerId())
								+ String.format("and ID eq '%s'", ticketId))
				.query(Customerticketingc4cintegrationConstants.EXPAND_SUFFIX);

		LOGGER.info("Result uri: " + builder.build().encode().toUri());

		try
		{
			final HttpHeaders headers = getHttpHeaderUtil().getEnrichedHeaders();
			final HttpEntity<String> entity = new HttpEntity<>(headers);

			final ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
					String.class);

			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			final List<TicketData> dataList = jacksonObjectMapper.readValue(result.getBody(), ODataListResponseData.class).getD()
					.getResults().stream().map(ticketConverter::convert).collect(Collectors.toList());

			LOGGER.info(dataList);

			return dataList.isEmpty() ? null : dataList.get(0);
		}
		catch (final IOException e)
		{
			LOGGER.warn("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.warn("Can't send request " + e);
		}

		return null; // or throw
	}

	private TicketData getTicketFromNewUrl(final String ticketId)
	{
		validateParameterNotNullStandardMessage("ticketId", ticketId);

		final UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(Customerticketingc4cintegrationConstants.URL + Customerticketingc4cintegrationConstants.TICKETING_SUFFIX)
				.queryParam(Customerticketingc4cintegrationConstants.FILETR_SUFFIX,
						String.format("CustomerID eq '%s'", customerFacade.getCurrentCustomer().getCustomerId())
								+ String.format("and ID eq '%s'", ticketId));

		LOGGER.info("Result uri: " + builder.build().encode().toUri());

		try
		{
			final HttpHeaders headers = getHttpHeaderUtil().getEnrichedHeaders();
			final HttpEntity<String> entity = new HttpEntity<>(headers);

			final ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
					String.class);

			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			final List<TicketData> dataList = jacksonObjectMapper.readValue(result.getBody(), TestServiceRequestData.class).getD()
					.getResults().stream().map(newTicketConverter::convert).collect(Collectors.toList());

			LOGGER.info(dataList);

			final TicketData ticketData = dataList.isEmpty() ? null : dataList.get(0);
			final TicketData ticket = getTicketFromOldUrl(ticketId);

			if (null != ticket && null != ticketData)
			{
				ticketData.setTicketEvents(ticket.getTicketEvents());
				ticketData.setObjectID(ticket.getId());
				//ticket.setProductId(ticketData.getProductId());
				//ticket.setProduct(ticketData.getProduct());
			}

			return ticketData;
		}
		catch (final IOException e)
		{
			LOGGER.warn("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.warn("Can't send request " + e);
		}

		return null; // or throw
	}

	@Override
	public SearchPageData<TicketData> getTickets(final PageableData pageableData)
	{

		final boolean IS_OOTB_URL = Config.getBoolean("customerticketingc4cintegration.c4c-ticket-ootb-url", true);
		if (IS_OOTB_URL)
		{
			return getTicketsFromOldUrl(pageableData);
		}
		else
		{
			return getTicketsFromNewUrl(pageableData);
		}

	}

	private SearchPageData<TicketData> getTicketsFromNewUrl(final PageableData pageableData)
	{
		final int skip = pageableData.getPageSize() * pageableData.getCurrentPage();
		final int top = pageableData.getPageSize();
		final String sorting = StringUtils.isNotBlank(pageableData.getSort()) ? pageableData.getSort()
				: Customerticketingc4cintegrationConstants.ORDER_DEFAULT_VALUE;
		LOGGER.info("Sorting: " + sorting);
		final UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(Customerticketingc4cintegrationConstants.URL + Customerticketingc4cintegrationConstants.TICKETING_SUFFIX)
				.queryParam(Customerticketingc4cintegrationConstants.FILETR_SUFFIX,
						String.format("CustomerID eq '%s'", customerFacade.getCurrentCustomer().getCustomerId()))
				.query(Customerticketingc4cintegrationConstants.ORDER_BY_SUFFIX + sorting + " desc")
				.queryParam(Customerticketingc4cintegrationConstants.PAGING_SKIP_SUFFIX, Integer.valueOf(skip))
				.queryParam(Customerticketingc4cintegrationConstants.PAGING_TOP_SUFFIX, Integer.valueOf(top))
				.query(Customerticketingc4cintegrationConstants.PAGING_COUNT_SUFFIX);

		LOGGER.info("Result uri: " + builder.build().encode().toUri());
		try
		{
			final HttpHeaders headers = getHttpHeaderUtil().getEnrichedHeaders();
			final HttpEntity<String> entity = new HttpEntity<>(headers);

			final ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
					String.class);

			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			final D oDataListResultsData = jacksonObjectMapper.readValue(result.getBody(), TestServiceRequestData.class).getD();

			LOGGER.debug(oDataListResultsData.getCount());
			return getC4cBaseFacade().convertPageData(oDataListResultsData.getResults(), newTicketConverter, pageableData,
					Integer.parseInt(oDataListResultsData.getCount()));
		}
		catch (final IOException e)
		{
			LOGGER.error("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.error("Can't send request " + e);
		}

		return getC4cBaseFacade().convertPageData(Collections.EMPTY_LIST, newTicketConverter, pageableData, 0);
	}

	private SearchPageData<TicketData> getTicketsFromOldUrl(final PageableData pageableData)
	{
		final int skip = pageableData.getPageSize() * pageableData.getCurrentPage();
		final int top = pageableData.getPageSize();
		final String sorting = StringUtils.isNotBlank(pageableData.getSort()) ? pageableData.getSort()
				: Customerticketingc4cintegrationConstants.ORDER_DEFAULT_VALUE;
		LOGGER.info("Sorting: " + sorting);
		final UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(Democustomerticketingc4cb2bintegrationConstants.URL
						+ Democustomerticketingc4cb2bintegrationConstants.URL_SUFFIX)
				.queryParam(Customerticketingc4cintegrationConstants.FILETR_SUFFIX,
						String.format("CustomerID eq '%s'", customerFacade.getCurrentCustomer().getCustomerId()))
				.query(Customerticketingc4cintegrationConstants.ORDER_BY_SUFFIX + sorting + " desc")
				.queryParam(Customerticketingc4cintegrationConstants.PAGING_SKIP_SUFFIX, Integer.valueOf(skip))
				.queryParam(Customerticketingc4cintegrationConstants.PAGING_TOP_SUFFIX, Integer.valueOf(top))
				.query(Customerticketingc4cintegrationConstants.PAGING_COUNT_SUFFIX)
				.query(Customerticketingc4cintegrationConstants.EXPAND_SUFFIX);
		LOGGER.info("Result uri: " + builder.build().encode().toUri());
		try
		{
			final HttpHeaders headers = getHttpHeaderUtil().getEnrichedHeaders();
			final HttpEntity<String> entity = new HttpEntity<>(headers);

			final ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
					String.class);

			LOGGER.info("Response status: " + result.getStatusCode());
			LOGGER.info("Response headers: " + result.getHeaders());
			LOGGER.info("Response body: " + result.getBody());

			final ODataListResultsData oDataListResultsData = jacksonObjectMapper
					.readValue(result.getBody(), ODataListResponseData.class).getD();

			LOGGER.info(oDataListResultsData.get__count());
			return getC4cBaseFacade().convertPageData(oDataListResultsData.getResults(), ticketConverter, pageableData,
					Integer.parseInt(oDataListResultsData.get__count()));
		}
		catch (final IOException e)
		{
			LOGGER.error("Can't convert ticketData: " + e);
		}
		catch (final RestClientException e)
		{
			LOGGER.error("Can't send request " + e);
		}

		return getC4cBaseFacade().convertPageData(Collections.EMPTY_LIST, ticketConverter, pageableData, 0);
	}

	@Override
	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

	@Override
	public void setRestTemplate(final RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	@Override
	public Converter<ServiceRequestData, TicketData> getTicketConverter()
	{
		return ticketConverter;
	}

	@Override
	public void setTicketConverter(final Converter<ServiceRequestData, TicketData> ticketConverter)
	{
		this.ticketConverter = ticketConverter;
	}

	@Override
	public ObjectMapper getJacksonObjectMapper()
	{
		return jacksonObjectMapper;
	}

	@Override
	public void setJacksonObjectMapper(final ObjectMapper jacksonObjectMapper)
	{
		this.jacksonObjectMapper = jacksonObjectMapper;
	}

	@Override
	public Converter<TicketData, ServiceRequestData> getDefaultC4CTicketConverter()
	{
		return defaultC4CTicketConverter;
	}

	@Override
	public void setDefaultC4CTicketConverter(final Converter<TicketData, ServiceRequestData> defaultC4CTicketConverter)
	{
		this.defaultC4CTicketConverter = defaultC4CTicketConverter;
	}

	@Override
	public Converter<TicketData, Note> getUpdateMessageConverter()
	{
		return updateMessageConverter;
	}

	@Override
	public void setUpdateMessageConverter(final Converter<TicketData, Note> updateMessageConverter)
	{
		this.updateMessageConverter = updateMessageConverter;
	}

	@Override
	public Map<String, List<TicketAssociatedData>> getAssociatedToObjects()
	{
		return new HashMap<String, List<TicketAssociatedData>>();
	}

	@Override
	public List<TicketCategory> getTicketCategories()
	{
		return new ArrayList<TicketCategory>();
	}

	public HashMap<String, String> getAssociatedProducts()
	{
		//final Set<String> products = new TreeSet<String>();
		final HashMap<String, String> products = new HashMap<String, String>();

		final UserModel user = userService.getCurrentUser();
		final List<OrderModel> orders = c4cIntegrationService.findOrdersByUser(user);
		for (final OrderModel order : orders)
		{
			for (final AbstractOrderEntryModel entry : order.getEntries())
			{
				//products.add(entry.getProduct().getCode());
				final String productCode = entry.getParentEquipment();
				try
				{
					if (StringUtils.isNotEmpty(productCode))
					{
						//target.setProductId(productCode);
						final List<ProductOption> extraOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
								ProductOption.VARIANT_MATRIX_URL, ProductOption.VARIANT_MATRIX_MEDIA);

						final ProductData productData = productFacade.getProductForCodeAndOptions(productCode, extraOptions);
						//target.setProduct(productData);
						if (null != productData && null != productData.getCode() && null != productData.getName())
						{
							products.put(productData.getCode(), productData.getName());
						}
					}
				}
				catch (final Exception e)
				{
					LOGGER.warn(" ProductId: " + productCode + "does not exist. Exception details: " + e.getMessage());
					//Ignore the exception
				}

			}
		}
		return products;
	}

	/**
	 * Getter
	 *
	 * @return the c4cIntegrationService
	 */
	public DefaultDemocustomerticketingc4cb2bintegrationService getC4cIntegrationService()
	{
		return c4cIntegrationService;
	}

	/**
	 * Setter
	 *
	 * @param c4cIntegrationService
	 *           the c4cIntegrationService to set
	 */
	public void setC4cIntegrationService(final DefaultDemocustomerticketingc4cb2bintegrationService c4cIntegrationService)
	{
		this.c4cIntegrationService = c4cIntegrationService;
	}

	/**
	 * Getter
	 *
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * Setter
	 *
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the demoDefaultC4CTicketConverter
	 */
	public Converter<TicketData, DemoServiceRequestData> getDemoDefaultC4CTicketConverter()
	{
		return demoDefaultC4CTicketConverter;
	}

	/**
	 * @param demoDefaultC4CTicketConverter
	 *           the demoDefaultC4CTicketConverter to set
	 */
	public void setDemoDefaultC4CTicketConverter(final Converter<TicketData, DemoServiceRequestData> demoDefaultC4CTicketConverter)
	{
		this.demoDefaultC4CTicketConverter = demoDefaultC4CTicketConverter;
	}

	/**
	 * @return the demoTicketConverter
	 */
	public Converter<DemoServiceRequestData, TicketData> getDemoTicketConverter()
	{
		return demoTicketConverter;
	}

	/**
	 * @param demoTicketConverter
	 *           the demoTicketConverter to set
	 */
	public void setDemoTicketConverter(final Converter<DemoServiceRequestData, TicketData> demoTicketConverter)
	{
		this.demoTicketConverter = demoTicketConverter;
	}


	/**
	 * @return the newTicketConverter
	 */
	public Converter<Result, TicketData> getNewTicketConverter()
	{
		return newTicketConverter;
	}


	/**
	 * @param newTicketConverter
	 *           the newTicketConverter to set
	 */
	public void setNewTicketConverter(final Converter<Result, TicketData> newTicketConverter)
	{
		this.newTicketConverter = newTicketConverter;
	}


	/**
	 * Getter
	 *
	 * @return the jacksonObjectMapperForCreate
	 */
	public ObjectMapper getJacksonObjectMapperForCreate()
	{
		return jacksonObjectMapperForCreate;
	}


	/**
	 * Setter
	 *
	 * @param jacksonObjectMapperForCreate
	 *           the jacksonObjectMapperForCreate to set
	 */
	public void setJacksonObjectMapperForCreate(final ObjectMapper jacksonObjectMapperForCreate)
	{
		this.jacksonObjectMapperForCreate = jacksonObjectMapperForCreate;
	}

	@Override
	protected DemoC4CBaseFacade getC4cBaseFacade()
	{
		return ((DemoC4CBaseFacade) super.getC4cBaseFacade());
	}

}
