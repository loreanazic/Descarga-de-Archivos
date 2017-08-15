

package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;


public class Servidor extends Thread{
    ServerSocket servidor=null;
    Socket servicio=null;
    ArrayList<Comunica> socketcomunica = new ArrayList<Comunica>();
    //Comunica[] socketcomunica = new Comunica[20];
    int clientes=0;
    Frameservidor obj1;
    MultiDescargas enviar;
    public Servidor(Frameservidor model) throws IOException{
      servidor= new ServerSocket(3000);
      obj1=model;
    }
    
    
    
     public void run(){
        super.run(); 
        System.out.println("Servidor Listo...");
         try {
            enviar=new MultiDescargas(obj1);
            enviar.start();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true)
        {
       
         try {
             servicio=servidor.accept();
             socketcomunica.add(new Comunica(servicio,clientes,obj1));
             socketcomunica.get(clientes).start();
             //socketcomunica[clientes]=new Comunica(servicio,clientes,obj1);
             //socketcomunica[clientes].start();
             clientes++;
         } catch (IOException ex) {
             System.out.println("aqui1111");
             Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
         }
         
        
        }
     }
}
