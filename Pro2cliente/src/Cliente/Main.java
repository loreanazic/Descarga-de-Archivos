

package Cliente;

import java.io.File;
import java.io.IOException;


public class Main {

    
    public static void main(String[] args) throws IOException {
        File arch1= new File("src/Recursos");
        File[] vec= arch1.listFiles();
        for (int i = 0; i < vec.length; i++) {
            vec[i].delete();
        }
        Frame obj= new Frame();  
        Cliente clin=new Cliente(obj);
        obj.setVisible(true);
   //     Barra bar= new Barra(30);
    //    obj.panelbar.add(bar.barra);
        clin.start();
        
        
    }
    
}
