package com.o4codes.copaste.services;

import io.javalin.Javalin;

public class ClipService {
    static Javalin app;
    public static void startClipService() {
        app = Javalin.create().start();
        System.out.println("ClipService started");
    }

    public static void stopClipService() {
        app.stop();
        System.out.println("ClipService stopped");
    }
}
