package com.syzegee.customer.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Riya Patel
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateResponse {
    private Long customerId;
}


