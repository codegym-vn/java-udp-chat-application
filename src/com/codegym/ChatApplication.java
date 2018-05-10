package com.codegym;

import java.io.IOException;
import java.net.*;

public class ChatApplication {
    private MessageEvent messageEvent;
    private DatagramSocket datagramSocket;
    private int port;

    public ChatApplication(MessageEvent messageEvent, int port) {
        if(messageEvent == null){
            throw new RuntimeException("MessageEvent can't be null");
        }
        this.messageEvent = messageEvent;
        this.port = port;
    }

    public void start() throws SocketException {
        datagramSocket = new DatagramSocket(this.port);

        new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        byte[] buffer = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                        datagramSocket.receive(datagramPacket);
                        String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                        messageEvent.onMessageReceived(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void sendMessage(String clientHost, int clientPort, String message) throws IOException {
        SocketAddress clientAddress = new InetSocketAddress(clientHost, clientPort);
        byte[] buffer = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length, clientAddress);
        datagramSocket.send(datagramPacket);
    }
}
