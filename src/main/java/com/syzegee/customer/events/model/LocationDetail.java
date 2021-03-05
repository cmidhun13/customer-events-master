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
public class LocationDetail {

    private Long locationId;
    private long accountId;
    private String locationName;
    private Boolean isActive;
    private String state;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
