package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtReceipt;
import com.osp.model.view.BienNhanForm;
import com.osp.web.dao.BienNhanDAO;
import com.osp.web.dao.ThanhToanDAO;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

}
