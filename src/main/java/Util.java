import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.deploy.util.StringUtils;

public class Util {

   private static final String IPv4_REGEX =
         "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
               "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
               "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
               "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
   private static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);

   protected String getUserInput() {
      Scanner scanner = new Scanner(System.in);
      return scanner.nextLine();
   }

   protected boolean isValidAddress(String address) {
      Matcher matcher = IPv4_PATTERN.matcher(address);
      return matcher.matches();
   }

   protected List<String> checkUserRangeInput(String userInput) {
      List<String> result = new ArrayList<String>();
      if(isValidAddress(userInput)) {
         result.add(userInput);
      }
      else {
         final String[] addresses = userInput.split("-");
         for (final String address: addresses) {
            if (isValidAddress(address.trim())) {
               result.add(address.trim());
            }
         }
      }
      return result;
   }

   protected List<String> generateIps(List<String> boundryIps) {
      List<String> allIps = new ArrayList<String>();
      long ipOne = ipToLong(boundryIps.get(0));
      long ipTwo = ipToLong(boundryIps.get(1));
      long highLong = ipOne > ipTwo ? ipOne : ipTwo;
      long lowLong = highLong == ipOne ? ipTwo : ipOne;
      long _step = 65536;
      long steps = (highLong - lowLong) / _step;
      for(long step=0; step<=steps; step++) {
         allIps.add(longToIp(lowLong + _step*step));
      }
      return allIps;
   }


   public static long ipToLong(String ipAddress) {
      String[] ip = ipAddress.split("\\.");
      long result = 0;
      result |= Long.parseLong(ip[0]) << 40;
      result |= Long.parseLong(ip[1]) << 32;
      result |= Long.parseLong(ip[2]) << 24;
      result |= Long.parseLong(ip[3]) << 16;

      return result;
   }

   public String longToIp(Long l) {
      return ((l >> 40) & 0xFF) +
            "." + ((l >> 32) & 0xFF) +
            "." + ((l >> 24) & 0xFF) +
            "." + ((l >> 16) & 0xFF);
   }

   private String getHigherIp(List<String> ips) {
      String biggerIp = "";
      String[] ipOneDelim = ips.get(0).split(".");
      String[] ipTwoDelim = ips.get(1).split(".");
      if(ips.get(0).equals(ips.get(1))) {
         return ips.get(0);
      }
      for (int i=0; i < ipOneDelim.length; i++) {
         int octetOne = Integer.parseInt(ipOneDelim[i]);
         int octetTwo = Integer.parseInt(ipTwoDelim[i]);
         if (octetOne > octetTwo) {
            biggerIp = StringUtils.join(Arrays.asList(ipOneDelim), ".");
            break;
         } else if (octetTwo > octetOne) {
            biggerIp = StringUtils.join(Arrays.asList(octetTwo), ".");
            break;
         }
      }
      return biggerIp;
   }

   private String convertToHex(String ip) {
      String hexIp="";
      if(ip != null && !ip.equals("")) {
         String replaced = ip.replace(".", "");
         char[] messChar = replaced.toCharArray();
         for (int i=0; i<messChar.length; i++) {
            hexIp += Integer.toBinaryString(messChar[i]);
         }
      }
      System.out.println(hexIp);
      return hexIp;
   }
}
