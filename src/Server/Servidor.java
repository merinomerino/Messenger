package Server;



import javax.swing.*;

import java.awt.*;

public class Servidor  {
	// El socket del servidor.
    private static ServerSocket servidor = null;
    
// El socket del cliente.
    private static Socket connection = null;

    public static String mensagens = "";


// Este servidor de chat puede aceptar hasta las conexiones de los clientes de maxClientsCount.
    private static final int maxClientsCount = 10;
    private static final ClienteThread[] threads = new ClienteThread[maxClientsCount];
    
     public Servidor(int porta, int tamFila) {

        try {

            servidor = new ServerSocket(porta, tamFila);

        } catch (EOFException eof) {
            System.out.println("\n Error de conexión con el cliente\n");
            eof.printStackTrace();

        } catch (IOException io) {
            io.printStackTrace();
        }
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		}
	
	private	JTextArea areatexto;
}
