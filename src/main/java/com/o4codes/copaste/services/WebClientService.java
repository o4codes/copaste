package com.o4codes.copaste.services;

import com.o4codes.copaste.MainApp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebClientService implements WebSocket.Listener {



    @Override
    public void onOpen(WebSocket webSocket) {
        webSocket.request(1);
        System.out.println("onOpen using subprotocol " + webSocket.getSubprotocol());
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
//        MainApp.latch.countDown();
        webSocket.request(1);
        System.out.println("onText received " + data);

        return CompletableFuture.completedFuture(data)
                .thenAccept(o -> System.out.println("Handling data: " + o));
    }


    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Bad day! " + webSocket.toString());
        WebSocket.Listener.super.onError(webSocket, error);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("onClose " + statusCode + " " + reason);
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    public void startClient() throws InterruptedException {
        System.out.println("Starting client");

        WebSocket ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("ws://127.0.0.1:7235/clip"), this)
                .join();

//        while (true) {
            ws.sendText(" ", true);
//        }


    }
}
