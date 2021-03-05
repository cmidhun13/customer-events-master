package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long> {

    @Query("from CustomerRole where customerId=:customerId")
    CustomerRole getRecordById(@Param("customerId") Long customerId);

}
