package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.ConstantAuthor.KHACH_HANG;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtPartner;
import com.osp.web.dao.KhachHangDAO;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/bienNhan/khach-hang")
public class KhachHangController {
  @Autowired
  KhachHangDAO khachHangDAO;

  @GetMapping("/list")
  @Secured(ConstantAuthor.KHACH_HANG.view)
  public String list() {
    return "khachHang.list";
  }

  @GetMapping("/search")
  @Secured(ConstantAuthor.KHACH_HANG.view)
  public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
      @RequestParam(value = "numberPerPage", required = false, defaultValue = "25") int numberPerPage,
      @RequestParam(value = "fullName", required = false, defaultValue = "") String fullName,
      @RequestParam(value = "taxCode", required = false, defaultValue = "") String taxCode,
      @RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
      @RequestParam(value = "typePartner", required = false, defaultValue = "") Long typePartner,
      @RequestParam(value = "address", required = false, defaultValue = "") String address) {
    PagingResult page = new PagingResult();
    page.setPageNumber(pageNumber);
    page.setNumberPerPage(numberPerPage);

    try {
      page = khachHangDAO.page(page, Utils.trim(fullName), Utils.trim(taxCode), Utils.trim(mobile), Utils.trim(address), typePartner).orElse(new PagingResult());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
  }

  @PostMapping(value = "/delete")
  @Secured(ConstantAuthor.KHACH_HANG.delete)
  public ResponseEntity<String> delete(@RequestBody VtPartner vtPartner, HttpServletRequest request) {
    try {
      if (vtPartner.getId() == null) {
        return new ResponseEntity<String>("1", HttpStatus.OK);
      }
      boolean isDelete = khachHangDAO.delete(vtPartner.getId());
      if (isDelete) {
        return new ResponseEntity<String>("0", HttpStatus.OK);
      } else {
        return new ResponseEntity<String>("1", HttpStatus.OK);

      }

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("1", HttpStatus.OK);
    }
  }

  @PostMapping(value = "/them-moi-khach-hang")
  @Secured(ConstantAuthor.KHACH_HANG.add)
  public ResponseEntity<String> addKhachHang(@RequestBody VtPartner vtPartner, HttpServletRequest request) {
    try {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if ("".equals(vtPartner.getTaxCode()) || vtPartner.getTaxCode() == null) {
        return new ResponseEntity<String>("1", HttpStatus.OK);
      }
      VtPartner saveObj = new VtPartner();
      VtPartner objDb = new VtPartner();
      if (vtPartner.getId() != null) {
        objDb = khachHangDAO.getById(vtPartner.getId());
      }
      if (vtPartner.getId() != null) {
        if (objDb != null) {
          objDb.setFullName(vtPartner.getFullName().trim());
          objDb.setTaxCode(vtPartner.getTaxCode().trim());
          if (vtPartner.getMobile() != null && !"".equals(vtPartner.getMobile())) {
            objDb.setMobile(vtPartner.getMobile().trim());

          }
          if (vtPartner.getAddress() != null && !"".equals(vtPartner.getAddress())) {
            objDb.setAddress(vtPartner.getAddress().trim());

          }
          if (vtPartner.getEmail() != null && !"".equals(vtPartner.getEmail())) {
            objDb.setEmail(vtPartner.getEmail().trim());

          }
          objDb.setTypePartner(vtPartner.getTypePartner());
          objDb.setLastUpdate(new Date());
          objDb.setUpdatedBy(user.getUsername());
          khachHangDAO.edit(objDb);
          }
          return new ResponseEntity<String>("0", HttpStatus.OK);
        } else {
        saveObj.setTaxCode(vtPartner.getTaxCode().trim());
        saveObj.setFullName(vtPartner.getFullName().trim());
        if (vtPartner.getMobile() != null && !"".equals(vtPartner.getMobile())) {
          saveObj.setMobile(vtPartner.getMobile().trim());

        }
        if (vtPartner.getAddress() != null && !"".equals(vtPartner.getAddress())) {
          saveObj.setAddress(vtPartner.getAddress().trim());

        }
        if (vtPartner.getEmail() != null && !"".equals(vtPartner.getEmail())) {
          saveObj.setEmail(vtPartner.getEmail().trim());

        }
        saveObj.setTypePartner(vtPartner.getTypePartner());
        saveObj.setGenDate(new Date());
        saveObj.setCreatedBy(user.getUsername());
        saveObj.setLastUpdate(new Date());
        saveObj.setCreatedBy(user.getUsername());
        khachHangDAO.add(saveObj);
        return new ResponseEntity<String>("0", HttpStatus.OK);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("1", HttpStatus.OK);
    }
  }
}
