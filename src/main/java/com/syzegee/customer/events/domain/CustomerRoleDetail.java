/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.domain;

import com.syzegee.customer.events.entity.Customer;
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
public class CustomerRoleDetail {
   
    private String customerRoleType;
    private Boolean isActive;
    private String state;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private Customer customerId;

}
