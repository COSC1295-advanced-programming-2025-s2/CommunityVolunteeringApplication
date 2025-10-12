package model;

public final class StringUtil {

    private StringUtil() { }

    //makes first letter uppercase 
    public static String toUpper(String txt) {
        if (txt == null || txt.isEmpty()) return txt;
        return txt.substring(0, 1).toUpperCase() + txt.substring(1);
    }
    
    public static String toUpper(String[] txt) {
    	String str = "";
    	for (String t : txt) {
    		str += t.substring(0,1).toUpperCase() + t.substring(1) + " ";
    	}
    	return str.trim();
    }
}
