 import java.awt.GridLayout;
 import java.awt.event.*;
 import java.io.*;
 import java.util.*;
 import javax.swing.*;
import org.omg.CORBA.*;
import static java.lang.System.*;
import org.omg.CosNaming.*;


public class GUIClient extends JPanel {

    private ORB orb;
    private Horloge c;
    private javax.swing.JLabel jLabel4;
    
   

    private void initializeORB(String[] args) {
      Properties props = getProperties();
      orb = ORB.init(args, props);
    }

    private org.omg.CORBA.Object getNaming() throws InterruptedException {
      
    org.omg.CORBA.Object obj = null;
      try{

        
      
      NamingContext nc =  NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
      
      NameComponent[] name = new NameComponent[2];
      name[0]=new NameComponent();
      name[0].id="home";
      name[0].kind="Objects";

      name[1]=new NameComponent();
      name[1].id="alarm";
      name[1].kind="fontion";

      obj = nc.resolve(name);

    c = CounterHelper.narrow(obj);
    
      }catch(Exception ex){
        out.println(ex.getMessage());
      }
      return obj;
    }

    private void  createGui(){

    setLayout(new GridLayout(2, 1));
    JPanel p = new  javax.swing.JPanel();
    add(p);
        jLabel4 = new javax.swing.JLabel();
        

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel4.setText("jLabel4");
        jLabel4.setAlignmentY(2.0F);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(p);
        p.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGap(87, 87, 87))
        );
     
    }
    public GUIClient(String[] args) throws InterruptedException {
        initializeORB(args);
        org.omg.CORBA.Object obj = getNaming();
         try {
                c = HorlogeHelper.narrow(obj);
        } catch (BAD_PARAM ex) {
        out.println("Narrowing failed");
        exit(3); }
        
}



public static void main(String[] args) throws InterruptedException {
  try {
    
    GUIClient client = new GUIClient(args);
    
    JFrame f = new JFrame("Horloge Client");
    f.getContentPane().add(client);
    f.pack();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setSize(500, 500);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    client.createGui();
    while(true){
        client.jLabel4.setText(client.c.afficher());
        
        Thread.sleep(60000);
       
    }
    

    }catch (Exception ex) {
        out.println("Exception: " + ex.getMessage());
        exit(1);
} 
}

    
    public String getHour(){
        return c.afficher(); 
    }
}
