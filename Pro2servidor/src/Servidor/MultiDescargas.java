

package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiDescargas extends Thread{
    ServerSocket servidor=null;
    Socket servicio=null;
    ArrayList<EnviarArchivo> socketcomunica = new ArrayList<EnviarArchivo>();
    //EnviarArchivo[] socketcomunica = new EnviarArchivo[300];
    int clientes=0;
    Frameservidor obj1;
   
    
    public MultiDescargas(Frameservidor obj) throws IOException{
      servidor= new ServerSocket(4000);
     obj1=obj;
    }
   
     public void run(){
        super.run(); 
        System.out.println("Servidor Listo...");
        while(true)
        {
       
         try {
             servicio=servidor.accept();
             socketcomunica.add(new EnviarArchivo(servicio,clientes,obj1));
             socketcomunica.get(clientes).start();
             //socketcomunica[clientes]=new EnviarArchivo(servicio,clientes,obj1);
             //socketcomunica[clientes].start();
             clientes++;
         } catch (IOException ex) {
             System.out.println("aqui1111");
             Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        }
     }
}
