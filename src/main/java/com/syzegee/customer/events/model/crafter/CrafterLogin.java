/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * Sagar
 */
@Data
@Builder
@AllArgsConstructor
public class CrafterLogin implements Serializable {

    private String username;
    private String password;
    private String firstname;
    private String customerId;
    private String secureKey;
    private String email;
    private String lastname;
    private String groups;

    @Tolerate
    public CrafterLogin(){}

}
