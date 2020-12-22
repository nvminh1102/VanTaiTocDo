package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Parameter;
import com.osp.model.User;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.UserDAO;
import com.osp.web.service.ParameterService;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Admin on 1/9/2018.
 */
@Controller
@RequestMapping("/system/parameter")
public class ParameterController {

    private Logger logger = LogManager.getLogger(ParameterController.class);
    @Autowired
    LogAccessDAO logAccessDao;
    @Autowired
    UserDAO userDao;
    @Autowired
    ParameterService parameterService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @GetMapping("/list")
    @Secured(ConstantAuthor.Parameter.view)
    public String list() {
        return "parameter.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.Parameter.view)
    public ResponseEntity<PagingResult> parameterList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String paramKey) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        try {
            page = parameterService.page(page, Utils.trim(paramKey)).orElse(new PagingResult());
        } catch (Exception e) {

        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    @Secured(ConstantAuthor.Parameter.update)
    public ResponseEntity<String> addParameter(@RequestBody Parameter paramItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 key exits
        try {
            if (!checkRequired(paramItem)) {
                return new ResponseEntity<String>("2", HttpStatus.OK);
            } else if (parameterService.isExits(paramItem)) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            } else {
                boolean isUpdate = parameterService.addParameter(paramItem, Utils.getIpClient(request));
                if (isUpdate) {
                    return new ResponseEntity<String>("0", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("1", HttpStatus.OK);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/edit")
    @Secured(ConstantAuthor.Parameter.update)
    public ResponseEntity<String> editParameter(@RequestBody Parameter paramItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 not exits
        try {
            if (!checkRequired(paramItem)) {
                return new ResponseEntity<String>("2", HttpStatus.OK);
//            } else if (parameterService.isExits(paramItem)) {
//                return new ResponseEntity<String>("3", HttpStatus.OK);
            } else {
                if (paramItem == null || paramItem.getId() == 0L) {
                    return new ResponseEntity<String>("3", HttpStatus.OK);
                }
                Parameter parameterEdit = parameterService.getParamById(paramItem.getId());
                if (parameterEdit == null) {
                    return new ResponseEntity<String>("3", HttpStatus.OK);
                }
                parameterEdit.setValue(paramItem.getValue());
                parameterEdit.setDescription(paramItem.getDescription());

                boolean isUpdate = parameterService.editParameter(parameterEdit, Utils.getIpClient(request));
                if (isUpdate) {
                    return new ResponseEntity<String>("0", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("1", HttpStatus.OK);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/delete")
    @Secured(ConstantAuthor.Parameter.update)
    public ResponseEntity<String> deleteParameter(@RequestBody Parameter paramItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 not exits
        try {
            if (paramItem == null || paramItem.getId() == 0L) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            }
            Parameter parameterDel = parameterService.getParamById(paramItem.getId());
            if (parameterDel == null) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            }
            boolean isUpdate = parameterService.deleteParameter(parameterDel, Utils.getIpClient(request));
            if (isUpdate) {
                return new ResponseEntity<String>("0", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("1", HttpStatus.OK);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("1", HttpStatus.OK);
        }
    }

    private boolean checkRequired(Parameter param) {
        boolean result = false;
        if (param.getKey() == null || param.getKey().isEmpty()) {
            result = false;
        } else if (param.getValue() == null || param.getValue().isEmpty()) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
