package com.syzegee.customer.events.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "customer_role")
@XmlRootElement
@Builder
public class CustomerRole implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_role_id")
    private Long customerRoleId;
    @Column(name = "customer_id")
    private Long customerId;
    @Basic(optional = false)
    @Column(name = "customer_user_id")
    private Long customerUserId;
    @Column(name = "correlation_id")
    private String correlationId;
    @Column(name = "is_active")
    private Boolean isActive;
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
    @Column(name = "customer_role_type")
    private String roles;



    @Tolerate
    public CustomerRole() {
    }

}
