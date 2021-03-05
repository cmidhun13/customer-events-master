package com.syzegee.customer.events.domain.crafter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CrafterAuhtnticatedToken {
    private String secureKey;
    private String jessionId;
    private String xsrfTOken;
}
