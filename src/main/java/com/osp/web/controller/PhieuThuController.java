package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.MessReponse;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.model.view.VtPhieuThuView;
import com.osp.web.dao.PhieuNhanHangDAO;
import com.osp.web.dao.PhieuThuDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/phieu-thu")
public class PhieuThuController {

    SimpleDateFormat formatteryyyy = new SimpleDateFormat("yyyy");

    @Autowired
    PhieuThuDAO phieuThuDAO;
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    @Secured(ConstantAuthor.PHIEU_THU.view)
    public String list() {
        return "phieuthu.list";
    }

    @RequestMapping(value = "/load-list", method = RequestMethod.GET)
    @Secured(ConstantAuthor.PHIEU_THU.view)
    public ResponseEntity<PagingResult> searchAdd(@RequestParam @Valid final String search, @RequestParam @Valid final int offset, @RequestParam @Valid final int number, HttpServletRequest request) {
        VtPhieuThuView item = new VtPhieuThuView();
        PagingResult page = new PagingResult();
        JSONObject searchObject = new JSONObject(search);
        SimpleDateFormat formatterddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (searchObject.has("receiptCode") && !StringUtils.isBlank(searchObject.get("receiptCode").toString())) {
                item.setReceiptCode(searchObject.get("receiptCode").toString().trim());
            }
            if (searchObject.has("fromPushStock") && !StringUtils.isBlank(searchObject.get("fromPushStock").toString())) {
                item.setFromPushStock(formatterddMMyyyy.parse(searchObject.get("fromPushStock").toString().trim()));
            }
            if (searchObject.has("toPushStock") && !StringUtils.isBlank(searchObject.get("toPushStock").toString())) {
                item.setToPushStock(formatterddMMyyyy.parse(searchObject.get("toPushStock").toString().trim()));
            }
            if (searchObject.has("nameStock") && !StringUtils.isBlank(searchObject.get("nameStock").toString())) {
                item.setNameStock(searchObject.get("nameStock").toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setNumberPerPage(number);
        page.setPageNumber(offset);
        page = phieuThuDAO.search(item, page).orElse(new PagingResult());
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/preAdd")
    @Secured(ConstantAuthor.PHIEU_THU.add)
    public String preAdd(HttpServletRequest request) {
        Integer maxId = phieuThuDAO.getMaxId();
        String receiptCode = "PT-" + formatteryyyy.format(new Date()) + "-" + ((maxId!=null? maxId : 0)+ 1);
        request.setAttribute("receiptCode", receiptCode);
        return "phieuthu.add";
    }

    @GetMapping("/preEdit/{id}")
    @Secured(ConstantAuthor.PHIEU_THU.edit)
    public String preEdit(@PathVariable("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "phieuthu.add";
    }

    @RequestMapping(value = "/loadDataEdit", method = RequestMethod.GET)
    public ResponseEntity<VTGoodsReceiptForm> loadDataEdit(@RequestParam @Valid final Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = phieuThuDAO.getVtPhieuThuFormById(id);
        return new ResponseEntity<VTGoodsReceiptForm>(vTGoodsReceiptForm, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Secured(ConstantAuthor.PHIEU_THU.add)
    public ResponseEntity<MessReponse> add(@RequestBody @Valid final VTGoodsReceiptForm vTGoodsReceiptForm, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MessReponse reponse = new MessReponse();
        boolean check = phieuThuDAO.add(vTGoodsReceiptForm, user);
        if (check) {
            reponse = new MessReponse(true, 200, "Lưu thành công!");
        } else {
            reponse = new MessReponse(false, 500, "Hệ thống đang bận, vui lòng thực hiện lại sau!");
        }
        return new ResponseEntity<MessReponse>(reponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    @Secured(ConstantAuthor.PHIEU_THU.delete)
    public ResponseEntity<String> delete(@RequestBody @Valid final VtPhieuThuView vtPhieuThuView, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String ipClient = Utils.getIpClient(request);
            boolean check = phieuThuDAO.delete(vtPhieuThuView.getId(), user, ipClient);
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
