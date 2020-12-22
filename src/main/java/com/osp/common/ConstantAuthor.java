package com.osp.common;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class ConstantAuthor {
    public static final String SCHEMA_DB="MSERVICE";
    public class User { //nguoi dung admin

        public static final String view = "ROLE_SYSTEM_USER_VIEW";
        public static final String add = "ROLE_SYSTEM_USER_ADD";
        public static final String edit = "ROLE_SYSTEM_USER_EDIT";
        public static final String author = "ROLE_SYSTEM_USER_AUTHORITY";
        public static final String delete = "ROLE_SYSTEM_USER_DELETE";
    }

    public class Group { // nhom quyen

        public static final String view = "ROLE_SYSTEM_GROUP_VIEW";
        public static final String add = "ROLE_SYSTEM_GROUP_ADD";
        public static final String edit = "ROLE_SYSTEM_GROUP_EDIT";
        public static final String delete = "ROLE_SYSTEM_GROUP_DELETE";
    }

    public class Log {//log

        public static final String view = "ROLE_SYSTEM_LOG_VIEW";
    }
    public class LeftPanel{
        public static final String view_customer_care ="ROLE_SYSTEM_CUSTOMER_CARE";
        public static final String view_page_tool="ROLE_SYSTEM_TOOL";
        public static final String view_conf="ROLE_SYSTEM_CONF";
        public static final String view_customer_els="ROLE_SYSTEM_CUSTOMER_CARE_ELS";
        public static final String view_sys_log_monitor="ROLE_SYSTEM_LOG_MONITOR";
        public static final String view_sub="ROLE_SYSTEM_SUB";
        public static final String view_report="ROLE_REPORT";
    }
    
    public class Parameter{
    	public static final String view="ROLE_SYSTEM_PARAMETER_VIEW";
    	public static final String update="ROLE_SYSTEM_PARAMETER_UPDATE";
    }
    
    public static class Authority {

       public static final String view="ROLE_SYSTEM_AUTHORITY_VIEW";
       public static final String edit="ROLE_SYSTEM_AUTHORITY_EDIT";
       public static final String add="ROLE_SYSTEM_AUTHORITY_ADD";
       public static final String delete="ROLE_SYSTEM_AUTHORITY_DELETE";
    }


    public static boolean contain(String right) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> checkRight = auth.getAuthorities();
        boolean authorized = checkRight.contains(new SimpleGrantedAuthority(right));
        if (!authorized) {
            return false;
        }
        return true;
    }

}
