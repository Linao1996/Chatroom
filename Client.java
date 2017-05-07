package pac1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Frame {
    TextField tf = new TextField();
    TextArea ta = new TextArea();
    Socket s;
    PrintWriter out;
    Scanner in;
    boolean connected;

    public Client() {
        setBounds(300, 300, 300, 300);
        ta.setEditable(false);
        add(tf, BorderLayout.SOUTH);
        add(ta, BorderLayout.NORTH);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disconnect();
                System.exit(0);
            }
        });
        connect();
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str = tf.getText();
                tf.setText("");
                out.println(str);
            }
        });
        pack(); // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents. The resulting width and height of the window are automatically enlarged if either of dimensions is less than the minimum size as specified by the previous call to the setMinimumSize method.
        setVisible(true);
        new Thread(new Runnable(){// a new thread responsible for input stream from server and running constantly.
            @Override
            public void run() {
                while(connected ){
                    ta.setText(ta.getText() + in.nextLine() + "\n");
                }
            }
        }).start();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1", 8888);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new Scanner(s.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            out.close();
            in.close();
            s.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Client c = new Client();
    }
}
