package com.syzegee.customer.events.model.crafter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CrafterAuhtnticatedToken {
    private String secureKey;
    private String jessionId;
    private String xsrfTOken;
}
