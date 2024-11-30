import helpers.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Barista {

    final static int PORT_NUMBER= 100;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Socket socket;



    Barista(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }


    Barista(){}

    public void startServer(){
        try {
            File file = new File("server.log");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("Pelican Server running...");
            printWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        while (!serverSocket.isClosed()){
            try{
                socket=serverSocket.accept();
                System.out.println("A new device has connected");
                ClientHandler clientHandler= new ClientHandler(socket);
                Thread thread= new Thread(clientHandler);
                thread.start();

            } catch (IOException e) {
                closeEverything(bufferedReader, bufferedWriter, serverSocket);
            }
        }
    }

    private void closeEverything(BufferedReader bufferedReader, BufferedWriter bufferedWriter, ServerSocket serverSocket) {
        try{
            if(bufferedReader == null)bufferedReader.close();
            if (bufferedWriter == null)bufferedWriter.close();
            if (serverSocket == null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket= new ServerSocket(PORT_NUMBER);
        Barista barista = new Barista(serverSocket);
        barista.startServer();
    }
}
