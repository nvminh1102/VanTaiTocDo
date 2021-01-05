package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.VtToaHang;
import com.osp.model.view.ThongKeGiaoNhanView;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.web.dao.ThongKeGiaoNhanHangDAO;
import java.io.InputStream;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Controller
@RequestMapping("/managerVanTai/thong-ke")
public class ThongKePhieuGiaoNhanController {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    
    private final String templateThongKeXe = "/fileTemplate/listThongKeXe.xlsx";
    
    
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
    
    
    
    @GetMapping("/exportExcel")
//    @Secured(ConstantAuthor.PublishAuctionTc.view)
//    @Secured(ConstantAuthor.TOA_HANG.export)
    public void exportExcel(HttpServletResponse response, HttpServletRequest request,
                            @RequestParam(value = "bienSo", required = false, defaultValue = "") String  bienSo,
                            @RequestParam(value = "loaiXe", required = false, defaultValue = "") String  loaiXe,
                            @RequestParam(value = "hinhThucVanChuyen", required = false, defaultValue = "") String  hinhThucVanChuyen,
                            @RequestParam(value = "fromDate", required = false, defaultValue = "") String  fromDateStr,
                            @RequestParam(value = "toDate", required = false, defaultValue = "") String  toDateStr) {
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        page.setNumberPerPage(1000000);
        Date fromDate = null;
        Date toDate = null;
        String paramBienSo  = "";
        String paramloaiXe  = "";
        String paramHinhThucVanChuyen  = "";
        try {
            
            if(bienSo!=null && !bienSo.trim().equals("undefined")){
                paramBienSo = bienSo.trim();
            }
            if(loaiXe!=null && !loaiXe.trim().equals("undefined")){
                paramloaiXe = loaiXe.trim();
            }
            if(hinhThucVanChuyen!=null && !hinhThucVanChuyen.trim().equals("undefined")){
                paramHinhThucVanChuyen = hinhThucVanChuyen.trim();
            }
            if (fromDateStr != null && !"undefined".equals(fromDateStr) && !"".equals(fromDateStr)) {
                String strFdate = fromDateStr + " 00:00:00";
                fromDate  = new Timestamp(sdf.parse(strFdate).getTime());
            }
            if (toDateStr != null && !"undefined".equals(toDateStr)&& !"".equals(toDateStr)) {
                String strTdate = toDateStr + " 23:59:59";
                toDate = sdf.parse(strTdate);
            }
            page = thongKeGiaoNhanHangDAO.page(page, paramBienSo, paramloaiXe, paramHinhThucVanChuyen, fromDate, toDate, false).orElse(new PagingResult());
            List<ThongKeGiaoNhanView> thongKeGiaoNhanViews = new ArrayList<>();
            if(page!=null && page.getItems()!=null){
                thongKeGiaoNhanViews = (List<ThongKeGiaoNhanView>) page.getItems();
            }
            if(thongKeGiaoNhanViews == null){
                thongKeGiaoNhanViews = new ArrayList<>();
            }
            Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("listItem", thongKeGiaoNhanViews);
            beans.put("fromDate", !"undefined".equals(fromDateStr) ? fromDateStr: "");
            beans.put("toDate", !"undefined".equals(toDateStr)? toDateStr : "");
            Resource resource = new ClassPathResource(templateThongKeXe);
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "ThongKe-xe-cho-hang-" + sdf.format(new Date())+ ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
