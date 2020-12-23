package com.osp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bienNhan")
public class BienNhanController {

  @GetMapping("/preAdd")
  public String list() {
    return "bienNhan.add";
  }
}
