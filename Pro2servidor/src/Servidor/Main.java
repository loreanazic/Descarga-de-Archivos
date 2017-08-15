

package Servidor;

import java.io.File;
import java.io.IOException;


public class Main {
   public static File carpeta;
    public static void main(String[] args) throws IOException {
       Frameservidor obj= new Frameservidor();  
       Servidor ser;
       carpeta = new File("src/Archivos");
       
        File[] vec= carpeta.listFiles();
        if (carpeta.exists()) {
           for (int i = 0; i < vec.length; i++) {
            obj.model.addElement(vec[i].getName());
        } 
        }else{System.out.println("NO EXISTE");}
        
        ser=new Servidor(obj);
  ser.start();
  obj.setVisible(true);
    
}}
