package com.osp.web.controller;

import com.osp.common.MessReponse;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtPartner;
import com.osp.model.VtPhieuGiaoHang;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.VtToaHang;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.web.dao.PhieuGiaoHangDAO;
import com.osp.web.dao.PhieuNhanHangDAO;
import com.osp.web.dao.ToaHangDAO;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/phieu-giao-hang")
public class PhieuGiaoHangController {

    SimpleDateFormat formatteryyyy = new SimpleDateFormat("yyyy");
    private final String templatePhieuGiaoHang = "/fileTemplate/templatePhieuGiaoHang.xlsx";
    private final String templatePhieuThu = "/fileTemplate/templatePhieuThu.xlsx";

    @Autowired
    PhieuGiaoHangDAO phieuGiaoHangDAO;
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    public String list() {
        return "phieugiaohang.list";
    }

    @RequestMapping(value = "/load-list", method = RequestMethod.GET)
    public ResponseEntity<PagingResult> searchAdd(@RequestParam @Valid final String search, @RequestParam @Valid final int offset, @RequestParam @Valid final int number, HttpServletRequest request) {
        VtPhieuGiaoHang vtPhieuGiaoHang  = new VtPhieuGiaoHang();
        PagingResult page = new PagingResult();
        JSONObject searchObject = new JSONObject(search);
        SimpleDateFormat formatterddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        try {
            if (searchObject.has("maPhieuGiao") && !StringUtils.isBlank(searchObject.get("maPhieuGiao").toString())) {
                vtPhieuGiaoHang.setMaPhieuGiao(searchObject.get("maPhieuGiao").toString().trim());
            }
            if (searchObject.has("bienSo") && !StringUtils.isBlank(searchObject.get("bienSo").toString())) {
                vtPhieuGiaoHang.setBienSo(searchObject.get("bienSo").toString().trim());
            }
            if (searchObject.has("fromGenDate") && !StringUtils.isBlank(searchObject.get("fromGenDate").toString())) {
                vtPhieuGiaoHang.setFromGenDate(formatterddMMyyyy.parse(searchObject.get("fromGenDate").toString().trim()));
            }
            if (searchObject.has("toGenDate") && !StringUtils.isBlank(searchObject.get("toGenDate").toString())) {
                vtPhieuGiaoHang.setToGenDate(formatterddMMyyyy.parse(searchObject.get("toGenDate").toString().trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        page.setNumberPerPage(number);
        page.setPageNumber(offset);
        page = phieuGiaoHangDAO.search(vtPhieuGiaoHang, page).orElse(new PagingResult());
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/preAdd")
    public String preAdd(HttpServletRequest request) {
        Integer maxId = phieuGiaoHangDAO.getMaxId();
        String maPhieuGiao = "GH-" + formatteryyyy.format(new Date()) + "-" + ((maxId != null ? maxId : 0) + 1);
        request.setAttribute("maPhieuGiao", maPhieuGiao);
        return "phieugiaohang.add";
    }

    @GetMapping("/preEdit/{id}")
    public String preEdit(@PathVariable("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "phieugiaohang.add";
    }

    @RequestMapping(value = "/loadDataEdit", method = RequestMethod.GET)
    public ResponseEntity<VTGoodsReceiptForm> loadDataEdit(@RequestParam @Valid final Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = phieuGiaoHangDAO.getVTGoodsReceiptFormById(id);
        return new ResponseEntity<VTGoodsReceiptForm>(vTGoodsReceiptForm, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<MessReponse> addAppoint(@RequestBody @Valid final VTGoodsReceiptForm vTGoodsReceiptForm, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MessReponse reponse = new MessReponse();
        boolean check = phieuGiaoHangDAO.add(vTGoodsReceiptForm, user);
        if (check) {
            reponse = new MessReponse(true, 200, "Lưu thành công!");
        } else {
            reponse = new MessReponse(false, 500, "Hệ thống đang bận, vui lòng thực hiện lại sau!");
        }
        return new ResponseEntity<MessReponse>(reponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody @Valid final VtPhieuGiaoHang vtPhieuGiaoHang, HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            String ipClient = Utils.getIpClient(request);
            boolean check = phieuGiaoHangDAO.delete(vtPhieuGiaoHang.getId(), user, ipClient);
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
    
    
    @GetMapping("/exportPhieu")
//    @Secured(ConstantAuthor.PublishAuctionTc.view)
    public void exportExcel(HttpServletResponse response, HttpServletRequest request,
                            @RequestParam(value = "idPhieu", required = false) Integer idPhieu) {
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        VtPhieuGiaoHang vtPhieuGiaoHang = new VtPhieuGiaoHang();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            VTGoodsReceiptForm vTGoodsReceiptForm = phieuGiaoHangDAO.getExportById(idPhieu);
            vtPhieuGiaoHang = vTGoodsReceiptForm.getVtPhieuGiaoHang();
            page.setItems(vTGoodsReceiptForm.getVtReceiptViews());
            Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("strGenDate", sdf.format(vtPhieuGiaoHang.getGenDate()));
            beans.put("vtPhieuGiaoHang", vtPhieuGiaoHang);
            beans.put("ngayLap", sdf.format(new Date()));
            beans.put("page", page);
            Resource resource = new ClassPathResource(templatePhieuGiaoHang);
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "Phieu-giao-hang-" + vtPhieuGiaoHang.getMaPhieuGiao()+ "-" + sdf.format(new Date())+ ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    @GetMapping("/exportPhieuThu")
//    @Secured(ConstantAuthor.PublishAuctionTc.view)
    public void exportPhieuThu(HttpServletResponse response, HttpServletRequest request,
                            @RequestParam(value = "idPhieuThu", required = false) Integer idPhieuThu) {
        PagingResult page = new PagingResult();
        page.setPageNumber(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        try {
            List<VtReceiptDetail> vtReceiptDetails = phieuGiaoHangDAO.getPhieuNhanHang(idPhieuThu);
            VtReceiptDetail vtReceiptDetail = (vtReceiptDetails!=null ? vtReceiptDetails.get(0): new VtReceiptDetail());
            page.setItems(vtReceiptDetails);
            Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("vtReceiptDetail", vtReceiptDetail);
            beans.put("maPhieuThu", "PT-" + sdf2.format(new Date())+ "-" +(vtReceiptDetail!=null ? vtReceiptDetail.getId(): 0));
            beans.put("ngayLap", sdf.format(new Date()));
            beans.put("page", page);
            Resource resource = new ClassPathResource(templatePhieuThu);
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "Phieu-thu-" + (vtReceiptDetails!=null ? vtReceiptDetails.get(0).getReceiptCode() + "-":"")+ sdf.format(new Date())+ ".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}