package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sagar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivationDetails {
    private CustomerDetail customerDetail;
    private OrganizationDetail orgDetail;
    private CustomerUserDetail userDetail;
}
