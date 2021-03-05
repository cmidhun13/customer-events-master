/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

/**
 * Sagar
 */
@Data
@Builder
@XmlRootElement
public class Features {

    private Item[]  item;

    @Override
    public String toString() {
        return "Features{" +
                "item=" + Arrays.toString(item) +
                '}';
    }
}
