package com.syzegee.customer.events.util;

import static com.syzegee.customer.events.constants.Constants.*;

public class CrafterTokenUtil {

    public static String token(String token) {
         String tokenValue = null;

        if (token != null && token.contains(HEADER_NAME_XSRF_TOKEN)) {
            int xsrfstartIndex = token.lastIndexOf(XSRF_TOKEN);
            int xsrfLastIndex = token.lastIndexOf(";");
            tokenValue = token.substring(11, xsrfLastIndex);
            System.out.println(" keyString : " + tokenValue);
        }
        if (token != null && token.contains(HEADER_NAME_JSESSIONID)) {
            int jessionstartIndex = token.indexOf(JSESSIONID);
            int jessionLastIndex = token.indexOf(";");
            tokenValue = token.substring(11, jessionLastIndex);
            System.out.println(" Json keyString : " + tokenValue);
        }
        return tokenValue;
    }

}
