package sapphire.hcl.com.sap.orderexchange.datahub.inbound.impl;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.sap.orderexchange.constants.DataHubInboundConstants;
import de.hybris.platform.sap.orderexchange.datahub.inbound.DataHubInboundDeliveryHelper;
import de.hybris.platform.sap.orderexchange.datahub.inbound.impl.DefaultDataHubInboundDeliveryHelper;
import de.hybris.platform.warehousing.allocation.AllocationService;
import de.hybris.platform.warehousing.data.sourcing.SourcingResult;
import de.hybris.platform.warehousing.data.sourcing.SourcingResults;
import de.hybris.platform.warehousing.sourcing.SourcingService;

import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;


/**
 * Default Data Hub Inbound helper for delivery related notifications for Sapphire to add sourcing
 */
public class SapphireDefaultDataHubInboundDeliveryHelper extends DefaultDataHubInboundDeliveryHelper
		implements DataHubInboundDeliveryHelper
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SapphireDefaultDataHubInboundDeliveryHelper.class);

	private AllocationService allocationService;
	private SourcingService sourcingService;

	@Override
	protected void processDeliveryCreation(final String warehouseId, final OrderModel order)
	{
		if (order.getConsignments().isEmpty())
		{
			createInventoryAllocationEvent(order);
		}
		getBusinessProcessService().triggerEvent(DataHubInboundConstants.CONSIGNMENT_CREATION_EVENTNAME_PREFIX + order.getCode());
	}



	/**
	 * This method creates inventory allocation event for the consignments when created.
	 *
	 * @param consignment
	 */
	private void createInventoryAllocationEvent(final OrderModel order)
	{
		try
		{
			final SourcingResults results = getSourcingService().sourceOrder(order);
			if (results != null)
			{
				results.getResults().forEach(result -> logSourcingInfo(result));
				LOGGER.info("Before allocation event for the the consignemnts on Order : " + order.getCode());
				final Collection<ConsignmentModel> consignments = getAllocationService().createConsignments(order,
						"cons" + order.getCode(), results);
				LOGGER.info("After allocation event for the the consignemnts on Order : " + order.getCode());
				
				int counter = 0;
				for (final ConsignmentModel consignment : consignments)
				{
					LOGGER.info("consignemnts after allocation : " + consignment.getCode());
					for (final ConsignmentEntryModel consignmentEntry : consignment.getConsignmentEntries())
					{
						consignmentEntry.setSapOrderEntryRowNumber(++counter);
						getModelService().save(consignmentEntry);
						LOGGER.info("consignemnt entries after save for consignemnt : " + consignment.getCode());
					}
					LOGGER.info("Inventory allocation event has been created on Consignment : " + consignment.getCode());

				}
			}
		}
		catch (final Exception e)
		{
			LOGGER.warn("Allocation event cannot  triggered for consignment : ", e);
		}
	}

	private void logSourcingInfo(final SourcingResult result)
	{
		if (result != null)
		{
			LOGGER.info(String.format("Sourcing from Location: %s", result.getWarehouse().getCode()));
			result.getAllocation().forEach((product, qty) -> LOGGER.info(String.format("\tProduct [%s]: %s \tQuantity: '%d'",
					product.getProduct().getCode(), product.getProduct().getName(getSessionLocale()), (long) qty)));

		}
		else
		{
			LOGGER.info("The sourcing result is null");
		}
	}

	protected Locale getSessionLocale()
	{
		return JaloSession.getCurrentSession().getSessionContext().getLocale();
	}

	@Override
	protected void processDeliveryGoodsIssue(final String warehouseId, final String issueDate, final OrderModel order)
	{
		if (order.getConsignments().isEmpty())
		{
			final ConsignmentModel consignment = createConsignmentAndPopulate(warehouseId, order);
			mapDeliveryToConsignment(issueDate, consignment, order);
		}
		else
		{
			for (final ConsignmentModel consignment : order.getConsignments())
			{
				mapDeliveryToConsignment(issueDate, consignment, order);
			}

			LOGGER.info("Recieved good issue notification from SAP for order :" + order.getCode());
		}
		getModelService().saveAll();
		getBusinessProcessService().triggerEvent(DataHubInboundConstants.GOODS_ISSUE_EVENTNAME_PREFIX + order.getCode());
	}

	/**
	 * Getter
	 *
	 * @return
	 */
	protected AllocationService getAllocationService()
	{
		return allocationService;
	}

	/**
	 * Setter
	 *
	 * @param allocationService
	 */
	@Required
	public void setAllocationService(final AllocationService allocationService)
	{
		this.allocationService = allocationService;
	}


	/**
	 * Getter
	 *
	 * @return
	 */
	protected SourcingService getSourcingService()
	{
		return sourcingService;
	}

	/**
	 * Setter
	 *
	 * @param sourcingService
	 */
	@Required
	public void setSourcingService(final SourcingService sourcingService)
	{
		this.sourcingService = sourcingService;
	}

}
