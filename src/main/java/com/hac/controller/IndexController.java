package com.hac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 3/13/2017.
 */
@Controller
public class IndexController {
  @RequestMapping(value = "/",produces = "text/html")
  public @ResponseBody String index() {
    return "test";
  }

  @RequestMapping(value="/upload", method= RequestMethod.POST)

  public @ResponseBody String handleFileUpload(
      @RequestParam("file") MultipartFile file){
    String name = "video";
    if (!file.isEmpty()) {
      try {
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream =
            new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
        stream.write(bytes);
        stream.close();
        return "You successfully uploaded " + name + " into " + name + "-uploaded !";
      } catch (Exception e) {
        return "You failed to upload " + name + " => " + e.getMessage();
      }
    } else {
      return "You failed to upload " + name + " because the file was empty.";
    }
  }
}
