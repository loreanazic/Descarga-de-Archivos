/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author loreanaisabel
 */
public class Copiar {
    private String nombreArchivo = "";
   
     public  File archivo;
         
         public Copiar( String nombreArchivo )
         {
              this.nombreArchivo = nombreArchivo;
         }
         
         public void copiararchivo(){
             
                 try {
                 
            
                  archivo = new File( nombreArchivo );
          
                 int tamañoArchivo = ( int )archivo.length();
                 FileInputStream fis = new FileInputStream( nombreArchivo );
                 BufferedInputStream bis = new BufferedInputStream( fis );
                 
                 byte[] buffer = new byte[ tamañoArchivo ];
                 bis.read( buffer );
                 
                 FileOutputStream fos = new FileOutputStream("src/Archivos//"+archivo.getName());
                 BufferedOutputStream out = new BufferedOutputStream(fos);
                 out.write(buffer);
                
                    out.flush();
                    out.close();
                    bis.close();
          
                
          } catch (Exception e) {
             }
         }
         
    
}
