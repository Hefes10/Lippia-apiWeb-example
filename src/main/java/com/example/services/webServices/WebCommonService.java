package com.example.services.webServices;

import com.crowdar.core.actions.WebActionManager;
import com.crowdar.core.PropertyManager;

public class WebCommonService {
    public static String webURL(){
        return PropertyManager.getProperty("web.url");
    }

    public static void goToLogin(){
        WebActionManager.navigateTo(webURL());
    }

}
