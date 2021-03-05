/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.domain.crafter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * Sagar
 */
//@Data
//@Builder
@XmlRootElement
public class Features {

    private Item[]  item;

    public Item[] getItem() {
        return item;
    }

    public void setItem(Item[] item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Features{" +
                "item=" + Arrays.toString(item) +
                '}';
    }
}
