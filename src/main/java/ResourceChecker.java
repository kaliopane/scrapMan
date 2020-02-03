import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ResourceChecker {

   final static int _MAX_PORT_NUM = 65535;
   //TTL in milliseconds
   final static int _CONNECTION_MAX_TTL = 50;

   public void checkResourcesForAddress(String ip) {
      for (int portNum=1; portNum<_MAX_PORT_NUM; portNum++) {
         checkResourceOnPort(ip, portNum);
      }
   }

   private void checkResourceOnPort(String ip, int portNum) {
      try {
         Socket sock = new Socket();
         sock.connect(new InetSocketAddress(ip, portNum), _CONNECTION_MAX_TTL);
         sock.close();
         System.out.println(String.format("Connected to port %s on host: %s", portNum, ip));
      } catch (SocketException e) {
        System.out.println(String.format("Could not connect to port: %s on host: %s", portNum, ip));
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
