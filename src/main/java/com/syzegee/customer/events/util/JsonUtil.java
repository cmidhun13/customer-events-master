package com.syzegee.customer.events.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.syzegee.customer.events.model.*;
import com.syzegee.customer.events.model.crafter.CustomerSiteEvent;
import com.syzegee.customer.events.model.crafter.CustomerTemplateEvent;
import com.syzegee.customer.events.model.ruleengine.CustomerRuleEvent;
import com.syzegee.customer.events.entity.CustomerInbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Sagar
 */
@Slf4j
@Component
public class JsonUtil {
    public JsonNode stringToJsonNode(String value) {
        try {
            return new ObjectMapper().readTree(value);
        } catch (Exception e) {
            return null;
        }
    }

    public String objectToString(Object value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (Exception e) {
            return null;
        }
    }

    public JsonNode objectToJsonNode(Object object) {
        return stringToJsonNode(objectToString(object));
    }

    //get the customer details from inbox and convert to into CustomerCreateDetail domain class
    public CustomerCreateDetail getCreateDetailfromJson(CustomerInbox customerCreateDetail) {
        Gson gson = new Gson();
        CustomerCreateDetail createDetail = gson.fromJson(customerCreateDetail.getDetails(), CustomerCreateDetail.class);
        return createDetail;
    }

    //get the customer details from inbox and convert to into CustomerCreateDetail domain class
    public CustomerSiteUpdateRequest getSiteUpdateDetailfromJson(CustomerInbox customerSiteUpdateDetail) {
        Gson gson = new Gson();
        CustomerSiteUpdateRequest siteUpdateDetail = gson.fromJson(customerSiteUpdateDetail.getDetails(), CustomerSiteUpdateRequest.class);
        return siteUpdateDetail;
    }
    //get the CustomerEvent from Listener and convert to into CustomerEvent domain class
    public CustomerEvent getCustomerEventFromJson(String event) {
        Gson gson = new Gson();
        CustomerEvent customerEvent = gson.fromJson(event, CustomerEvent.class);
        return customerEvent;
    }

    //get the CustomerRuleEvent from Listener and convert to into CustomerRuleEvent domain class
    public CustomerRuleEvent getCustomerRuleEventFromJson(String event) {
        Gson gson = new Gson();
        CustomerRuleEvent customerRuleEvent = gson.fromJson(event, CustomerRuleEvent.class);
        return customerRuleEvent;
    }

    //get the CustomerTemplateEvent from Listener and convert to into CustomerTemplateEvent domain class
    public CustomerTemplateEvent getCustomerTemplateEventFromJson(String event) {
        Gson gson = new Gson();
        CustomerTemplateEvent customerTemplateEvent = gson.fromJson(event, CustomerTemplateEvent.class);
        return customerTemplateEvent;
    }

    //get the CustomerTemplateEvent from Listener and convert to into CustomerTemplateEvent domain class
    public CustomerSiteEvent getCustomerSiteUpdateEventFromJson(String event) {
        Gson gson = new Gson();
        CustomerSiteEvent customerSiteUpdateEvent = gson.fromJson(event, CustomerSiteEvent.class);
        return customerSiteUpdateEvent;
    }
    public MarketingAutomationCreationEvent getMarketingAutomationtFromJson(String event) {
        Gson gson = new Gson();
        MarketingAutomationCreationEvent marketingAutomationCreationEvent = gson.fromJson(event, MarketingAutomationCreationEvent.class);
        return marketingAutomationCreationEvent;
    }
    public CustomerDomainStatusUpdateEvent getMarketingAutomationtEventCreatedFromJson(String event) {
        Gson gson = new Gson();
        CustomerDomainStatusUpdateEvent customerDomainStatusUpdateEvent = gson.fromJson(event, CustomerDomainStatusUpdateEvent.class);
        return customerDomainStatusUpdateEvent;
    }

    public CustomerUpdateDetail getCustomerUpdateDetailFromJson(String event) {
        Gson gson = new Gson();
        CustomerUpdateDetail customerSiteUpdateEvent = gson.fromJson(event, CustomerUpdateDetail.class);
        return customerSiteUpdateEvent;
    }
}
