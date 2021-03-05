package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sagar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActivationResponse {

    private Long customerId;
    private String emailId;
    private String correlationId;
    private String activation_code;
    
    @Override
    public String toString() {
    	
    	return "{"+"customerId="+customerId+",emailId="+emailId+",correlationId="+correlationId+",activation_code="+activation_code+"}";
    }
}
