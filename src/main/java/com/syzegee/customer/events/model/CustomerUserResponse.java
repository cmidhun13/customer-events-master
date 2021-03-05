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
public class CustomerUserResponse {

    private String correlationId;
    private Date createdDate;
    private String emailId;
    private Boolean isActive;
    private String createdBy;
    private List<CustomerUserRoleResponse> roles;



}
