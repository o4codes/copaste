package com.o4codes.copaste.utils;

import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.models.Config;
import com.o4codes.copaste.services.AppPreferenceService;
import io.javalin.websocket.WsContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


public class Session {
    public static Config config = AppPreferenceService.getAppPrefs();
    public static Clip clip =  new Clip(config.getName(),"No Clipboard value", "text");
    public static CountDownLatch latch = new CountDownLatch(1);
    public static Map<WsContext, String> usersMap = new ConcurrentHashMap<>();
}
