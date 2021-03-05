package com.syzegee.customer.events.domain;

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
        private CustomerDetail customerDetail;
        private CustomerAddressDetail custAddressDetail;
        private TierDetail tierDetail;
        private PackagesDetail packagesDetail;
        private BenefitDetail benefitDetail;
        private PackageBenefitsDetail packageBenefitsDetail;
}
