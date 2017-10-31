/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

// Para cada conexión de cliente llamamos a esta clase
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

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

}
