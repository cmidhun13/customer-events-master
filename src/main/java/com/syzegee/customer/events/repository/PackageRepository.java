package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sagar
 */
@Repository
public interface PackageRepository extends JpaRepository<Packages,Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param packageId
     * @return Rule
     */
    @Query("from Packages where packageId=:packageId")
    Packages getRecordById(@Param("packageId") Long packageId);
}
