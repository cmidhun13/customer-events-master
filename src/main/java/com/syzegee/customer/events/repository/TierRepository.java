package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sagar
 */
@Repository
public interface TierRepository extends JpaRepository<Tier,Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param tierId
     * @return Rule
     */
    @Query("from Tier where tierId=:tierId")
    Tier getRecordById(@Param("tierId") Long tierId);
}
