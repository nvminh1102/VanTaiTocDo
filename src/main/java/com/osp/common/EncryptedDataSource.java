package com.osp.common;
import org.apache.commons.dbcp2.BasicDataSource;
import sun.misc.BASE64Decoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class EncryptedDataSource extends BasicDataSource {
    protected synchronized DataSource createDataSource() throws SQLException {
        String decryptedPassword = decode( super.getPassword() );
        super.setPassword( decryptedPassword );
        return super.createDataSource();
    }
    /***
     * Decode Password
     */
    private String decode(String decode) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            decode = new String(decoder.decodeBuffer(decode));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return decode;
    }
}