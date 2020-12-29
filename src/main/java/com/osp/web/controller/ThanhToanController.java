package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtPartner;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.BienNhanForm;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bienNhan/thanh-toan")
public class ThanhToanController {
  private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
  @Autowired
  ThanhToanDAO thanhToanDAO;
  @Autowired
  BienNhanDAO bienNhanDAO;
  @Autowired
  MatHangDAO matHangDAO;

  @GetMapping("/list")
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
        fromDate  = new Timestamp(sdf.parse(strFdate).getTime());
      }
      if (toDateReceipt != null && !"".equals(toDateReceipt)) {
        String strTdate = toDateReceipt + " 23:59:59";
        toDate = sdf.parse(strTdate);
      }
      page = thanhToanDAO.page(page, Utils.trim(soPhieuNhan), Utils.trim(nguoiGui), typePayment, isPayment, fromDate, toDate).orElse(new PagingResult());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
  }

  @PostMapping(value = "/update-tien-da-tra")
//  @Secured(ConstantAuthor.PublishAuctionTc.edit)
  public ResponseEntity<String> updateTienDaTra(@RequestBody BienNhanForm item, HttpServletRequest request) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    try {
      VtReceipt phieuNhanHang = bienNhanDAO.getById(item.getBienNhan().getId());
      phieuNhanHang.setTienDaTra(item.getBienNhan().getTienDaTra());
      bienNhanDAO.edit(phieuNhanHang);
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

  @GetMapping("/exportExcelCongNo")
//    @Secured(ConstantAuthor.PublishAuctionTc.view)
  public void exportExcel(HttpServletResponse response, HttpServletRequest request,
                          @RequestParam(value = "giaoHangId", required = false) Integer giaoHangId) {
    PagingResult page = new PagingResult();
    page.setPageNumber(1);
    VtReceipt phieuNhanHang = new VtReceipt();
    VtPartner nguoiGui = new VtPartner();
    VtPartner nguoiNhan = new VtPartner();
    String typePayment = "";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
//            Calendar cal = Calendar.getInstance();
//            int day = cal.get(Calendar.DATE);
//            int month = cal.get(Calendar.MONTH) + 1;
//            int year = cal.get(Calendar.YEAR);
//      phieuNhanHang = bienNhanDAO.getById(giaoHangId);
//      if (phieuNhanHang.getPaymentType() == 1) {
//        typePayment = "Trả trước";
//      } else if (phieuNhanHang.getPaymentType() == 2) {
//        typePayment = "Trả sau";
//      } else {
//        typePayment = "Công nợ";
//      }
//      nguoiGui = khachHangDAO.getById(phieuNhanHang.getDeliveryPartnerId());
//      nguoiNhan = khachHangDAO.getById(phieuNhanHang.getReceivePartnerId());
//      page.setItems(matHangDAO.getDsMatHang(giaoHangId));
//      Map<String, Object> beans = new HashMap<String, Object>();
//      if (phieuNhanHang.getDateReceipt() != null) {
//        beans.put("ngayNhanHang", sdf.format(phieuNhanHang.getDateReceipt()));
//      }
//      beans.put("phieuNhanHang", phieuNhanHang);
//      beans.put("nguoiGui", nguoiGui);
//      beans.put("loaiThanhToan", typePayment);
//      beans.put("nguoiNhan", nguoiNhan);
//      beans.put("page", page);
//      Resource resource = new ClassPathResource(templatePhieuNhanHang);
//      InputStream fileIn = resource.getInputStream();
//      ExcelTransformer transformer = new ExcelTransformer();
//      Workbook workbook = transformer.transform(fileIn, beans);
//
//      response.setContentType("application/vnd.ms-excel");
//      response.setHeader("Content-Disposition", "attachment; filename=" + "Phieu-nhan-hang-" + phieuNhanHang.getReceiptCode() + ".xlsx");
//      ServletOutputStream out = response.getOutputStream();
//      workbook.write(out);
//      out.flush();
//      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
