package com.syzegee.customer.events.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "customer_status")
@Builder
public class CustomerStatus  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_status_id")
    private Long customerStatusId;
    @Basic(optional = false)
    @Column(name = "customer_id")
    private long customerId;
    @Column(name = "correlation_id")
    private String correlationId;
    @Tolerate
    public CustomerStatus(Long customerStatusId, long customerId, String correlationId, Boolean onBoardStatus, Boolean cmsSiteUpdateStatus, Boolean marketingAutomationStatus) {
        this.customerStatusId = customerStatusId;
        this.customerId = customerId;
        this.correlationId = correlationId;
        this.onBoardStatus = onBoardStatus;
        this.cmsSiteUpdateStatus = cmsSiteUpdateStatus;
        this.marketingAutomationStatus = marketingAutomationStatus;
    }

    @Column(name = "on_boarding_status")
    private Boolean onBoardStatus;
    @Column(name = "cms_site_update_status")
    private Boolean cmsSiteUpdateStatus;
    @Column(name = "marketing_automation_status")
    private Boolean marketingAutomationStatus;
    public CustomerStatus() {
    }
    @Tolerate
    public CustomerStatus(Long customerStatusId) {
        this.customerStatusId = customerStatusId;
    }
    @Tolerate
    public CustomerStatus(Long customerStatusId, long customerId) {
        this.customerStatusId = customerStatusId;
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerStatusId != null ? customerStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerStatus)) {
            return false;
        }
        CustomerStatus other = (CustomerStatus) object;
        if ((this.customerStatusId == null && other.customerStatusId != null) || (this.customerStatusId != null && !this.customerStatusId.equals(other.customerStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.syzegee.customer.service.entity.CustomerStatus[ customerStatusId=" + customerStatusId + " ]";
    }

}
