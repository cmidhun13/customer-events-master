package com.syzegee.customer.events.domain.crafter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sagar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTemplate {

    private Long customerId;
    private String customerTemplateId;
}
