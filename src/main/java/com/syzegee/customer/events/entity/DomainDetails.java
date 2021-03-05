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
@Table(name = "domain_details")
@XmlRootElement
@Builder
@NamedQueries({
    @NamedQuery(name = "DomainDetails.findAll", query = "SELECT d FROM DomainDetails d")
    , @NamedQuery(name = "DomainDetails.findBySiteId", query = "SELECT d FROM DomainDetails d WHERE d.siteId = :siteId")
    , @NamedQuery(name = "DomainDetails.findBySiteCode", query = "SELECT d FROM DomainDetails d WHERE d.siteCode = :siteCode")
    , @NamedQuery(name = "DomainDetails.findByTemplateCode", query = "SELECT d FROM DomainDetails d WHERE d.templateCode = :templateCode")
    , @NamedQuery(name = "DomainDetails.findBySiteName", query = "SELECT d FROM DomainDetails d WHERE d.siteName = :siteName")
    , @NamedQuery(name = "DomainDetails.findBySiteDesc", query = "SELECT d FROM DomainDetails d WHERE d.siteDesc = :siteDesc")
     ,@NamedQuery(name = "DomainDetails.findBySiteType", query = "SELECT d FROM DomainDetails d WHERE d.siteType = :siteType")
    , @NamedQuery(name = "DomainDetails.findByDomainName", query = "SELECT d FROM DomainDetails d WHERE d.domainName = :domainName")
    , @NamedQuery(name = "DomainDetails.findByLogo", query = "SELECT d FROM DomainDetails d WHERE d.logo = :logo")
    , @NamedQuery(name = "DomainDetails.findByTagline", query = "SELECT d FROM DomainDetails d WHERE d.tagline = :tagline")
    , @NamedQuery(name = "DomainDetails.findByIsActive", query = "SELECT d FROM DomainDetails d WHERE d.isActive = :isActive")
    , @NamedQuery(name = "DomainDetails.findByCorrelationId", query = "SELECT d FROM DomainDetails d WHERE d.correlationId = :correlationId")
    , @NamedQuery(name = "DomainDetails.findByState", query = "SELECT d FROM DomainDetails d WHERE d.state = :state")
    , @NamedQuery(name = "DomainDetails.findByCreatedBy", query = "SELECT d FROM DomainDetails d WHERE d.createdBy = :createdBy")
    , @NamedQuery(name = "DomainDetails.findByCreatedDate", query = "SELECT d FROM DomainDetails d WHERE d.createdDate = :createdDate")
    , @NamedQuery(name = "DomainDetails.findByUpdatedBy", query = "SELECT d FROM DomainDetails d WHERE d.updatedBy = :updatedBy")
    , @NamedQuery(name = "DomainDetails.findByUpdatedDate", query = "SELECT d FROM DomainDetails d WHERE d.updatedDate = :updatedDate")})
public class DomainDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "site_id")
    private Long siteId;
    @Basic(optional = false)
    @Column(name = "site_code")
    private String siteCode;
    @Basic(optional = false)
    @Column(name = "template_code")
    private String templateCode;
    @Column(name = "site_name")
    private String siteName;
    @Column(name = "site_desc")
    private String siteDesc;
    @Column(name = "site_type")
    private String siteType;
    @Column(name = "domain_name")
    private String domainName;
    @Column(name = "logo")
    private String logo;
    @Column(name = "tagline")
    private String tagline;
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
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;
    @Tolerate
    public DomainDetails() {
    }
    @Tolerate
    public DomainDetails(Long siteId) {
        this.siteId = siteId;
    }
    @Tolerate
    public DomainDetails(Long siteId, String siteCode, String templateCode) {
        this.siteId = siteId;
        this.siteCode = siteCode;
        this.templateCode = templateCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siteId != null ? siteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DomainDetails)) {
            return false;
        }
        DomainDetails other = (DomainDetails) object;
        if ((this.siteId == null && other.siteId != null) || (this.siteId != null && !this.siteId.equals(other.siteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.DomainDetails[ siteId=" + siteId + " ]";
    }
    
}
