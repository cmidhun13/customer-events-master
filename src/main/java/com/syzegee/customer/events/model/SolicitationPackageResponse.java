package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitationPackageResponse {

	private Integer solicitationId;
	private Integer customerId;
	private Integer packageId;
	private Date startForm;
	private Date endDate;
	private boolean isActive;
	private String createdBy;
	private Date createdAt;
	private String updatedBy;
	private Date updatedAt;

}
