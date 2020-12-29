package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.ConstantAuthor.NHA_XE;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.NhaXe;
import com.osp.model.User;
import com.osp.model.VtPartner;
import com.osp.web.dao.NhaXeDAO;
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
@RequestMapping("/bienNhan/nha-xe")
public class NhaXeController {
  @Autowired
  NhaXeDAO nhaXeDAO;

  @GetMapping("/list")
  @Secured(ConstantAuthor.NHA_XE.view)
  public String list() {
    return "nhaXe.list";
  }

  @GetMapping("/search")
  @Secured(ConstantAuthor.NHA_XE.view)
  public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
      @RequestParam(value = "numberPerPage", required = false, defaultValue = "25") int numberPerPage,
      @RequestParam(value = "nhaXe", required = false, defaultValue = "") String nhaXe,
      @RequestParam(value = "loaiXe", required = false, defaultValue = "") String loaiXe,
      @RequestParam(value = "bienSo", required = false, defaultValue = "") String bienSo,
      @RequestParam(value = "tenLaiXe", required = false, defaultValue = "") String tenLaiXe) {
    PagingResult page = new PagingResult();
    page.setPageNumber(pageNumber);
    page.setNumberPerPage(numberPerPage);

    try {
      page = nhaXeDAO.page(page, Utils.trim(nhaXe), Utils.trim(loaiXe), Utils.trim(bienSo), Utils.trim(tenLaiXe)).orElse(new PagingResult());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
  }

  @PostMapping(value = "/delete")
  @Secured(ConstantAuthor.NHA_XE.delete)
  public ResponseEntity<String> delete(@RequestBody NhaXe nhaXe, HttpServletRequest request) {
    try {
      if (nhaXe.getId() == null) {
        return new ResponseEntity<String>("1", HttpStatus.OK);
      }
      boolean isDelete = nhaXeDAO.delete(nhaXe.getId());
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

  @PostMapping(value = "/them-moi-nha-xe")
  @Secured(ConstantAuthor.NHA_XE.add)
  public ResponseEntity<String> addKhachHang(@RequestBody NhaXe nhaXe, HttpServletRequest request) {
    try {
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if ("".equals(nhaXe.getNhaXe()) || nhaXe.getNhaXe() == null) {
        return new ResponseEntity<String>("1", HttpStatus.OK);
      }
      NhaXe saveObj = new NhaXe();
      NhaXe objDb = new NhaXe();
      if (nhaXe.getId() != null) {
        objDb = nhaXeDAO.getById(nhaXe.getId());
      }
      if (nhaXe.getId() != null) {
        if (objDb != null) {
          objDb.setNhaXe(nhaXe.getNhaXe().trim());
          objDb.setLoaiXe(nhaXe.getLoaiXe().trim());
          objDb.setBienSo(nhaXe.getBienSo().trim());
          if (nhaXe.getTenLaiXe() != null && !"".equals(nhaXe.getTenLaiXe())) {
            objDb.setTenLaiXe(nhaXe.getTenLaiXe().trim());

          }
          if (nhaXe.getSdtLaiXe() != null && !"".equals(nhaXe.getSdtLaiXe())) {
            objDb.setSdtLaiXe(nhaXe.getSdtLaiXe().trim());

          }
          objDb.setLastUpdate(new Date());
          objDb.setUpdatedBy(user.getUsername());
          nhaXeDAO.edit(objDb);
        }
        return new ResponseEntity<String>("0", HttpStatus.OK);
      } else {
        saveObj.setNhaXe(nhaXe.getNhaXe().trim());
        saveObj.setLoaiXe(nhaXe.getNhaXe().trim());
        saveObj.setBienSo(nhaXe.getBienSo().trim());
        if (nhaXe.getTenLaiXe() != null && !"".equals(nhaXe.getTenLaiXe())) {
          saveObj.setTenLaiXe(nhaXe.getTenLaiXe().trim());

        }
        if (nhaXe.getSdtLaiXe() != null && !"".equals(nhaXe.getSdtLaiXe())) {
          saveObj.setSdtLaiXe(nhaXe.getSdtLaiXe().trim());

        }
        saveObj.setGenDate(new Date());
        saveObj.setCreatedBy(user.getUsername());
        saveObj.setLastUpdate(new Date());
        saveObj.setUpdatedBy(user.getUsername());
        nhaXeDAO.add(saveObj);
        return new ResponseEntity<String>("0", HttpStatus.OK);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>("1", HttpStatus.OK);
    }
  }
}
