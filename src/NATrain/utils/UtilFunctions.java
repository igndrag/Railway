package NATrain.utils;

public class UtilFunctions {
    public static int parseIfPositiveNumeric(String str) {
        try {
            int i = Integer.parseInt(str);
             if (i >= 0) {
                 return i;
             } else {
                 return -1;
             }
        } catch(NumberFormatException e){
            return -1;
        }
    }

    public static long convertUidToLong (String b1, String b2, String b3, String b4) {
        try {
            return   Long.parseLong(b1, 16) << 24 |
                    (Long.parseLong(b2, 16) & 0xff) << 16 |
                    (Long.parseLong(b3, 16) & 0xff) << 8|
                    (Long.parseLong(b4, 16 ) & 0xff);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
