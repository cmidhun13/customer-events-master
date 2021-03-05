/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.service;

import com.google.gson.Gson;
import com.syzegee.customer.events.adapter.*;
import com.syzegee.customer.events.entity.*;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.model.crafter.CrafterCreateSite;
import com.syzegee.customer.events.model.crafter.CustomerSiteEvent;
import com.syzegee.customer.events.model.crafter.CustomerTemplateEvent;
import com.syzegee.customer.events.model.crafter.DeploymentSite;
import com.syzegee.customer.events.model.ruleengine.CustomerRuleEvent;
import com.syzegee.customer.events.exception.CustomerRuntimeException;
import com.syzegee.customer.events.repository.CustomerInboxRepository;
import com.syzegee.customer.events.repository.CustomerRepository;
import com.syzegee.customer.events.repository.CustomerUserRepository;
import com.syzegee.customer.events.util.BuilderGenerator;
import com.syzegee.customer.events.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.syzegee.customer.events.constants.Constants.*;


/**
 * @author Sagar
 */
@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerAdapter customerAdapter;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BuilderGenerator builderGenerator;
    @Autowired
    private DomainDetailsAdapter domainDetailsAdapter;
    @Autowired
    CustomerStatusAdapter customerStatusAdapter;
    @Autowired
    private CustomerInboxAdapter customerInboxAdapter;
    @Autowired
    private CustomerInboxRepository customerInboxRepository;
    @Autowired
    private RuleEngineAdapter ruleEngineAdapter;
    @Autowired
    private EventPublisher publisher;
    @Autowired
    private JsonUtil util;
    @Autowired
    private CommunicationAdapter communicationAdapter;
    @Autowired
    private CrafterAdapter crafterAdapter;
    @Autowired
    private CustomerUserRepository customerUserRepository;
    @Value("${Crafter.createOption}")
    private String createOption;
    @Value("${Crafter.authenticationType}")
    private String authenticationType;
    @Value("${Crafter.blueprint}")
    private String blueprint;
    @Value("${Crafter.description}")
    private String description;
    @Value("${Crafter.remoteName}")
    private String remoteName;
    @Value("${Crafter.remotePassword}")
    private String remotePassword;
    @Value("${Crafter.remoteUrl}")
    private String remoteUrl;
    @Value("${Crafter.remoteUsername}")
    private String remoteUsername;
    @Value("${Crafter.useRemote}")
    private Boolean useRemote;
    @Value("${Crafter.deployment.url}")
    private String deploymentUrl;
    @Value("${Crafter.deployment.replace}")
    private Boolean replace;
    @Value("${Crafter.deployment.env}")
    private String env;
    @Value("${Crafter.deployment.templateName}")
    private String templateName;
    @Value("${Crafter.deployment.repoUrl}")
    private String repoUrl;
    @Value("${Crafter.deployment.repoBranch}")
    private String repoBranch;
    @Value("${Crafter.deployment.engineUrl}")
    private String engineUrl;

    private String customerRequestId = " - CustomerRequestId: ";
    private String correlationId = " - CorrelationId: ";



    //TODO This method needs to be simpleified.
    public CustomerResponse customerCreateEvent(CustomerCreateDetail customerCreateDetail,
                                                String correlationId, Long customerId) throws CustomerRuntimeException, IOException {
        log.info("Initiate customerCreateEvent in service new request " + correlationId + correlationId);
        CustomerInbox customerInbox = customerInboxAdapter.saveToinbox(customerCreateDetail, customerId, correlationId);
        CustomerEvent customerEvent = builderGenerator.generateCustomerEvent(customerCreateDetail,customerInbox);
        log.info("customerEvent builder ->"+customerEvent);
        sendDataToPublisher(customerEvent, customerEvent.getState());
        log.info("customerEvent Published in service: " + customerRequestId + customerEvent.getCustomerRequestId());

        CustomerRuleEvent ruleEvent = builderGenerator.generateCustomerRuleEvent(customerInbox);
        sendDataToPublisher(ruleEvent, ruleEvent.getState());
        log.info("ruleEvent Published in service: " + customerRequestId + customerEvent.getCustomerRequestId());
        CustomerSiteEvent customerSiteEvent = builderGenerator.generateCustomerSiteEvent(customerCreateDetail, customerInbox);
        sendDataToPublisher(customerSiteEvent, customerSiteEvent.getState());
        log.info("customerSiteEvent Published in service: " + customerRequestId + customerSiteEvent.getCustomerRequestId());
        CustomerResponse customerResponse = builderGenerator.generateCustomerResponse(customerInbox);
        // communicationAdapter.sendCustomerSiteInviteEmail(customerCreateDetail);
        log.info("End of customerCreateEvent in service " + correlationId + correlationId);
        /* marketing automation event */
        CustomerUser customerUser = null;
        try
        {
            customerUser = customerUserRepository.getCustomerUserByEmailId(customerCreateDetail.getCustomerDetail().getCustomerUserEmailId());
        }
        catch (Exception exception)
        {
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "Duplicate user exists");
        }

        MarketingAutomationCreationEvent marketingAutomationCreationEventEvent = builderGenerator.generateMarketingAutomationEvent(customerCreateDetail, customerUser);
        sendDataToPublisher(marketingAutomationCreationEventEvent, marketingAutomationCreationEventEvent.getState());
        log.info("marketingAutomationEvent Published in service: " + customerRequestId + customerSiteEvent.getCustomerRequestId());
        return customerResponse;
    }

    private void sendDataToPublisher(Object eventType, String state) {
        String jsoncustomerEvent = util.objectToString(eventType);
        publisher.send(jsoncustomerEvent, state);
    }


    public void createCustomerRule(CustomerRuleEvent event) {
        log.info("Initiate createCustomerRule in service" + correlationId + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerForRuleRequest(event);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        ruleEngineAdapter.createCustomerRuleEvent(createDetailJson, event.getCustomerId());
        log.info("End of the createCustomerRule in service " + correlationId + event.getCorrelationId());
    }

    //TODO change this t create site and update the domain details
    public CustomerDomainDetails createCrafterSite(CustomerTemplateEvent templateEvent) throws JAXBException {
        log.info("Initiate createCrafterSite in service" + correlationId + templateEvent.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerForTemplateRequest(templateEvent);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        CrafterCreateSite createSite = CrafterCreateSite.builder().create_option(createOption)
                .site_id(createDetailJson.getDomainDetails().getSiteName().toLowerCase())
                .authentication_type(authenticationType).blueprint(blueprint).description(description)
                .remote_name(remoteName).remote_password(remotePassword).remote_url(remoteUrl)
                .remote_username(remoteUsername).use_remote(useRemote).build();

        CustomerDomainDetails customerDomainDetails = CustomerDomainDetails.builder()
                .customerId(createDetailJson.getDomainDetails().getCustomerId())
                .domainName(createDetailJson.getDomainDetails().getDomainName())
                .siteCode(createDetailJson.getDomainDetails().getSiteCode())
        .build();
        crafterAdapter.createCMSSite(createSite,customerDeatils.getCustomerId());

        /*List<String> listString=new ArrayList<>();
            listString.add("package1");
        listString.add("package2");
        crafterConnectors.writeContent(listString,createSite.getSite_id(),
                "riya.patel@balajiinfosol.com","logo");*/
        log.info("End of the createCrafterSite in service " + correlationId);
        CustomerStatus customerStatus =  CustomerStatus.builder().customerId(templateEvent.getCustomerId())
                .correlationId(templateEvent.getCorrelationId())
                .build();
        customerStatusAdapter.updateOnboardCustomerStatus(customerStatus);
        return customerDomainDetails;
    }
    //TODO change this t create site and update the domain details
    public void createSites(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        log.info("Initiate createSites in service" + correlationId + customerSiteEvent.getCorrelationId());
        Optional<Customer> customer = Optional.ofNullable(customerRepository.getRecordById(customerSiteEvent.getCustomerId()));
        customerSiteEvent.getBenefitDetailsList().forEach(item->{
            //TODO Get the SSO parameters populated here
            CrafterCreateSite createSite = CrafterCreateSite.builder().create_option(createOption)
                    .site_id(item.getDomainDetails().getSiteName().toLowerCase())
                    .authentication_type(authenticationType).blueprint(blueprint).description(item.getDomainDetails().getSiteDesc())
                    .remote_name(remoteName).remote_password(remotePassword).remote_url(remoteUrl)
                    .remote_username(remoteUsername).use_remote(useRemote).build();
            CompletableFuture.supplyAsync(() ->
                crafterAdapter.createCMSSite(createSite,customerSiteEvent.getCustomerId())
            ).thenRun(() ->
                //TODO Find a way to populate the customer object for domain and SSO
                domainDetailsAdapter.createDomainDetails(item.getDomainDetails(),customer.get(),customerSiteEvent.getCorrelationId())

            );
        });

    }

    public void createSitesNUpdateCustomerStatus(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        createSites(customerSiteEvent);
        CustomerStatus customerStatus = CustomerStatus.builder().customerId(customerSiteEvent.getCustomerId())
                .correlationId(customerSiteEvent.getCorrelationId())
                .build();
        customerStatusAdapter.updateOnboardCustomerStatus(customerStatus);
    }

    //TODO change this t create site and update the domain details
    public void deleteSites(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        log.info("Initiate createSites in service" + correlationId + customerSiteEvent.getCorrelationId());
        Optional<Customer> customer = Optional.ofNullable(customerRepository.getRecordById(customerSiteEvent.getCustomerId()));
        customerSiteEvent.getBenefitDetailsList().forEach(item->{
            //TODO Get the SSO parameters populated here
            CrafterCreateSite createSite = CrafterCreateSite.builder().create_option(createOption)
                    .site_id(item.getDomainDetails().getSiteName().toLowerCase())
                    .authentication_type(authenticationType).blueprint(blueprint).description(item.getDomainDetails().getSiteDesc())
                    .remote_name(remoteName).remote_password(remotePassword).remote_url(remoteUrl)
                    .remote_username(remoteUsername).use_remote(useRemote).build();
            CompletableFuture.supplyAsync(() ->
                    crafterAdapter.deleteCMSSite(createSite,customerSiteEvent.getCustomerId())
            ).thenRun(() ->
                    //TODO Find a way to populate the customer object for domain and SSO
                    domainDetailsAdapter.updateExistingDomainDetails(item.getDomainDetails(),customer.get(),customerSiteEvent.getCorrelationId())

            );
        });

    }
    public void updateCustomerSites(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        log.info("Initiate createCrafterSite in service" + correlationId + customerSiteEvent.getCorrelationId());
        CustomerInbox customerUpdateSiteDetails = customerInboxAdapter.getCustomerInboxForSiteUpdate(customerSiteEvent);
        log.info("Retrieve the customer site details "+ customerUpdateSiteDetails);
        CustomerSiteUpdateRequest customerSiteUpdateRequest = util.getSiteUpdateDetailfromJson(customerUpdateSiteDetails);
        List<BenefitDetail> benefitList = customerSiteUpdateRequest.getBenefitDetail();
        List<BenefitDetail> newSiteList = benefitList.parallelStream().filter(b->b.isNewlyAdded()).collect(Collectors.toList());
        List<BenefitDetail> updatingSiteList = benefitList.parallelStream().filter(b->b.isDeleted()).collect(Collectors.toList());
        //CustomerSiteEvent newcustomerSiteEvent = customerSiteEvent;
        CustomerSiteEvent newcustomerSiteEvent = CustomerSiteEvent.builder()
                .customerId(customerSiteEvent.getCustomerId())
                .customerRequestId(customerSiteEvent.getCustomerRequestId())
                .state(customerSiteEvent.getState())
                .correlationId(customerSiteEvent.getCorrelationId())
                .customerTemplateId(customerSiteEvent.getCustomerTemplateId())
                .email(customerSiteEvent.getEmail())
                .firsName(customerSiteEvent.getFirsName())
                .benefitDetailsList(newSiteList)
                .build();
      //  newcustomerSiteEvent.setBenefitDetailsList(newSiteList);
        createSites(newcustomerSiteEvent);
      //  CustomerSiteEvent deletecustomerSiteEvent = customerSiteEvent;
        CustomerSiteEvent deletecustomerSiteEvent = CustomerSiteEvent.builder()
                .customerId(customerSiteEvent.getCustomerId())
                .customerRequestId(customerSiteEvent.getCustomerRequestId())
                .state(customerSiteEvent.getState())
                .correlationId(customerSiteEvent.getCorrelationId())
                .customerTemplateId(customerSiteEvent.getCustomerTemplateId())
                .email(customerSiteEvent.getEmail())
                .firsName(customerSiteEvent.getFirsName())
                .benefitDetailsList(updatingSiteList)
                .build();
       // deletecustomerSiteEvent.setBenefitDetailsList(updatingSiteList);
        deleteSites(deletecustomerSiteEvent);
        CustomerStatus customerStatus =  CustomerStatus.builder().customerId(customerSiteEvent.getCustomerId())
                .correlationId(customerSiteEvent.getCorrelationId())
                .build();
        customerStatusAdapter.updateCmsSiteCustomerStatus(customerStatus);
        benefitList.parallelStream().filter(b->b.isNewlyAdded()).collect(Collectors.toList());
        //TODO complete the delete site
        log.info("End of the createCrafterSite in service " + correlationId);
    }
    public void publishTemplate(CustomerTemplateEvent templateEvent) throws JAXBException {
        log.info("End of the publishTemplate in service " + correlationId);
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerForTemplateRequest(templateEvent);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        CrafterCreateSite createSite = CrafterCreateSite.builder().create_option(createOption)
                .site_id(createDetailJson.getDomainDetails().getSiteName().toLowerCase())
                .authentication_type(authenticationType).blueprint(blueprint).description(description)
                .remote_name(remoteName).remote_password(remotePassword).remote_url(remoteUrl)
                .remote_username(remoteUsername).use_remote(useRemote).build();

        crafterAdapter.startCMSSite(createSite,customerDeatils.getCustomerId());

        log.info("End of the publishTemplate in service " + correlationId);
    }

    public void publishTemplate(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        log.info("End of the publishTemplate in service " + correlationId);
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerInboxForSiteUpdate(customerSiteEvent);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        CrafterCreateSite createSite = CrafterCreateSite.builder().create_option(createOption)
                .site_id(createDetailJson.getDomainDetails().getSiteName().toLowerCase())
                .authentication_type(authenticationType).blueprint(blueprint).description(description)
                .remote_name(remoteName).remote_password(remotePassword).remote_url(remoteUrl)
                .remote_username(remoteUsername).use_remote(useRemote).build();

        crafterAdapter.startCMSSite(createSite,customerDeatils.getCustomerId());

        log.info("End of the publishTemplate in service " + correlationId);
    }

    public void deployTemplate(CustomerSiteEvent customerSiteEvent) throws JAXBException {
        log.info("End of the deployTemplate in service " + correlationId);
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerInboxForSiteUpdate(customerSiteEvent);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        DeploymentSite deploymentSite = DeploymentSite.builder()
                .env(env)
                .site_name(createDetailJson.getDomainDetails().getSiteName().toLowerCase())
                .replace(replace).template_name(templateName)
                .repo_url(repoUrl.replace("member-templates",createDetailJson.getDomainDetails().getSiteName().toLowerCase()))
                .repo_branch(repoBranch)
                .engine_url(engineUrl).build();
        crafterAdapter.deploymentTemplate(deploymentSite,customerDeatils.getCustomerId());

        log.info("End of the deployTemplate in service " + correlationId);
    }
    public void deployTemplate(CustomerTemplateEvent templateEvent) throws JAXBException {
        log.info("End of the deployTemplate in service " + correlationId);
        CustomerInbox customerDeatils = customerInboxAdapter.getCustomerForTemplateRequest(templateEvent);
        CustomerCreateDetail createDetailJson = util.getCreateDetailfromJson(customerDeatils);
        DeploymentSite deploymentSite = DeploymentSite.builder()
                .env(env)
                .site_name(createDetailJson.getDomainDetails().getSiteName().toLowerCase())
                .replace(replace).template_name(templateName)
                .repo_url(repoUrl.replace("member-templates",createDetailJson.getDomainDetails().getSiteName().toLowerCase()))
                .repo_branch(repoBranch)
                .engine_url(engineUrl).build();
        crafterAdapter.deploymentTemplate(deploymentSite,customerDeatils.getCustomerId());

        log.info("End of the deployTemplate in service " + correlationId);
    }

    @Transactional
    // TODO This method may not be used in current
    public CustomerResponse updateCustomerEvent(CustomerUpdateDetail customerUpdateDetail, Long id, String correlationId)
            throws CustomerRuntimeException {
        log.info("Initiate updateCustomerRegistration in service " + correlationId + correlationId);
        Customer customer = customerAdapter.getCustomer(id, correlationId);

        CustomerInbox customerInbox = customerInboxAdapter.updateToinbox(customerUpdateDetail, customer, correlationId);
        //TODO Decide what event and what fields need to be adjusted here
        CustomerEvent customerEvent = CustomerEvent.builder().state(CUSTOMER_UPDATED)
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customer.getCorrelationId())
                .customerRequestId(customerInbox.getCustomerRequestId())
                .customerId(customerInbox.getCustomerId()).build();
        publisher.send(customerEvent.toString(), customerEvent.getState());
        log.info("updateCustomerEvent Published in service: " + customerRequestId + customerEvent.getCustomerRequestId());
        CustomerRuleEvent ruleEvent = CustomerRuleEvent.builder().state("rules.updated")
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customer.getCorrelationId())
                .customerId(customerInbox.getCustomerId()).build();
        publisher.send(ruleEvent.toString(), ruleEvent.getState());
        log.info("ruleEvent Published in service: " + customerRequestId + customerEvent.getCustomerRequestId());
        List<BenefitDetail> benefitDetailsList = new ArrayList<>();
        benefitDetailsList.add(customerUpdateDetail.getBenefitDetail());
        CustomerSiteEvent customerSiteEvent = CustomerSiteEvent.builder().state(CUSTOMER_SITE_UPDATED)
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customer.getCorrelationId())
                .benefitDetailsList(benefitDetailsList)
                .customerId(customerInbox.getCustomerId()).build();
        publisher.send(customerSiteEvent.toString(), customerSiteEvent.getState());
        log.info("customerSiteEvent Published in service: " + customerRequestId + customerSiteEvent.getCustomerRequestId());


        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customer.getCustomerId())
                .correlationId(customer.getCorrelationId())
                .customerRequestId(customer.getCorrelationId())
                .message("Customer update request has been received and you will be receiving an Email Shortly!! ").build();
        log.info("End of createCustomerEvent in service " + correlationId + correlationId);
        return customerResponse;
    }
    @Transactional
    //TODO THIS method is being used to update the customer sites
    public CustomerResponse updateCustomerSitesEvent(CustomerSiteUpdateRequest customerSiteUpdateRequest, String correlationId,Long id)
            throws CustomerRuntimeException {
        log.info("Initiate updateCustomerRegistration in service " + correlationId + correlationId);
        Customer customer = customerAdapter.getCustomer(id, correlationId);
        // TODO Determine what details need to be updated
        CustomerInbox customerInbox = customerInboxAdapter.updateCustomerSiteToinbox(customerSiteUpdateRequest, customer, correlationId);
      /*  CustomerEvent customerEvent = CustomerEvent.builder().state(CUSTOMER_UPDATED)
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customer.getCorrelationId())
                .customerRequestId(customerInbox.getCustomerRequestId())
                .customerId(customerInbox.getCustomerId()).build();
        sendDataToPublisher(customerEvent, customerEvent.getState());
        log.info("updateCustomerEvent Published in service: " + customerRequestId + customerEvent.getCustomerRequestId());*/
        CustomerUser customerUser = null;
        try
        {
            customerUser = customerUserRepository.getCustomerUserByEmailId(customerSiteUpdateRequest.getCustomerDetail().getCustomerUserEmailId());
        }
        catch (Exception exception)
        {
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "Duplicate user exists");
        }
        if(Objects.isNull(customerUser))
        {
            throw new CustomerRuntimeException(HttpStatus.BAD_REQUEST,400,"Customer with user email id :"
                    + customerSiteUpdateRequest.getCustomerDetail().getCustomerUserEmailId() +" does not exist");
        }
        CustomerSiteEvent siteUpdateEvent = CustomerSiteEvent.builder().state(CUSTOMER_SITE_UPDATED)
                .correlationId(customerInbox.getCorrelationId())
                .customerTemplateId("b") //TODO get the template from benefit details
                .firsName(customerUser.getFirstName())
                .lastName(customerUser.getLastName())
                .email(customerUser.getEmailId())
                .benefitDetailsList(customerSiteUpdateRequest.getBenefitDetail())
                .customerRequestId(customerInbox.getCustomerRequestId())
                .groupName("test") //TODO Change the group name from the organization
                .customerId(customerInbox.getCustomerId()).build();
        sendDataToPublisher(siteUpdateEvent, siteUpdateEvent.getState());
        log.info("Customer site update event is published in service: " + customerRequestId + siteUpdateEvent.getCustomerRequestId());
        // TODO think about different events and update the status
        CustomerResponse customerResponse = CustomerResponse.builder().customerId(customer.getCustomerId())
                .correlationId(customer.getCorrelationId())
                .customerRequestId(customer.getCorrelationId())
                .message("Customer update request has been received and you will be receiving an Email Shortly!! ").build();
        log.info("End of createCustomerEvent in service " + correlationId + correlationId);
        return customerResponse;
    }

    @Transactional
    public Customer updateCustomer(CreateUpdateCustomerRequest request)
            throws CustomerRuntimeException {

        Customer customer = customerAdapter.getCustomer(request.getCustomerId(),"");
        log.info("customer details fetched success"+customer);

        customer.setUserId(request.getUserId());
        Customer  customerResponse=null;
        try {
            customerResponse=  customerRepository.save(customer);
            log.info("update customer -by praveen success");
        }catch(Exception e) {
            e.printStackTrace();
            throw new CustomerRuntimeException();
            //throw here customerruntimeexception
        }

        return customerResponse;
    }

    public Customer updateCustomerRegistration(CustomerEvent event) throws CustomerRuntimeException {
        log.info("Initiate createCustomerRegistration in service" + correlationId + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        Gson g = new Gson();
        CustomerUpdateDetail updateDatail = g.fromJson(customerDeatils.getDetails(), CustomerUpdateDetail.class);
        Customer customerDetails = customerAdapter.updateAllCustomerDetails(updateDatail, event.getCustomerId(), event.getCorrelationId());
        log.info("End of the createCustomerRegistration in service " + correlationId + event.getCorrelationId());
        return customerDetails;
    }


    @Transactional
    public void updateMarketingAutomationStatus(CustomerDomainStatusUpdateEvent eventCreated) throws CustomerRuntimeException {
        log.info("Initiate updateMarketingAutomationStatus request");
        customerAdapter.updateCustomerDomainNMAStatus(eventCreated);

    }

    @Transactional
    public void updateCustomerDetails(CustomerUpdateDetail eventCreated) throws CustomerRuntimeException {
        log.info("Initiate updateCustomerUpdateDetails request");
        Organization organization = customerAdapter.appendOrganization(eventCreated.getOrgDetail(),eventCreated.getCustomerId(),null);
        Customer customerEntity = Customer.builder().customerId(eventCreated.getCustomerId()).build();
        Tier tier = customerAdapter.createTier(eventCreated.getTierDetail(),customerEntity,null);
        List<Tier> tierList = new ArrayList<>();
        tierList.add(tier);
        customerEntity.setTierCollection(tierList);
        customerAdapter.updateCustomerOrgTierNAddressDetails(eventCreated,organization,tierList);

    }

    @Transactional
    public CustomerUserResponse addCustomerUser(CustomerUserRequest customerUserRequest, String correlationId, Long customerId)
            throws CustomerRuntimeException {
        log.info("Initiate customerUserRole in service " + " - CorrelationId: " + correlationId);
        CustomerUserResponse customerUserResponse=customerAdapter.addCustomerUserAndRole(customerUserRequest,correlationId,customerId);

        return  customerUserResponse;
    }

}
