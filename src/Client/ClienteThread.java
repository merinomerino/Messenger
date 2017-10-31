/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import Server.Servidor;

// Para cada conexión de cliente llamamos a esta clase
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteThread extends Thread {

    private String clientName = null;
    private DataInputStream is = null;
    private PrintStream os = null;//para las operaciones de entrada y salida
    private Socket clientSocket = null;//Se crea el Socket para establecer la comunicación
    private final ClienteThread[] threads;
    private int maxClientsCount;//Establece el maximo de clientes para conectarse

    public ClienteThread(Socket clientSocket, ClienteThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        try {
            int maxClientsCount = this.maxClientsCount;
            ClienteThread[] threads = this.threads;

            //Crea flujos de entrada y salida para clientes cliente.
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String name;
            while (true) {
                os.println("Introduzca su nombre");
                name = is.readLine().trim();
                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    os.println("nombre invalido");

                }

            }
//              Msg de bienvenida y envío de conversaciones anteriores 
            System.out.println("" + name + "  Se unio al grupo");
            System.out.print(Servidor.mensagens);
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].os.println("" + name + "  Se unio al grupo");
                    }
                }
            }
            /* Comienza la conversación*/
            while (true) {
                String line = is.readLine();
                /*asigna nuevas conversaciones a la variable globo de msg */
                Server.mensagens += name + " dice: " + line + "\n";
                if (line.startsWith("/quit")) {
                    break;
                } else {
                    /* El mensaje es público y lo transmite a todos los demás clientes. */
                    synchronized (this) {
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].clientName != null) {
                                threads[i].os.println(name + " dice: " + line);
                            }
                        }
                    }
                }
            }
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].os.println("*** El usuario " + name
                                + " está saliendo de la sala de chat !!! ***");
                    }
                }
            }
            os.println("*** Bye " + name + " ***");

            
//       * Limpiar. Establezca la variable de subproceso actual en nulo para que un nuevo cliente
//       * podría ser aceptado por el servidor.
          
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
             /**
             * Cierre la secuencia de salida, cierre la secuencia de entrada,
             * cierre el socket.
             */
            is.close();
            os.close();
            clientSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
