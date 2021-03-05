package com.syzegee.customer.events.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sagar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDomainDetails {
    private Long siteId;
    private Long customerId;
    private String siteCode;
    private String templateCode;
    private String siteName;
    private String siteDesc;
    private String siteType;
    private String domainName;
    private String logo;
    private String tagline;
    private boolean isActive;
    private String correlationId;
    private String state;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;

}
