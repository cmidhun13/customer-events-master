package com.syzegee.customer.events.adapter;

import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.CustomerInbox;
import com.syzegee.customer.events.model.CustomerCreateDetail;
import com.syzegee.customer.events.model.CustomerEvent;
import com.syzegee.customer.events.model.CustomerSiteUpdateRequest;
import com.syzegee.customer.events.model.CustomerUpdateDetail;
import com.syzegee.customer.events.model.crafter.CustomerSiteEvent;
import com.syzegee.customer.events.model.crafter.CustomerTemplateEvent;
import com.syzegee.customer.events.model.ruleengine.CustomerRuleEvent;
import com.syzegee.customer.events.repository.CustomerInboxRepository;
import com.syzegee.customer.events.util.CorrelationIdUtil;
import com.syzegee.customer.events.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.syzegee.customer.events.constants.Constants.CUSTOMER_CREATED;
import static com.syzegee.customer.events.constants.Constants.CUSTOMER_UPDATED;

@Component
@Slf4j
public class CustomerInboxAdapter {

    @Autowired
    private CustomerInboxRepository customerInboxRepository;

    @Autowired
    private JsonUtil util;

    /**
     * This method is used to create Tier for the customer
     *
     * @param detail     ,
     * @param customerId id is required to create the tier
     * @return tier object
     */

    public CustomerInbox saveToinbox(CustomerCreateDetail detail, Long customerId, String correlationId) {
        log.info("Initiate saveToinbox in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        String details = util.objectToString(detail);
        CustomerInbox customerInbox = CustomerInbox.builder().
                customerId(customerId)
                .customerRequestId(customerRequestId)
                .status(CUSTOMER_CREATED).details(details)
                .correlationId(correlationId).build();
        CustomerInbox inbox = customerInboxRepository.save(customerInbox);
        log.info("End of  saveToinbox in connector " + "correlationId:" + correlationId);
        return inbox;
    }

    @Transactional
    //TODO Convert into one single method
    public CustomerInbox updateCustomerSiteToinbox(CustomerSiteUpdateRequest customerSiteUpdateRequest, Customer customer, String correlationId) {
        log.info("Initiate updateToinbox in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        String details = util.objectToString(customerSiteUpdateRequest);
        CustomerInbox customerInbox = CustomerInbox.builder().
                customerId(customer.getCustomerId())
                .customerRequestId(customerRequestId)
                .status(CUSTOMER_UPDATED).details(details.toString())
                .correlationId(correlationId).build();
        CustomerInbox inbox = customerInboxRepository.save(customerInbox);
        log.info("End of  updateToinbox in connector " + "correlationId:" + correlationId);
        return inbox;
    }

    @Transactional
    public CustomerInbox updateToinbox(CustomerUpdateDetail detail, Customer customer, String correlationId) {
        log.info("Initiate updateToinbox in connector " + "correlationId:" + correlationId);
        String customerRequestId = CorrelationIdUtil.generateCorrelationId();
        String details = util.objectToString(detail);
        CustomerInbox customerInbox = CustomerInbox.builder().
                customerId(customer.getCustomerId())
                .customerRequestId(customerRequestId)
                .status(CUSTOMER_UPDATED).details(details.toString())
                .correlationId(correlationId).build();
        CustomerInbox inbox = customerInboxRepository.save(customerInbox);
        log.info("End of  updateToinbox in connector " + "correlationId:" + correlationId);
        return inbox;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerForRuleRequest(CustomerRuleEvent event) {
        log.info("Initiate getCustomerForRuleRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerForRuleRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerForTemplateRequest(CustomerTemplateEvent event) {
        log.info("Initiate getCustomerForTemplateRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerForTemplateRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }
    @Transactional
    public CustomerInbox getCustomerInboxForSiteUpdate(CustomerSiteEvent event) {
        log.info("Initiate getCustomerForSiteUpdate in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerForSiteUpdate in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }

    /**
     * This method is used to create customer with organization
     *
     * @param event
     * @return CustomerInbox
     */
    @Transactional
    public CustomerInbox getCustomerRequest(CustomerEvent event) {
        log.info("Initiate getCustomerRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        CustomerInbox customerDeatils = customerInboxRepository.getCustomerRequest(event.getCustomerRequestId());
        log.info("End of getCustomerRequest in Connector " + " - CorrelationId: " + event.getCorrelationId());
        return customerDeatils;
    }




}
