/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * Sagar
 */
@Data
@Builder
public class CrafterUser implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private Boolean externallyManaged;

    @Tolerate
    public CrafterUser(){}

}
