
package de.hybris.platform.democustomerticketingc4cb2bintegration.data;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "__metadata",
    "ChangedBy",
    "CustomerMainContactPartyID",
    "RequestAssignmentStatusCode",
    "CompletedOnDate",
    "ObjectID",
    "ActivityServiceIssueCategoryID",
    "ApprovalStatusCode",
    "CauseServiceIssueCategoryID",
    "CompletionDueDate",
    "ContractID",
    "CreatedBy",
    "CreationDate",
    "Customer",
    "CustomerID",
    "DataOriginTypeCode",
    "ID",
    "IncidentCategoryName",
    "ActivityCategoryName",
    "CauseCategoryName",
    "IncidentServiceIssueCategoryID",
    "InitialReviewDueDate",
    "InstallationPointID",
    "InstalledBaseID",
    "ItemListServiceRequestExecutionLifeCycleStatusCode",
    "LastChangeDate",
    "LastResponseOnDate",
    "Name",
    "NextResponseDueDate",
    "ObjectCategoryName",
    "ObjectServiceIssueCategoryID",
    "Partner",
    "PartnerID",
    "ProcessingTypeCode",
    "ProductID",
    "ReferenceDate",
    "ReportedForEmail",
    "ReportedForPartyID",
    "ReporterEmail",
    "ReporterPartyID",
    "RequestedEnd",
    "RequestedStart",
    "RoleCode",
    "SalesTerritoryID",
    "SerialID",
    "ServiceCategoryName",
    "ServiceIssueCategoryID",
    "ServiceLevelAgreement",
    "ServicePriorityCode",
    "ServiceRequestClassificationCode",
    "ServiceRequestLifeCycleStatusCode",
    "ServiceTechnician",
    "ServiceTechnicianTeam",
    "WarrantyDescription",
    "WarrantyFrom",
    "WarrantyTo",
    "ServiceAndSupportTeam",
    "ServiceRequestUserLifeCycleStatusCode",
    "AssignedTo",
    "EscalationStatus",
    "AssignedToName",
    "ProductCategoryDescription",
    "InitialResponseDate",
    "Contact",
    "ParentServiceRequest",
    "ETag",
    "CreationDateTime",
    "LastChangeDateTime",
    "RequestedStartTimeZoneCode",
    "RequestedEndTimeZoneCode",
    "RequestAssignmentStatusCodeText",
    "ApprovalStatusCodeText",
    "DataOriginTypeCodeText",
    "ItemListServiceRequestExecutionLifeCycleStatusCodeText",
    "ProcessingTypeCodeText",
    "RoleCodeText",
    "ServicePriorityCodeText",
    "ServiceRequestClassificationCodeText",
    "ServiceRequestLifeCycleStatusCodeText",
    "ServiceRequestUserLifeCycleStatusCodeText",
    "EscalationStatusText",
    "RequestedStartTimeZoneCodeText",
    "RequestedEndTimeZoneCodeText",
    "ServiceRequestItem",
    "ServiceRequestAttachmentFolder",
    "ServiceRequestBusinessTransactionDocumentReference",
    "ServiceRequestDescription",
    "ServicePointLocationAddress",
    "ServiceRequestHistoricalVersion"
})
public class Result {

    @JsonProperty("__metadata")
    private Metadata metadata;
    @JsonProperty("ChangedBy")
    private String changedBy;
    @JsonProperty("CustomerMainContactPartyID")
    private String customerMainContactPartyID;
    @JsonProperty("RequestAssignmentStatusCode")
    private String requestAssignmentStatusCode;
    @JsonProperty("CompletedOnDate")
    private String completedOnDate;
    @JsonProperty("ObjectID")
    private String objectID;
    @JsonProperty("ActivityServiceIssueCategoryID")
    private String activityServiceIssueCategoryID;
    @JsonProperty("ApprovalStatusCode")
    private String approvalStatusCode;
    @JsonProperty("CauseServiceIssueCategoryID")
    private String causeServiceIssueCategoryID;
    @JsonProperty("CompletionDueDate")
    private String completionDueDate;
    @JsonProperty("ContractID")
    private String contractID;
    @JsonProperty("CreatedBy")
    private String createdBy;
    @JsonProperty("CreationDate")
    private String creationDate;
    @JsonProperty("Customer")
    private String customer;
    @JsonProperty("CustomerID")
    private String customerID;
    @JsonProperty("DataOriginTypeCode")
    private String dataOriginTypeCode;
    @JsonProperty("ID")
    private String iD;
    @JsonProperty("IncidentCategoryName")
    private IncidentCategoryName incidentCategoryName;
    @JsonProperty("ActivityCategoryName")
    private ActivityCategoryName activityCategoryName;
    @JsonProperty("CauseCategoryName")
    private CauseCategoryName causeCategoryName;
    @JsonProperty("IncidentServiceIssueCategoryID")
    private String incidentServiceIssueCategoryID;
    @JsonProperty("InitialReviewDueDate")
    private String initialReviewDueDate;
    @JsonProperty("InstallationPointID")
    private String installationPointID;
    @JsonProperty("InstalledBaseID")
    private String installedBaseID;
    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCode")
    private String itemListServiceRequestExecutionLifeCycleStatusCode;
    @JsonProperty("LastChangeDate")
    private String lastChangeDate;
    @JsonProperty("LastResponseOnDate")
    private String lastResponseOnDate;
    @JsonProperty("Name")
    private Name name;
    @JsonProperty("NextResponseDueDate")
    private Object nextResponseDueDate;
    @JsonProperty("ObjectCategoryName")
    private ObjectCategoryName objectCategoryName;
    @JsonProperty("ObjectServiceIssueCategoryID")
    private String objectServiceIssueCategoryID;
    @JsonProperty("Partner")
    private String partner;
    @JsonProperty("PartnerID")
    private String partnerID;
    @JsonProperty("ProcessingTypeCode")
    private String processingTypeCode;
    @JsonProperty("ProductID")
    private String productID;
    @JsonProperty("ReferenceDate")
    private Object referenceDate;
    @JsonProperty("ReportedForEmail")
    private String reportedForEmail;
    @JsonProperty("ReportedForPartyID")
    private String reportedForPartyID;
    @JsonProperty("ReporterEmail")
    private String reporterEmail;
    @JsonProperty("ReporterPartyID")
    private String reporterPartyID;
    @JsonProperty("RequestedEnd")
    private String requestedEnd;
    @JsonProperty("RequestedStart")
    private String requestedStart;
    @JsonProperty("RoleCode")
    private String roleCode;
    @JsonProperty("SalesTerritoryID")
    private String salesTerritoryID;
    @JsonProperty("SerialID")
    private String serialID;
    @JsonProperty("ServiceCategoryName")
    private ServiceCategoryName serviceCategoryName;
    @JsonProperty("ServiceIssueCategoryID")
    private String serviceIssueCategoryID;
    @JsonProperty("ServiceLevelAgreement")
    private String serviceLevelAgreement;
    @JsonProperty("ServicePriorityCode")
    private String servicePriorityCode;
    @JsonProperty("ServiceRequestClassificationCode")
    private String serviceRequestClassificationCode;
    @JsonProperty("ServiceRequestLifeCycleStatusCode")
    private String serviceRequestLifeCycleStatusCode;
    @JsonProperty("ServiceTechnician")
    private String serviceTechnician;
    @JsonProperty("ServiceTechnicianTeam")
    private String serviceTechnicianTeam;
    @JsonProperty("WarrantyDescription")
    private String warrantyDescription;
    @JsonProperty("WarrantyFrom")
    private Object warrantyFrom;
    @JsonProperty("WarrantyTo")
    private Object warrantyTo;
    @JsonProperty("ServiceAndSupportTeam")
    private String serviceAndSupportTeam;
    @JsonProperty("ServiceRequestUserLifeCycleStatusCode")
    private String serviceRequestUserLifeCycleStatusCode;
    @JsonProperty("AssignedTo")
    private String assignedTo;
    @JsonProperty("EscalationStatus")
    private String escalationStatus;
    @JsonProperty("AssignedToName")
    private AssignedToName assignedToName;
    @JsonProperty("ProductCategoryDescription")
    private String productCategoryDescription;
    @JsonProperty("InitialResponseDate")
    private String initialResponseDate;
    @JsonProperty("Contact")
    private String contact;
    @JsonProperty("ParentServiceRequest")
    private String parentServiceRequest;
    @JsonProperty("ETag")
    private String eTag;
    @JsonProperty("CreationDateTime")
    private String creationDateTime;
    @JsonProperty("LastChangeDateTime")
    private String lastChangeDateTime;
    @JsonProperty("RequestedStartTimeZoneCode")
    private String requestedStartTimeZoneCode;
    @JsonProperty("RequestedEndTimeZoneCode")
    private String requestedEndTimeZoneCode;
    @JsonProperty("RequestAssignmentStatusCodeText")
    private String requestAssignmentStatusCodeText;
    @JsonProperty("ApprovalStatusCodeText")
    private String approvalStatusCodeText;
    @JsonProperty("DataOriginTypeCodeText")
    private String dataOriginTypeCodeText;
    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCodeText")
    private String itemListServiceRequestExecutionLifeCycleStatusCodeText;
    @JsonProperty("ProcessingTypeCodeText")
    private String processingTypeCodeText;
    @JsonProperty("RoleCodeText")
    private String roleCodeText;
    @JsonProperty("ServicePriorityCodeText")
    private String servicePriorityCodeText;
    @JsonProperty("ServiceRequestClassificationCodeText")
    private String serviceRequestClassificationCodeText;
    @JsonProperty("ServiceRequestLifeCycleStatusCodeText")
    private String serviceRequestLifeCycleStatusCodeText;
    @JsonProperty("ServiceRequestUserLifeCycleStatusCodeText")
    private String serviceRequestUserLifeCycleStatusCodeText;
    @JsonProperty("EscalationStatusText")
    private String escalationStatusText;
    @JsonProperty("RequestedStartTimeZoneCodeText")
    private String requestedStartTimeZoneCodeText;
    @JsonProperty("RequestedEndTimeZoneCodeText")
    private String requestedEndTimeZoneCodeText;
    @JsonProperty("ServiceRequestItem")
    private ServiceRequestItem serviceRequestItem;
    @JsonProperty("ServiceRequestAttachmentFolder")
    private ServiceRequestAttachmentFolder serviceRequestAttachmentFolder;
    @JsonProperty("ServiceRequestBusinessTransactionDocumentReference")
    private ServiceRequestBusinessTransactionDocumentReference serviceRequestBusinessTransactionDocumentReference;
    @JsonProperty("ServiceRequestDescription")
    private ServiceRequestDescription serviceRequestDescription;
    @JsonProperty("ServicePointLocationAddress")
    private ServicePointLocationAddress servicePointLocationAddress;
    @JsonProperty("ServiceRequestHistoricalVersion")
    private ServiceRequestHistoricalVersion serviceRequestHistoricalVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("__metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("__metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("ChangedBy")
    public String getChangedBy() {
        return changedBy;
    }

    @JsonProperty("ChangedBy")
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @JsonProperty("CustomerMainContactPartyID")
    public String getCustomerMainContactPartyID() {
        return customerMainContactPartyID;
    }

    @JsonProperty("CustomerMainContactPartyID")
    public void setCustomerMainContactPartyID(String customerMainContactPartyID) {
        this.customerMainContactPartyID = customerMainContactPartyID;
    }

    @JsonProperty("RequestAssignmentStatusCode")
    public String getRequestAssignmentStatusCode() {
        return requestAssignmentStatusCode;
    }

    @JsonProperty("RequestAssignmentStatusCode")
    public void setRequestAssignmentStatusCode(String requestAssignmentStatusCode) {
        this.requestAssignmentStatusCode = requestAssignmentStatusCode;
    }

    @JsonProperty("CompletedOnDate")
    public String getCompletedOnDate() {
        return completedOnDate;
    }

    @JsonProperty("CompletedOnDate")
    public void setCompletedOnDate(String completedOnDate) {
        this.completedOnDate = completedOnDate;
    }

    @JsonProperty("ObjectID")
    public String getObjectID() {
        return objectID;
    }

    @JsonProperty("ObjectID")
    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    @JsonProperty("ActivityServiceIssueCategoryID")
    public String getActivityServiceIssueCategoryID() {
        return activityServiceIssueCategoryID;
    }

    @JsonProperty("ActivityServiceIssueCategoryID")
    public void setActivityServiceIssueCategoryID(String activityServiceIssueCategoryID) {
        this.activityServiceIssueCategoryID = activityServiceIssueCategoryID;
    }

    @JsonProperty("ApprovalStatusCode")
    public String getApprovalStatusCode() {
        return approvalStatusCode;
    }

    @JsonProperty("ApprovalStatusCode")
    public void setApprovalStatusCode(String approvalStatusCode) {
        this.approvalStatusCode = approvalStatusCode;
    }

    @JsonProperty("CauseServiceIssueCategoryID")
    public String getCauseServiceIssueCategoryID() {
        return causeServiceIssueCategoryID;
    }

    @JsonProperty("CauseServiceIssueCategoryID")
    public void setCauseServiceIssueCategoryID(String causeServiceIssueCategoryID) {
        this.causeServiceIssueCategoryID = causeServiceIssueCategoryID;
    }

    @JsonProperty("CompletionDueDate")
    public String getCompletionDueDate() {
        return completionDueDate;
    }

    @JsonProperty("CompletionDueDate")
    public void setCompletionDueDate(String completionDueDate) {
        this.completionDueDate = completionDueDate;
    }

    @JsonProperty("ContractID")
    public String getContractID() {
        return contractID;
    }

    @JsonProperty("ContractID")
    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    @JsonProperty("CreatedBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("CreatedBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("CreationDate")
    public String getCreationDate() {
        return creationDate;
    }

    @JsonProperty("CreationDate")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("Customer")
    public String getCustomer() {
        return customer;
    }

    @JsonProperty("Customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @JsonProperty("CustomerID")
    public String getCustomerID() {
        return customerID;
    }

    @JsonProperty("CustomerID")
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @JsonProperty("DataOriginTypeCode")
    public String getDataOriginTypeCode() {
        return dataOriginTypeCode;
    }

    @JsonProperty("DataOriginTypeCode")
    public void setDataOriginTypeCode(String dataOriginTypeCode) {
        this.dataOriginTypeCode = dataOriginTypeCode;
    }

    @JsonProperty("ID")
    public String getID() {
        return iD;
    }

    @JsonProperty("ID")
    public void setID(String iD) {
        this.iD = iD;
    }

    @JsonProperty("IncidentCategoryName")
    public IncidentCategoryName getIncidentCategoryName() {
        return incidentCategoryName;
    }

    @JsonProperty("IncidentCategoryName")
    public void setIncidentCategoryName(IncidentCategoryName incidentCategoryName) {
        this.incidentCategoryName = incidentCategoryName;
    }

    @JsonProperty("ActivityCategoryName")
    public ActivityCategoryName getActivityCategoryName() {
        return activityCategoryName;
    }

    @JsonProperty("ActivityCategoryName")
    public void setActivityCategoryName(ActivityCategoryName activityCategoryName) {
        this.activityCategoryName = activityCategoryName;
    }

    @JsonProperty("CauseCategoryName")
    public CauseCategoryName getCauseCategoryName() {
        return causeCategoryName;
    }

    @JsonProperty("CauseCategoryName")
    public void setCauseCategoryName(CauseCategoryName causeCategoryName) {
        this.causeCategoryName = causeCategoryName;
    }

    @JsonProperty("IncidentServiceIssueCategoryID")
    public String getIncidentServiceIssueCategoryID() {
        return incidentServiceIssueCategoryID;
    }

    @JsonProperty("IncidentServiceIssueCategoryID")
    public void setIncidentServiceIssueCategoryID(String incidentServiceIssueCategoryID) {
        this.incidentServiceIssueCategoryID = incidentServiceIssueCategoryID;
    }

    @JsonProperty("InitialReviewDueDate")
    public String getInitialReviewDueDate() {
        return initialReviewDueDate;
    }

    @JsonProperty("InitialReviewDueDate")
    public void setInitialReviewDueDate(String initialReviewDueDate) {
        this.initialReviewDueDate = initialReviewDueDate;
    }

    @JsonProperty("InstallationPointID")
    public String getInstallationPointID() {
        return installationPointID;
    }

    @JsonProperty("InstallationPointID")
    public void setInstallationPointID(String installationPointID) {
        this.installationPointID = installationPointID;
    }

    @JsonProperty("InstalledBaseID")
    public String getInstalledBaseID() {
        return installedBaseID;
    }

    @JsonProperty("InstalledBaseID")
    public void setInstalledBaseID(String installedBaseID) {
        this.installedBaseID = installedBaseID;
    }

    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCode")
    public String getItemListServiceRequestExecutionLifeCycleStatusCode() {
        return itemListServiceRequestExecutionLifeCycleStatusCode;
    }

    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCode")
    public void setItemListServiceRequestExecutionLifeCycleStatusCode(String itemListServiceRequestExecutionLifeCycleStatusCode) {
        this.itemListServiceRequestExecutionLifeCycleStatusCode = itemListServiceRequestExecutionLifeCycleStatusCode;
    }

    @JsonProperty("LastChangeDate")
    public String getLastChangeDate() {
        return lastChangeDate;
    }

    @JsonProperty("LastChangeDate")
    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    @JsonProperty("LastResponseOnDate")
    public String getLastResponseOnDate() {
        return lastResponseOnDate;
    }

    @JsonProperty("LastResponseOnDate")
    public void setLastResponseOnDate(String lastResponseOnDate) {
        this.lastResponseOnDate = lastResponseOnDate;
    }

    @JsonProperty("Name")
    public Name getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(Name name) {
        this.name = name;
    }

    @JsonProperty("NextResponseDueDate")
    public Object getNextResponseDueDate() {
        return nextResponseDueDate;
    }

    @JsonProperty("NextResponseDueDate")
    public void setNextResponseDueDate(Object nextResponseDueDate) {
        this.nextResponseDueDate = nextResponseDueDate;
    }

    @JsonProperty("ObjectCategoryName")
    public ObjectCategoryName getObjectCategoryName() {
        return objectCategoryName;
    }

    @JsonProperty("ObjectCategoryName")
    public void setObjectCategoryName(ObjectCategoryName objectCategoryName) {
        this.objectCategoryName = objectCategoryName;
    }

    @JsonProperty("ObjectServiceIssueCategoryID")
    public String getObjectServiceIssueCategoryID() {
        return objectServiceIssueCategoryID;
    }

    @JsonProperty("ObjectServiceIssueCategoryID")
    public void setObjectServiceIssueCategoryID(String objectServiceIssueCategoryID) {
        this.objectServiceIssueCategoryID = objectServiceIssueCategoryID;
    }

    @JsonProperty("Partner")
    public String getPartner() {
        return partner;
    }

    @JsonProperty("Partner")
    public void setPartner(String partner) {
        this.partner = partner;
    }

    @JsonProperty("PartnerID")
    public String getPartnerID() {
        return partnerID;
    }

    @JsonProperty("PartnerID")
    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    @JsonProperty("ProcessingTypeCode")
    public String getProcessingTypeCode() {
        return processingTypeCode;
    }

    @JsonProperty("ProcessingTypeCode")
    public void setProcessingTypeCode(String processingTypeCode) {
        this.processingTypeCode = processingTypeCode;
    }

    @JsonProperty("ProductID")
    public String getProductID() {
        return productID;
    }

    @JsonProperty("ProductID")
    public void setProductID(String productID) {
        this.productID = productID;
    }

    @JsonProperty("ReferenceDate")
    public Object getReferenceDate() {
        return referenceDate;
    }

    @JsonProperty("ReferenceDate")
    public void setReferenceDate(Object referenceDate) {
        this.referenceDate = referenceDate;
    }

    @JsonProperty("ReportedForEmail")
    public String getReportedForEmail() {
        return reportedForEmail;
    }

    @JsonProperty("ReportedForEmail")
    public void setReportedForEmail(String reportedForEmail) {
        this.reportedForEmail = reportedForEmail;
    }

    @JsonProperty("ReportedForPartyID")
    public String getReportedForPartyID() {
        return reportedForPartyID;
    }

    @JsonProperty("ReportedForPartyID")
    public void setReportedForPartyID(String reportedForPartyID) {
        this.reportedForPartyID = reportedForPartyID;
    }

    @JsonProperty("ReporterEmail")
    public String getReporterEmail() {
        return reporterEmail;
    }

    @JsonProperty("ReporterEmail")
    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    @JsonProperty("ReporterPartyID")
    public String getReporterPartyID() {
        return reporterPartyID;
    }

    @JsonProperty("ReporterPartyID")
    public void setReporterPartyID(String reporterPartyID) {
        this.reporterPartyID = reporterPartyID;
    }

    @JsonProperty("RequestedEnd")
    public String getRequestedEnd() {
        return requestedEnd;
    }

    @JsonProperty("RequestedEnd")
    public void setRequestedEnd(String requestedEnd) {
        this.requestedEnd = requestedEnd;
    }

    @JsonProperty("RequestedStart")
    public String getRequestedStart() {
        return requestedStart;
    }

    @JsonProperty("RequestedStart")
    public void setRequestedStart(String requestedStart) {
        this.requestedStart = requestedStart;
    }

    @JsonProperty("RoleCode")
    public String getRoleCode() {
        return roleCode;
    }

    @JsonProperty("RoleCode")
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @JsonProperty("SalesTerritoryID")
    public String getSalesTerritoryID() {
        return salesTerritoryID;
    }

    @JsonProperty("SalesTerritoryID")
    public void setSalesTerritoryID(String salesTerritoryID) {
        this.salesTerritoryID = salesTerritoryID;
    }

    @JsonProperty("SerialID")
    public String getSerialID() {
        return serialID;
    }

    @JsonProperty("SerialID")
    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    @JsonProperty("ServiceCategoryName")
    public ServiceCategoryName getServiceCategoryName() {
        return serviceCategoryName;
    }

    @JsonProperty("ServiceCategoryName")
    public void setServiceCategoryName(ServiceCategoryName serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    @JsonProperty("ServiceIssueCategoryID")
    public String getServiceIssueCategoryID() {
        return serviceIssueCategoryID;
    }

    @JsonProperty("ServiceIssueCategoryID")
    public void setServiceIssueCategoryID(String serviceIssueCategoryID) {
        this.serviceIssueCategoryID = serviceIssueCategoryID;
    }

    @JsonProperty("ServiceLevelAgreement")
    public String getServiceLevelAgreement() {
        return serviceLevelAgreement;
    }

    @JsonProperty("ServiceLevelAgreement")
    public void setServiceLevelAgreement(String serviceLevelAgreement) {
        this.serviceLevelAgreement = serviceLevelAgreement;
    }

    @JsonProperty("ServicePriorityCode")
    public String getServicePriorityCode() {
        return servicePriorityCode;
    }

    @JsonProperty("ServicePriorityCode")
    public void setServicePriorityCode(String servicePriorityCode) {
        this.servicePriorityCode = servicePriorityCode;
    }

    @JsonProperty("ServiceRequestClassificationCode")
    public String getServiceRequestClassificationCode() {
        return serviceRequestClassificationCode;
    }

    @JsonProperty("ServiceRequestClassificationCode")
    public void setServiceRequestClassificationCode(String serviceRequestClassificationCode) {
        this.serviceRequestClassificationCode = serviceRequestClassificationCode;
    }

    @JsonProperty("ServiceRequestLifeCycleStatusCode")
    public String getServiceRequestLifeCycleStatusCode() {
        return serviceRequestLifeCycleStatusCode;
    }

    @JsonProperty("ServiceRequestLifeCycleStatusCode")
    public void setServiceRequestLifeCycleStatusCode(String serviceRequestLifeCycleStatusCode) {
        this.serviceRequestLifeCycleStatusCode = serviceRequestLifeCycleStatusCode;
    }

    @JsonProperty("ServiceTechnician")
    public String getServiceTechnician() {
        return serviceTechnician;
    }

    @JsonProperty("ServiceTechnician")
    public void setServiceTechnician(String serviceTechnician) {
        this.serviceTechnician = serviceTechnician;
    }

    @JsonProperty("ServiceTechnicianTeam")
    public String getServiceTechnicianTeam() {
        return serviceTechnicianTeam;
    }

    @JsonProperty("ServiceTechnicianTeam")
    public void setServiceTechnicianTeam(String serviceTechnicianTeam) {
        this.serviceTechnicianTeam = serviceTechnicianTeam;
    }

    @JsonProperty("WarrantyDescription")
    public String getWarrantyDescription() {
        return warrantyDescription;
    }

    @JsonProperty("WarrantyDescription")
    public void setWarrantyDescription(String warrantyDescription) {
        this.warrantyDescription = warrantyDescription;
    }

    @JsonProperty("WarrantyFrom")
    public Object getWarrantyFrom() {
        return warrantyFrom;
    }

    @JsonProperty("WarrantyFrom")
    public void setWarrantyFrom(Object warrantyFrom) {
        this.warrantyFrom = warrantyFrom;
    }

    @JsonProperty("WarrantyTo")
    public Object getWarrantyTo() {
        return warrantyTo;
    }

    @JsonProperty("WarrantyTo")
    public void setWarrantyTo(Object warrantyTo) {
        this.warrantyTo = warrantyTo;
    }

    @JsonProperty("ServiceAndSupportTeam")
    public String getServiceAndSupportTeam() {
        return serviceAndSupportTeam;
    }

    @JsonProperty("ServiceAndSupportTeam")
    public void setServiceAndSupportTeam(String serviceAndSupportTeam) {
        this.serviceAndSupportTeam = serviceAndSupportTeam;
    }

    @JsonProperty("ServiceRequestUserLifeCycleStatusCode")
    public String getServiceRequestUserLifeCycleStatusCode() {
        return serviceRequestUserLifeCycleStatusCode;
    }

    @JsonProperty("ServiceRequestUserLifeCycleStatusCode")
    public void setServiceRequestUserLifeCycleStatusCode(String serviceRequestUserLifeCycleStatusCode) {
        this.serviceRequestUserLifeCycleStatusCode = serviceRequestUserLifeCycleStatusCode;
    }

    @JsonProperty("AssignedTo")
    public String getAssignedTo() {
        return assignedTo;
    }

    @JsonProperty("AssignedTo")
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @JsonProperty("EscalationStatus")
    public String getEscalationStatus() {
        return escalationStatus;
    }

    @JsonProperty("EscalationStatus")
    public void setEscalationStatus(String escalationStatus) {
        this.escalationStatus = escalationStatus;
    }

    @JsonProperty("AssignedToName")
    public AssignedToName getAssignedToName() {
        return assignedToName;
    }

    @JsonProperty("AssignedToName")
    public void setAssignedToName(AssignedToName assignedToName) {
        this.assignedToName = assignedToName;
    }

    @JsonProperty("ProductCategoryDescription")
    public String getProductCategoryDescription() {
        return productCategoryDescription;
    }

    @JsonProperty("ProductCategoryDescription")
    public void setProductCategoryDescription(String productCategoryDescription) {
        this.productCategoryDescription = productCategoryDescription;
    }

    @JsonProperty("InitialResponseDate")
    public String getInitialResponseDate() {
        return initialResponseDate;
    }

    @JsonProperty("InitialResponseDate")
    public void setInitialResponseDate(String initialResponseDate) {
        this.initialResponseDate = initialResponseDate;
    }

    @JsonProperty("Contact")
    public String getContact() {
        return contact;
    }

    @JsonProperty("Contact")
    public void setContact(String contact) {
        this.contact = contact;
    }

    @JsonProperty("ParentServiceRequest")
    public String getParentServiceRequest() {
        return parentServiceRequest;
    }

    @JsonProperty("ParentServiceRequest")
    public void setParentServiceRequest(String parentServiceRequest) {
        this.parentServiceRequest = parentServiceRequest;
    }

    @JsonProperty("ETag")
    public String getETag() {
        return eTag;
    }

    @JsonProperty("ETag")
    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    @JsonProperty("CreationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }

    @JsonProperty("CreationDateTime")
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @JsonProperty("LastChangeDateTime")
    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    @JsonProperty("LastChangeDateTime")
    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    @JsonProperty("RequestedStartTimeZoneCode")
    public String getRequestedStartTimeZoneCode() {
        return requestedStartTimeZoneCode;
    }

    @JsonProperty("RequestedStartTimeZoneCode")
    public void setRequestedStartTimeZoneCode(String requestedStartTimeZoneCode) {
        this.requestedStartTimeZoneCode = requestedStartTimeZoneCode;
    }

    @JsonProperty("RequestedEndTimeZoneCode")
    public String getRequestedEndTimeZoneCode() {
        return requestedEndTimeZoneCode;
    }

    @JsonProperty("RequestedEndTimeZoneCode")
    public void setRequestedEndTimeZoneCode(String requestedEndTimeZoneCode) {
        this.requestedEndTimeZoneCode = requestedEndTimeZoneCode;
    }

    @JsonProperty("RequestAssignmentStatusCodeText")
    public String getRequestAssignmentStatusCodeText() {
        return requestAssignmentStatusCodeText;
    }

    @JsonProperty("RequestAssignmentStatusCodeText")
    public void setRequestAssignmentStatusCodeText(String requestAssignmentStatusCodeText) {
        this.requestAssignmentStatusCodeText = requestAssignmentStatusCodeText;
    }

    @JsonProperty("ApprovalStatusCodeText")
    public String getApprovalStatusCodeText() {
        return approvalStatusCodeText;
    }

    @JsonProperty("ApprovalStatusCodeText")
    public void setApprovalStatusCodeText(String approvalStatusCodeText) {
        this.approvalStatusCodeText = approvalStatusCodeText;
    }

    @JsonProperty("DataOriginTypeCodeText")
    public String getDataOriginTypeCodeText() {
        return dataOriginTypeCodeText;
    }

    @JsonProperty("DataOriginTypeCodeText")
    public void setDataOriginTypeCodeText(String dataOriginTypeCodeText) {
        this.dataOriginTypeCodeText = dataOriginTypeCodeText;
    }

    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCodeText")
    public String getItemListServiceRequestExecutionLifeCycleStatusCodeText() {
        return itemListServiceRequestExecutionLifeCycleStatusCodeText;
    }

    @JsonProperty("ItemListServiceRequestExecutionLifeCycleStatusCodeText")
    public void setItemListServiceRequestExecutionLifeCycleStatusCodeText(String itemListServiceRequestExecutionLifeCycleStatusCodeText) {
        this.itemListServiceRequestExecutionLifeCycleStatusCodeText = itemListServiceRequestExecutionLifeCycleStatusCodeText;
    }

    @JsonProperty("ProcessingTypeCodeText")
    public String getProcessingTypeCodeText() {
        return processingTypeCodeText;
    }

    @JsonProperty("ProcessingTypeCodeText")
    public void setProcessingTypeCodeText(String processingTypeCodeText) {
        this.processingTypeCodeText = processingTypeCodeText;
    }

    @JsonProperty("RoleCodeText")
    public String getRoleCodeText() {
        return roleCodeText;
    }

    @JsonProperty("RoleCodeText")
    public void setRoleCodeText(String roleCodeText) {
        this.roleCodeText = roleCodeText;
    }

    @JsonProperty("ServicePriorityCodeText")
    public String getServicePriorityCodeText() {
        return servicePriorityCodeText;
    }

    @JsonProperty("ServicePriorityCodeText")
    public void setServicePriorityCodeText(String servicePriorityCodeText) {
        this.servicePriorityCodeText = servicePriorityCodeText;
    }

    @JsonProperty("ServiceRequestClassificationCodeText")
    public String getServiceRequestClassificationCodeText() {
        return serviceRequestClassificationCodeText;
    }

    @JsonProperty("ServiceRequestClassificationCodeText")
    public void setServiceRequestClassificationCodeText(String serviceRequestClassificationCodeText) {
        this.serviceRequestClassificationCodeText = serviceRequestClassificationCodeText;
    }

    @JsonProperty("ServiceRequestLifeCycleStatusCodeText")
    public String getServiceRequestLifeCycleStatusCodeText() {
        return serviceRequestLifeCycleStatusCodeText;
    }

    @JsonProperty("ServiceRequestLifeCycleStatusCodeText")
    public void setServiceRequestLifeCycleStatusCodeText(String serviceRequestLifeCycleStatusCodeText) {
        this.serviceRequestLifeCycleStatusCodeText = serviceRequestLifeCycleStatusCodeText;
    }

    @JsonProperty("ServiceRequestUserLifeCycleStatusCodeText")
    public String getServiceRequestUserLifeCycleStatusCodeText() {
        return serviceRequestUserLifeCycleStatusCodeText;
    }

    @JsonProperty("ServiceRequestUserLifeCycleStatusCodeText")
    public void setServiceRequestUserLifeCycleStatusCodeText(String serviceRequestUserLifeCycleStatusCodeText) {
        this.serviceRequestUserLifeCycleStatusCodeText = serviceRequestUserLifeCycleStatusCodeText;
    }

    @JsonProperty("EscalationStatusText")
    public String getEscalationStatusText() {
        return escalationStatusText;
    }

    @JsonProperty("EscalationStatusText")
    public void setEscalationStatusText(String escalationStatusText) {
        this.escalationStatusText = escalationStatusText;
    }

    @JsonProperty("RequestedStartTimeZoneCodeText")
    public String getRequestedStartTimeZoneCodeText() {
        return requestedStartTimeZoneCodeText;
    }

    @JsonProperty("RequestedStartTimeZoneCodeText")
    public void setRequestedStartTimeZoneCodeText(String requestedStartTimeZoneCodeText) {
        this.requestedStartTimeZoneCodeText = requestedStartTimeZoneCodeText;
    }

    @JsonProperty("RequestedEndTimeZoneCodeText")
    public String getRequestedEndTimeZoneCodeText() {
        return requestedEndTimeZoneCodeText;
    }

    @JsonProperty("RequestedEndTimeZoneCodeText")
    public void setRequestedEndTimeZoneCodeText(String requestedEndTimeZoneCodeText) {
        this.requestedEndTimeZoneCodeText = requestedEndTimeZoneCodeText;
    }

    @JsonProperty("ServiceRequestItem")
    public ServiceRequestItem getServiceRequestItem() {
        return serviceRequestItem;
    }

    @JsonProperty("ServiceRequestItem")
    public void setServiceRequestItem(ServiceRequestItem serviceRequestItem) {
        this.serviceRequestItem = serviceRequestItem;
    }

    @JsonProperty("ServiceRequestAttachmentFolder")
    public ServiceRequestAttachmentFolder getServiceRequestAttachmentFolder() {
        return serviceRequestAttachmentFolder;
    }

    @JsonProperty("ServiceRequestAttachmentFolder")
    public void setServiceRequestAttachmentFolder(ServiceRequestAttachmentFolder serviceRequestAttachmentFolder) {
        this.serviceRequestAttachmentFolder = serviceRequestAttachmentFolder;
    }

    @JsonProperty("ServiceRequestBusinessTransactionDocumentReference")
    public ServiceRequestBusinessTransactionDocumentReference getServiceRequestBusinessTransactionDocumentReference() {
        return serviceRequestBusinessTransactionDocumentReference;
    }

    @JsonProperty("ServiceRequestBusinessTransactionDocumentReference")
    public void setServiceRequestBusinessTransactionDocumentReference(ServiceRequestBusinessTransactionDocumentReference serviceRequestBusinessTransactionDocumentReference) {
        this.serviceRequestBusinessTransactionDocumentReference = serviceRequestBusinessTransactionDocumentReference;
    }

    @JsonProperty("ServiceRequestDescription")
    public ServiceRequestDescription getServiceRequestDescription() {
        return serviceRequestDescription;
    }

    @JsonProperty("ServiceRequestDescription")
    public void setServiceRequestDescription(ServiceRequestDescription serviceRequestDescription) {
        this.serviceRequestDescription = serviceRequestDescription;
    }

    @JsonProperty("ServicePointLocationAddress")
    public ServicePointLocationAddress getServicePointLocationAddress() {
        return servicePointLocationAddress;
    }

    @JsonProperty("ServicePointLocationAddress")
    public void setServicePointLocationAddress(ServicePointLocationAddress servicePointLocationAddress) {
        this.servicePointLocationAddress = servicePointLocationAddress;
    }

    @JsonProperty("ServiceRequestHistoricalVersion")
    public ServiceRequestHistoricalVersion getServiceRequestHistoricalVersion() {
        return serviceRequestHistoricalVersion;
    }

    @JsonProperty("ServiceRequestHistoricalVersion")
    public void setServiceRequestHistoricalVersion(ServiceRequestHistoricalVersion serviceRequestHistoricalVersion) {
        this.serviceRequestHistoricalVersion = serviceRequestHistoricalVersion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
