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

}
