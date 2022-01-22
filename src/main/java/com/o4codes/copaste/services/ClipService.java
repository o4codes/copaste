package com.o4codes.copaste.services;

import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;

public class ClipService {
    static Javalin app;
    static String message = "testing";
    public static void startClipService() {
        app = Javalin.create(config -> {
            config.addStaticFiles(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/com/o4codes/copaste/public";
                staticFileConfig.location = Location.CLASSPATH;
            });
        }).start(7235);
        setupRoutes(); // setup server routes
        System.out.println("ClipService started");
    }

    public static void stopClipService() {
        if (app != null && app.jettyServer().started) {
            app.stop();
            System.out.println("ClipService stopped");
        }
        else {
            System.out.println("ClipService not running");
        }
    }


    public static void setupRoutes() {
        app.get("/api/v1", ctx -> {
            ctx.result("CoPaste API");
        });

        app.get("/api/v1/clip", ctx -> {
            ctx.json(Session.clip);
        });

        app.put("/api/v1/clip", ctx -> {
           Clip clip =  ctx.bodyValidator(Clip.class)
                    .check(obj -> obj.getCreatedAt() > Session.clip.getCreatedAt(), "Clip is older than last one")
                    .get();

           Session.clip = clip;
           ctx.result("Clip updated").status(200);
        });

        app.ws("/clip", ws -> {
            ws.onConnect(ctx -> {
                ctx.send(Session.clip);
            });

            ws.onMessage(ctx -> {
                if (ctx.message() != ""){
                    message = ctx.message();
                }
                ctx.send(message);
            });
        });
    }


}
