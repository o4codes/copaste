package com.o4codes.copaste.utils;

import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.models.Config;

import java.util.ArrayList;
import java.util.List;


public class Session {
    public static Config config = new Config("Shaddy",8080, true);
    public static Clip clip =  new Clip(config.getName(),"No Clipboard value", "text");
}
