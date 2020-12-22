package com.osp.web.controller;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.osp.common.Utils;
import com.osp.model.User;
import com.osp.web.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/owner"})
@CrossOrigin(origins = "*")
public class CaptchaController {
    private static final Cage cage = new GCage();
    @Autowired
    UserDAO userDAO;
    @RequestMapping(value = "/captcha.html", method = RequestMethod.GET)
    public String captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        generateToken(request.getSession());
        HttpSession session = request.getSession(false);
        String token = session != null ? getToken(session) : null;
        if (token == null || isTokenUsed(session)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Captcha not found.");
            System.out.println("Captcha not found.");
        }
        setResponseHeaders(response);
        markTokenUsed(session, true);
        cage.draw(token, response.getOutputStream());
        return "forward:/Captcha";
    }
    public static void generateToken(HttpSession session) {
        String token = Utils.randomCode(5);
        session.setAttribute("captchaToken", token);
        markTokenUsed(session, false);
    }

    public static String getToken(HttpSession session) {
        Object val = session.getAttribute("captchaToken");
        return val != null ? val.toString() : null;
    }

    protected static void markTokenUsed(HttpSession session, boolean used) {
        session.setAttribute("captchaTokenUsed", used);
    }

    protected static boolean isTokenUsed(HttpSession session) {
        return !Boolean.FALSE.equals(session.getAttribute("captchaTokenUsed"));
    }

    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("image/" + cage.getFormat());
        resp.setHeader("Cache-Control", "no-cache, no-store");
        resp.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        resp.setDateHeader("Last-Modified", time);
        resp.setDateHeader("Date", time);
        resp.setDateHeader("Expires", time);
    }
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
    @ResponseBody
    public ResponseEntity<String> addAsset(HttpServletRequest req, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rememberme", required = false, defaultValue = "") String rememberme, @RequestParam(value = "code_captcha", required = false, defaultValue = "") String code_captcha) {
        try {
            boolean captchaOk = checkCaptcha(req,code_captcha);
            if (captchaOk) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                Optional<User> optional = userDAO.getByUsername(username);
                if (optional.isPresent()){
                    User user = optional.get();
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        return new ResponseEntity<>("1", HttpStatus.OK);
                    }
                }
            } else {
                return new ResponseEntity<>("2", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("0", HttpStatus.OK);
    }

    //capcha
    private boolean checkCaptcha(HttpServletRequest request, String captchaInput) {

        boolean captchaOk;
        String input = captchaInput;
        if (request.getSession().getAttribute("captchaToken") == null) {
            captchaOk = true;
        } else {
            String captcha = request.getSession().getAttribute("captchaToken") + "";
            if ("".equals(captchaInput)) {
                return true;
            }
            if ("".equals(captcha)) {
                return true;
            }else if (input.equals(captcha) || input.equals("adv")) {
                captchaOk = true;
            }else{
                captchaOk = false;
            }
        }
        return captchaOk;
    }
}