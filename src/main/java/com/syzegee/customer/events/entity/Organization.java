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
@Table(name = "organization")
@XmlRootElement
@Builder
@NamedQueries({
        @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o")
        , @NamedQuery(name = "Organization.findByOrganizationId", query = "SELECT o FROM Organization o WHERE o.organizationId = :organizationId")
        , @NamedQuery(name = "Organization.findByOrganizationName", query = "SELECT o FROM Organization o WHERE o.organizationName = :organizationName")
        , @NamedQuery(name = "Organization.findByOrganizationDesc", query = "SELECT o FROM Organization o WHERE o.organizationDesc = :organizationDesc")
        , @NamedQuery(name = "Organization.findByOrganizationSize", query = "SELECT o FROM Organization o WHERE o.organizationSize = :organizationSize")
        , @NamedQuery(name = "Organization.findByIsActive", query = "SELECT o FROM Organization o WHERE o.isActive = :isActive")
        , @NamedQuery(name = "Organization.findByCorrelationId", query = "SELECT o FROM Organization o WHERE o.correlationId = :correlationId")
        , @NamedQuery(name = "Organization.findByState", query = "SELECT o FROM Organization o WHERE o.state = :state")
        , @NamedQuery(name = "Organization.findByCreatedBy", query = "SELECT o FROM Organization o WHERE o.createdBy = :createdBy")
        , @NamedQuery(name = "Organization.findByCreatedDate", query = "SELECT o FROM Organization o WHERE o.createdDate = :createdDate")
        , @NamedQuery(name = "Organization.findByUpdatedBy", query = "SELECT o FROM Organization o WHERE o.updatedBy = :updatedBy")
        , @NamedQuery(name = "Organization.findByUpdatedDate", query = "SELECT o FROM Organization o WHERE o.updatedDate = :updatedDate")})
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organization_id")
    private Long organizationId;
    @Size(max = 120)
    @Column(name = "organization_name")
    private String organizationName;
    @Size(max = 120)
    @Column(name = "organization_desc")
    private String organizationDesc;
    @Size(max = 120)
    @Column(name = "organization_size")
    private String organizationSize;
    @Column(name = "is_active")
    private Boolean isActive;
    @Size(max = 120)
    @Column(name = "correlation_id")
    private String correlationId;
    @Size(max = 120)
    @Column(name = "state")
    private String state;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizationId")
    private Collection<Customer> customerCollection;

    @Tolerate
    public Organization() {
    }

    @Tolerate
    public Organization(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Tolerate
    public Organization(Long organizationId, String organizationSize) {
        this.organizationId = organizationId;
        this.organizationSize = organizationSize;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizationId != null ? organizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.organizationId == null && other.organizationId != null) || (this.organizationId != null && !this.organizationId.equals(other.organizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.Organization[ organizationId=" + organizationId + " ]";
    }

}
