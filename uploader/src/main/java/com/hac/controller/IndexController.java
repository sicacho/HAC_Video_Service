package com.hac.controller;

import com.hac.configuration.AppConfiguration;
import com.hac.domain.Video;
import com.hac.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;

/**
 * Created by Administrator on 3/13/2017.
 */
@Controller
public class IndexController {

  @Autowired
  AppConfiguration appConfiguration;

  @Autowired
  VideoService videoService;

  private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping(value="/upload", method= RequestMethod.POST)
  public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
    String name = "userId.mp4";
    name = buildNameByStartTimeUpload(name);
    if (!file.isEmpty()) {
      try {
        String location = appConfiguration.getMovieLocation();
        location = location + File.separator + name;
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream =
            new BufferedOutputStream(FileUtils.openOutputStream(new File(location)));
        stream.write(bytes);
        stream.close();
        Video video = new Video();
        video.setName(name);
        video.setLocation(location);
        logger.debug("Video : " + video.toString());
        videoService.sendVideoToEncoder(video);
        return "successfully" ;
      } catch (Exception e) {
        logger.error("Error : " + e);
        return "You failed to upload " + name + " => " + e.getMessage();
      }
    } else {
      return "You failed to upload " + name + " because the file was empty.";
    }
  }

  private String buildNameByStartTimeUpload(String name) {
    String startTime =  String.valueOf(System.currentTimeMillis());
    name = name + "-" + startTime;
    return name;
  }
}
