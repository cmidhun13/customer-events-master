package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 *
 * @author Sagar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageBenefitsDetail {
    private Long id;
    private Long packageId;
    private Long benefitId;
    private boolean isActive;
    private String state;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
