package root;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;

            try {
            socket = new Socket("localhost", 10200);

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter (socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true){
                        String str = in.nextLine();
                        if (str.equals("/end")) {
                            out.println("/end");
                            break;
                            }
                            System.out.println("Server: " + str);
                        }
                    }
            });
            t1.start();

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Введите сообщение: ");
                    String text = scanner.nextLine();
                    System.out.println("Сообщение отправлено!");
                    out.println(text);
                }
            });
            t2.setDaemon(true);
            t2.start();


            try {
               t1.join();
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
