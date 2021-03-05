package com.syzegee.customer.events.util;

import com.syzegee.customer.events.entity.CustomerInbox;
import com.syzegee.customer.events.entity.CustomerUser;
import com.syzegee.customer.events.model.CustomerCreateDetail;
import com.syzegee.customer.events.model.CustomerEvent;
import com.syzegee.customer.events.model.CustomerResponse;
import com.syzegee.customer.events.model.MarketingAutomationCreationEvent;
import com.syzegee.customer.events.model.crafter.CustomerSiteEvent;
import com.syzegee.customer.events.model.ruleengine.CustomerRuleEvent;
import org.springframework.stereotype.Component;

import static com.syzegee.customer.events.constants.Constants.*;
import static com.syzegee.customer.events.constants.Constants.CUSTOMER_SITE_CREATION;

@Component
public class BuilderGenerator {


    public CustomerEvent generateCustomerEvent(CustomerCreateDetail customerCreateDetail,CustomerInbox customerInbox) {
        return CustomerEvent.builder().state(CUSTOMER_UPDATED)
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customerInbox.getCustomerRequestId())
                .customerId(customerInbox.getCustomerId())
                .customerDetail(customerCreateDetail.getCustomerDetail())
                .orgDetail(customerCreateDetail.getOrgDetail())
                .tierDetail(customerCreateDetail.getTierDetail())
                .custAddressDetail(customerCreateDetail.getCustAddressDetail())
                .build();
    }

    public CustomerResponse generateCustomerResponse(CustomerInbox customerInbox) {
        return CustomerResponse.builder().customerId(customerInbox.getCustomerId())
                .correlationId(customerInbox.getCorrelationId())
                .customerRequestId(customerInbox.getCustomerRequestId())
                .message("Customer create request has been received and you will be receiving an Email ").build();
    }

    public CustomerRuleEvent generateCustomerRuleEvent(CustomerInbox customerInbox) {
        return CustomerRuleEvent.builder().state(CUSTOMER_RULE_CREATED)
                .projectId(001L)
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customerInbox.getCustomerRequestId())
                .customerId(customerInbox.getCustomerId()).build();
    }

    public MarketingAutomationCreationEvent generateMarketingAutomationEvent(CustomerCreateDetail customerCreateDetail, CustomerUser customerUser) {
        return MarketingAutomationCreationEvent.builder().state(MARKETING_AUTOMATION)
                .customerEmail(customerCreateDetail.getCustomerDetail().getCustomerUserEmailId())
                .customerOrganizationName(customerCreateDetail.getOrgDetail().getOrganizationName())
                .customerId(customerCreateDetail.getCustomerDetail().getUserId())
                .customerFirstName(customerUser.getFirstName())
                .customerLastName(customerUser.getLastName())
                .customerId(customerCreateDetail.getCustomerDetail().getCustomerId()).build();
    }

    public CustomerSiteEvent generateCustomerSiteEvent(CustomerCreateDetail customerCreateDetail, CustomerInbox customerInbox) {
        return CustomerSiteEvent.builder().state(CUSTOMER_SITE_CREATION)
                .benefitDetailsList(customerCreateDetail.getBenefitDetail())
             //   .customerTemplateId(customerCreateDetail.getCustomerTemplate().getCustomerTemplateId())
                .correlationId(customerInbox.getCorrelationId()).customerRequestId(customerInbox.getCustomerRequestId())
                .customerId(customerInbox.getCustomerId()).build();
    }

}
