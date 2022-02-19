package com.o4codes.copaste.services;


//import com.google.gson.Gson;
import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
//import org.hildan.fxgson.FxGson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

public class WebClientService implements WebSocket.Listener {


    @Override
    public void onOpen(WebSocket webSocket) {
        webSocket.request(1);
        System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        webSocket.request(1);
        System.out.println("onText received " + data);
//        Gson gson = new Gson();
//        Session.clip = gson.fromJson(data.toString(), Clip.class);
        System.out.println(Session.clip.getContent());
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Clip Client Closed! " + webSocket.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("onClose " + statusCode + " " + reason);
        Session.latch.countDown();
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("onPing " + message);
        webSocket.sendPong(message);
        return WebSocket.Listener.super.onPing(webSocket, message);
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("onPong " + message);
        webSocket.sendPing(message);
        return WebSocket.Listener.super.onPong(webSocket, message);
    }

    public void startClient() throws InterruptedException {
        new Thread(() -> {
            System.out.println("Starting client");

            WebSocket ws = HttpClient
                    .newHttpClient()
                    .newWebSocketBuilder()
                    .buildAsync(URI.create("ws://127.0.0.1:7235/clip"), this)
                    .join();

//            Gson gson = FxGson.create();
//            while (true) {
//                ws.sendText(gson.toJson(Session.clip), true);
//            }
        }).start();

    }


}
