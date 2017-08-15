
package Cliente;

import com.sun.prism.shader.DrawEllipse_RadialGradient_PAD_AlphaTest_Loader;
import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.plaf.BorderUIResource;


public class Cliente extends Thread{
     Socket cliente;
     Frame obj;
     DefaultListModel model1;
      int esperar=0, num_eti=0;     
     public ArrayList<RecibirArchivo> recibo;
   //  public static int y=30;
     public ArrayList<JLabel> eti;
     public Cliente(Frame frame) throws IOException {
     cliente=new Socket("localhost",3000);
     obj=frame;
     eti=new ArrayList<JLabel>();
     model1=obj.model;
    }
     
      public void run(){
         super.run();
         
         DataOutputStream salida=null;
         DataInputStream entradita = null;
        try {
            
            entradita = new DataInputStream(cliente.getInputStream());
            salida= new DataOutputStream(cliente.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        String cadena, nombreArchivo="",msj="";
        int numero, tam=0,cant_part=0,ult_part=0;
       while(true){
            try {
                
                if (obj.band) {
                        System.out.println("entroooooooooooooooooooooo");
                        salida.writeByte(0);
                        salida.writeInt(obj.Lista.getSelectedIndex());
                        msj=entradita.readLine();
                        tam=entradita.readInt();
                        
                      //  obj.c++;
                        recibo=new ArrayList<RecibirArchivo>();
                        if (obj.c==0) {
                        obj.c++;
                        }
                        cant_part=tam/obj.c;
                        ult_part=tam-(cant_part*(obj.c-1));
                        int x=0;
                        
                        eti.add(new JLabel((String) obj.model.get(obj.Lista.getSelectedIndex())));
                        eti.get(num_eti).setBounds(20, obj.y, 150, 20);
                        obj.panelbar.add(eti.get(num_eti));
                        obj.panelbar.updateUI();
                        
                        num_eti++;
                        obj.y=obj.y+25;
                        
                        for (int i = 0; i < obj.c; i++) {
                            if(i==obj.c-1){
                                
                                recibo.add(new RecibirArchivo(obj,x,x+ult_part,i,20,obj.y));
                                obj.y=obj.y+25;
                                recibo.get(i).start();
                            }else{
                                
                                recibo.add(new RecibirArchivo(obj,x,(cant_part*(i+1))-1,i,20,obj.y));
                                obj.y=obj.y+25;
                                recibo.get(i).start();
                                x=x+cant_part;
                            }
                        }
                        Descargas des= new Descargas(recibo,obj);
                        Esperar esp= new Esperar(recibo, obj,20,obj.y);
                        des.start();
                        esp.start();
                        obj.y=obj.y+25;
                        obj.band=false;
                        obj.c=0;
                        
                    
                }else{
                   salida.writeByte(1);
                   if ((esperar=entradita.read())==2) {
                        cadena=entradita.readLine();
                        
                        if (cadena.equals("eliminar")) {
                            
                            cadena=entradita.readLine();
                            numero=Integer.parseInt(cadena); 
                            model1.remove(numero);
                        }else{
                            model1.addElement(cadena);
                            System.out.println("hola"+cadena);
                        }
                    }
                    
                }
               
                
                
                
               // areatex.append("Mensaje: "+entradita.readLine()+"\n");
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
    }
}
