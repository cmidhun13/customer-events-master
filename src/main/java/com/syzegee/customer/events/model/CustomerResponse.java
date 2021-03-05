package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 *
 * @author Sagar
 */
@Data
@Builder
@AllArgsConstructor
public class CustomerResponse {

    private Long customerId;
    private String correlationId;
    private String message;
    private String customerRequestId;
    
    

    @Tolerate
    public CustomerResponse(){}
    
    @Override
    public String toString() {
    	
    	return "{"+"customerId="+customerId+",message="+message+",correlationId="+correlationId+",customerRequestId="+customerRequestId+"}";
    }
}
