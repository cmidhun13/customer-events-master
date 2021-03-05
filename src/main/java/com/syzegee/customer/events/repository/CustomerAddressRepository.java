/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.Customer;
import com.syzegee.customer.events.entity.CustomerAddress;
import com.syzegee.customer.events.entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Sagar
 */
@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param customerAddressId
     * @return Rule
     */
    @Query("from CustomerAddress where customerAddressId=:customerAddressId")
    CustomerAddress getRecordById(@Param("customerAddressId") Long customerAddressId);

    @Query("from CustomerAddress where customerId=:customerId")
    CustomerAddress getRecordByCustomerId(@Param("customerId") Customer customerId);

}
