package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.*;
import com.osp.model.view.BienNhanForm;
import com.osp.web.dao.BienNhanDAO;
import com.osp.web.dao.KhachHangDAO;
import com.osp.web.dao.MatHangDAO;
import com.osp.web.dao.NhaXeDAO;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bienNhan")
public class BienNhanController {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private SimpleDateFormat formatteryyyy = new SimpleDateFormat("yyyy");
    private final String templatePhieuNhanHang = "/fileTemplate/templatePhieuNhanHang.xlsx";
    @Autowired
    BienNhanDAO bienNhanDAO;
    @Autowired
    KhachHangDAO khachHangDAO;
    @Autowired
    MatHangDAO matHangDAO;
    @Autowired
    NhaXeDAO nhaXeDAO;

    @GetMapping("/preAdd")
    public String list(Model model) {
        Integer maxId = bienNhanDAO.getMaxId();
        String receiptCode = "NH-" + formatteryyyy.format(new Date()) + "-" + ((maxId!=null? maxId : 0)+ 1);
        model.addAttribute("receiptCode", receiptCode);
        return "bienNhan.add";
    }

    @PostMapping(value = "/them-moi-bien-nhan")
//  @Secured(ConstantAuthor.PublishAuctionTc.edit)
    public ResponseEntity<String> addAucInfo(@RequestBody BienNhanForm item, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            VtPartner rsNguoiGui = null;
            VtPartner rsNguoiNhan = null;
            VtPartner nguoiGuiDb = khachHangDAO.getByCMND(item.getNguoiGui().getTaxCode(), 2);
            VtPartner nguoiNhanDb = khachHangDAO.getByCMND(item.getNguoiNhan().getTaxCode(), 3);
            // insert người gửi
            if (nguoiGuiDb.getId() == null) {
                VtPartner nguoiGui = new VtPartner();
                nguoiGui.setTaxCode(item.getNguoiGui().getTaxCode());
                nguoiGui.setFullName(item.getNguoiGui().getFullName());
                nguoiGui.setMobile(item.getNguoiGui().getMobile());
                nguoiGui.setAddress(item.getNguoiGui().getAddress());
                nguoiGui.setCreatedBy(user.getUsername());
                nguoiGui.setLastUpdate(new Date());
                nguoiGui.setGenDate(new Date());
                nguoiGui.setUpdatedBy(user.getUsername());
                //type 1 khách hàng, 2 người gửi, 3 người nhận
                nguoiGui.setTypePartner(2);
                rsNguoiGui = khachHangDAO.add(nguoiGui);
            }

            // insert người nhận
            if (nguoiNhanDb.getId() == null) {
                VtPartner nguoiNhan = new VtPartner();
                nguoiNhan.setTaxCode(item.getNguoiNhan().getTaxCode());
                nguoiNhan.setFullName(item.getNguoiNhan().getFullName());
                nguoiNhan.setMobile(item.getNguoiNhan().getMobile());
                nguoiNhan.setAddress(item.getNguoiNhan().getAddress());
                nguoiNhan.setCreatedBy(user.getUsername());
                nguoiNhan.setLastUpdate(new Date());
                nguoiNhan.setGenDate(new Date());
                nguoiNhan.setUpdatedBy(user.getUsername());
                //type 1 khách hàng, 2 người gửi, 3 người nhận
                nguoiNhan.setTypePartner(3);
                rsNguoiNhan = khachHangDAO.add(nguoiNhan);
            }

            //insert biên nhận
            VtReceipt bienNhan = new VtReceipt();
            bienNhan.setReceiptCode(item.getBienNhan().getReceiptCode());
            if (rsNguoiGui != null) {
                bienNhan.setDeliveryPartnerId(rsNguoiGui.getId());
            } else {
                bienNhan.setDeliveryPartnerId(nguoiGuiDb.getId());
            }
            if (rsNguoiNhan != null) {
                bienNhan.setReceivePartnerId(rsNguoiNhan.getId());
            } else {
                bienNhan.setReceivePartnerId(nguoiNhanDb.getId());
            }
            bienNhan.setNameStock(item.getBienNhan().getNameStock());
            bienNhan.setDateReceipt(item.getBienNhan().getDateReceipt());
            bienNhan.setDatepushStock(new Date());
            // 1 trả trước, 2 trả sau, 3 công nợ
            bienNhan.setPaymentType(item.getBienNhan().getPaymentType());
            bienNhan.setPayer(item.getBienNhan().getPayer());
            bienNhan.setTaxCode(item.getBienNhan().getTaxCode());
            bienNhan.setNhaXe(item.getBienNhan().getNhaXe());
            bienNhan.setBienSo(item.getBienNhan().getBienSo());
            bienNhan.setLoaiXe(item.getBienNhan().getLoaiXe());
            bienNhan.setEmployee(item.getBienNhan().getEmployee());
            //Nhận hàng
            //1: Nhận hàng, 2: Nhập kho, 3: Đang giao, 4: Đã giao hàng
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

    @GetMapping("/ThongTinNguoiGui")
    public ResponseEntity<VtPartner> ThongTinNguoiGui(@RequestParam(value = "taxCode") String taxCode, @RequestParam(value = "typePartner") int typePartner) {
        return new ResponseEntity<>(khachHangDAO.getByCMND(taxCode, typePartner), HttpStatus.OK);
    }

    @RequestMapping(value = "/list-bien-nhan", method = RequestMethod.GET)
    public ResponseEntity<PagingResult> searchAdd(@RequestParam @Valid final String searchBienNhan, @RequestParam @Valid final int offset, @RequestParam @Valid final int number, HttpServletRequest request) {
        VtReceipt item = new VtReceipt();
        PagingResult page = new PagingResult();
        JSONObject searchObject = new JSONObject(searchBienNhan);
        SimpleDateFormat formatterddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (searchObject.has("receiptCode") && !StringUtils.isBlank(searchObject.get("receiptCode").toString())) {
                item.setReceiptCode(searchObject.get("receiptCode").toString().trim());
            }
            if (searchObject.has("fromDeceipt") && !StringUtils.isBlank(searchObject.get("fromDeceipt").toString())) {
                item.setFromDeceipt(formatterddMMyyyy.parse(searchObject.get("fromDeceipt").toString().trim()));
            }
            if (searchObject.has("toDeceipt") && !StringUtils.isBlank(searchObject.get("toDeceipt").toString())) {
                item.setToDeceipt(formatterddMMyyyy.parse(searchObject.get("toDeceipt").toString().trim()));
            }
            if (searchObject.has("nhaXe") && !StringUtils.isBlank(searchObject.get("nhaXe").toString())) {
                item.setNhaXe(searchObject.get("nhaXe").toString().trim());
            }
            if (searchObject.has("nameStock") && !StringUtils.isBlank(searchObject.get("nameStock").toString())) {
                item.setNameStock(searchObject.get("nameStock").toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setNumberPerPage(number);
        page.setPageNumber(offset);
        page = bienNhanDAO.page(page, item.getReceiptCode(), item.getNameStock(), item.getFromDeceipt(), item.getToDeceipt(), item.getLoaiXe(), item.getBienSo()).orElse(new PagingResult());
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/list")
    public String listBn() {
        return "bienNhan.list";
    }

    @GetMapping("/search")
    public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
        @RequestParam(value = "numberPerPage", required = false, defaultValue = "25") int numberPerPage,
        @RequestParam(value = "receiptCode", required = false, defaultValue = "") String receiptCode,
        @RequestParam(value = "nameStock", required = false, defaultValue = "") String nameStock,
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
            page = bienNhanDAO.page(page, Utils.trim(receiptCode), Utils.trim(nameStock), fromDate, toDate, null, null).orElse(new PagingResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @PostMapping(value = "/delete")
//    @Secured(ConstantAuthor.Parameter.update)
    public ResponseEntity<String> delete(@RequestBody VtReceipt vtReceipt, HttpServletRequest request) {
        try {
            if (vtReceipt.getId() == null) {
                return new ResponseEntity<String>("1", HttpStatus.OK);
            }
            boolean isDelete = bienNhanDAO.delete(vtReceipt.getId());
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

    @PostMapping("/preEdit")
    //    @Secured(ConstantAuthor.Parameter.update)
    public String preEdit(Model model, @RequestParam(value = "editId", required = false) Long editId) {
        model.addAttribute("receiptId", editId);
        return "bienNhan.edit";
    }

    @GetMapping("/ThongTinBienNhan")
    public ResponseEntity<BienNhanForm> getInfoEditAuction(@RequestParam(value = "id", required = true) Integer bienNhanId,
                                                                    HttpServletRequest request) {
        BienNhanForm item = new BienNhanForm();
        try {
            item.setBienNhan(bienNhanDAO.getById(bienNhanId));
            if (item.getBienNhan() != null) {
                VtPartner nguoiGuiFull = khachHangDAO.getById(item.getBienNhan().getDeliveryPartnerId());
                VtPartner nguoiGuiShort = new VtPartner();
                nguoiGuiShort.setTaxCode(nguoiGuiFull.getTaxCode());
                nguoiGuiShort.setFullName(nguoiGuiFull.getFullName());
                nguoiGuiShort.setMobile(nguoiGuiFull.getMobile());
                nguoiGuiShort.setAddress(nguoiGuiFull.getAddress());
                item.setNguoiGui(nguoiGuiShort);
                VtPartner nguoiNhanFull = khachHangDAO.getById(item.getBienNhan().getReceivePartnerId());
                VtPartner nguoiNhanShort = new VtPartner();
                nguoiNhanShort.setTaxCode(nguoiNhanFull.getTaxCode());
                nguoiNhanShort.setFullName(nguoiNhanFull.getFullName());
                nguoiNhanShort.setMobile(nguoiNhanFull.getMobile());
                nguoiNhanShort.setAddress(nguoiNhanFull.getAddress());
                item.setNguoiNhan(nguoiNhanShort);
                List<VtReceiptDetail> listProperty = matHangDAO.getDsMatHang(item.getBienNhan().getId());
                item.setMatHang(listProperty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<BienNhanForm>(item, HttpStatus.OK);
    }

    @GetMapping("/deleteProperty")
    public ResponseEntity<String> deleteProperty(
            @RequestParam(value = "arrIdDelete", required = true) int[] arrIdDelete,
            HttpServletRequest request) {
        try {
            for (int propertyID : arrIdDelete) {
                matHangDAO.delete(propertyID);
            }
            return new ResponseEntity<String>("1", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/chinh-sua-bien-nhan")
    public ResponseEntity<String> editAuctionInfo(@RequestBody BienNhanForm item, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        VtReceipt bienNhanCu = bienNhanDAO.getById(item.getBienNhan().getId());
        List<VtReceiptDetail> propertyList = item.getMatHang();
        try {
            VtPartner nguoiGui = new VtPartner();
            VtPartner nguoiNhan = new VtPartner();
            if (item.getNguoiGui().getTaxCode() != null && !"".equals(item.getNguoiGui().getTaxCode())) {
                nguoiGui.setTaxCode(item.getNguoiGui().getTaxCode().trim());
            }
            if (item.getNguoiGui().getFullName() != null && !"".equals(item.getNguoiGui().getFullName())) {
                nguoiGui.setFullName(item.getNguoiGui().getFullName().trim());
            }
            if (item.getNguoiGui().getMobile() != null && !"".equals(item.getNguoiGui().getMobile())) {
                nguoiGui.setMobile(item.getNguoiGui().getMobile().trim());
            }
            if (item.getNguoiGui().getAddress() != null && !"".equals(item.getNguoiGui().getAddress())) {
                nguoiGui.setAddress(item.getNguoiGui().getAddress().trim());
            }
            // update người gửi
            VtPartner nguoiGuiCu = khachHangDAO.getById(bienNhanCu.getDeliveryPartnerId());
            if (nguoiGuiCu != null) {
                nguoiGui.setId(nguoiGuiCu.getId());
                if (!Objects.equals(nguoiGui.getFullName(), nguoiGuiCu.getFullName()) || !Objects.equals(nguoiGui.getTaxCode(), nguoiGuiCu.getTaxCode())
                        || !Objects.equals(nguoiGui.getMobile(), nguoiGuiCu.getMobile()) || !Objects.equals(nguoiGui.getAddress(), nguoiGuiCu.getAddress())) {
                    nguoiGuiCu.setTaxCode(nguoiGui.getTaxCode());
                    nguoiGuiCu.setFullName(nguoiGui.getFullName());
                    nguoiGuiCu.setMobile(nguoiGui.getMobile());
                    nguoiGuiCu.setAddress(nguoiGui.getAddress());
                   boolean blUpdate = khachHangDAO.edit(nguoiGuiCu);

                }
            }

            if (item.getNguoiNhan().getTaxCode() != null && !"".equals(item.getNguoiNhan().getTaxCode())) {
                nguoiNhan.setTaxCode(item.getNguoiNhan().getTaxCode().trim());
            }
            if (item.getNguoiNhan().getFullName() != null && !"".equals(item.getNguoiNhan().getFullName())) {
                nguoiNhan.setFullName(item.getNguoiNhan().getFullName().trim());
            }
            if (item.getNguoiNhan().getMobile() != null && !"".equals(item.getNguoiNhan().getMobile())) {
                nguoiNhan.setMobile(item.getNguoiNhan().getMobile().trim());
            }
            if (item.getNguoiNhan().getAddress() != null && !"".equals(item.getNguoiNhan().getAddress())) {
                nguoiNhan.setAddress(item.getNguoiNhan().getAddress().trim());
            }

            // update người nhận
            VtPartner nguoiNhanCu = khachHangDAO.getById(bienNhanCu.getReceivePartnerId());
            if (nguoiNhanCu != null) {
                nguoiNhanCu.setId(nguoiNhanCu.getId());
                if (!Objects.equals(nguoiNhanCu.getFullName(), nguoiNhan.getFullName()) || !Objects.equals(nguoiNhanCu.getTaxCode(), nguoiNhan.getTaxCode())
                        || !Objects.equals(nguoiNhanCu.getMobile(), nguoiNhan.getMobile()) || !Objects.equals(nguoiNhanCu.getAddress(), nguoiNhan.getAddress())) {
                    nguoiNhanCu.setTaxCode(nguoiNhan.getTaxCode());
                    nguoiNhanCu.setFullName(nguoiNhan.getFullName());
                    nguoiNhanCu.setMobile(nguoiNhan.getMobile());
                    nguoiNhanCu.setAddress(nguoiNhan.getAddress());
                    boolean blUpdate = khachHangDAO.edit(nguoiNhanCu);

                }
            }

            bienNhanCu.setLastUpdate(new Date());
//            bienNhanCu.setReceiptCode(item.getBienNhan().getReceiptCode().trim());
            bienNhanCu.setNameStock(item.getBienNhan().getNameStock().trim());
            bienNhanCu.setDateReceipt(item.getBienNhan().getDateReceipt());
            bienNhanCu.setPaymentType(item.getBienNhan().getPaymentType());
            bienNhanCu.setPayer(item.getBienNhan().getPayer());
            bienNhanCu.setDeliveryPartnerId(item.getBienNhan().getDeliveryPartnerId());
            bienNhanCu.setReceivePartnerId(item.getBienNhan().getReceivePartnerId());
            bienNhanCu.setNhaXe(item.getBienNhan().getNhaXe());
            bienNhanCu.setBienSo(item.getBienNhan().getBienSo());
            bienNhanCu.setLoaiXe(item.getBienNhan().getLoaiXe());
            bienNhanCu.setEmployee(item.getBienNhan().getEmployee());
            bienNhanCu.setUpdatedBy(user.getUsername());
            boolean blUpdate = bienNhanDAO.edit(bienNhanCu);

            if (blUpdate) {
                for (VtReceiptDetail property : propertyList) {
                    handleProperty(property, bienNhanCu.getId());
                }
            }
            return new ResponseEntity<String>("1", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("0", HttpStatus.OK);
        }
    }

    private void handleProperty(VtReceiptDetail item, Integer bienNhanId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (item.getId() != null) {
            VtReceiptDetail info = matHangDAO.getById(item.getId());
            info.setLastUpdate(new Date());
            info.setUpdatedBy(user.getUsername());
            info.setName(item.getName().trim());
            info.setUnit(item.getUnit().trim());
            info.setNumbers(item.getNumbers());
            info.setWeight(item.getWeight());
            info.setSizes(item.getSizes());
            info.setCost(item.getCost());
            info.setNote(item.getNote());
            matHangDAO.edit(info);
        } else {
            VtReceiptDetail info = new VtReceiptDetail();
            info.setName(item.getName().trim());
            info.setUnit(item.getUnit().trim());
            info.setNumbers(item.getNumbers());
            info.setWeight(item.getWeight());
            info.setSizes(item.getSizes());
            info.setCost(item.getCost());
            info.setNote(item.getNote());
            info.setGenDate(new Date());
            info.setLastUpdate(new Date());
            info.setCreatedBy(user.getUsername());
            info.setUpdatedBy(user.getUsername());
            info.setReceiptId(bienNhanId);
            //1 nhận hàng, 2 nhập kho, 3 đang giao, 4 đã giao
            info.setStatus(1);
            matHangDAO.add(info);
        }
    }

    @GetMapping("/danhSachNhaXe")
    public ResponseEntity<List> danhSachNhaXe() {
        return new ResponseEntity<>(nhaXeDAO.danhSachNhaXe(), HttpStatus.OK);
    }

    @GetMapping("/thongTinNhaXe")
    public ResponseEntity<NhaXe> thongTinNhaXe(@RequestParam(value = "bienSo", required = true) String bienSo,
                                               HttpServletRequest request) {
        NhaXe nhaXe = new NhaXe();
        try {
            nhaXe = nhaXeDAO.getByBienSo(bienSo.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<NhaXe>(nhaXe, HttpStatus.OK);
    }

    @GetMapping("/exportExcelPhieuNhan")
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
            phieuNhanHang = bienNhanDAO.getById(giaoHangId);
            if (phieuNhanHang.getPaymentType() == 1) {
                typePayment = "Trả trước";
            } else if (phieuNhanHang.getPaymentType() == 2) {
                typePayment = "Trả sau";
            } else {
                typePayment = "Công nợ";
            }
            nguoiGui = khachHangDAO.getById(phieuNhanHang.getDeliveryPartnerId());
            nguoiNhan = khachHangDAO.getById(phieuNhanHang.getReceivePartnerId());
            page.setItems(matHangDAO.getDsMatHang(giaoHangId));
            Map<String, Object> beans = new HashMap<String, Object>();
            if (phieuNhanHang.getDateReceipt() != null) {
                beans.put("ngayNhanHang", sdf.format(phieuNhanHang.getDateReceipt()));
            }
            beans.put("phieuNhanHang", phieuNhanHang);
            beans.put("nguoiGui", nguoiGui);
            beans.put("loaiThanhToan", typePayment);
            beans.put("nguoiNhan", nguoiNhan);
            beans.put("page", page);
            Resource resource = new ClassPathResource(templatePhieuNhanHang);
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "Phieu-nhan-hang-" + phieuNhanHang.getReceiptCode() + ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
