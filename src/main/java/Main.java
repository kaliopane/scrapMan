import java.util.List;

public class Main {

   final static Util _util = new Util();
   final static ResourceChecker _resourceChecker = new ResourceChecker();


   public static void main(String... args) {
      System.out.println("Enter IP or range of IP addresses with a dash delimiter");
      String userInput = _util.getUserInput();
      List<String> boundries = _util.checkUserRangeInput(userInput);
      List<String> allIps = _util.generateIps(boundries);
      for(String ip: allIps){
         System.out.println(ip);
        // _resourceChecker.checkResourcesForAddress(ip);
      }
   }
}

