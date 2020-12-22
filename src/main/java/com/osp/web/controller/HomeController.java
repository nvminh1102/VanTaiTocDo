package com.osp.web.controller;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {

    private Logger logger = LogManager.getLogger(HomeController.class);
    @Autowired
    LogAccessDAO logAccessService;
    @Autowired
    UserDAO userDAO;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            if (error.equals("true")) {
                model.addAttribute("error", "label.system.authority.account.notcorrect");
            } else if (error.equals("disable")) {
                model.addAttribute("error", "label.system.author.account.block");
            }

        }

        return "login";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.getSession().setAttribute("userLogin", user);
        request.getSession().setAttribute("layout", "/WEB-INF/views/layout/layout_1.jsp");
        return "index";
    }

    /*For my history*/
    @GetMapping("/history")
    public String getOfUser() {
        return "my.history";
    }

    @GetMapping("/history/my-log")
    public ResponseEntity<PagingResult> logOfUser(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            page = logAccessService.getByUserId(page, user.getId()).orElse(new PagingResult());
        } catch (Exception e) {
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    //ham lay du lieu kiem tra leftmenu dang dong hay mo
    @GetMapping("/change-nav")
    public ResponseEntity<Boolean> changeNavXs(HttpServletRequest request) {
        if (request.getSession().getAttribute("nav-xs") != null) {
            Boolean navXs = (Boolean) request.getSession().getAttribute("nav-xs");
            if (navXs != null) {
                request.getSession().setAttribute("nav-xs", !navXs);
            }
        } else {
            request.getSession().setAttribute("nav-xs", true);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

}
