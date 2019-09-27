import java.io.*;
import java.net.Socket;

public class Broker {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static int brokerId = -1;

    public static void main(String[] args) {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 5000); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String word = "";
                // wait for conection
                //word = reader.readLine();
                out.write("Register\n"); // отправляем сообщение на сервер
                out.flush();
                String serverWord = in.readLine();
                    System.out.println(serverWord);
                
                String[] strId = null;
                if (serverWord != null)
                    strId = serverWord.split("\\\\");
                brokerId = Integer.parseInt(strId[1]);
                System.out.println("Id resieved: " + strId[1]);
                System.out.println("Enter message to sent: ");
                while (!word.equals("exit"))
                {
                    word = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
                    out.write(word + "\n"); // отправляем сообщение на сервер
                    out.flush();
                    serverWord = in.readLine(); // ждём, что скажет сервер
                    System.out.println(serverWord); // получив - выводим на экран
                }
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Client: closed");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        
    }
}