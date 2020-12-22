package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.UserDAO;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Admin on 1/9/2018.
 */
@Controller
@RequestMapping("/system/history")
public class LogAccessController {

    private Logger logger = LogManager.getLogger(LogAccessController.class);
    @Autowired
    LogAccessDAO logAccessService;
    @Autowired
    UserDAO userService;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @GetMapping("/list")
    @Secured(ConstantAuthor.Log.view)
    public String list() {
        return "log.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.Log.view)
    public ResponseEntity<PagingResult> HistoryList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String username) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        try {
            page = logAccessService.page(page, Utils.trim(username)).orElse(new PagingResult());
        } catch (Exception e) {

        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/user-log")
    @Secured(ConstantAuthor.Log.view)
    public ResponseEntity<PagingResult> logOfUser(Long userId, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        if (userId == null || userId.longValue() == 0) {
            return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
        }
        try {
            page = logAccessService.getByUserId(page, userId).orElse(new PagingResult());
        } catch (Exception e) {

        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Secured(ConstantAuthor.Log.view)
    public String getOfUser(Model model, @PathVariable("id") Long userId) {
        if (userId == null || userId.longValue() == 0) {
            return "404";
        }
        try {
            User user = userService.get(userId).orElse(null);
            if (user == null) {
                return "404";
            }
            model.addAttribute("user", user);
        } catch (Exception e) {
            return "404";
        }
        return "log.user";
    }

    @GetMapping("/logAccessSystem/download")
    @Secured(ConstantAuthor.Log.view)
    public void logAccessSystem(HttpServletResponse response, String username,HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PagingResult page = new PagingResult();
        page.setPageNumber(0);
        try {
            page = logAccessService.page(page, Utils.trim(username)).orElse(new PagingResult());

            if (page.getItems().isEmpty()) {
                return;
            }
            logAccessService.addLog(Constants.Log.system, "Export lịch sử hệ thống " + username, Utils.getIpClient(request));
            Map<String, Object> beans = new HashMap<String, Object>();

//            beans.put("type", user.getType());
//            beans.put("fromDate", fromDate);
//            if (StringUtils.isNotBlank(toDate)) {
//                beans.put("toDate", "Đến ngày: " + toDate);
//            }
//            if (StringUtils.isNotBlank(userId)) {
//                User user1 = userService.get(Long.parseLong(userId)).orElse(null);
//                if (user1 != null) {
//                    beans.put("username", "Người giao: " + user1.getFullName());
//                }
//            }
            if (username != null && !username.isEmpty()) {
                beans.put("username", "Tài khoản người dùng: " + username);
            }
            beans.put("page", page);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            beans.put("year", cal.get(Calendar.YEAR));
            beans.put("month", cal.get(Calendar.MONTH) + 1);
            beans.put("day", cal.get(Calendar.DAY_OF_MONTH));
            beans.put("total", page.getRowCount());

            Resource resource = new ClassPathResource("fileTemplate/logAccessTemplate.xlsx");
            InputStream fileIn = resource.getInputStream();
            ExcelTransformer transformer = new ExcelTransformer();
            Workbook workbook = transformer.transform(fileIn, beans);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + "log-access-export-"+dateFormat.format(new Date())+".xlsx");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();

        } catch (Exception e) {
            logger.warn("Have an error when get data method logAccessSystem:" + e.getMessage());
        }
    }

}
