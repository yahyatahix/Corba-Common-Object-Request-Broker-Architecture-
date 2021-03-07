// GUIClient.java
  import java.awt.GridLayout;
  import java.awt.event.*;
  import java.io.*;
  import java.util.*;
  import javax.swing.*;
  import org.omg.CORBA.*;
  import static java.lang.System.*;
  public class GUIClient extends JPanel {
    private ORB orb;
    private Counter c;
    private void initializeORB(String[] args) {
      Properties props = getProperties();
      orb = ORB.init(args, props);
    }
    private org.omg.CORBA.Object getRef(String refFile) {
      String ref = null;
      try {
        Scanner reader = new Scanner(new File(refFile));
        ref = reader.nextLine();
      } catch (IOException ex) {
        out.println("File error: "   + ex.getMessage());
    exit(2);
  }
  org.omg.CORBA.Object obj = orb.string_to_object(ref);
  if (obj == null) {
    out.println("Invalid IOR");
exit(4); }
return obj; }
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
}); }
public GUIClient(String[] args, String refFile) {
  initializeORB(args);
  org.omg.CORBA.Object obj = getRef(refFile);
  try {
    c = CounterHelper.narrow(obj);
  } catch (BAD_PARAM ex) {
    out.println("Narrowing failed");
exit(3); }
  createGUI();
}
public static void main(String[] args) {
  try {
    String refFile = "Counter.ref";
    JFrame f = new JFrame("Counter Client");
    f.getContentPane().add(
      new GUIClient(args, refFile));
    f.pack();
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setVisible(true);
    } catch (Exception ex) {
        out.println("Exception: " + ex.getMessage());
        exit(1);
} }
}
