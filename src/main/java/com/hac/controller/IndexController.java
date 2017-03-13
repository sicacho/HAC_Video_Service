package com.hac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 3/13/2017.
 */
@Controller
public class IndexController {
  @RequestMapping(value = "/",produces = "text/html")
  public @ResponseBody String index() {
    return "test";
  }
}
