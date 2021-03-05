/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.syzegee.customer.events.util;


import com.syzegee.customer.events.model.crafter.Features;
import com.syzegee.customer.events.model.crafter.Item;
import com.syzegee.customer.events.model.crafter.Page;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sagar
 */
public class CrafterXmlConverter {

    public String xmlPayload(Item[] items,String email,String logo) throws JAXBException {
        Features feature = Features.builder().item(items).build();
        Page page = Page.builder().content_type("/page/entry")
                .placeInNav("false")
                .display_template("/templates/web/entry.ftl")
                .merge_strategy("inherit-levels")
                .file_name("index.xml")
                .internal_name("Home example")
                .orderDefault_f("-1").header("<span>Next Level</span>")
                .content("<span>Digital Concierge Service</span>")
                .description("<span>Accelerate and personalize content delivery with Syzegee.</span>")
                .objectGroupId("8d7f")
                .objectId("8d7f21fa-5e09-00aa-8340-853b7db302da")
                .folder_name("")
                .createdDate("2017-1-31T16:18:14.000Z")
                .createdDate_dt("2017-1-31T16:18:14.000Z")
                .lastModifiedDate("2017-12-22T21:49:29.275Z")
                .lastModifiedDate_dt("2017-12-22T21:49:29.275Z")
                .menu_bar_link_1("Admin")
                .menu_bar_link_2("News")
                .menu_bar_link_3("Client Login")
                .menu_bar_link_4("Contact Us")
                .menu_bar_link_5("Careers")
                .logo_image("/static-assets/image/"+logo)
                .hero_title(" Create movements of value that drive people closer to your brand.")
                .hero_text(" Digital promotions and loyalty solutions for the world best brand")
                .generation_card_title("Generation Z eBook")
                .generation_card_text("Apply storyliving phygital, and gamification tactics to\n"
                + " reach this group as it grows its influence and purchasing\n"
                + "                    power.")
                .generation_card_image("/static-assets/image/six-ways.jpg")
                .generation_title_1("Merkle/HelloWorld named a stromg performer in both Loyalty\n"
                + "                      Services &amp; Technology")
                .generation_text_1(" Download a complimentary copy of the Forrester\n"
                + "                      Wave<sup>TM</sup> : Loyalty Service Provider, Q3 2019.")
                .generation_title_2("2019 Loyalty Barometer Report")
                .generation_text_2("Did you know 54% of consumers want swifter reward\n"
                + "                      redemption? Get exclusive insights on what consumers\n"
                + "                      really want.")
                .generation_title_3("Report:Nine Insights for a Solid Prizing strategy")
                .generation_text_3(" Updated research reveals 64% of consumers preferences when\n"
                + "                      its comes to being rewarded by brands")
                .royal_title("Royal Caribbean International")
                .royal_text("Engagement Hub To Reward Exploration")
                .anheuser_title("Anheuser-Busch InBev")
                .anheuser_text("Trade Marketing Promotions House")
                .carousel_title(email)
                .carousel_image_1("/static-assets/image/carousel-01.jpg")
                .carousel_image_2("/static-assets/image/carousel-02.jpg")
                .carousel_image_3("/static-assets/image/carousel-03.jpg")
                .carousel_image_4("/static-assets/image/carousel-04.jpg")
                .features(feature)
                .copyright_text("&#169;2019, Syzegee - All Rights Reserved")
                .copyright_link_1("Privacy policy")
                .copyright_link_2("Mobile Terms &amp; Conditions")
                .copyright_link_3("Site map")
                .disabled("false").build();

        JAXBContext context = JAXBContext.newInstance(Page.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter ss = new StringWriter();
        marshaller.marshal(page, ss);

        return ss.toString();
    }

    public String setFeatures(List<String> binifitName,String email,String logo) throws JAXBException {
        List<Item> binifits = new ArrayList<>();

        for (String benefit : binifitName) {
            Item itemOne = new Item();
            itemOne.setKey("/site/components/features/" + benefit.toLowerCase() + ".xml");
            itemOne.setValue(benefit.toUpperCase());
            itemOne.setInclude("/site/components/features/" + benefit.toLowerCase() + ".xml");
            itemOne.setDisableFlattening("false");
            binifits.add(itemOne);
        }
        Object[] toArray = binifits.toArray();
        Item[] items = new Item[toArray.length];

        for (int i = 0; i < toArray.length; i++) {
            items[i] = (Item) toArray[i];
        }
        return xmlPayload(items,email,logo);

    }
}
