/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.model.crafter;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Sagar
 */
@Data
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Page {
    
    @XmlElement(name = "content-type")
    private String content_type;
    private String placeInNav;
    @XmlElement(name = "display-template")
    private String display_template;
    @XmlElement(name = "merge-strategy")
    private String merge_strategy ;
    @XmlElement(name = "file-name")
    private String file_name ;
    @XmlElement(name = "internal-name")
    private String internal_name ;
    private String orderDefault_f;
    private String header;
    private String content;
    private String description;
    private String objectGroupId;
    private String objectId;
    @XmlElement(name = "folder-name")
    private String folder_name;
    private String createdDate;
    private String createdDate_dt;
    private String lastModifiedDate;
    private String lastModifiedDate_dt;
    private String menu_bar_link_1;
    private String menu_bar_link_2;
    private String menu_bar_link_3;
    private String menu_bar_link_4;
    private String menu_bar_link_5;
    private String logo_image;
    private String hero_title;
    private String hero_text;
    private String generation_card_title;
    private String generation_card_text;
    private String generation_card_image;

    private String generation_title_1;
    private String generation_text_1;
    private String generation_title_2;
    private String generation_text_2;
    private String generation_title_3;
    private String generation_text_3;

    private String royal_title;
    private String royal_text;

    private String anheuser_title;
    private String anheuser_text;
    private String carousel_title;

    private String carousel_image_1;
    private String carousel_image_2;
    private String carousel_image_3;
    private String carousel_image_4;

    private Features features;
    private String copyright_text;
    private String copyright_link_1;
    private String copyright_link_2;
    private String copyright_link_3;
    private String disabled;


    @Override
    public String toString() {
        return "Page{" + "content_type=" + content_type + ", placeInNav=" + placeInNav + ", display_template=" + display_template + ", merge_strategy=" + merge_strategy + ", file_name=" + file_name + ", internal_name=" + internal_name + ", orderDefault_f=" + orderDefault_f + ", header=" + header + ", content=" + content + ", description=" + description + ", objectGroupId=" + objectGroupId + ", objectId=" + objectId + ", folder_name=" + folder_name + ", createdDate=" + createdDate + ", createdDate_dt=" + createdDate_dt + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedDate_dt=" + lastModifiedDate_dt + ", menu_bar_link_1=" + menu_bar_link_1 + ", menu_bar_link_2=" + menu_bar_link_2 + ", menu_bar_link_3=" + menu_bar_link_3 + ", menu_bar_link_4=" + menu_bar_link_4 + ", menu_bar_link_5=" + menu_bar_link_5 + ", logo_image=" + logo_image + ", hero_title=" + hero_title + ", hero_text=" + hero_text + ", generation_card_title=" + generation_card_title + ", generation_card_text=" + generation_card_text + ", generation_card_image=" + generation_card_image + ", generation_title_1=" + generation_title_1 + ", generation_text_1=" + generation_text_1 + ", generation_title_2=" + generation_title_2 + ", generation_text_2=" + generation_text_2 + ", generation_title_3=" + generation_title_3 + ", generation_text_3=" + generation_text_3 + ", royal_title=" + royal_title + ", royal_text=" + royal_text + ", anheuser_title=" + anheuser_title + ", anheuser_text=" + anheuser_text + ", carousel_title=" + carousel_title + ", carousel_image_1=" + carousel_image_1 + ", carousel_image_2=" + carousel_image_2 + ", carousel_image_3=" + carousel_image_3 + ", carousel_image_4=" + carousel_image_4 + ", features=" + features + ", copyright_text=" + copyright_text + ", copyright_link_1=" + copyright_link_1 + ", copyright_link_2=" + copyright_link_2 + ", copyright_link_3=" + copyright_link_3 + ", disabled=" + disabled + '}';
    }
}
