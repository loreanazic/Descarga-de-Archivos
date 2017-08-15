

package Servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class EnviarArchivo extends Thread{
     ServerSocket servidor=null;
    Socket servicio=null;
    int index=0;
    Frameservidor obj;
    int inicio,fin;
    String activo="";
    public EnviarArchivo(Socket serv, int clien,Frameservidor obj1){
        obj= obj1;
        servicio=serv;
        
      
    }
    public void run(){
        super.run();
         try {
             System.out.println("ENVIANDO----");
            
             DataInputStream entrada=null;
             DataOutputStream salida=null;
             
             entrada=new DataInputStream(servicio.getInputStream());
             salida = new DataOutputStream(servicio.getOutputStream());
             System.out.println("22222222--");
             index=entrada.read();
             inicio=entrada.readInt();
             fin=entrada.readInt();
             System.out.println("index-----"+index);
             File archivo = new File( "src/Archivos//"+obj.model.get(index));
             int tamañoArchivo = ( int )archivo.length();
             
             salida.writeInt(tamañoArchivo);
             salida.writeBytes(archivo.getName()+"\n");
             
             FileInputStream fis = new FileInputStream("src/Archivos//"+obj.model.get(index));
             BufferedInputStream bis = new BufferedInputStream( fis );
             BufferedOutputStream bos = new BufferedOutputStream( servicio.getOutputStream());
            
            int tam_aux=0;
            
             do{  
                  byte[] buffer = new byte[1];
               
                  bis.read( buffer);
                     
                    if (tam_aux>=inicio && tam_aux<=tamañoArchivo) {           
                         bos.write( buffer);  
                    }
                        tam_aux=tam_aux+1;  
            }while(tam_aux<tamañoArchivo);
             
             bis.close();
             bos.close(); 
             servicio.close();
         } catch (IOException ex) {
             Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
         }
      
         
    }
}
