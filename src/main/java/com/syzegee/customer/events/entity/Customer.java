/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @author Sagar
 */
@Entity
@Data
@Table(name = "customer")
@XmlRootElement
@Builder()
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
        , @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId")
        , @NamedQuery(name = "Customer.findByUserId", query = "SELECT c FROM Customer c WHERE c.userId = :userId")
        , @NamedQuery(name = "Customer.findByState", query = "SELECT c FROM Customer c WHERE c.state = :state")
        , @NamedQuery(name = "Customer.findByBusinessName", query = "SELECT c FROM Customer c WHERE c.businessName = :businessName")
        , @NamedQuery(name = "Customer.findByBusinessEmail", query = "SELECT c FROM Customer c WHERE c.businessEmail = :businessEmail")
        , @NamedQuery(name = "Customer.findByBusinessType", query = "SELECT c FROM Customer c WHERE c.businessType = :businessType")
        , @NamedQuery(name = "Customer.findByBusinessCategory", query = "SELECT c FROM Customer c WHERE c.businessCategory = :businessCategory")
        , @NamedQuery(name = "Customer.findByRegion", query = "SELECT c FROM Customer c WHERE c.region = :region")
        , @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone")
        , @NamedQuery(name = "Customer.findByCurrency", query = "SELECT c FROM Customer c WHERE c.currency = :currency")
        , @NamedQuery(name = "Customer.findByIsActive", query = "SELECT c FROM Customer c WHERE c.isActive = :isActive")
        , @NamedQuery(name = "Customer.findByActivationStatus", query = "SELECT c FROM Customer c WHERE c.activationStatus = :activationStatus")
        , @NamedQuery(name = "Customer.findByActivationCode", query = "SELECT c FROM Customer c WHERE c.activationCode = :activationCode")
        , @NamedQuery(name = "Customer.findByCorrelationId", query = "SELECT c FROM Customer c WHERE c.correlationId = :correlationId")
        , @NamedQuery(name = "Customer.findByCreatedBy", query = "SELECT c FROM Customer c WHERE c.createdBy = :createdBy")
        , @NamedQuery(name = "Customer.findByCreatedDate", query = "SELECT c FROM Customer c WHERE c.createdDate = :createdDate")
        , @NamedQuery(name = "Customer.findByUpdatedBy", query = "SELECT c FROM Customer c WHERE c.updatedBy = :updatedBy")
        , @NamedQuery(name = "Customer.findByUpdatedDate", query = "SELECT c FROM Customer c WHERE c.updatedDate = :updatedDate")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Long customerId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;
    @Size(max = 120)
    @Column(name = "state")
    private String state;
    @Size(max = 1024)
    @Column(name = "business_name")
    private String businessName;
    @Size(max = 1024)
    @Column(name = "business_email")
    private String businessEmail;
    @Size(max = 120)
    @Column(name = "business_type")
    private String businessType;
    @Size(max = 120)
    @Column(name = "business_category")
    private String businessCategory;
    @Size(max = 120)
    @Column(name = "region")
    private String region;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 120)
    @Column(name = "phone")
    private String phone;
    @Size(max = 120)
    @Column(name = "currency")
    private String currency;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "activation_status")
    private Boolean activationStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "activation_code")
    private String activationCode;
    @Size(max = 120)
    @Column(name = "correlation_id")
    private String correlationId;
    @Size(max = 120)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 120)
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "communication_preferences")
    private String communicationPreferences;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerAddress> customerAddressCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerBenefit> customerBenefitCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<Tier> tierCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerUser> customerUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<DomainDetails> domainDetailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<Packages> packagesCollection;
    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id")
    @ManyToOne(optional = false)
    private Organization organizationId;

    @Tolerate
    public Customer() {
    }

    @Tolerate
    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    @Tolerate
    public Customer(Long customerId, Long userId, String activationCode) {
        this.customerId = customerId;
        this.userId = userId;
        this.activationCode = activationCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.Customer[ customerId=" + customerId + " ]";
    }

}
