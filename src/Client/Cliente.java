package Cliente;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class Cliente {
	
 public static int id ;
	
	static class Chat extends Observable {
		
		
	    }

	static class ChatFrame extends JFrame implements Observer {
	
	
	}
	public static void main(String[] args) {
        String server = "127.0.0.1";
        int port = 2222;
        Chat access = null;

        try {
            access = new Chat(server, port);
        } catch (IOException ex) {

            ex.printStackTrace();
            System.exit(0);
        }
        JFrame frame = new ChatFrame(access);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
