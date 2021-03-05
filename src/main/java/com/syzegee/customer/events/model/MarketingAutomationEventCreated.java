package com.syzegee.customer.events.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarketingAutomationEventCreated {
    private String customerUserName;
    private String customerEmail;
    private String customerOrganizationName;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String state;
    private String correlationId;
    private String siteName;
    private String siteType;
    private String domainName;
    private String templateCode;

}
