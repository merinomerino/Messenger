package Servidor;

import Cliente.ClienteThread;
import javax.swing.*;

import java.awt.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    // El socket del servidor.

    private static ServerSocket servidor = null;

// El socket del cliente.
    private static Socket connection = null;

    public static String mensagens = "";

// Este servidor de chat puede aceptar hasta las conexiones de los clientes de maxClientsCount.
    private static final int maxClientsCount = 10;
    private static final ClienteThread[] threads = new ClienteThread[maxClientsCount];

    public server(int porta, int tamFila) {

        try {

            servidor = new ServerSocket(porta, tamFila);

        } catch (EOFException eof) {
            System.out.println("\n Error de conexión con el cliente\n");
            eof.printStackTrace();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    public void runServidor() {

        System.out.println("Servidor listo y esperando ...");
        while (true) {
            try {
                connection = servidor.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClienteThread(connection, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(connection.getOutputStream());
                    os.println("Servidor muy ocupado. Intenta más tarde.");
                    os.close();
                    connection.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        MarcoServidor mimarco = new MarcoServidor();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class MarcoServidor extends JFrame {

    public MarcoServidor() {

        setBounds(1200, 300, 280, 350);

        JPanel milamina = new JPanel();

        milamina.setLayout(new BorderLayout());

        areatexto = new JTextArea();

        milamina.add(areatexto, BorderLayout.CENTER);

        add(milamina);

        setVisible(true);
        server s = new server(2222, 0);
        //Server s2 = new Server(2223, 0);
       // Server s3 = new Server(2225, 0);
        s.runServidor();

    }

    private JTextArea areatexto;
}
