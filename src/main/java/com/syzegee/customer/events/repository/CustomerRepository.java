/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param customerId
     * @return Rule
     */
    @Query("from Customer where customerId=:customerId")
    Customer getRecordById(@Param("customerId") Long customerId);

    @Query("from Customer where isActive=true and customerId=:customerId")
    Customer getActiveCustomerById(@Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE customer u set user_id =:userId where u.customer_id = :customerId",
            nativeQuery = true)
    void updateUserIdForCustomer(@Param("userId") Long userId,@Param("customerId") Long customerId);

}
