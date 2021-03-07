 // DelegationServer.java
  import java.io.*;
  import java.util.Properties;
  import org.omg.CORBA.*;
  import org.omg.PortableServer.*;
  import javax.swing.*;
  import static java.lang.System.*;
  public class DelegationServer {
    public static void main(String[] args) {
      try {
        CounterDelegate cd;
        JFrame f = new JFrame("Counter Server");
        f.getContentPane().add(cd = new CounterDelegate());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        Properties props = getProperties();
        ORB orb = ORB.init(args, props);
        org.omg.CORBA.Object obj = null;
        POA rootPOA = null;
        try {
          obj = orb.resolve_initial_references("RootPOA");
          rootPOA = POAHelper.narrow(obj);
        } catch (org.omg.CORBA.ORBPackage.InvalidName e) { }
        CounterPOATie c_impl = new CounterPOATie(cd);
        Counter c = c_impl._this(orb);
        try {
          FileOutputStream file =
            new FileOutputStream("Counter.ref");
          PrintWriter writer = new PrintWriter(file);
          String ref = orb.object_to_string(c);
          writer.println(ref);
          writer.flush();
          file.close();
          out.println("Server started."
            + " Stop: Close-Button");
        } catch(IOException ex) {
          err.println("File error: " + ex.getMessage());
exit(2); }
        rootPOA.the_POAManager().activate();
        orb.run();
      } catch(Exception ex) {
        out.println("Exception: " + ex.getMessage());
exit(1); }
} }