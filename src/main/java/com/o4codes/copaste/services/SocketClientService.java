package com.o4codes.copaste.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import javafx.application.Platform;

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
        try {
            Clip clip = Clip.toObject(data.toString());
            System.out.println("Converted text is "+clip.toString());
            Platform.runLater(() -> Session.clip.copyProperties(clip));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
        Session.isClientConnected.set(false);
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


    public void startClient(String hostUrl, String port) {
        System.out.println("Starting client");
        System.out.println(Session.executor.isShutdown());
        Session.webSocketClient = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(String.format("ws://%s:%s/clip", hostUrl, port)), this)
                .join();

        // executes a scheduled server ping every 1 minute
        Session.executor.scheduleAtFixedRate(() -> Session.webSocketClient
                .sendPing(ByteBuffer
                        .wrap("ping".getBytes())), 1, 1, TimeUnit.MINUTES);

        Session.isClientConnected.set(true);
    }

    public static void stopClient() {
        if (Session.webSocketClient != null) {
            Session.webSocketClient.sendClose(1000, "Closing client");
            Session.webSocketClient = null;
        }

        if (!Session.executor.isShutdown()) {
            Session.executor.shutdownNow();
        }
        Session.isClientConnected.set(false);
    }

    public static void sendClip(Clip clip) throws JsonProcessingException {
        if (Session.webSocketClient != null) {
            String jsonString = clip.toJson();
            Session.webSocketClient.sendText(jsonString, true);
        }
    }


}
