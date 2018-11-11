package client.controller;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

import client.net.OutputHandler;
import client.net.ServerConnection;

/**
 * This controller decouples the view from the network layer. All methods, except
 * <code>disconnect</code>, submit their task to the common thread pool, provided by
 * <code>ForkJoinPool.commonPool</code>, and then return immediately.
 */
public class NetworkController {
    private final ServerConnection serverConnection = new ServerConnection();

    /**
     * @see ServerConnection#connect(java.lang.String, int,
     * textprotocolchat.client.net.OutputHandler)
     */
    public void connect(String host, int port, OutputHandler outputHandler) {
        CompletableFuture.runAsync(() -> {
            try {
                serverConnection.connect(host, port, outputHandler);
            } catch (IOException ioe) {
                throw new UncheckedIOException(ioe);
            }
        }).thenRun(() -> outputHandler.handleMsg("Connected to " + host + ":" + port));
    }

    /**
     * @see ServerConnection#disconnect() Blocks until disconnection is completed.
     */
    public void disconnect() throws IOException {
        serverConnection.disconnect();
    }
    
    public void start(){
    	serverConnection.sendStart();
    }
    /**
     * @see ServerConnection#sendUsername(java.lang.String)
     */
    public void sendUsername(String username) {
        CompletableFuture.runAsync(() -> serverConnection.sendUsername(username));
    }

    /**
     * @see ServerConnection#sendChatEntry(java.lang.String)
     */
    public void sendInput(String input) {
        CompletableFuture.runAsync(() -> serverConnection.sendInput(input));
    }
}
