package com.syzegee.customer.events.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syzegee.customer.events.adapter.CustomerConnectors;


import com.syzegee.customer.events.model.CustomerDomainStatusUpdateEvent;
import com.syzegee.customer.events.model.CustomerUpdateDetail;
import com.syzegee.customer.events.model.crafter.CustomerSiteEvent;
import com.syzegee.customer.events.model.ruleengine.CustomerRuleEvent;
import com.syzegee.customer.events.model.CustomerEvent;
import com.syzegee.customer.events.service.ActivationService;
import com.syzegee.customer.events.service.CustomerService;
import com.syzegee.customer.events.util.JsonUtil;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;

import static com.syzegee.customer.events.constants.Constants.*;

@Data
@Builder
@Slf4j
@Service
public class CustomerCommandHandler {

    @Autowired
    private CustomerConnectors connector;
    @Autowired
    private CustomerService service;
    @Autowired
    private ActivationService activationService;
    // TODO Util class should not be autowired and change to static accessed way.
    @Autowired
    JsonUtil util;
    @Tolerate
    public CustomerCommandHandler() {
    }

    @KafkaListener(topics = {CUSTOMER_CREATED, CUSTOMER_UPDATED, CUSTOMER_SITE_UPDATED,CUSTOMER_DELETED,
            CUSTOMER_RULE_CREATED, CUSTOMER_RULE_UPDATED, CUSTOMER_RULE_DELETED, CUSTOMER_SITE_CREATION,CUSTOMER_SITE_DELETED})
    public void consumeCustomerEvent(ConsumerRecord<String, Object> event) throws JsonProcessingException, JAXBException {
        try {
            log.info("Initiate consumeCustomerEvent in CustomerCommandHandler" + " - CorrelationId: " + event);

            switch (event.topic()){
                case CUSTOMER_CREATED:
                    customerCreatedEventListener(event);

                case CUSTOMER_UPDATED:
                    customerUpdatedEventListener(event);

                case CUSTOMER_RULE_CREATED:
                    customerRuleCreatedEventListener(event);

                case CUSTOMER_SITE_CREATION:
                    customerSiteCreatedEventListener(event);

                case CUSTOMER_SITE_UPDATED:
                    customerSiteUpdatedEventListener(event);

                case CUSTOMER_SITE_DELETED:
                    customerSiteUpdatedEventListener(event);

                case MARKETING_AUTOMATION_CREATED:
                    marketingAutomationCreatedEventListener(event);

            }
            log.info("End of consumeCustomerEvent in CustomerCommandHandler");
        } catch (Exception ex){
            log.info("In CustomerCommandHandler Error occured "+ex+"  correlationId="+event.value().toString());
        }
    }

    private void marketingAutomationCreatedEventListener(ConsumerRecord<String, Object> event) {
        CustomerDomainStatusUpdateEvent customerDomainStatusUpdateEvent = util.getMarketingAutomationtEventCreatedFromJson(event.value().toString());
        service.updateMarketingAutomationStatus(customerDomainStatusUpdateEvent);
        log.info("End of MARKETING_AUTOMATION_CREATED in CustomerCommandHandler" + " - CustomerRequestID: " + customerDomainStatusUpdateEvent.getCorrelationId());
    }

    private void customerSiteUpdatedEventListener(ConsumerRecord<String, Object> event) throws JAXBException {
        System.out.println("-------------------------------CUSTOMER_SITE_UPDATED : "+ (event.value().toString()));
        CustomerSiteEvent customerSiteUpdateEvent = util.getCustomerSiteUpdateEventFromJson(event.value().toString());
        System.out.println("Customer Site update event String : "+ customerSiteUpdateEvent.toString());
        service.updateCustomerSites(customerSiteUpdateEvent);
        System.out.println("CUSTOMER_SITE_UPDATED : "+ (event.value().toString()));
        // TODO decide the logic here
        // TODO create Customer status table and update the status
        log.info("End of CUSTOMER_TEMPLATE_CREATED in CustomerCommandHandler" + " - CustomerRequestID: " + customerSiteUpdateEvent.getCustomerRequestId());
    }

    private void customerSiteCreatedEventListener(ConsumerRecord<String, Object> event) throws JAXBException {
        //CustomerTemplateEvent customerTemplateEvent = util.getCustomerTemplateEventFromJson( event.value().toString());
        //service.createCrafterSite(customerTemplateEvent);
        CustomerSiteEvent customerSiteEvent = util.getCustomerSiteUpdateEventFromJson(event.value().toString());
        //   List<BenefitDetail> benefitList = new ArrayList<>(); //Check what needs to be added here
  //      service.createSites(customerSiteEvent);
        service.createSitesNUpdateCustomerStatus(customerSiteEvent);
        //T0DO check if the above method is working fine
     //   service.publishTemplate(customerSiteEvent);
    //    service.deployTemplate(customerSiteEvent);
        //TODO investigate why these two methods are needed
        //  service.publishTemplate(customerSiteUpdateEvent);
        //service.deployTemplate(customerSiteUpdateEvent);
        log.info("End of CUSTOMER_TEMPLATE_CREATED in CustomerCommandHandler" + " - CustomerRequestID: " + customerSiteEvent.getCustomerRequestId());
    }

    private void customerRuleCreatedEventListener(ConsumerRecord<String, Object> event) {
        CustomerRuleEvent customerRuleEventFromJson = util.getCustomerRuleEventFromJson(event.value().toString());
        service.createCustomerRule(customerRuleEventFromJson);
        log.info("End of CUSTOMER_RULE_CREATED in CustomerCommandHandler" + " - CustomerRequestID: " + customerRuleEventFromJson.getCustomerRequestId());
        //TODO this CUSTOMER_TEMPLATE_CREATED need to be changed as Customer site created.
    }

    private void customerUpdatedEventListener(ConsumerRecord<String, Object> event) {
        //TODO DISCUSS and update what ned to be done
        CustomerUpdateDetail customerUpdateDetail = util.getCustomerUpdateDetailFromJson(event.value().toString());
        service.updateCustomerDetails(customerUpdateDetail);

    }

    private void customerCreatedEventListener(ConsumerRecord<String, Object> event) {
        CustomerEvent customerEventFromJson = util.getCustomerEventFromJson(event.value().toString());
        activationService.createCustomerRegistration(customerEventFromJson);
        log.info("End of CUSTOMER_CREATED in CustomerCommandHandler" + " - CustomerRequestID: " + customerEventFromJson.getCustomerRequestId());
    }

}
