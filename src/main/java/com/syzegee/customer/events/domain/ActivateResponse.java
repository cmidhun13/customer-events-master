package com.syzegee.customer.events.domain;

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


