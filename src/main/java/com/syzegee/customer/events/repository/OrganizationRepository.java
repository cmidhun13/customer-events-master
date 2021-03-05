/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.repository;

import com.syzegee.customer.events.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author Sagar
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param organizationId
     * @return Organization
     */
    @Query("from Organization where isActive = true and organizationId=:organizationId")
    Organization getRecordById(@Param("organizationId") Long organizationId);

    /**
     * Custom query for getRecordById only getting object where isActive=true
     *
     * @param organizationName
     * @return Organization
     */
    @Query("from Organization where isActive = true and organizationName=:organizationName")
    Organization getRecordByName(@Param("organizationName") String organizationName);
 }
