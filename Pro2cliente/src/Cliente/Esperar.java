

package Cliente;

import com.sun.corba.se.impl.transport.EventHandlerBase;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;


public class Esperar extends Thread{
    ArrayList<RecibirArchivo> descarga;
    String ruta;
    Frame obj;
    Barra barrita;
   // int variable=0;
    
    public Esperar(ArrayList vec,Frame rut,int x, int y){
        descarga=vec;
        ruta=rut.rutaguar;
        obj=rut;
        barrita= new Barra(x,y);
        obj.panelbar.add(barrita.barra);
        obj.panelbar.add(barrita.kb);
        barrita.barra.setToolTipText("Archivo Completo");
        barrita.barra.setBackground(Color.blue);
        barrita.barra.setName("Archivo Completo");
        obj.panelbar.updateUI();
        
    }
     public void run(){
         super.run();
        try {
            for (int i = 0; i < descarga.size(); i++) {
                try {           
                    
                    descarga.get(i).join();
                    System.out.println("tamano +++ "+descarga.size());
                   
                } catch (InterruptedException ex) {
                    Logger.getLogger(Descargas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
             unirArchivo();
        } catch (IOException ex) {
            Logger.getLogger(Descargas.class.getName()).log(Level.SEVERE, null, ex);
        }
        String ext=getExtension(descarga.get(0).nombreArchivo);
        System.out.println("---- EXT --------- "+ext);
            if(ext.compareTo("PNG")==0 || ext.compareTo("png")==0 || ext.compareTo("JPG")==0 || ext.compareTo("jpg")==0){
               AbrirImg(descarga.get(0).nombreArchivo);
               
            }else{
                if(ext.compareTo("mp3")==0 || ext.compareTo("MP3")==0 || ext.compareTo("OGG")==0 || ext.compareTo("ogg")==0 || ext.compareTo("WAV")==0 || ext.compareTo("wav")==0){
                    try {
                        AbrirAudio();
                    } catch (BasicPlayerException ex) {
                        Logger.getLogger(Esperar.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                       try {
                            File path = new File (ruta+"\\"+descarga.get(0).nombreArchivo);
                            Desktop.getDesktop().open(path);
                       }catch (IOException ex) {
                            ex.printStackTrace();
                       }
                }
            }
        
    }
    public void unirArchivo() throws IOException{
        File arch= new File("src/Recursos");
        File[] vec= arch.listFiles();
        int variable=0;
          FileOutputStream fos = new FileOutputStream(ruta+"\\"+descarga.get(0).nombreArchivo);
          BufferedOutputStream out = new BufferedOutputStream(fos);
          double div=0;
          int aux=0, aux_fin=0;
          long Ftiempo, Itiempo, tiempo, total;
          Itiempo=System.currentTimeMillis();
         
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].getName().equals(variable+descarga.get(0).nombreArchivo)) {
                FileInputStream fis;
                variable++;
                try {
                    
                    fis = new FileInputStream("src/Recursos/"+vec[i].getName());
                    BufferedInputStream bis = new BufferedInputStream( fis );
                    int tam_aux=0;
                    
                    aux_fin= descarga.get(0).tam;
                 
                    do{
                        byte[] buffer = new byte[1];
                        bis.read( buffer );
                        div=((aux)*100)/(aux_fin); 
                          
                                out.write(buffer);
                                Ftiempo=System.currentTimeMillis(); 
                                barrita.barra.setValue((int) div);
                                tiempo=(long) ((Ftiempo-Itiempo)*0.001);
                                total=(long) ((aux)*0.001);
                                barrita.kb.setText(total+"/"+tiempo+" Kb/seg");
                                obj.panelbar.updateUI(); 
                            
                       

                       tam_aux=tam_aux+1;
                       aux++;
                    }while(tam_aux<vec[i].length());
                    
                    System.out.println(" NUM----------- "+i);
                    barrita.barra.setValue((int) div+1);
                    obj.panelbar.updateUI();
                    bis.close();
                   vec[i].delete();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Descargas.class.getName()).log(Level.SEVERE, null, ex);
                }
              //  i=vec.length;
            }
        }
        
        out.close();
        
                  
    }
    public String getExtension(String filename) {
            int index = filename.lastIndexOf('.');
            if (index == -1) {
                  return "";
            } else {
                  return filename.substring(index + 1);
            }
    }
    public void AbrirImg(String nombreArchivo){
       
                JFrame showImg=new JFrame();
                showImg.setTitle(nombreArchivo);
                showImg.setResizable(false);
                JLabel labelImag= new JLabel();
                showImg.setBounds(20, 20, 500, 500);
                labelImag.setBounds(0, 0, 500, 500);
                ImageIcon icon = new ImageIcon(ruta+"\\"+descarga.get(0).nombreArchivo);
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(labelImag.getWidth(), labelImag.getHeight(), Image.SCALE_DEFAULT));
                
                labelImag.setIcon(icono);
                showImg.getContentPane().add(labelImag);
                showImg.setVisible(true);
                showImg.show();
            
    
    }
    public void AbrirAudio() throws BasicPlayerException{
        BasicPlayer basicPlayer=new BasicPlayer();
        basicPlayer.open(new File(ruta+"\\"+descarga.get(0).nombreArchivo));
        basicPlayer.play();
        
        JFrame venta= new JFrame();
        venta.setLayout(null);
        JButton pausa= new JButton();
        JButton iniciar= new JButton();
        iniciar.setBounds(5, 5, 65, 30);
        iniciar.setText("Play");
        pausa.setBounds(70, 5, 65, 30);
        pausa.setText("Stop");
        venta.setBounds(100, 100, 150, 100);
        venta.getContentPane().add(iniciar);
        venta.getContentPane().add(pausa);
        venta.setVisible(true);
        venta.setResizable(false);
        venta.show();
        
          iniciar.addMouseListener(new MouseAdapter(){//-------iniciar
           public void mousePressed(MouseEvent e){
               try {
                   basicPlayer.play();
               } catch (BasicPlayerException ex) {
                   Logger.getLogger(Esperar.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           });
          
           pausa.addMouseListener(new MouseAdapter(){//-------iniciar
           public void mousePressed(MouseEvent e){
               try {
                   basicPlayer.stop();
               } catch (BasicPlayerException ex) {
                   Logger.getLogger(Esperar.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           });
          
          venta.addWindowListener( new WindowAdapter() { 
          public void windowClosing( WindowEvent evt ) { 
                try {
                    basicPlayer.stop();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(Esperar.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } 
            } ); 
    }
}
