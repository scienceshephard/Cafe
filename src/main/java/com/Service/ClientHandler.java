package com.Service;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private static ArrayList<ClientHandler> clientHandlers= new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
//    private PrintWriter printWriter;
    private String clientName;
    private Orders currentOrder;
    private BufferedWriter bufferedWriter;
    public ClientHandler (Socket socket){
//        try {
//            this.clientSocket = clientSocket;
//            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            this.printWriter= new PrintWriter(clientSocket.getOutputStream(), true);
//
//        } catch (IOException e) {
//            closeEverthing(clientSocket, printWriter, bufferedReader);
//        }
        try{
            this.socket= socket;
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientName = bufferedReader.readLine();
            clientHandlers.add(this);
            broacastMessage("Server: " + clientName+" has entered the chat!");
        }catch (IOException e){
            closeEverthing(socket, bufferedWriter, bufferedReader);
        }
    }

    @Override
    public void run() {
//        try{
//            printWriter.println("Please enter your name: ");
//            clientName= bufferedReader.readLine();
//            printWriter.println("Welcome, "+ clientName + ", May I have your order. ");
//            handleCommands();
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
        String messageFromClient;
        while (socket.isConnected()){
            try{
                messageFromClient= bufferedReader.readLine();
                broacastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverthing(socket, bufferedWriter,bufferedReader);
                break;
            }
        }
    }


    private void broacastMessage(String messageToSend) {
        for(ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientName.equals(clientName)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverthing(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    private void closeEverthing(Socket clientSocket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (clientSocket == null) clientSocket.close();
            if(bufferedWriter == null) bufferedWriter.close();
            if(bufferedReader == null) bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void removeClientHandler(){
        clientHandlers.remove(this);
        broacastMessage("SERVER: " + clientName + " has left the chat!");
    }

    private void handleCommands() {
        String command;
        try{
            while ((command = bufferedReader.readLine()) != null){
                if (command.equalsIgnoreCase("exit")){
                    System.out.println("Goodbye!");
                    break;
                } else if (command.startsWith("order")) {
                    handleOrder();
                } else if (command.equalsIgnoreCase("collect")) {
                    handleCollect();
                } else if (command.equalsIgnoreCase("order status")) {
                    handleOrderStatus();
                } else {
                    System.out.println("Error: Unknown command.");
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
