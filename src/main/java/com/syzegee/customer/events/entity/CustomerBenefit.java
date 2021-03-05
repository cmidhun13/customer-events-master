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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Sagar
 */
@Entity
@Data
@Table(name = "customer_benefit")
@XmlRootElement
@Builder
@NamedQueries({
    @NamedQuery(name = "CustomerBenefit.findAll", query = "SELECT c FROM CustomerBenefit c")
    , @NamedQuery(name = "CustomerBenefit.findByCustomerBenefitId", query = "SELECT c FROM CustomerBenefit c WHERE c.customerBenefitId = :customerBenefitId")
    , @NamedQuery(name = "CustomerBenefit.findByVendorId", query = "SELECT c FROM CustomerBenefit c WHERE c.vendorId = :vendorId")
    , @NamedQuery(name = "CustomerBenefit.findByBenefitName", query = "SELECT c FROM CustomerBenefit c WHERE c.benefitName = :benefitName")
    ,@NamedQuery(name = "CustomerBenefit.findByState", query = "SELECT c FROM CustomerBenefit c WHERE c.state = :state")
    , @NamedQuery(name = "CustomerBenefit.findByCorrelationId", query = "SELECT c FROM CustomerBenefit c WHERE c.correlationId = :correlationId")
    , @NamedQuery(name = "CustomerBenefit.findByIsActive", query = "SELECT c FROM CustomerBenefit c WHERE c.isActive = :isActive")
    , @NamedQuery(name = "CustomerBenefit.findByCreatedBy", query = "SELECT c FROM CustomerBenefit c WHERE c.createdBy = :createdBy")
    , @NamedQuery(name = "CustomerBenefit.findByCreatedDate", query = "SELECT c FROM CustomerBenefit c WHERE c.createdDate = :createdDate")
    , @NamedQuery(name = "CustomerBenefit.findByUpdatedBy", query = "SELECT c FROM CustomerBenefit c WHERE c.updatedBy = :updatedBy")
    , @NamedQuery(name = "CustomerBenefit.findByUpdatedDate", query = "SELECT c FROM CustomerBenefit c WHERE c.updatedDate = :updatedDate")})
public class CustomerBenefit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_benefit_id")
    private Long customerBenefitId;
    @Basic(optional = false)
    @Column(name = "vendor_id")
    private long vendorId;
    @Column(name = "benefit_name")
    private String benefitName;
    @Column(name = "state")
    private String state;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "redirect_url")
    private String redirectUrl;
    @Column(name = "description")
    private String description;
    @Column(name = "correlation_id")
    private String correlationId;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerBenefitId")
    private Collection<PackagesBenefit> packagesBenefitCollection;
    @Tolerate
    public CustomerBenefit() {
    }
    @Tolerate
    public CustomerBenefit(Long customerBenefitId) {
        this.customerBenefitId = customerBenefitId;
    }
    @Tolerate
    public CustomerBenefit(Long customerBenefitId, long vendorId) {
        this.customerBenefitId = customerBenefitId;
        this.vendorId = vendorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerBenefitId != null ? customerBenefitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerBenefit)) {
            return false;
        }
        CustomerBenefit other = (CustomerBenefit) object;
        if ((this.customerBenefitId == null && other.customerBenefitId != null) || (this.customerBenefitId != null && !this.customerBenefitId.equals(other.customerBenefitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.CustomerBenefit[ customerBenefitId=" + customerBenefitId + " ]";
    }
    
}
