package com.osp.web.controller;

import com.osp.model.User;
import com.osp.model.VtPartner;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.BienNhanForm;
import com.osp.web.dao.BienNhanDAO;
import com.osp.web.dao.KhachHangDAO;
import com.osp.web.dao.MatHangDAO;
import java.util.Date;
import java.util.List;
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

@Controller
@RequestMapping("/bienNhan")
public class BienNhanController {
  @Autowired
  BienNhanDAO bienNhanDAO;
  @Autowired
  KhachHangDAO khachHangDAO;
  @Autowired
  MatHangDAO matHangDAO;

  @GetMapping("/preAdd")
  public String list() {
    return "bienNhan.add";
  }

  @PostMapping(value = "/them-moi-bien-nhan")
//  @Secured(ConstantAuthor.PublishAuctionTc.edit)
  public ResponseEntity<String> addAucInfo(@RequestBody BienNhanForm item, HttpServletRequest request) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    try {
      // insert người gửi
      VtPartner nguoiGui = new VtPartner();
      nguoiGui.setTaxCode(item.getNguoiGui().getTaxCode());
      nguoiGui.setFullName(item.getNguoiGui().getFullName());
      nguoiGui.setMobile(item.getNguoiGui().getMobile());
      nguoiGui.setAddress(item.getNguoiGui().getAddress());
      nguoiGui.setCreatedBy(user.getUsername());
      nguoiGui.setLastUpdate(new Date());
      nguoiGui.setGenDate(new Date());
      nguoiGui.setUpdatedBy(user.getUsername());
      //type 1 người gửi
      nguoiGui.setTypePartner(1);
      VtPartner rsNguoiGui = khachHangDAO.add(nguoiGui);

      // insert người nhận
      VtPartner nguoiNhan = new VtPartner();
      nguoiNhan.setTaxCode(item.getNguoiNhan().getTaxCode());
      nguoiNhan.setFullName(item.getNguoiNhan().getFullName());
      nguoiNhan.setMobile(item.getNguoiNhan().getMobile());
      nguoiNhan.setAddress(item.getNguoiNhan().getAddress());
      nguoiNhan.setCreatedBy(user.getUsername());
      nguoiNhan.setLastUpdate(new Date());
      nguoiNhan.setGenDate(new Date());
      nguoiNhan.setUpdatedBy(user.getUsername());
      //type 1 người nhận
      nguoiGui.setTypePartner(2);
      VtPartner rsNguoiNhan = khachHangDAO.add(nguoiNhan);

      //insert biên nhận
      VtReceipt bienNhan = new VtReceipt();
      bienNhan.setReceiptCode(item.getBienNhan().getReceiptCode());
      if (rsNguoiGui != null) {
        bienNhan.setDeliveryPartnerId(rsNguoiGui.getId());
      }
      if (rsNguoiNhan != null) {
        bienNhan.setReceivePartnerId(rsNguoiNhan.getId());

      }
      bienNhan.setNameStock(item.getBienNhan().getNameStock());
      bienNhan.setDateReceipt(item.getBienNhan().getDateReceipt());
      bienNhan.setDatepushStock(new Date());
      // 1 trả trước, 2 trả sau, 3 công nợ
      bienNhan.setPaymentType(1);
      bienNhan.setPayer(item.getBienNhan().getPayer());
      bienNhan.setTaxCode(item.getBienNhan().getTaxCode());
      bienNhan.setNhaXe(item.getBienNhan().getNhaXe());
      bienNhan.setBienSo(item.getBienNhan().getBienSo());
      bienNhan.setEmployee(item.getBienNhan().getEmployee());
      //Nhận hàng
      bienNhan.setStatus(1);
      bienNhan.setFileAttach(item.getBienNhan().getFileAttach());
      bienNhan.setGenDate(new Date());
      bienNhan.setLastUpdate(new Date());
      bienNhan.setCreatedBy(user.getUsername());
      bienNhan.setUpdatedBy(user.getUsername());
      VtReceipt rsBienNhanAdd = bienNhanDAO.add(bienNhan);

      List<VtReceiptDetail> dsMatHang = item.getMatHang();
      for (VtReceiptDetail matHang : dsMatHang) {
        matHang.setCreatedBy(user.getUsername());
        matHang.setUpdatedBy(user.getUsername());
        handleMatHang(matHang, rsBienNhanAdd.getId());
      }

      return new ResponseEntity<String>("1", HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("0", HttpStatus.OK);
    }
  }

  private void handleMatHang(VtReceiptDetail item, Integer bienNhanId) {
    VtReceiptDetail info = new VtReceiptDetail();
    info.setCost(item.getCost());
    info.setCreatedBy(item.getCreatedBy());
    info.setDebt(item.getDebt());
    info.setDocument(item.getDocument());
    info.setGenDate(new Date());
    info.setLastUpdate(new Date());
    info.setName(item.getName());
    info.setNote(item.getNote());
    info.setNumbers(item.getNumbers());
    info.setReceiptId(bienNhanId);
    info.setSizes(item.getSizes());
    //1 nhận hàng, 2 nhập kho, 3 đang giao, 4 đã giao
    info.setStatus(1);
    info.setUnit(item.getUnit());
    info.setUpdatedBy(item.getUpdatedBy());
    info.setWeight(item.getWeight());
    matHangDAO.add(info);
  }
}
