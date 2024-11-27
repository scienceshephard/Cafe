package com.Service;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private String clientName;
    private Orders currentOrder;
    public ClientHandler (Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.printWriter= new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            closeEverthing(clientSocket, printWriter, bufferedReader);
        }
    }

    private void closeEverthing(Socket clientSocket, PrintWriter printWriter, BufferedReader bufferedReader) {
        try {
            if (clientSocket == null) clientSocket.close();
            if(printWriter == null) printWriter.close();
            if(bufferedReader == null) bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            printWriter.println("Please enter your name: ");
            clientName= bufferedReader.readLine();
            printWriter.println("Welcome, "+ clientName + ", May I have your order. ");
            handleCommands();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void handleCommands() {
        String command;
        try{
            while ((command = bufferedReader.readLine()) != null){
                if (command.equalsIgnoreCase("exit")){
                    printWriter.println("Goodbye!");
                    break;
                } else if (command.startsWith("order")) {
                    handleOrder();
                } else if (command.equalsIgnoreCase("collect")) {
                    handleCollect();
                } else if (command.equalsIgnoreCase("order status")) {
                    handleOrderStatus();
                } else {
                    printWriter.println("Error: Unknown command.");
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void handleOrderStatus() {
    }

    private void handleCollect() {
    }

    private void handleOrder() {
    }
}
