package sapphire.hcl.com.actions;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordercancel.dao.OrderCancelDao;
import de.hybris.platform.ordercancel.model.OrderCancelConfigModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.util.Config;



/**
 * This example action checks the order against the order cancel configuration in the business process. Skipping this action may result in
 * failure in one of the subsequent steps of the process. The relation between the order and the business process is
 * defined in basecommerce extension through item OrderProcess. Therefore if your business process has to access the
 * order (a typical case), it is recommended to use the OrderProcess as a parentClass instead of the plain
 * BusinessProcess.
 */

public class WaitImmediateCancelOrderAction<T extends BusinessProcessModel>
		extends AbstractAction<T> {
	private static final Logger LOG = Logger
			.getLogger(WaitImmediateCancelOrderAction.class);

	private OrderCancelDao orderCancelDao;
	
	public static enum Transition {
		OK, NOK, CANCELLED, ERROR;
		public static Set<String> getStringValues() {
			Set<String> hashSet = new HashSet<String>();
			for (Transition t : values()) {
				hashSet.add(t.toString());
			}
			return hashSet;
		}
	}

	public Set<String> getTransitions() {
		return Transition.getStringValues();
	}

	@Override
	public final String execute(T process) throws RetryLaterException,
			Exception {
		return executeAction(process).toString();
	}

	public Transition executeAction(final T process) {
		if (process instanceof OrderProcessModel) {
			int waitTime=Config.getInt("sapphiresaporderfulfillment.cancel.wait.time", 0);
			OrderProcessModel orderProcess = (OrderProcessModel) process;
			final OrderModel order = orderProcess.getOrder();
			if (order == null) {
				LOG.error("Missing the order, exiting the process");
				return Transition.ERROR;
			}

			if (OrderStatus.CANCELLED.equals(order.getStatus())
					|| OrderStatus.CANCELLING.equals(order.getStatus())){
				LOG.info("Process [" + process.getCode()+ "] : Order ["+ order.getCode() + "] was cancelled");
				return Transition.CANCELLED;
			}
			
			if(stillWait(order)){
				LOG.info("Process [" + process.getCode()+ "] : Order ["+ order.getCode() + "] in a wait node");
				try{
					Thread.sleep(waitTime);
				}catch(InterruptedException e){
					LOG.error("Sleep Interuppted", e);
				}
				return Transition.NOK;
			}
			return Transition.OK;
		}
		throw  new IllegalStateException ("Process must be of type "+OrderProcessModel.class.getName());
	}
	
	private boolean stillWait(final OrderModel order){
		final long orderPlaceMillis = order.getCreationtime().getTime();
		final long timeToWait = getImmediateCancelWaitingTime() *1000;
		return new Date().getTime() < orderPlaceMillis+timeToWait;
	}
	
	private long getImmediateCancelWaitingTime(){
		return getCancelConfiguration().getQueuedOrderWaitingTime();
	}
	
	private OrderCancelConfigModel getCancelConfiguration(){
		return getOrderCancelDao().getOrderCancelConfiguration();
	}
	/**
	 * Getter for orderCancelDao
	 * 
	 * @return OrderCancelDao
	 */
	public OrderCancelDao getOrderCancelDao() {
		return orderCancelDao;
	}

	/**
	 * Setter for orderCancelDao
	 * 
	 * @param orderCancelDao
	 */
	public void setOrderCancelDao(OrderCancelDao orderCancelDao) {
		this.orderCancelDao = orderCancelDao;
	}

}
