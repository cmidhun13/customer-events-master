package com.syzegee.customer.events.domain;

import com.syzegee.customer.events.entity.CustomerBenefit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Sagar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackagesDetail {

    private Long id;
    private Long tierId;
    private Long customerId;
    private String packageName;
    private boolean isActive;
    private String state;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<CustomerBenefit> benefits;

}
