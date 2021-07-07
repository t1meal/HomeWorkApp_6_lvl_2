package root;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServ {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8888);
            System.out.println("Сервер запущен!");

            socket = server.accept();
            System.out.println("Клиент подключился!");

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner console = new Scanner(System.in);


            Thread intro = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        String str = in.nextLine();
                        if (str.equals("end")){
                            out.println("Клиент вышел!");
                            break;
                        }
                        System.out.println("Сервер: " + str);
                    }
                }
            });

            intro.start();

            Thread outro = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Введите сообщение: ");
                    String text = console.nextLine();
                    out.println("Сообщение отправлено!");
                    out.println(text);
                }
            });
            outro.setDaemon(true);
            outro.start();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
