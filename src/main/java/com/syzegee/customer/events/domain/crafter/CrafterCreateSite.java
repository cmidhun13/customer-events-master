/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.domain.crafter;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Sagar
 */
@Data
@Builder
public class CrafterCreateSite {

    private String authentication_type;
    private String blueprint;
    private String create_option;
    private String description;
    private String remote_name;
    private String remote_password;
    private String remote_url;
    private String remote_username;
    private String site_id;
    private Boolean use_remote;
    private Long customerId;

    @Tolerate
    public CrafterCreateSite(){

    }

    public String getAuthentication_type() {
        return authentication_type;
    }

    public void setAuthentication_type(String authentication_type) {
        this.authentication_type = authentication_type;
    }

    public String getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(String blueprint) {
        this.blueprint = blueprint;
    }

    public String getCreate_option() {
        return create_option;
    }

    public void setCreate_option(String create_option) {
        this.create_option = create_option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemote_name() {
        return remote_name;
    }

    public void setRemote_name(String remote_name) {
        this.remote_name = remote_name;
    }

    public String getRemote_password() {
        return remote_password;
    }

    public void setRemote_password(String remote_password) {
        this.remote_password = remote_password;
    }

    public String getRemote_url() {
        return remote_url;
    }

    public void setRemote_url(String remote_url) {
        this.remote_url = remote_url;
    }

    public String getRemote_username() {
        return remote_username;
    }

    public void setRemote_username(String remote_username) {
        this.remote_username = remote_username;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public Boolean getUse_remote() {
        return use_remote;
    }

    public void setUse_remote(Boolean use_remote) {
        this.use_remote = use_remote;
    }
 
}
