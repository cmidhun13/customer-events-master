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

/**
 *
 * @author Sagar
 */
@Entity
@Data
@Table(name = "customer_inbox")
@XmlRootElement
@Builder
@NamedQueries({
    @NamedQuery(name = "CustomerInbox.findAll", query = "SELECT c FROM CustomerInbox c")
    , @NamedQuery(name = "CustomerInbox.findByCustomerInboxId", query = "SELECT c FROM CustomerInbox c WHERE c.customerInboxId = :customerInboxId")
    , @NamedQuery(name = "CustomerInbox.findByCustomerId", query = "SELECT c FROM CustomerInbox c WHERE c.customerId = :customerId")
    , @NamedQuery(name = "CustomerInbox.findByCorrelationId", query = "SELECT c FROM CustomerInbox c WHERE c.correlationId = :correlationId")
    , @NamedQuery(name = "CustomerInbox.findByCustomerRequestId", query = "SELECT c FROM CustomerInbox c WHERE c.customerRequestId = :customerRequestId")
    , @NamedQuery(name = "CustomerInbox.findByStatus", query = "SELECT c FROM CustomerInbox c WHERE c.status = :status")
    , @NamedQuery(name = "CustomerInbox.findByDetails", query = "SELECT c FROM CustomerInbox c WHERE c.details = :details")})
public class CustomerInbox implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_inbox_id")
    private Long customerInboxId;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private long customerId;
    @Column(name = "correlation_id")
    private String correlationId;
    @Column(name = "customer_request_id")
    private String customerRequestId;
    @Column(name = "status")
    private String status;
    @Column(name = "details")
    private String details;
    @Tolerate
    public CustomerInbox() {
    }
    @Tolerate
    public CustomerInbox(Long customerInboxId) {
        this.customerInboxId = customerInboxId;
    }
    @Tolerate
    public CustomerInbox(Long customerInboxId, long customerId) {
        this.customerInboxId = customerInboxId;
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerInboxId != null ? customerInboxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerInbox)) {
            return false;
        }
        CustomerInbox other = (CustomerInbox) object;
        if ((this.customerInboxId == null && other.customerInboxId != null) || (this.customerInboxId != null && !this.customerInboxId.equals(other.customerInboxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.events.entity.CustomerInbox[ customerInboxId=" + customerInboxId + " ]";
    }
    
}
