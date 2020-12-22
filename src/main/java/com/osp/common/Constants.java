package com.osp.common;

/**
 * Created by Admin on 1/4/2018.
 */
public class Constants {

    //Danh sách biểu thức bất quy tắc
    public static final String REGEX_NUMBER = "^[0-9]*$";
    public static final String REGEX_SEARCH_NUMBER = "^[0-9*]*$";
    public static final String REGEX_TEXT_NUMBER = "^[a-zA-Z0-9]+$";
    public static final String REGEX_TEXT_USERNAME= "^[_a-zA-Z0-9]+$";
    public static final String REGEX_DATE = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    public class Log { //Log hệ thống

        public static final String system = "SYSTEM";
        public static final String user = "USER";
        public static final String category = "CATEGORY";
    }
    public static class STATUS {
    public static final int ACTIVE=1;
    public static final int INACTIVE=0;
    public static final int BLOCK=2;
    public static final int PENDING=3;
    }
    public static final String path="http://10.201.193.4:9222";
    public static final String PATH_CALL_API="http://192.168.1.89:8866/cms_splus_vt/api/splus";
    public static class StatusSync{
        public static final Long NoSync=0L;
        public static final Long Sync=1L;
    }
    public static class TrangThai{
        public static final Long HIEU_LUC=0L;
        public static final Long HET_HIEU_LUC=1L;
    }
   // public static final String path="http://localhost:9200";
}
