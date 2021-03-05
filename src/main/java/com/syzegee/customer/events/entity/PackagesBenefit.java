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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Sagar
 */
@Entity
@Data
@Table(name = "packages_benefit")
@XmlRootElement
@Builder
@NamedQueries({
    @NamedQuery(name = "PackagesBenefit.findAll", query = "SELECT p FROM PackagesBenefit p")
    , @NamedQuery(name = "PackagesBenefit.findByPackageBenefitId", query = "SELECT p FROM PackagesBenefit p WHERE p.packageBenefitId = :packageBenefitId")
    , @NamedQuery(name = "PackagesBenefit.findByIsActive", query = "SELECT p FROM PackagesBenefit p WHERE p.isActive = :isActive")
    , @NamedQuery(name = "PackagesBenefit.findByCorrelationId", query = "SELECT p FROM PackagesBenefit p WHERE p.correlationId = :correlationId")
    , @NamedQuery(name = "PackagesBenefit.findByState", query = "SELECT p FROM PackagesBenefit p WHERE p.state = :state")
    , @NamedQuery(name = "PackagesBenefit.findByCreatedBy", query = "SELECT p FROM PackagesBenefit p WHERE p.createdBy = :createdBy")
    , @NamedQuery(name = "PackagesBenefit.findByCreatedDate", query = "SELECT p FROM PackagesBenefit p WHERE p.createdDate = :createdDate")
    , @NamedQuery(name = "PackagesBenefit.findByUpdatedBy", query = "SELECT p FROM PackagesBenefit p WHERE p.updatedBy = :updatedBy")
    , @NamedQuery(name = "PackagesBenefit.findByUpdatedDate", query = "SELECT p FROM PackagesBenefit p WHERE p.updatedDate = :updatedDate")})
public class PackagesBenefit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "package_benefit_id")
    private Long packageBenefitId;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "correlation_id")
    private String correlationId;
    @Column(name = "state")
    private String state;
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
    @JoinColumn(name = "customer_benefit_id", referencedColumnName = "customer_benefit_id")
    @ManyToOne(optional = false)
    private CustomerBenefit customerBenefitId;
    @JoinColumn(name = "package_id", referencedColumnName = "package_id")
    @ManyToOne(optional = false)
    private Packages packageId;
    @Tolerate
    public PackagesBenefit() {
    }
    @Tolerate
    public PackagesBenefit(Long packageBenefitId) {
        this.packageBenefitId = packageBenefitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packageBenefitId != null ? packageBenefitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PackagesBenefit)) {
            return false;
        }
        PackagesBenefit other = (PackagesBenefit) object;
        if ((this.packageBenefitId == null && other.packageBenefitId != null) || (this.packageBenefitId != null && !this.packageBenefitId.equals(other.packageBenefitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.PackagesBenefit[ packageBenefitId=" + packageBenefitId + " ]";
    }
    
}
