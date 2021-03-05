package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.syzegee.customer.events.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param customerId
     * @return Rule
     */
    @Query("from CustomerStatus where customerId=:customerId")
    Customer getStatusById(@Param("customerId") Long customerId);

}

