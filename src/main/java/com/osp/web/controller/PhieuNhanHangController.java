package com.osp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/phieu-nhan-hang")
public class PhieuNhanHangController {
    
//    @Autowired
//    LogAccessDAO logAccessDao;

    @GetMapping("/list")
    public String list() {
        return "phieunhanhang.list";
    }
}
