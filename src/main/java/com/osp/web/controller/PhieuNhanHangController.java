package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.model.VtGoodsReceipt;
import com.osp.web.dao.PhieuNhanHangDAO;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/phieu-nhan-hang")
public class PhieuNhanHangController {

    @Autowired
    PhieuNhanHangDAO phieuNhanHangDAO;
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    public String list() {
        return "phieunhanhang.list";
    }

    @GetMapping("/preAdd")
    public String preAdd() {
        return "phieuNhan.add";
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

}
