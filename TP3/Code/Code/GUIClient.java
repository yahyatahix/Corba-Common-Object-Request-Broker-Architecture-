 // GUIClient.java
  //import Count.*;
  import java.awt.GridLayout;
  import java.awt.event.*;
  import java.util.Properties;
  import javax.swing.*;
  import org.omg.CORBA.*;
  import org.omg.CosNaming.*;
  import org.omg.CosNaming.NamingContextPackage.*;
  import static java.lang.System.*;

  public class GUIClient extends JPanel {
    private Counter c;
    private ORB orb;

    private void initializeORB(String[] args) {
      Properties props = getProperties();
      orb = ORB.init(args, props);
    }

  private void createGUI() {
  setLayout(new GridLayout(2, 1));
  JPanel p = new JPanel();
  final JLabel value;
  p.add(new JLabel("Counter value: ", JLabel.RIGHT));
  p.add(value = new JLabel(String.valueOf(c.value())));
  add(p);
  p = new JPanel();
  JButton inc, dec;
  p.add(inc = new JButton("Increment"));
  p.add(dec = new JButton("Decrement"));
  add(p);
  inc.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      c.inc();
      value.setText(String.valueOf(c.value()));
    }
  });
  dec.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      c.dec();
      value.setText(String.valueOf(c.value()));
    }
});
    }


    public GUIClient(String[] args) {
      try {
        initializeORB(args);
        NamingContext nc = NamingContextHelper.narrow(
          orb.resolve_initial_references("NameService"));
        NameComponent[] name = new NameComponent[1];
        name[0] = new NameComponent();
        name[0].id = "Counter";
        name[0].kind = "IIOP";
        org.omg.CORBA.Object obj = nc.resolve(name);
        c = CounterHelper.narrow(obj);
        createGUI();
      } catch (BAD_PARAM ex) {
        out.println("Narrowing failed");
        exit(3);
      } catch(Exception ex) {
        out.println("Exception: " + ex.getMessage());
exit(1); }
    }
    
    public static void main(String[] args) {
      JFrame f = new JFrame("Counter Client");
      f.getContentPane().add(new GUIClient(args));
      f.pack();
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setVisible(true);
} }