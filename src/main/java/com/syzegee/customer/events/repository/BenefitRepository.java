package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.CustomerBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sagar
 */
@Repository
public interface BenefitRepository extends JpaRepository<CustomerBenefit,Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param customerBenefitId
     * @description get the benefit object
     * @return Rule
     */
    @Query("from CustomerBenefit where isActive = true and customerBenefitId=:customerBenefitId")
    CustomerBenefit getRecordById(@Param("customerBenefitId") Long customerBenefitId);
}
