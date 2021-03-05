/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CustomerDetail {
    
    private Long customerId;
    private Long organizationId;
    private Long userId;
    private String businessName;
    private String businessEmail;
    private String businessCategory;
    private String businessType;
    private String customerUserEmailId;
    private String currency;
    private String region;
    private String phoneNo;
    private boolean isActive;
    private boolean activationStatus;
    private boolean activationCode;
    private String state;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
    private String communicationPreferences;

}