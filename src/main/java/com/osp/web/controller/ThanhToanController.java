package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.ConstantAuthor.THANH_TOAN;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.*;
import com.osp.model.view.BienNhanForm;
import com.osp.model.view.VtReceiptView;
import com.osp.web.dao.AdmLogDataDAO;
import com.osp.web.dao.AreaDAO;
import com.osp.web.dao.BienNhanDAO;
import com.osp.web.dao.MatHangDAO;
import com.osp.web.dao.ThanhToanDAO;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jett.transform.ExcelTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/managerVanTai/thanh-toan")
public class ThanhToanController {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    @Autowired
    ThanhToanDAO thanhToanDAO;

    @Autowired
    BienNhanDAO bienNhanDAO;

    @Autowired
    MatHangDAO matHangDAO;

    @Autowired
    AreaDAO areaDAO;

    @Autowired
    AdmLogDataDAO admLogDataDAO;

    private final String templateThanhToan = "/fileTemplate/templateCongNo.xlsx";

    @GetMapping("/list")
    @Secured(ConstantAuthor.THANH_TOAN.view)
    public String listBn() {
        return "thanhToan.list";
    }

    @GetMapping("/search")
    public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "numberPerPage", required = false, defaultValue = "25") int numberPerPage,
            @RequestParam(value = "soPhieuNhan", required = false, defaultValue = "") String soPhieuNhan,
            @RequestParam(value = "nguoiGui", required = false, defaultValue = "") String nguoiGui,
            @RequestParam(value = "typePayment", required = false, defaultValue = "") Long typePayment,
            @RequestParam(value = "isPayment", required = false, defaultValue = "") Long isPayment,
            @RequestParam(value = "fromDateReceipt", required = false, defaultValue = "") String fromDateReceipt,
            @RequestParam(value = "toDateReceipt", required = false, defaultValue = "") String toDateReceipt) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(numberPerPage);
        Date fromDate = null;
        Date toDate = null;

        try {
            if (fromDateReceipt != null && !"".equals(fromDateReceipt)) {
                String strFdate = fromDateReceipt + " 00:00:00";
                fromDate = new Timestamp(sdf.parse(strFdate).getTime());
            }
            if (toDateReceipt != null && !"".equals(toDateReceipt)) {
                String strTdate = toDateReceipt + " 23:59:59";
                toDate = sdf.parse(strTdate);
            }
            page = thanhToanDAO.page(page, Utils.trim(soPhieuNhan), Utils.trim(nguoiGui), typePayment, isPayment, fromDate, toDate, true).orElse(new PagingResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @PostMapping(value = "/update-tien-da-tra")
    @Secured(ConstantAuthor.THANH_TOAN.edit)
    public ResponseEntity<String> updateTienDaTra(@RequestBody BienNhanForm item, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            VtReceipt phieuNhanHang = bienNhanDAO.getById(item.getBienNhan().getId());
            VtReceipt oldData = bienNhanDAO.getById(item.getBienNhan().getId());
            phieuNhanHang.setTienDaTra((phieuNhanHang.getTienDaTra() != null ? phieuNhanHang.getTienDaTra() : 0) + item.getBienNhan().getTienDaTra());
            bienNhanDAO.edit(phieuNhanHang);
            
            // insert log data
            AdmLogData admLogData = new AdmLogData(oldData, phieuNhanHang, user.getUsername(), request, "/managerVanTai/thanh-toan/update-tien-da-tra", Constants.action.UPDATE);
            admLogDataDAO.add(admLogData);
            
            return new ResponseEntity<String>("1", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @GetMapping("/tong-tien-mat-hang")
    public ResponseEntity<Integer> getInfoEditAuction(@RequestParam(value = "id", required = true) Integer phieuNhanId,
            HttpServletRequest request) {
        BienNhanForm item = new BienNhanForm();
        Integer tongTien = 0;
        try {
            List<VtReceiptDetail> listProperty = matHangDAO.getDsMatHang(phieuNhanId);
            if (listProperty.size() > 0) {
                for (int i = 0; i < listProperty.size(); i++) {
                    tongTien += listProperty.get(i).getCost();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Integer>(tongTien, HttpStatus.OK);
    }

    @GetMapping("/exportExcelThanhToan")
    @Secured(ConstantAuthor.THANH_TOAN.add)
    public void exportExcel(HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "soPhieuNhan", required = false) String soPhieuNhan,
            @RequestParam(value = "nguoiGui", required = false) String nguoiGui,
            @RequestParam(value = "typePayment", required = false) Long typePayment,
            @RequestParam(value = "isPayment", required = false) Long isPayment,
            @RequestParam(value = "fromGenDate", required = false) String fromGenDate,
            @RequestParam(value = "toGenDate", required = false) String toGenDate) {
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        page.setNumberPerPage(25);
        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VtArea vtArea = areaDAO.getVtAreaById(user.getAreaId());
        try {
            if (fromGenDate != null && !"".equals(fromGenDate)) {
                String strFdate = fromGenDate + " 00:00:00";
                fromDate = new Timestamp(sdf2.parse(strFdate).getTime());
            }
            if (toGenDate != null && !"".equals(toGenDate)) {
                String strTdate = toGenDate + " 23:59:59";
                toDate = sdf2.parse(strTdate);
            }
            Integer maxId = thanhToanDAO.getMaxId();
            String congNoCode = (vtArea != null ? vtArea.getCode() + "-" : "") + "CN-" + yyyy.format(new Date()) + "-" + ((maxId != null ? maxId : 0) + 1);
            // Thêm mới công nợ
            VtCongNo congNo = new VtCongNo();
            congNo.setCongNoCode(congNoCode);
            congNo.setDateCongNo(new Date());
            congNo.setCreatedBy(user.getUsername());
            congNo.setGenDate(new Date());
            congNo.setLastUpdate(new Date());
            congNo.setUpdatedBy(user.getUsername());
            VtCongNo rsCongNo = thanhToanDAO.addCongNo(congNo);
            Long tongTienAll = 0L;
            Long tongTienTraTruoc = 0L;
            Long tongTienTraSau = 0L;
            Long tongTienCongNo = 0L;
            Long tongConPhaiThu = 0L;

            page = thanhToanDAO.page(page, Utils.trim(soPhieuNhan), Utils.trim(nguoiGui), typePayment, isPayment, fromDate, toDate, true).orElse(new PagingResult());
            List<VtReceiptView> viewList = (List<VtReceiptView>) page.getItems();
            for (int i = 0; i < viewList.size(); i++) {
                // Thêm mới công nợ detail
                VtCongNoDetail congNoDetail = new VtCongNoDetail();
                congNoDetail.setCongNoId(rsCongNo.getId());
                congNoDetail.setCreatedBy(user.getUsername());
                congNoDetail.setReceiptId(viewList.get(i).getId().intValue());
                congNoDetail.setGenDate(new Date());
                congNoDetail.setLastUpdate(new Date());
                congNoDetail.setUpdatedBy(user.getUsername());
                thanhToanDAO.addCongNoDetail(congNoDetail);

                if (viewList.get(i).getTienDaTra() != null && viewList.get(i).getPaymentType() == 1) {
                    viewList.get(i).setTienTraTruoc(viewList.get(i).getTienDaTra());
                    tongTienTraTruoc += viewList.get(i).getTienDaTra();
                } else if (viewList.get(i).getTienDaTra() != null && viewList.get(i).getPaymentType() == 2) {
                    viewList.get(i).setTienTraSau(viewList.get(i).getTienDaTra());
                    tongTienTraSau += viewList.get(i).getTienDaTra();
                } else if (viewList.get(i).getTienDaTra() != null && viewList.get(i).getPaymentType() == 3) {
                    viewList.get(i).setTienCongNo(viewList.get(i).getTienDaTra());
                    tongTienCongNo += viewList.get(i).getTienDaTra();
                }
                if (viewList.get(i).getTienDaTra() != null && viewList.get(i).getTongTien() != null) {
                    viewList.get(i).setTienConPhaiThu(viewList.get(i).getTongTien() - viewList.get(i).getTienDaTra());
                    tongConPhaiThu += viewList.get(i).getTienConPhaiThu();
                }
                if (viewList.get(i).getTienDaTra() == null && viewList.get(i).getTongTien() != null) {
                    viewList.get(i).setTienConPhaiThu(viewList.get(i).getTongTien());
                    tongConPhaiThu += viewList.get(i).getTienConPhaiThu();
                }
                if (viewList.get(i).getTongTien() != null) {
                    tongTienAll += viewList.get(i).getTongTien();
                }
                if (viewList.get(i).getTienDaTra() != null && viewList.get(i).getTienDaTra() > 0) {
                    viewList.get(i).setDaThanhToan("Đã thanh toán");
                } else {
                    viewList.get(i).setDaThanhToan("Chưa thanh toán");
                }
            }
            page.setItems(viewList);

            Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("tongTienAll", tongTienAll);
            beans.put("tongTienTraTruoc", tongTienTraTruoc);
            beans.put("tongTienTraSau", tongTienTraSau);
            beans.put("tongTienCongNo", tongTienCongNo);
            beans.put("tongConPhaiThu", tongConPhaiThu);
            beans.put("fromGenDate", fromGenDate);
            beans.put("toGenDate", toGenDate);
            beans.put("congNo", congNo);
            beans.put("page", page);
            beans.put("date", sdf.format(new Date()));
            Resource resource = new ClassPathResource(templateThanhToan);
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "Danh-sach-phieu-thanh-toan-" + ddMMyyyy.format(new Date()) + ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
