package com.syzegee.customer.events.model.ruleengine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ram Prasad
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetail {
    private long projectId;
    private String projectName;
}
