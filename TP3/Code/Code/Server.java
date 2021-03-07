// Server.java
  //import Count.*;
  import java.util.Properties;
  import org.omg.CORBA.*;
  import org.omg.PortableServer.*;
  import org.omg.CosNaming.*;
  import org.omg.CosNaming.NamingContextPackage.*;
  import static java.lang.System.*;
  public class Server {
    private ORB orb;
    private POA rootPOA;
    private void initializeORB(String[] args) {
     Properties props = getProperties();
      orb = ORB.init(args, props);
      try {
        rootPOA = POAHelper.narrow(orb.
          resolve_initial_references("RootPOA"));
      } catch (org.omg.CORBA.ORBPackage.InvalidName ex) { }
    }
    public Server(String[] args) {
      try {
        initializeORB(args);
        NamingContext nc = NamingContextHelper.narrow(
          orb.resolve_initial_references("NameService"));
          
        CounterImpl c_impl = new CounterImpl();
        Counter c = c_impl._this(orb);
        
        NameComponent[] name = new NameComponent[1];
        name[0] = new NameComponent();
        name[0].id = "Counter";
        name[0].kind = "IIOP";
        
        
        nc.rebind(name, c);
        out.println("Server started. Stop: Ctrl-C");
        rootPOA.the_POAManager().activate();
        orb.run();
      } catch(Exception ex) {
        out.println("Exception: " + ex.getMessage());
        exit(1);
} }
    public static void main(String args[]) {
      new Server(args);
} }
