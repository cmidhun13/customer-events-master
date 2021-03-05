/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Sagar
 */
//@Data
//@Builder
@XmlRootElement
public class Item {
    private String include;
    private String value;
    private String disableFlattening;
    private String key;

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisableFlattening() {
        return disableFlattening;
    }

    public void setDisableFlattening(String disableFlattening) {
        this.disableFlattening = disableFlattening;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Item{" +
                "include='" + include + '\'' +
                ", value='" + value + '\'' +
                ", disableFlattening='" + disableFlattening + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
