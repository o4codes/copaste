package com.o4codes.copaste.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import javafx.application.Platform;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import static java.lang.System.out;


public class ClipBoardService implements ClipboardOwner, Runnable {
    public static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard(); //get system clipboard
    private final Consumer<String> bufferConsumer;


    public ClipBoardService(Consumer<String> bufferConsumer) {
        this.bufferConsumer = bufferConsumer;
    }

    //listen to clipboard changes
    public void ClipBoardListener() {
        this.SYSTEM_CLIPBOARD.addFlavorListener(listener -> {
            try {
                Transferable contents = SYSTEM_CLIPBOARD.getContents(this);
                if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    String clip = (String) SYSTEM_CLIPBOARD.getData(DataFlavor.stringFlavor);
                    bufferConsumer.accept(clip);
                    Session.clip = new Clip(Session.config.getName(), clip, "text/plain");
                    out.println("Copied: " + Session.clip.getContent());

                    // code to put clip on server
                    this.broadCastClip(Session.clip);
                    Toolkit.getDefaultToolkit().beep();
                }
                getOwnership(contents);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void lostOwnership(Clipboard clipboard, Transferable notUsed) {
        Transferable contents = clipboard.getContents( this );
        getOwnership( contents );
        if (contents.isDataFlavorSupported( DataFlavor.stringFlavor )) {
            try {
                String string = (String) contents.getTransferData( DataFlavor.stringFlavor );
                bufferConsumer.accept( string );

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }

        }
        out.println("Regained ownership");
    }


    @Override
    public void run() {
        clipListener();
    }

    private void getOwnership(Transferable transferable) {
        SYSTEM_CLIPBOARD.setContents( transferable, this );
    }

    public void setBuffer(String buffer) {
        getOwnership( new StringSelection( buffer ) );
    }

    public static void broadCastClip(Clip clip) throws JsonProcessingException {
        if (Session.webSocketClient != null) {
            SocketClientService.sendClip(clip);
        } else {
            SocketServerService.sendClip(clip);
        }

        Platform.runLater(() -> Session.clip.copyProperties(clip));
    }

    public void clipListener(){
        /**
         * Listens for data being copied........
         */
        SYSTEM_CLIPBOARD.addFlavorListener(listener -> {
            String clipboardText = null;
            try {
                clipboardText = (String) SYSTEM_CLIPBOARD.getData(DataFlavor.stringFlavor);
                SYSTEM_CLIPBOARD.setContents(new StringSelection(clipboardText), null);

                Clip clip = new Clip(Session.config.getName(), clipboardText, "text/plain");
                Platform.runLater(() -> Session.clip.copyProperties(clip)); // update the UI
                this.broadCastClip(Session.clip); // sends clip to server
                out.println("Copied: " + Session.clip.getContent());
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static void startClipBoardListener(){
        Session.clipListenerThread = new Thread(new ClipBoardService(out::println));
        Session.clipListenerThread.start();
        out.println("Clipboard listener started");
    }

    public static void stopClipBoardListener(){
        Session.clipListenerThread.interrupt();
        Session.clipListenerThread = null;
        Arrays.stream(SYSTEM_CLIPBOARD.getFlavorListeners()).forEach(SYSTEM_CLIPBOARD::removeFlavorListener);
        out.println("Clipboard Listener stopped");
    }

    public static void copyToClipBoard(String text) throws JsonProcessingException {
        Clip clip = new Clip(Session.config.getName(), text, "text/plain");
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(text),
                        null
                );
        broadCastClip(clip);
    }

}
