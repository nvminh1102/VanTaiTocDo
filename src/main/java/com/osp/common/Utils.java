package com.osp.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 1/3/2018.
 */
public class Utils {

    public static String getIpClient(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    public static String randomCode(int length) {
        String result = "";
        try {
            String pattern = "123456789qwertyupasdfghjkzxcvbnm";
            for (int i = 0; i < length; i++) {
                Random rand = new Random();
                int pos = rand.nextInt((31 - 0) + 1) + 0;
                result += pattern.charAt(pos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public static String DateToString(Date date){

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
    public static String DateFormat(String strDate) {
        if (strDate.indexOf("/") != -1) {
            strDate = strDate.split("/")[2] + strDate.split("/")[1] + strDate.split("/")[0];
        }
        return strDate;
    }
    public boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static String trim(String str) {
        if (StringUtils.isNotBlank(str)) {
            str.trim();
        }
        return str;
    }

    public static void trimAllFieldOfObject(Object item) {
        if (item == null) {
            return;
        }
        Field[] fields = item.getClass().getDeclaredFields();
        if (fields == null) {
            return;
        }

        for (Field f : fields) {
            if (f.getType().isPrimitive()) {
                continue;
            }
            if (f.getType().equals(String.class)) {
                try {
                    f.setAccessible(true);
                    String value = (String) f.get(item);
                    f.set(item, org.apache.commons.lang3.StringUtils.trimToNull(value));
                } catch (IllegalAccessException e) {
                }

            }
        }
    }

    public static Date addDate(Date date, int type, int input) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, input);
        date = c.getTime();
        return date;
    }

    //Check Regular expression
    public static boolean checkRegex(String input, String exp) {
        boolean result = false;
        if (!input.isEmpty()) {
            try {
                Pattern pattern;
                pattern = Pattern.compile(exp);
                Matcher matcher;
                matcher = pattern.matcher(input);
                result = matcher.matches();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            result = true;
        }
        return result;
    }

    //Convert String to Date
    public static Date str2date(String input, String format) throws java.text.ParseException {
        Date result = null;
        if (!input.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                result = formatter.parse(input);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //Convert Date to String
    public static String date2str(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    private static int MYANMAR_AREACODE = 95;
    private static int MYANMAR_PHONENUMBER_MINLEN = 9;
    public static String getStandardMytelMobileNumber(String msisdn) {
        if (msisdn == null) { return null; }
        if (msisdn.length() < MYANMAR_PHONENUMBER_MINLEN) { return null; }
        return MYANMAR_AREACODE + getMytelMobileNumberWithoutPre(msisdn);
    }

    public static String getMytelMobileNumberWithoutPre(String msisdn) {
        if (msisdn == null) { return ""; }
        if (msisdn.startsWith("0")) {
            msisdn = msisdn.substring(1);
        } else if (msisdn.startsWith(MYANMAR_AREACODE+"0") && msisdn.length()>MYANMAR_PHONENUMBER_MINLEN ) { //Tranh case sđt bắt đầu giống với mã vùng
            msisdn = msisdn.substring((MYANMAR_AREACODE+"0").length());
        } else if (msisdn.startsWith(MYANMAR_AREACODE+"") && msisdn.length()>MYANMAR_PHONENUMBER_MINLEN) {
            msisdn = msisdn.substring((MYANMAR_AREACODE+"").length());
        } else if (msisdn.startsWith("+"+MYANMAR_AREACODE+"0")) {
            msisdn = msisdn.substring(("+"+MYANMAR_AREACODE+"0").length());
        } else if (msisdn.startsWith("+"+MYANMAR_AREACODE)) {
            msisdn = msisdn.substring(("+"+MYANMAR_AREACODE).length());
        }
        return msisdn;
    }

}
