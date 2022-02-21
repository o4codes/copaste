package com.o4codes.copaste.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class SocketClientService implements WebSocket.Listener {


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
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("onPing " + message);
        return WebSocket.Listener.super.onPing(webSocket, message);
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("onPong " + message);
        return WebSocket.Listener.super.onPong(webSocket, message);
    }



    public void startClient() {
        System.out.println("Starting client");
        Session.webSocketClient = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://127.0.0.1:7235/clip"), this)
                .join();

        // executes a scheduled server ping every 1 minute
        Session.executor.scheduleAtFixedRate(() -> Session.webSocketClient.sendPing(ByteBuffer.wrap("ping".getBytes())), 1, 1, TimeUnit.MINUTES);
    }

    public void stopClient() {
        if (Session.webSocketClient != null) {
            Session.webSocketClient.sendClose(1000, "Closing client");
            Session.webSocketClient = null;
        }

        if (!Session.executor.isShutdown()) {
            Session.executor.shutdown();
        }

    }


    public static void sendClip(Clip clip) throws JsonProcessingException {
        if (Session.webSocketClient != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(clip);
            Session.webSocketClient.sendText(jsonString, true);
        }
    }


}
