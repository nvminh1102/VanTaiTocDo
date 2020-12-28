package com.osp.web.controller;

import com.osp.common.MessReponse;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.VtToaHang;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.web.dao.PhieuNhanHangDAO;
import com.osp.web.dao.ToaHangDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/toa-hang")
public class ToaHangController {

    SimpleDateFormat formatteryyyy = new SimpleDateFormat("yyyy");

    @Autowired
    ToaHangDAO toaHangDAO;
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    public String list() {
        return "toahang.list";
    }

    @RequestMapping(value = "/load-list", method = RequestMethod.GET)
    public ResponseEntity<PagingResult> searchAdd(@RequestParam @Valid final String search, @RequestParam @Valid final int offset, @RequestParam @Valid final int number, HttpServletRequest request) {
        VtToaHang vtToaHang = new VtToaHang();
        PagingResult page = new PagingResult();
        JSONObject searchObject = new JSONObject(search);
        SimpleDateFormat formatterddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (searchObject.has("toaHangCode") && !StringUtils.isBlank(searchObject.get("toaHangCode").toString())) {
                vtToaHang.setToaHangCode(searchObject.get("toaHangCode").toString().trim());
            }
            if (searchObject.has("bienSo") && !StringUtils.isBlank(searchObject.get("bienSo").toString())) {
                vtToaHang.setBienSo(searchObject.get("bienSo").toString().trim());
            }
            if (searchObject.has("noiDen") && !StringUtils.isBlank(searchObject.get("noiDen").toString())) {
                vtToaHang.setNoiDen(searchObject.get("noiDen").toString().trim());
            }
            if (searchObject.has("noiDi") && !StringUtils.isBlank(searchObject.get("noiDi").toString())) {
                vtToaHang.setNoiDi(searchObject.get("noiDi").toString().trim());
            }
            if (searchObject.has("fromGenDate") && !StringUtils.isBlank(searchObject.get("fromGenDate").toString())) {
                vtToaHang.setFromGenDate(formatterddMMyyyy.parse(searchObject.get("fromGenDate").toString().trim()));
            }
            if (searchObject.has("toGenDate") && !StringUtils.isBlank(searchObject.get("toGenDate").toString())) {
                vtToaHang.setToGenDate(formatterddMMyyyy.parse(searchObject.get("toGenDate").toString().trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setNumberPerPage(number);
        page.setPageNumber(offset);
        page = toaHangDAO.search(vtToaHang, page).orElse(new PagingResult());
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/preAdd")
    public String preAdd(HttpServletRequest request) {
        Integer maxId = toaHangDAO.getMaxId();
        String toaHangCode = "TOA-" + formatteryyyy.format(new Date()) + "-" + ((maxId != null ? maxId : 0) + 1);
        request.setAttribute("toaHangCode", toaHangCode);
        return "toahang.add";
    }

    @GetMapping("/preEdit/{id}")
    public String preEdit(@PathVariable("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "toahang.add";
    }

    @RequestMapping(value = "/loadDataEdit", method = RequestMethod.GET)
    public ResponseEntity<VTGoodsReceiptForm> loadDataEdit(@RequestParam @Valid final Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = toaHangDAO.getVTGoodsReceiptFormById(id);
        return new ResponseEntity<VTGoodsReceiptForm>(vTGoodsReceiptForm, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<MessReponse> addAppoint(@RequestBody @Valid final VTGoodsReceiptForm vTGoodsReceiptForm, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MessReponse reponse = new MessReponse();
        boolean check = toaHangDAO.add(vTGoodsReceiptForm, user);
        if (check) {
            reponse = new MessReponse(true, 200, "Lưu thành công!");
        } else {
            reponse = new MessReponse(false, 500, "Hệ thống đang bận, vui lòng thực hiện lại sau!");
        }
        return new ResponseEntity<MessReponse>(reponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody @Valid final VtToaHang vtToaHang, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String ipClient = Utils.getIpClient(request);
            boolean check = toaHangDAO.delete(vtToaHang.getId(), user, ipClient);
            if (check) {
                return new ResponseEntity<String>("0", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("1", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("1", HttpStatus.OK);
    }

}
