<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="b2b-dashboard" tagdir="/WEB-INF/tags/addons/sapphirecustomerticketaddon/responsive/dashboard" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<div id="dashboard-re">
<div class="text-center" >
    <div class="dashboard">DASHBOARD</div>
</div>
<nav>
  <ul class="nav nav-tabs text-center" id="nav-tab" role="tablist">
    <li class="nav-item nav-text active">
    	<a class="nav-link" id="nav-support-tab" data-toggle="tab" href="#nav-support" role="tab" aria-controls="nav-support" aria-selected="true">SUPPORT TICKETS</a>
    </li>
    <li class="nav-item nav-text">
    	<a class="nav-link" id="nav-history-tab" data-toggle="tab" href="#nav-history" role="tab" aria-controls="nav-history" aria-selected="false">ORDER HISTORY</a>
   	</li>
   
</ul>
  <div class="tab-content" id="nav-tabContent">
  <div class="tab-pane fade active in" id="nav-support" role="tabpanel" aria-labelledby="nav-support-tab">
  
<c:set var="searchUrl" value="/my-account/support-tickets?sort=${searchPageDataForTicket.pagination.sort}"/>

<c:if test="${empty searchPageDataForTicket.results}">
	<div class="account-section-content col-md-6 col-md-push-3 content-empty">
    	<spring:theme code="text.account.supporttickets.noSupporttickets" text="You have no support tickets" />
	</div>
</c:if>

<div class="clearfix visible-md-block visible-lg-block"></div>

<div class="customer-ticketing account-overview-table">
	<c:if test="${not empty searchPageDataForTicket.results}">
		<b2b-dashboard:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowedForTicket}" supportShowAll="${isShowAllAllowedForTicket}" searchPageData="${searchPageDataForTicket}" searchUrl="${searchUrl}"  numberPagesShown="${numberPagesShownForTicket}"/>
		<table class="responsive-table">
            <thead>
                <tr class="responsive-table-head hidden-xs">
                	<th><spring:theme code="text.account.supporttickets.ticketId" text="Ticket ID" /></th>
                    <th><spring:theme code="text.account.supporttickets.subject" text="Subject" /></th>
                    <th><spring:theme code="text.account.supporttickets.dateCreated" text="Date Created" /></th>
                    <th><spring:theme code="text.account.supporttickets.dateUpdated" text="Date Updated" /></th>
                    <th class="supportTicketsTableState"><spring:theme code="text.account.supporttickets.status" text="Status" /></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach items="${searchPageDataForTicket.results}" var="supportTicket">
                	<c:url value="${supportTicket.product.url}" var="productUrl"/>
                    <c:url value="/my-account/support-ticket/${supportTicket.id}" var="myAccountsupportTicketDetailsUrl" />
                    <tr class="responsive-table-item">
                        <td class="hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.supporttickets.ticketId" text="Ticket ID" /></td>
                        <td><a href="${myAccountsupportTicketDetailsUrl}" class="responsive-table-link"><c:out value="${supportTicket.id}" /></a></td>
                        
                        <td class="hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.supporttickets.subject" text="Subject" /></td>
                        <td class="break-word"><a href="${myAccountsupportTicketDetailsUrl}" class="responsive-table-link"><c:out value="${supportTicket.subject}" /></a></td>
                        
                        <td class="hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.supporttickets.dateCreated" text="Date Created" /></td>
                        <td><fmt:formatDate value="${supportTicket.creationDate}" pattern="dd-MM-yy hh:mm a" /></td>
                        
                        <td class="hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.supporttickets.dateUpdated" text="Date Updated" /></td>
                        <td><fmt:formatDate value="${supportTicket.lastModificationDate}" pattern="dd-MM-yy hh:mm a" /></td>
                        
                        <td class="hidden-sm hidden-md hidden-lg"><spring:theme code="text.account.supporttickets.status" text="Status" /></td>
                        <td><spring:message code="ticketstatus.${fn:toUpperCase(supportTicket.status.id)}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
<%--         <nav:pagination top="false" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchUrl}"  numberPagesShown="${numberPagesShown}"/>
 --%>	</c:if>	
	</div>
	</div>




<div class="tab-pane fade" id="nav-history" role="tabpanel" aria-labelledby="nav-history-tab">

<c:set var="searchUrl" value="/my-account/orders?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<b2b-dashboard:dashBoardDetails searchUrl="${searchUrl}" messageKey="text.account.orderHistory.page"></b2b-dashboard:dashBoardDetails>

</div>
</div>
</div>