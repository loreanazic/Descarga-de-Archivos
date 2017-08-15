

package Cliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Descargas extends Thread{
    ArrayList<RecibirArchivo> descarga;
    String ruta;
    Frame obj;
    int yb=0;
    int pos=0;
    
    public Descargas(ArrayList vec,Frame rut){
        descarga=vec;
        ruta=rut.rutaguar;
        obj=rut;
       
    }
    public void run(){
        super.run();
        int inc,fn,nuevo,partes,parte2;
        int val;
        while(true){
            try {
                sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(Descargas.class.getName()).log(Level.SEVERE, null, ex);
            }
            val=0;
          if (obj.nueva && descarga.get(0).nombreArchivo.compareTo(obj.seleccion)==0) {
              try {
            //  System.out.println(" NUEVA******************************");
                    
                   // descarga.get(descarga.size()-1).suspend();
                   descarga.get(descarga.size()-1).time=1000;
                   if (descarga.get(descarga.size()-1).tam1>descarga.get(descarga.size()-1).inicio) {
                      val=descarga.get(descarga.size()-1).tam1;
                   }else{
                       val=descarga.get(descarga.size()-1).inicio;
                   }
                    nuevo= descarga.get(descarga.size()-1).fin-  val;
         
                    partes=nuevo/2;
                    parte2= nuevo-partes;
                    descarga.get(descarga.size()-1).fin=  val +partes;
                    
                    inc=descarga.get(descarga.size()-1).fin+1;
                    fn= inc +parte2-1;
                    descarga.add(new RecibirArchivo(obj, inc, fn, descarga.size(),420,(descarga.get(0).tanbarra+yb)));
                  //  descarga.get(descarga.size()-2).resume();
                   descarga.get(descarga.size()-2).time=0;
                    descarga.get(descarga.size()-1).start();
                    yb=yb+25;
                    pos++;
              } catch (IOException ex) {
                  Logger.getLogger(Descargas.class.getName()).log(Level.SEVERE, null, ex);
              }
                obj.nueva=false;
            }
            int nuevofin;
            if (obj.menos && descarga.get(0).nombreArchivo.compareTo(obj.seleccion)==0) {
                if(descarga.get(descarga.size()-2).isAlive()){
                    
                    descarga.get(descarga.size()-2).time=1000;
                    descarga.get(descarga.size()-1).time=1000;
                   // descarga.get(descarga.size()-2).suspend();
                   // descarga.get(descarga.size()-1).suspend();
                    descarga.get(descarga.size()-1).borrar=1;
                    nuevofin=descarga.get(descarga.size()-1).fin;
                    System.out.println("antes fin"+nuevofin);
                    descarga.get(descarga.size()-2).fin=nuevofin;
                  
                    
                   
                    obj.panelbar.remove(descarga.get(descarga.size()-1).barrap.barra);
                    obj.panelbar.remove(descarga.get(descarga.size()-1).barrap.kb);
                   // yb=yb-25;
                    descarga.remove(descarga.size()-1);
                    
                    descarga.get(descarga.size()-1).time=0; 
                    System.out.println("fin archivo"+descarga.get(descarga.size()-1).fin);
                    
                    if (pos>0) {
                        yb=yb-25;
                        pos--;
                    }
                    
                }
                obj.menos=false;
            }
          
        }
       
    
    } 

}
