package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.PackagesBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sagar
 */
@Repository
public interface PackageBenefitsRepository extends JpaRepository<PackagesBenefit,Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param packageBenefitId
     * @return Rule
     */
    @Query("from PackagesBenefit where  packageBenefitId=:packageBenefitId")
    PackagesBenefit getRecordById(@Param("packageBenefitId") Long packageBenefitId);
}
