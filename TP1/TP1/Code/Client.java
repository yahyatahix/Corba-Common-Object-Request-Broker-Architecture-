// Client.java
import java.io.*;
import java.util.*;
import org.omg.CORBA.*;
import static java.lang.System.*;
public class Client {
  public static void main(String[] args) {
    try {
      Properties props = getProperties();
      ORB orb = ORB.init(args, props);
      String ref = null;
      org.omg.CORBA.Object obj = null;
      try {
        Scanner reader =
          new Scanner(new File("Counter.ref"));
        ref = reader.nextLine();
      } catch (IOException ex) {
        out.println("File error: " + ex.getMessage());
exit(2); }
      obj = orb.string_to_object(ref);
      if (obj == null) {
        out.println("Invalid IOR");
exit(4); }
      Counter c = null;
      try {
        c = CounterHelper.narrow(obj);
      } catch (BAD_PARAM ex) {
        out.println("Narrowing failed");
exit(3); }
      int inp = -1;
      do {
        out.print("Counter value: " + c.value()
          + "\nAction (+/-/e)? ");
        out.flush();
        do {
          try {
            inp = in.read();
          } catch (IOException ioe) { }
        } while (inp != '+' && inp != '-' && inp != 'e');
        if (inp == '+')
          c.inc();
        else if (inp == '-')
          c.dec();
      } while (inp != 'e');
    } catch (Exception ex) {
      out.println("Exception: " + ex.getMessage());
      exit(1);
} }
}