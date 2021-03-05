package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sagar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDetail {
        private Long customerId;
        private CustomerDetail customerDetail;
        private OrganizationDetail orgDetail;
        private CustomerAddressDetail custAddressDetail;
        private TierDetail tierDetail;
        private PackagesDetail packagesDetail;
        private BenefitDetail benefitDetail;
        private PackageBenefitsDetail packageBenefitsDetail;
}
