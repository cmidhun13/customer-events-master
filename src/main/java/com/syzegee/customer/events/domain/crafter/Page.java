/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.domain.crafter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Sagar
 */
//@Data
//@Builder
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

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getPlaceInNav() {
        return placeInNav;
    }

    public void setPlaceInNav(String placeInNav) {
        this.placeInNav = placeInNav;
    }

    public String getDisplay_template() {
        return display_template;
    }

    public void setDisplay_template(String display_template) {
        this.display_template = display_template;
    }

    public String getMerge_strategy() {
        return merge_strategy;
    }

    public void setMerge_strategy(String merge_strategy) {
        this.merge_strategy = merge_strategy;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getInternal_name() {
        return internal_name;
    }

    public void setInternal_name(String internal_name) {
        this.internal_name = internal_name;
    }

    public String getOrderDefault_f() {
        return orderDefault_f;
    }

    public void setOrderDefault_f(String orderDefault_f) {
        this.orderDefault_f = orderDefault_f;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectGroupId() {
        return objectGroupId;
    }

    public void setObjectGroupId(String objectGroupId) {
        this.objectGroupId = objectGroupId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate_dt() {
        return createdDate_dt;
    }

    public void setCreatedDate_dt(String createdDate_dt) {
        this.createdDate_dt = createdDate_dt;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedDate_dt() {
        return lastModifiedDate_dt;
    }

    public void setLastModifiedDate_dt(String lastModifiedDate_dt) {
        this.lastModifiedDate_dt = lastModifiedDate_dt;
    }

    public String getMenu_bar_link_1() {
        return menu_bar_link_1;
    }

    public void setMenu_bar_link_1(String menu_bar_link_1) {
        this.menu_bar_link_1 = menu_bar_link_1;
    }

    public String getMenu_bar_link_2() {
        return menu_bar_link_2;
    }

    public void setMenu_bar_link_2(String menu_bar_link_2) {
        this.menu_bar_link_2 = menu_bar_link_2;
    }

    public String getMenu_bar_link_3() {
        return menu_bar_link_3;
    }

    public void setMenu_bar_link_3(String menu_bar_link_3) {
        this.menu_bar_link_3 = menu_bar_link_3;
    }

    public String getMenu_bar_link_4() {
        return menu_bar_link_4;
    }

    public void setMenu_bar_link_4(String menu_bar_link_4) {
        this.menu_bar_link_4 = menu_bar_link_4;
    }

    public String getMenu_bar_link_5() {
        return menu_bar_link_5;
    }

    public void setMenu_bar_link_5(String menu_bar_link_5) {
        this.menu_bar_link_5 = menu_bar_link_5;
    }

    public String getLogo_image() {
        return logo_image;
    }

    public void setLogo_image(String logo_image) {
        this.logo_image = logo_image;
    }

    public String getHero_title() {
        return hero_title;
    }

    public void setHero_title(String hero_title) {
        this.hero_title = hero_title;
    }

    public String getHero_text() {
        return hero_text;
    }

    public void setHero_text(String hero_text) {
        this.hero_text = hero_text;
    }

    public String getGeneration_card_title() {
        return generation_card_title;
    }

    public void setGeneration_card_title(String generation_card_title) {
        this.generation_card_title = generation_card_title;
    }

    public String getGeneration_card_text() {
        return generation_card_text;
    }

    public void setGeneration_card_text(String generation_card_text) {
        this.generation_card_text = generation_card_text;
    }

    public String getGeneration_card_image() {
        return generation_card_image;
    }

    public void setGeneration_card_image(String generation_card_image) {
        this.generation_card_image = generation_card_image;
    }

    public String getGeneration_title_1() {
        return generation_title_1;
    }

    public void setGeneration_title_1(String generation_title_1) {
        this.generation_title_1 = generation_title_1;
    }

    public String getGeneration_text_1() {
        return generation_text_1;
    }

    public void setGeneration_text_1(String generation_text_1) {
        this.generation_text_1 = generation_text_1;
    }

    public String getGeneration_title_2() {
        return generation_title_2;
    }

    public void setGeneration_title_2(String generation_title_2) {
        this.generation_title_2 = generation_title_2;
    }

    public String getGeneration_text_2() {
        return generation_text_2;
    }

    public void setGeneration_text_2(String generation_text_2) {
        this.generation_text_2 = generation_text_2;
    }

    public String getGeneration_title_3() {
        return generation_title_3;
    }

    public void setGeneration_title_3(String generation_title_3) {
        this.generation_title_3 = generation_title_3;
    }

    public String getGeneration_text_3() {
        return generation_text_3;
    }

    public void setGeneration_text_3(String generation_text_3) {
        this.generation_text_3 = generation_text_3;
    }

    public String getRoyal_title() {
        return royal_title;
    }

    public void setRoyal_title(String royal_title) {
        this.royal_title = royal_title;
    }

    public String getRoyal_text() {
        return royal_text;
    }

    public void setRoyal_text(String royal_text) {
        this.royal_text = royal_text;
    }

    public String getAnheuser_title() {
        return anheuser_title;
    }

    public void setAnheuser_title(String anheuser_title) {
        this.anheuser_title = anheuser_title;
    }

    public String getAnheuser_text() {
        return anheuser_text;
    }

    public void setAnheuser_text(String anheuser_text) {
        this.anheuser_text = anheuser_text;
    }

    public String getCarousel_title() {
        return carousel_title;
    }

    public void setCarousel_title(String carousel_title) {
        this.carousel_title = carousel_title;
    }

    public String getCarousel_image_1() {
        return carousel_image_1;
    }

    public void setCarousel_image_1(String carousel_image_1) {
        this.carousel_image_1 = carousel_image_1;
    }

    public String getCarousel_image_2() {
        return carousel_image_2;
    }

    public void setCarousel_image_2(String carousel_image_2) {
        this.carousel_image_2 = carousel_image_2;
    }

    public String getCarousel_image_3() {
        return carousel_image_3;
    }

    public void setCarousel_image_3(String carousel_image_3) {
        this.carousel_image_3 = carousel_image_3;
    }

    public String getCarousel_image_4() {
        return carousel_image_4;
    }

    public void setCarousel_image_4(String carousel_image_4) {
        this.carousel_image_4 = carousel_image_4;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getCopyright_text() {
        return copyright_text;
    }

    public void setCopyright_text(String copyright_text) {
        this.copyright_text = copyright_text;
    }

    public String getCopyright_link_1() {
        return copyright_link_1;
    }

    public void setCopyright_link_1(String copyright_link_1) {
        this.copyright_link_1 = copyright_link_1;
    }

    public String getCopyright_link_2() {
        return copyright_link_2;
    }

    public void setCopyright_link_2(String copyright_link_2) {
        this.copyright_link_2 = copyright_link_2;
    }

    public String getCopyright_link_3() {
        return copyright_link_3;
    }

    public void setCopyright_link_3(String copyright_link_3) {
        this.copyright_link_3 = copyright_link_3;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "Page{" + "content_type=" + content_type + ", placeInNav=" + placeInNav + ", display_template=" + display_template + ", merge_strategy=" + merge_strategy + ", file_name=" + file_name + ", internal_name=" + internal_name + ", orderDefault_f=" + orderDefault_f + ", header=" + header + ", content=" + content + ", description=" + description + ", objectGroupId=" + objectGroupId + ", objectId=" + objectId + ", folder_name=" + folder_name + ", createdDate=" + createdDate + ", createdDate_dt=" + createdDate_dt + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedDate_dt=" + lastModifiedDate_dt + ", menu_bar_link_1=" + menu_bar_link_1 + ", menu_bar_link_2=" + menu_bar_link_2 + ", menu_bar_link_3=" + menu_bar_link_3 + ", menu_bar_link_4=" + menu_bar_link_4 + ", menu_bar_link_5=" + menu_bar_link_5 + ", logo_image=" + logo_image + ", hero_title=" + hero_title + ", hero_text=" + hero_text + ", generation_card_title=" + generation_card_title + ", generation_card_text=" + generation_card_text + ", generation_card_image=" + generation_card_image + ", generation_title_1=" + generation_title_1 + ", generation_text_1=" + generation_text_1 + ", generation_title_2=" + generation_title_2 + ", generation_text_2=" + generation_text_2 + ", generation_title_3=" + generation_title_3 + ", generation_text_3=" + generation_text_3 + ", royal_title=" + royal_title + ", royal_text=" + royal_text + ", anheuser_title=" + anheuser_title + ", anheuser_text=" + anheuser_text + ", carousel_title=" + carousel_title + ", carousel_image_1=" + carousel_image_1 + ", carousel_image_2=" + carousel_image_2 + ", carousel_image_3=" + carousel_image_3 + ", carousel_image_4=" + carousel_image_4 + ", features=" + features + ", copyright_text=" + copyright_text + ", copyright_link_1=" + copyright_link_1 + ", copyright_link_2=" + copyright_link_2 + ", copyright_link_3=" + copyright_link_3 + ", disabled=" + disabled + '}';
    }
}
