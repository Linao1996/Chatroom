package pac1;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    ServerSocket ss = null;
    List<ChatClient> clients = new ArrayList<ChatClient>();

    public static void main(String[] args) {
        new Server().start();
    }

    private void start() {
        try {
            ss = new ServerSocket(8888);
        } catch (BindException e) {
            e.printStackTrace();
            System.exit(1);//by convention, a non-zero exit status indicates abnormal termination.
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
/* A try-with-resources statement can have catch and finally blocks just like an ordinary try statement.
 In a try-with-resources statement, any catch or finally block is run after the resources declared have been closed.
 A try-with-resources statement can have catch and finally blocks just like an ordinary try statement.
 In a try-with-resources statement, any catch or finally block is run after the resources declared have been closed. */
        try {
            while (true) {
                Socket s = ss.accept();
                System.out.println("clint in line");
                ChatClient c = new ChatClient(s);
                new Thread(c).start();
                clients.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ChatClient implements Runnable {
        private Socket s;
        Scanner in;
        PrintWriter out;
        boolean bConnected;

        public ChatClient(Socket s) {// constructor, create input and output Object.
            this.s = s;
            try {
                in = new Scanner(s.getInputStream());
                out = new PrintWriter(s.getOutputStream(),true);//There must be a true, figure out why!!!
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void send(String str) {
            out.println(str);
        }

        public void run() {
            try {
                while (bConnected) {
                    while (in.hasNextLine()) {
                        String str = in.nextLine();
//                        out.println(str);
//                        System.out.println(str);
                        for (int i = 0; i < clients.size(); i++) {
                            ChatClient c = clients.get(i);
                            c.send(str);
                        }
                    }
                }
            } finally {
                if (in != null)
                    if (s != null)
                        try {
                            in.close();
                            s.close();
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        }
    }
}
