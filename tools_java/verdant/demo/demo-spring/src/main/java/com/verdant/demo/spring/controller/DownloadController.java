package com.verdant.demo.spring.controller;

import com.verdant.jtools.common.web.model.ResponseErrorMessage;
import com.verdant.jtools.common.web.utils.DownloadUtils2;
import com.verdant.jtools.common.web.utils.WebUtils2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author verdant
 * @since 2016/11/07
 */
@Controller
@RequestMapping("/download")
public class DownloadController {

    @RequestMapping(value = "/way1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity way1() {
        byte[] bytes = null;
        String fileName = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/way2", method = RequestMethod.GET)
    @ResponseBody
    public void way2() {
        byte[] bytes = null;
        String fileName = "";
        try {
            DownloadUtils2.download(bytes, fileName);
        } catch (Exception e) {
            WebUtils2.responseJSON(new ResponseErrorMessage(e));
        }
    }
}
