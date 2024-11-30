package helpers;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    private static ArrayList<ClientHandler> clientHandlers= new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private String clientName;
    private Orders currentOrder;
    private BufferedWriter bufferedWriter;
    public ClientHandler (Socket socket){
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
        String messageFromClient;
        try {
            while (socket.isConnected() ) {
                messageFromClient = bufferedReader.readLine();
                handleCommands(messageFromClient);
            }
        }catch (IOException e) {
            closeEverthing(socket, bufferedWriter, bufferedReader);
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
        removeClientHandler();
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
    }

    private void handleCommands(String command) {
        while (socket.isConnected()) {
            if (command.equalsIgnoreCase("exit")) {
                System.out.println(clientName+ " has left the chat.");
                closeEverthing(socket, bufferedWriter, bufferedReader);
                break;
            } else if (command.startsWith("order")) {
                handleOrder();
            } else if (command.equalsIgnoreCase("collect")) {
                handleCollect();
            } else if (command.equalsIgnoreCase("order status")) {
                handleOrderStatus();
            }
            else {broacastMessage(command);
                break;
            }
        }
    }

    private void handleOrderStatus() {
    }

    private void handleCollect() {
    }

    private void handleOrder() {
    }
}
