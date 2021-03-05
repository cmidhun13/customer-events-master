package com.syzegee.customer.events.model;

import com.syzegee.customer.events.model.crafter.CustomerTemplate;
import com.syzegee.customer.events.model.ruleengine.CustomerRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;


import java.util.List;

/**
 * @author Sagar
 */
@Data
@Builder
@AllArgsConstructor
public class CustomerCreateDetail {
    private OrganizationDetail orgDetail;
    private CustomerDetail customerDetail;
    private CustomerAddressDetail custAddressDetail;
    private TierDetail tierDetail;
    private List<BenefitDetail> benefitDetail;
    private List<PackagesDetail> packagesDetail;
    private CustomerDomainDetails domainDetails;
    private CustomerRule customerRule;
    private CustomerTemplate customerTemplate;
    private List<SolicitationDetail> solicitationDetail;

    @Tolerate
    public CustomerCreateDetail() {

    }
}
