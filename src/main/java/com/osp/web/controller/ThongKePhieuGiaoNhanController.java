package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.web.dao.ThongKeGiaoNhanHangDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/managerVanTai/thong-ke")
public class ThongKePhieuGiaoNhanController {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    @Autowired
    ThongKeGiaoNhanHangDAO thongKeGiaoNhanHangDAO;

    @GetMapping("/danh-sach-giao-nhan")
    @Secured(ConstantAuthor.ROLE_THONG_KE.view)
    public String listBn() {
        return "thong-ke-phieu-giao-nhan.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.ROLE_THONG_KE.view)
    public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
                                                      @RequestParam(value = "numberPerPage", required = false, defaultValue = "25") int numberPerPage,
                                                      @RequestParam(value = "bienSo", required = false, defaultValue = "") String bienSo,
                                                      @RequestParam(value = "loaiXe", required = false, defaultValue = "") String loaiXe,
                                                      @RequestParam(value = "hinhThucVanChuyen", required = false, defaultValue = "") String hinhThucVanChuyen,
                                                      @RequestParam(value = "fromDate", required = false, defaultValue = "") String fromDateStr,
                                                      @RequestParam(value = "toDate", required = false, defaultValue = "") String toDateStr) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(numberPerPage);
        Date fromDate = null;
        Date toDate = null;

        try {
            if (fromDateStr != null && !"".equals(fromDateStr)) {
                String strFdate = fromDateStr + " 00:00:00";
                fromDate  = new Timestamp(sdf.parse(strFdate).getTime());
            }
            if (toDateStr != null && !"".equals(toDateStr)) {
                String strTdate = toDateStr + " 23:59:59";
                toDate = sdf.parse(strTdate);
            }
            page = thongKeGiaoNhanHangDAO.page(page, Utils.trim(bienSo), Utils.trim(loaiXe), Utils.trim(hinhThucVanChuyen), fromDate, toDate, false).orElse(new PagingResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

}
