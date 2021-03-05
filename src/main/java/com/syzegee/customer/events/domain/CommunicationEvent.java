package com.syzegee.customer.events.domain;

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
public class CommunicationEvent {

    private Long customerId;
    private String user_id;
    private String correlationId;
    private String activation_code;

}
