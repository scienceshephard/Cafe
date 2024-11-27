package com;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Customer {

    private Socket socket;
    private String message;
    private BufferedReader buffereReader;
    private PrintWriter printWriter;
    private Scanner readInput;

    public static void main(String[] args){
        Customer customer= new Customer();
        customer.sendMsg();
    }
    Customer(){

    }
    private void sendMsg(){
        try{
            socket= new Socket("localhost",Barista.PORT_NUMBER);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            buffereReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            readInput= new Scanner(System.in);
            System.out.println("Connected to the cafe!");
            String serverResponse = buffereReader.readLine();
            System.out.println(serverResponse);
            while (true){
                System.out.println("Enter command: ");
                String command=readInput.nextLine();
                if(command.equalsIgnoreCase("exit")){
                    System.out.println("Iam exiting the cafe already");
                    printWriter.println(command);
                    break;
                }
                printWriter.println(command);
                String response = buffereReader.readLine();
                System.out.println("Server: "+ response);

            }
        } catch (IOException e) {
            try {
                closeEverything(printWriter, buffereReader, socket);
            } catch (IOException ex) {
            }
        }
    }

    private void closeEverything( PrintWriter printWriter, BufferedReader bufferedReader, Socket socket) throws IOException {
        if (printWriter == null)printWriter.close();
        if (bufferedReader == null) bufferedReader.close();
        if (socket == null) socket.close();
    }
}
