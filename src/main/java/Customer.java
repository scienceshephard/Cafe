import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Customer {

    private Socket socket;
    private String username;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    Gson gson = new Gson();
    Customer(Socket socket, String username){
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(bufferedWriter, bufferedReader, socket);
        }
    }
    private void sendMsg(){
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner readInput = new Scanner(System.in);
            while (socket.isConnected()){
                String message= readInput.nextLine();
                if(message.equalsIgnoreCase("exit")){
                    bufferedWriter.write("exit");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    closeEverything(bufferedWriter, bufferedReader, socket);
                    System.exit(0);
                    break;
                }
                bufferedWriter.write(username + ": " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            closeEverything(bufferedWriter, bufferedReader, socket);
        }
    }
    private void ListenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()){
                    try{
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(bufferedWriter, bufferedReader,socket);
                    }
                }
            }
        }
        ).start();
    }

    public void closeEverything( BufferedWriter bufferedWriter, BufferedReader bufferedReader, Socket socket) {
        try {
            if (bufferedWriter == null) bufferedWriter.close();
            if (bufferedReader == null) bufferedReader.close();
            if (socket == null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scn.nextLine();
        Customer customer= new Customer(new Socket("localhost", Barista.PORT_NUMBER), username);
        customer.ListenForMessage();
        customer.sendMsg();
    }
}
