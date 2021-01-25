package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.AdmLogData;
import com.osp.model.User;
import com.osp.model.VtPartner;
import com.osp.web.dao.AdmLogDataDAO;
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
@RequestMapping("/managerVanTai/khach-hang")
public class KhachHangController {

    @Autowired
    KhachHangDAO khachHangDAO;

    @Autowired
    AdmLogDataDAO admLogDataDAO;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            if (vtPartner.getId() == null) {
                return new ResponseEntity<String>("1", HttpStatus.OK);
            }
            VtPartner oldData = khachHangDAO.getById(vtPartner.getId());
            boolean isDelete = khachHangDAO.delete(vtPartner.getId());
            // insert log data
            AdmLogData admLogData = new AdmLogData(oldData, null, user.getUsername(), request, "/managerVanTai/khach-hang/delete", Constants.action.DELETE);
            admLogDataDAO.add(admLogData);

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
                    VtPartner oldData = khachHangDAO.getById(vtPartner.getId());

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
                    objDb.setSoHopDong(vtPartner.getSoHopDong());
                    objDb.setUpdatedBy(user.getUsername());
                    khachHangDAO.edit(objDb);
                    // insert log data
                    AdmLogData admLogData = new AdmLogData(oldData, objDb, user.getUsername(), request, "/managerVanTai/khach-hang/them-moi-khach-hang", Constants.action.UPDATE);
                    admLogDataDAO.add(admLogData);

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
                saveObj.setSoHopDong(vtPartner.getSoHopDong());
                saveObj.setCreatedBy(user.getUsername());
                khachHangDAO.add(saveObj);
                // insert log data
                AdmLogData admLogData = new AdmLogData(null, saveObj, user.getUsername(), request, "/managerVanTai/khach-hang/them-moi-khach-hang", Constants.action.INSERT);
                admLogDataDAO.add(admLogData);

                return new ResponseEntity<String>("0", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
    }
}
