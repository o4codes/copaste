package com.o4codes.copaste.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.o4codes.copaste.models.Clip;
import com.o4codes.copaste.utils.Session;
import javafx.application.Platform;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.function.Consumer;

import static java.lang.System.out;


public class ClipBoardService implements ClipboardOwner, Runnable {
    private final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard(); //get system clipboard
    private final Consumer<String> bufferConsumer;


    public ClipBoardService(Consumer<String> bufferConsumer) {
        this.bufferConsumer = bufferConsumer;
    }

    //listen to clipboard changes
    public void ClipBoardListener() {
        this.SYSTEM_CLIPBOARD.addFlavorListener( listener -> {
            try {
                Transferable contents = SYSTEM_CLIPBOARD.getContents( this );
                getOwnership( contents );
                String clip = (String) SYSTEM_CLIPBOARD.getData( DataFlavor.stringFlavor );
                Platform.runLater( () -> {
                    Session.clip = new Clip(Session.config.getName(), clip, "text/plain");
                } );
                System.out.println( "Copied: " + Session.clip.getContent() );

                // code to put clip on server
                this.broadCastClip(Session.clip);
                Toolkit.getDefaultToolkit().beep();

            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        } );
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
    }


    @Override
    public void run() {
        Transferable transferable = SYSTEM_CLIPBOARD.getContents( this );
        getOwnership( transferable );
    }

    private void getOwnership(Transferable transferable) {
        SYSTEM_CLIPBOARD.setContents( transferable, this );
    }

    public void setBuffer(String buffer) {
        getOwnership( new StringSelection( buffer ) );
    }

    public void broadCastClip(Clip clip) throws JsonProcessingException {
        if (Session.webSocketClient != null) {
            SocketClientService.sendClip(clip);
        } else {
            SocketServerService.sendClip(clip);
        }
    }

    public static void startClipBoardListener(){
        Session.clipListenerThread = new Thread(() -> {
            ClipBoardService clipboardService = new ClipBoardService( out::println );
            EventQueue.invokeLater( clipboardService );
            clipboardService.ClipBoardListener();
        });

        Session.clipListenerThread.start();
        out.println("Clipboard listener started");
    }

    public static void stopClipBoardListener(){
        Session.clipListenerThread.interrupt();
        Session.clipListenerThread = null;
        out.println("Clipboard Listener stopped");
    }
}