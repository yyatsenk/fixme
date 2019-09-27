import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Router {

    private static Socket clientSocket1; //сокет для общения
    private static Socket clientSocket2; //сокет для общения
    private static ServerSocket server1; // серверсокет
    private static ServerSocket server2; // серверсокет
    private static BufferedReader in1; // поток чтения из сокета
    private static BufferedWriter out1; // поток записи в сокет
    private static BufferedReader in2; // поток чтения из сокета
    private static BufferedWriter out2; // поток записи в сокет

    public static void main(String[] args) {
        final int startId = 100000;
        final List<Integer> registry = new ArrayList<Integer>();
        Thread listener5000 = new Thread(){
            public void run(){
              System.out.println("\n5000 port listener run");
        try {
            String word = "";
            try  {
                server1 = new ServerSocket(5000);
                System.out.println("Server: started");
                clientSocket1 = server1.accept();
                try { 
                    in1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
                    out1 = new BufferedWriter(new OutputStreamWriter(clientSocket1.getOutputStream()));

                    word = in1.readLine();
                    System.out.println(word);
                     if (word.equals("Register"))
                     {
                        registry.add(startId + 1);
                        out1.write("Conection established  id =\\" + Integer.toString(startId) + "\\ \n");
                        out1.flush();
                    }
                    while (!word.equals("exit"))
                    {
                        word = in1.readLine();
                        System.out.println(word);
                        out1.write("Server: recieved " + word + "\n");
                        out1.flush();
                    }
                    clientSocket1.close();
                    in1.close();
                    out1.close();
                } catch(Exception e) {
                    System.out.println(e);
                }
            } finally {
                System.out.println("Server: conection closed");
                    server1.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
};
Thread listener5001 = new Thread(){
    public void run(){
      System.out.println("\n5001 port listener run");
try {
    String word = "";
    try  {
        server2 = new ServerSocket(5001);
        System.out.println("Server: started");
        clientSocket2 = server2.accept();
        try { 
            in2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
            out2 = new BufferedWriter(new OutputStreamWriter(clientSocket2.getOutputStream()));

            word = in2.readLine();
            System.out.println(word);
             if (word.equals("Register"))
             {
                registry.add(startId + 1);
                out2.write("Conection established  id =\\" + Integer.toString(startId) + "\\ \n");
                out2.flush();
            }
            while (!word.equals("exit"))
            {
                word = in2.readLine();
                System.out.println(word);
                out2.write("Server: recieved " + word + "\n");
                out2.flush();
            }
            clientSocket2.close();
            in2.close();
            out2.close();
        } finally {
        }
    } finally {
        System.out.println("Server: conection closed");
            server2.close();
    }
} catch (IOException e) {
    System.err.println(e);
}
}
};
    listener5000.start();
    listener5001.start();
    }
}