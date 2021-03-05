package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserRoleRequest {

    private String correlationId;
    private Date createdDate;
    private Long customerUserId;
    private String roleType;
    private String createdBy;
    private String updatedBy;
    private Date updatedDate;
}
