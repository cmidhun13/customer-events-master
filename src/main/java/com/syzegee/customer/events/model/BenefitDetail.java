package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDetail {

    private Long id;
    private Long customerId;
    private List<Long> vendorId;
    private String state;
    private String benefitName;
    private boolean active;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String imageUrl;
    private String redirectUrl;
    private String description;
    private boolean newlyAdded;
    private boolean deleted;

    private CustomerDomainDetails domainDetails;

    public BenefitDetail(Long id, String benefitName) {
    }
}
