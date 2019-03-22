package aplicacion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ParameterStringBuilder {
    public static String getParamsString(Map params) throws UnsupportedEncodingException {
        String result = "";

        Iterator entryIt = params.entrySet().iterator();
        for (Map.Entry entry; entryIt.hasNext(); ) {
        	entry = (Entry) entryIt.next();
            result = result.concat(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
            result = result.concat("=");
            result = result.concat(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
            result = result.concat("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }
}