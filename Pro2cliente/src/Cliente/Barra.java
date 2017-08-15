

package Cliente;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Barra  {
    JProgressBar barra;
    JLabel kb;
    public Barra(int x,int y){
        barra= new JProgressBar(0, 100);
        barra.setBounds(x, y, 250, 20);
        barra.setValue(0);
        barra.setStringPainted(true);
        barra.setVisible(true);
        kb=new JLabel();
        kb.setBounds(x+280, y, 100, 20);
        kb.setVisible(true);
        kb.setText("Kb/seg");
    }
}
