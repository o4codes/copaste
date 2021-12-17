package com.o4codes.copaste.utils;

import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.models.Config;
import com.o4codes.copaste.services.AppPreferenceService;


public class Session {
    public static Config config = AppPreferenceService.getAppPrefs();
    public static Clip clip =  new Clip(config.getName(),"No Clipboard value", "text");
}
