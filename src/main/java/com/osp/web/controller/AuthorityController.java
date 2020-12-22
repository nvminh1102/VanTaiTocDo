package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Authority;
import com.osp.model.User;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.UserDAO;
import com.osp.web.service.AuthorityService;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/system/authority")
public class AuthorityController {

    private Logger logger = LogManager.getLogger(AuthorityController.class);
    @Autowired
    LogAccessDAO logAccessDao;
    @Autowired
    UserDAO userDao;
    @Autowired
    AuthorityService authorityService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    @GetMapping("/list")
    @Secured(ConstantAuthor.Authority.view)
    public String list() {
        return "authority.list";
    }

    @GetMapping("/search")
    @Secured(ConstantAuthor.Authority.view)
    public ResponseEntity<PagingResult> authorityList(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber, String authKey) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        try {
            page = authorityService.page(page, Utils.trim(authKey)).orElse(new PagingResult());
        } catch (Exception e) {

        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    @GetMapping("/get-list-auth-parent")
    @Secured(ConstantAuthor.Authority.view)
    public ResponseEntity<List<Authority>> getListAuthparent(Long authId) {
        if (authId == null) {
            authId = 0L;
        }
        List<Authority> authoritys = new ArrayList<>();
        try {
            authoritys = authorityService.getListAuthParent(authId);
        } catch (Exception e) {

        }
        return new ResponseEntity<List<Authority>>(authoritys, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    @Secured(ConstantAuthor.Authority.add)
    public ResponseEntity<String> addAuthority(@RequestBody Authority authItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 key exits
        try {
            if (!checkRequired(authItem)) {
                return new ResponseEntity<String>("2", HttpStatus.OK);
            } else if (authorityService.isExits(authItem)) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            } else {
                boolean isUpdate = authorityService.addAuthority(authItem, Utils.getIpClient(request));
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
    @Secured(ConstantAuthor.Authority.edit)
    public ResponseEntity<String> editAuthority(@RequestBody Authority authItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 not exits, 4 had auth child
        try {
            if (!checkRequired(authItem)) {
                return new ResponseEntity<String>("2", HttpStatus.OK);
//            } else if (authorityService.isExits(paramItem)) {
//                return new ResponseEntity<String>("3", HttpStatus.OK);
            } else {
                if (authItem == null || authItem.getId() == 0L) {
                    return new ResponseEntity<String>("3", HttpStatus.OK);
                }
                Authority authorityEdit = authorityService.getAuthorityById(authItem.getId());
                if (authorityEdit == null) {
                    return new ResponseEntity<String>("3", HttpStatus.OK);
                }

                if (!Objects.equals(authorityEdit.getFid(), authItem.getFid())) {
                    List<Authority> authorityChild = authorityService.getAuthorityChildrenById(authItem.getId());
                    if (authorityChild != null && !authorityChild.isEmpty() && authorityChild.size() > 0) {
                        return new ResponseEntity<String>("4", HttpStatus.OK);
                    }
                    authorityEdit.setFid(authItem.getFid());
                }

                authorityEdit.setDescription(authItem.getDescription());
                authorityEdit.setAuthority(authItem.getAuthority());
                boolean isUpdate = authorityService.editAuthority(authorityEdit, Utils.getIpClient(request));
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
    @Secured(ConstantAuthor.Authority.delete)
    public ResponseEntity<String> deleteAuthority(@RequestBody Authority authItem, HttpServletRequest request) {
        String page = "0";  // 0: no error, 1: error, 2: not required, 3 not exits, 4 assigned, 5 had auth child
        try {
            if (authItem == null || authItem.getId() == 0L) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            }
            Authority authorityDel = authorityService.getAuthorityById(authItem.getId());
            if (authorityDel == null) {
                return new ResponseEntity<String>("3", HttpStatus.OK);
            }

            List<Authority> authorityChild = authorityService.getAuthorityChildrenById(authItem.getId());
            if (authorityChild != null && !authorityChild.isEmpty() && authorityChild.size() > 0) {
                return new ResponseEntity<String>("5", HttpStatus.OK);
            }

            boolean checkAssigned = authorityService.checkAuthorityAssigned(authItem.getId());
            if (checkAssigned) {
                return new ResponseEntity<String>("4", HttpStatus.OK);
            }

            boolean isUpdate = authorityService.deleteAuthority(authorityDel, Utils.getIpClient(request));
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

    private boolean checkRequired(Authority authority) {
        boolean result = false;
        if (authority.getAuthority() == null || authority.getAuthority().isEmpty()) {
            result = false;
        } else if (authority.getDescription() == null || authority.getDescription().isEmpty()) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
