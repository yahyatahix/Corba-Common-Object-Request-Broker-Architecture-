 // Server.java
  import java.io.*;
  import java.util.Properties;
  import org.omg.CORBA.*;
  import org.omg.PortableServer.*;
  import static java.lang.System.*;
  import org.omg.CosNaming.*;
  public class ServerNaming {
    public static void main(String[] args) {
      HorlogeDelegate cd=new HorlogeDelegate(); 
      try {
        Properties props = getProperties();
        ORB orb = ORB.init(args, props);
        org.omg.CORBA.Object obj = null;
        POA rootPOA = null;
        try {
          obj = orb.resolve_initial_references("RootPOA");
          rootPOA = POAHelper.narrow(obj);
        } catch (org.omg.CORBA.ORBPackage.InvalidName e) { }
     
        NamingContext nc =  NamingContextHelper.narrow(orb.resolve_initial_references("NameService"));
        HorlogePOATie c_impl = new HorlogePOATie(cd); 
        Horloge c = c_impl._this(orb);
      
        NameComponent[] name0 = new NameComponent[1];
        name0[0]=new NameComponent();
        name0[0].id="home";
        name0[0].kind="Objects";

        NamingContext nc1 = nc.bind_new_context(name0);

        NameComponent[] name1 = new NameComponent[1];
        name1[0]=new NameComponent();
        name1[0].id="alarm";
        name1[0].kind="fonction";

        nc.rebind(name1,c);
          
          
         System.out.println("Server Started. Stop:Ctl-c");
        rootPOA.the_POAManager().activate();
        orb.run();
      } catch(Exception ex) {
        out.println("Exception: " + ex.getMessage());
exit(1); }
} }