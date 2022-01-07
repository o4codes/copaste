package com.o4codes.copaste.services;

import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ClipService {
    static Javalin app;

    public static void startClipService() {
        app = Javalin.create().start();
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
        app.get("/", ctx -> {
            ctx.result("CoPaste API");
        });

        app.get("/clip", ctx -> {
            ctx.json(Session.clip);
        });

        app.put("/clip", ctx -> {
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
                
            });
        });
    }



}
