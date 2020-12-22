/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public class UpdateFiles {
    public static String UpdateFiles(MultipartFile file, String typeFile, String fileCode, String forderDirRoot) {
        if (!file.isEmpty() || file.getSize() > 0) {
            String fileName = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            try {
                String imgDir = forderDirRoot+ dateFormat.format(new Date()) + "/" + fileCode.toUpperCase();
                String dirspath = imgDir;
                java.io.File dirs = new java.io.File(dirspath);
                if (!dirs.exists()) {
                    dirs.mkdirs();
                }

                java.io.File fout = null;

                Random r = new Random();

                do {
                    String rdName = fileCode.toUpperCase() + "_" + dateFormat.format(new Date()) + "_" + typeFile + "_" + r.nextInt(999999999) + ""
                            + r.nextInt(999999999) + "" + r.nextInt(999999999) + file.getOriginalFilename();
                    fileName = rdName;
                    fout = new java.io.File(dirspath, fileName);
                } while (fout.exists());

                file.transferTo(fout);
              // chỉ dùng khi resizer lại kích thước định dạng ảnh
              //ImageResizer.resize(fout, dirspath + "/" + fileName, 1, file.getSize());

            } catch (Exception e) {
                e.printStackTrace();
                return "-1";
            }
            return dateFormat.format(new Date()) + "/" + fileCode.toUpperCase() + "/" + fileName;
        } else {
            return "-1";
        }

    }
}
