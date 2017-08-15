package Servidor;

import java.util.StringTokenizer;

public class CorregirRuta {
    
    String ruta, separador, nuevoSeparador; 
 
    public CorregirRuta(String laruta, String sep, String nuevoSep)
    {
        ruta=laruta;
        separador=sep;
        nuevoSeparador=nuevoSep;
    }
 
    public String obtenerRutaCorregidaWindows()
    {
         StringTokenizer tokens=new StringTokenizer(ruta, separador);
        
         String rutaCorregida = new String();
         
         int noTokens = tokens.countTokens();
         int i = 1;
         do
         {      
            rutaCorregida += tokens.nextToken()+nuevoSeparador;
            i++;
         }while(i<noTokens);
        
         rutaCorregida += tokens.nextToken();       
        
         System.out.println(rutaCorregida+"\n"+noTokens+ " tokens");
         return rutaCorregida;
    }
    
}
