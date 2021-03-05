package com.syzegee.customer.events.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Sagar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivationValidateResponse {

    private Boolean status;
    private Long customerId;
}
