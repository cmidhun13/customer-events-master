/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import java.util.ArrayList;
import java.util.List;
/**
 * Sagar
 */
//@Data
//@Builder
public class CrafterGroups {
     private List<String> ids= new ArrayList<>();
    private List<String> usernames=  new ArrayList<>();

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
