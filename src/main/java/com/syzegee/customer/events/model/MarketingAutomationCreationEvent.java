package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingAutomationCreationEvent {
    private String customerUserName;
    private String customerEmail;
    private String customerOrganizationName;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String state;
    private String correlationId;
}
