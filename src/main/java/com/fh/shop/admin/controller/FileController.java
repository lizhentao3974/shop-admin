package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.OssUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/cobyFile")
    @ResponseBody
    public ServerResponse cobyFile(@RequestParam MultipartFile filePhoto, HttpServletRequest request) {
        try {
            String fileName = filePhoto.getOriginalFilename();
            InputStream is = filePhoto.getInputStream();
            String upload = OssUtil.upload(is, fileName);
            return ServerResponse.success(upload);

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

    @RequestMapping("/cobyFiles")
    @ResponseBody
    public ServerResponse cobyFiles(@RequestParam MultipartFile[] filePhotos, HttpServletRequest request) {
        try {

            StringBuilder paths = new StringBuilder();

            for (MultipartFile filePhoto : filePhotos) {
                String fileName = filePhoto.getOriginalFilename();
                InputStream is = filePhoto.getInputStream();
                String upload = OssUtil.upload(is, fileName);
                paths.append(",").append(upload);
            }

            return ServerResponse.success(paths.length() > 0 ? paths.substring(1) : "");

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

    @RequestMapping("/cobyExcel")
    @ResponseBody
    public ServerResponse cobyExcel(@RequestParam MultipartFile excelFile) {
        try {
            String fileName = excelFile.getOriginalFilename();
            InputStream is = excelFile.getInputStream();
            String s = FileUtil.copyFile(is, fileName, SystemConstant.IMPORT_EXCEL_PATH);
            return ServerResponse.success(SystemConstant.IMPORT_EXCEL_PATH + s);

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

}
