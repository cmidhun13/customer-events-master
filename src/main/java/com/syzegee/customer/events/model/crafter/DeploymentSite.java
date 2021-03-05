package com.syzegee.customer.events.model.crafter;

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
public class DeploymentSite {
    private String env;
    private String site_name;
    private Boolean replace;
    private String template_name;
    private String repo_url;
    private String repo_branch;
    private String engine_url;

}
