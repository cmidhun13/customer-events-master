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
public class CustomerUserDetail {

    private Long customerUserId;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Boolean isActive;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

}
