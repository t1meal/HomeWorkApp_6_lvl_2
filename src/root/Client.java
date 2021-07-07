package root;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8888);

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner console = new Scanner(System.in);

            Thread intro = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        String string = in.nextLine();
                        if (string.equals("end")){
                            out.println("Клиент вышел!");
                            break;
                        }
                        System.out.println("Сервер: " + string);
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


            try {
                intro.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
