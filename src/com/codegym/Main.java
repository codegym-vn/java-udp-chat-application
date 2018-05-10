package com.codegym;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your port:");
        int port = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter client host: ");
        String clientHost = scanner.nextLine();
        System.out.println("Enter client port");
        int clientPort = scanner.nextInt();
        scanner.nextLine();


        MessageEvent messageEvent = new MessageEventImpl();
	    ChatApplication chatApplication = new ChatApplication(messageEvent, port);
        try {
            chatApplication.start();
            String message;
            do{
                message = scanner.nextLine();
                chatApplication.sendMessage(clientHost, clientPort, message);
            } while (!message.equals("bye"));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class MessageEventImpl implements MessageEvent{

        @Override
        public void onMessageReceived(String message) {
            System.out.println(message);
        }
    }
}
