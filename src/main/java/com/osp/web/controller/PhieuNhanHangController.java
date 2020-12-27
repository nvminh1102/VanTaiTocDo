package com.osp.web.controller;

import com.osp.common.MessReponse;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.web.dao.PhieuNhanHangDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/phieu-nhan-hang")
public class PhieuNhanHangController {

    SimpleDateFormat formatteryyyy = new SimpleDateFormat("yyyy");

    @Autowired
    PhieuNhanHangDAO phieuNhanHangDAO;
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    public String list() {
        return "phieunhanhang.list";
    }

    @RequestMapping(value = "/load-list", method = RequestMethod.GET)
    public ResponseEntity<PagingResult> searchAdd(@RequestParam @Valid final String search, @RequestParam @Valid final int offset, @RequestParam @Valid final int number, HttpServletRequest request) {
        VtGoodsReceipt item = new VtGoodsReceipt();
        PagingResult page = new PagingResult();
        JSONObject searchObject = new JSONObject(search);
        SimpleDateFormat formatterddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (searchObject.has("receiptCode") && !StringUtils.isBlank(searchObject.get("receiptCode").toString())) {
                item.setReceiptCode(searchObject.get("receiptCode").toString().trim());
            }
            if (searchObject.has("fromDelivery") && !StringUtils.isBlank(searchObject.get("fromDelivery").toString())) {
                item.setFromDelivery(formatterddMMyyyy.parse(searchObject.get("fromDelivery").toString().trim()));
            }
            if (searchObject.has("toDelivery") && !StringUtils.isBlank(searchObject.get("toDelivery").toString())) {
                item.setToDelivery(formatterddMMyyyy.parse(searchObject.get("toDelivery").toString().trim()));
            }
            if (searchObject.has("fromReceive") && !StringUtils.isBlank(searchObject.get("fromReceive").toString())) {
                item.setFromReceive(formatterddMMyyyy.parse(searchObject.get("fromReceive").toString().trim()));
            }
            if (searchObject.has("toReceive") && !StringUtils.isBlank(searchObject.get("toReceive").toString())) {
                item.setToReceive(formatterddMMyyyy.parse(searchObject.get("toReceive").toString().trim()));
            }
            if (searchObject.has("truckPartner") && !StringUtils.isBlank(searchObject.get("truckPartner").toString())) {
                item.setTruckPartnerName(searchObject.get("truckPartner").toString().trim());
            }
            if (searchObject.has("loaiXe") && !StringUtils.isBlank(searchObject.get("loaiXe").toString())) {
                item.setLoaiXe(searchObject.get("loaiXe").toString().trim());
            }
            if (searchObject.has("bienSo") && !StringUtils.isBlank(searchObject.get("bienSo").toString())) {
                item.setBienSo(searchObject.get("bienSo").toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setNumberPerPage(number);
        page.setPageNumber(offset);
        page = phieuNhanHangDAO.search(item, page).orElse(new PagingResult());
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/preAdd")
    public String preAdd(HttpServletRequest request) {
        Integer maxId = phieuNhanHangDAO.getMaxId();
        String receiptCode = "GH-" + formatteryyyy.format(new Date()) + "-" + ((maxId!=null? maxId : 0)+ 1);
        request.setAttribute("receiptCode", receiptCode);
        return "phieuNhan.add";
    }

    @GetMapping("/preEdit/{id}")
    public String preEdit(@PathVariable("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "phieuNhan.add";
    }

    @RequestMapping(value = "/loadDataEdit", method = RequestMethod.GET)
    public ResponseEntity<VTGoodsReceiptForm> loadDataEdit(@RequestParam @Valid final Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = phieuNhanHangDAO.getVTGoodsReceiptFormById(id);
        return new ResponseEntity<VTGoodsReceiptForm>(vTGoodsReceiptForm, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<MessReponse> addAppoint(@RequestBody @Valid final VTGoodsReceiptForm vTGoodsReceiptForm, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MessReponse reponse = new MessReponse();
        boolean check = phieuNhanHangDAO.add(vTGoodsReceiptForm, user);
        if (check) {
            reponse = new MessReponse(true, 200, "Lưu thành công!");
        } else {
            reponse = new MessReponse(false, 500, "Hệ thống đang bận, vui lòng thực hiện lại sau!");
        }
        return new ResponseEntity<MessReponse>(reponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody @Valid final VtGoodsReceiptBO vtGoodsReceiptBO, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String ipClient = Utils.getIpClient(request);
            boolean check = phieuNhanHangDAO.delete(vtGoodsReceiptBO.getId(), user, ipClient);
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
