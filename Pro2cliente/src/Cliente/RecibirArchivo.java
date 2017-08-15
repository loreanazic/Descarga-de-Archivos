/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.OBJECT_NOT_EXIST;


public class RecibirArchivo extends Thread{
    Socket cliente;
    Frame obj;
    int inicio, fin,num;
    String  nombreArchivo;
    int   tam;
    boolean activo=false;
    int tam1=0;
    Barra barrap;
    int tanbarra;
    int time=0;
    int borrar=0;
      FileOutputStream fos;
      BufferedOutputStream out;
      BufferedInputStream in;
    
    public RecibirArchivo(Frame obj1, int inicio1, int fin1,int i1,int xb,int y) throws IOException{
        cliente=new Socket("localhost",4000);
        obj=obj1;
        inicio=inicio1;
        fin=fin1;
        num=i1;
        tanbarra=y;
        barrap= new Barra(xb,tanbarra);
    }
    
    public void run(){
        super.run();
        DataOutputStream salida=null;
        DataInputStream entradita = null;
        if (inicio!=fin) {
           obj.panelbar.add(barrap.barra);
           obj.panelbar.add(barrap.kb);
           obj.panelbar.updateUI(); 
        }
        
        
        
        try {
            
            entradita = new DataInputStream(cliente.getInputStream());
            salida= new DataOutputStream(cliente.getOutputStream());
      
        
            salida.writeByte(obj.Lista.getSelectedIndex());
            salida.writeInt(inicio);
            salida.writeInt(fin);
            
       // System.out.println(" BAN CLIENTE-----------------------"+obj.Lista.getSelectedIndex());
                   tam = entradita.readInt();
                 // System.out.println("==========  "+tam);
                  nombreArchivo = entradita.readLine();
                    //System.out.println("----"+nombreArchivo);
             
                    FileOutputStream fos = new FileOutputStream("src/Recursos//"+num+nombreArchivo);
                    BufferedOutputStream out = new BufferedOutputStream(fos);
                    BufferedInputStream in = new BufferedInputStream(cliente.getInputStream());
                    
                    boolean ent=false;
                    double div=0,valor=0;
                    long Ftiempo, Itiempo, tiempo, total;
                    
                    Itiempo=System.currentTimeMillis();
                    time=0;
                    
                   tam1=inicio;
                    
                    do{
                       
                        try {
                        sleep(time);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RecibirArchivo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        byte[] buffer = new byte[1];
                            if (tam1>=inicio && tam1<=fin) {
                                    div=((tam1-inicio)*100)/(fin-inicio); 
                                    ent=true;
                                   // System.out.println(" DIV--------"+div+" FIN--------- "+fin);
                                    for(int i=0;i<buffer.length;i++)
                                     {
                                      
                                        buffer[i] = (byte)in.read();
                                        Ftiempo=System.currentTimeMillis(); 
                                         barrap.barra.setValue((int) div);
                                         tiempo=(long) ((Ftiempo-Itiempo)*0.001);
                                         total=(long) ((tam1-inicio)*0.001);
                                         barrap.kb.setText(total+"/"+tiempo+" Kb/seg");
                                       //  System.out.println(" VALORRR -----------"+valor);
                                        obj.panelbar.updateUI(); 
                                        
                                        
                                     }
                                 out.write(buffer);
                                 
                            }
                            tam1=tam1+1;
                            if (tam1>fin) {
                                tam1=tam;
                            }
                    }while(tam1<tam);
                    
                    
                    
                    
                    barrap.barra.setValue((int) div+1);
                    obj.panelbar.updateUI();
                    if (!ent) {
                       obj.panelbar.remove(barrap.barra);
                       obj.panelbar.remove(barrap.kb);
                     //  barrap.barra.setVisible(false);
                    //   barrap.kb.setVisible(false);
                       obj.panelbar.updateUI();
                    }
                    
                    out.flush();
                    in.close();
                    out.close();
                    cliente.close();  
                if (borrar==1) {
                    File fos1 = new File("src/Recursos//"+num+nombreArchivo);
                    System.out.println("---- "+fos1.getName());
                    fos1.delete();
                    borrar=0;
                }
    
                    
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
