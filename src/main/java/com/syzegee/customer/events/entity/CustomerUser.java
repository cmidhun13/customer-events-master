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
import java.io.Serializable;
import java.util.Date;

/**
 * @author Sagar
 */
@Entity
@Data
@Table(name = "customer_user")
@XmlRootElement
@Builder
@NamedQueries({
        @NamedQuery(name = "CustomerUser.findAll", query = "SELECT c FROM CustomerUser c")
        , @NamedQuery(name = "CustomerUser.findByCustomerUserId", query = "SELECT c FROM CustomerUser c WHERE c.customerUserId = :customerUserId")
        , @NamedQuery(name = "CustomerUser.findByEmailId", query = "SELECT c FROM CustomerUser c WHERE c.emailId = :emailId")
        , @NamedQuery(name = "CustomerUser.findByFirstName", query = "SELECT c FROM CustomerUser c WHERE c.firstName = :firstName")
        , @NamedQuery(name = "CustomerUser.findByLastName", query = "SELECT c FROM CustomerUser c WHERE c.lastName = :lastName")
        , @NamedQuery(name = "CustomerUser.findByIsActive", query = "SELECT c FROM CustomerUser c WHERE c.isActive = :isActive")
        , @NamedQuery(name = "CustomerUser.findByCorrelationId", query = "SELECT c FROM CustomerUser c WHERE c.correlationId = :correlationId")
        , @NamedQuery(name = "CustomerUser.findByCreatedBy", query = "SELECT c FROM CustomerUser c WHERE c.createdBy = :createdBy")
        , @NamedQuery(name = "CustomerUser.findByCreatedDate", query = "SELECT c FROM CustomerUser c WHERE c.createdDate = :createdDate")
        , @NamedQuery(name = "CustomerUser.findByUpdatedBy", query = "SELECT c FROM CustomerUser c WHERE c.updatedBy = :updatedBy")
        , @NamedQuery(name = "CustomerUser.findByUpdatedDate", query = "SELECT c FROM CustomerUser c WHERE c.updatedDate = :updatedDate")})
public class CustomerUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_user_id")
    private Long customerUserId;
    @Size(max = 120)
    @Column(name = "email_id")
    private String emailId;
    @Size(max = 120)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 120)
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "is_active")
    private Boolean isActive;
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
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;

    @Tolerate
    public CustomerUser() {
    }

    @Tolerate
    public CustomerUser(Long customerUserId) {
        this.customerUserId = customerUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerUserId != null ? customerUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerUser)) {
            return false;
        }
        CustomerUser other = (CustomerUser) object;
        if ((this.customerUserId == null && other.customerUserId != null) || (this.customerUserId != null && !this.customerUserId.equals(other.customerUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.CustomerUser[ customerUserId=" + customerUserId + " ]";
    }

}
