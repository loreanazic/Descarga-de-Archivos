

package Servidor;

import static Servidor.Frameservidor.banbuscar;
import static Servidor.Frameservidor.baneliminar;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;


public class Comunica extends Thread{
    public Socket comunicar;
    int num, ban1=0,particiones=0;
    Frameservidor obj2;
  //  MultiDescargas enviar;
	public Comunica (Socket llega, int numcli,Frameservidor model)
	{
            comunicar=llega;
            num=numcli;
            obj2=model;
        }
        
        public void run(){
        DataInputStream entrada=null;
        DataOutputStream salida=null;
         try {
             entrada=new DataInputStream(comunicar.getInputStream());
             salida = new DataOutputStream(comunicar.getOutputStream());
             System.out.println("comunicaaaa111111111111111111a   "+obj2.model.size());
             
             for (int i = 0; i < obj2.model.size(); i++) {
                salida.writeByte(2);
                salida.writeBytes(obj2.model.get(i)+"\n");
                
             }
             
         } catch (IOException ex) {
             Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
         }
        String cadena=null;
        int tamano=obj2.model.size();
       int ind;
        
         while(true){
            try {
                if ((ban1=entrada.read())==1) {
                    
                    if(obj2.model.size()>tamano){
                        
                        try {
                            salida.writeByte(2);
                            salida.writeBytes(obj2.model.get(obj2.model.size()-1)+"\n");
                            tamano=obj2.model.size();
                        } catch (IOException ex) {
                            Logger.getLogger(Comunica.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                    if (obj2.model.size()<tamano) {
                        
                        try {
                            salida.writeByte(2);
                            cadena=Integer.toString(baneliminar);
                            salida.writeBytes("eliminar"+"\n");
                            salida.writeBytes(cadena+"\n");
                            tamano=obj2.model.size();
                        } catch (IOException ex) {
                            System.out.println("------aquii eliminar------"+baneliminar);
                            Logger.getLogger(Comunica.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{salida.writeByte(0);}
                    
                    }
                   
                }else{
                    ind=entrada.readInt();
                    System.out.println(" IND -- "+ind);
                    File archivo = new File("src/Archivos//"+obj2.model.get(ind));
                    int tamañoArchivo = ( int )archivo.length();
                    salida.writeBytes("hola+++"+"\n");
                    salida.writeInt(tamañoArchivo);
                    System.out.println(" File tam---- "+tamañoArchivo);    
                }
            } catch (IOException ex) {
                Logger.getLogger(Comunica.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
           
            
        
}
}
