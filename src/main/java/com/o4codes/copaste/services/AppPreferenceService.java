package com.o4codes.copaste.services;

import com.o4codes.copaste.MainApp;
import com.o4codes.copaste.models.Config;
import com.o4codes.copaste.utils.NetworkUtils;
import java.util.prefs.Preferences;

public class AppPreferenceService {

     public static Config getAppPrefs() {
         Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
         return new Config(
                 prefs.get("name", NetworkUtils.getDeviceName()),
                 prefs.getInt("port", 8080),
                 prefs.getBoolean("dark_mode", false));
     }

     public static void saveAppPrefs(Config config) {
         Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
         prefs.put("name", config.getName());
         prefs.putInt("port", config.getPort());
         prefs.putBoolean("dark_mode", config.isDarkMode());
     }

}
