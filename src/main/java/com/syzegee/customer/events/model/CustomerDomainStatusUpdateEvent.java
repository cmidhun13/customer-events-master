package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDomainStatusUpdateEvent {
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
        private Date createdDate;
        private String createdBy;
        private String updatedBy;
        private Date updatedDate;
        private boolean active;
 }
