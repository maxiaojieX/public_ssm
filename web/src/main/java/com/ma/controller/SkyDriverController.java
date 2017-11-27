package com.ma.controller;

import com.ma.entity.Account;
import com.ma.entity.Disk;
import com.ma.exception.ServiceException;
import com.ma.service.SkyDriverService;
import com.ma.service.impl.util.AjaxStateJson;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
@Controller
@RequestMapping("/SkyDrive")
public class SkyDriverController {

    @Autowired
    private SkyDriverService skyDriverService;

    @GetMapping("/our")
    public String skyDrive(@RequestParam(required = false,defaultValue = "0") Integer pid,
                           Model model,
                           @RequestParam(required = false,defaultValue = "-1") Integer backId,
                           @RequestParam(required = false,defaultValue = "") String fileName) {
        if(backId != -1){
            //byId
            Disk disk = skyDriverService.findById(backId);
            pid = disk.getPid();
        }
        if("file".equals(fileName)){
            //点击的是一个文件,findById
            Disk disk = skyDriverService.findById(pid);
            model.addAttribute("oneDisk",disk);
        }else {
            List<Disk> diskList = skyDriverService.findByPid(pid);
            model.addAttribute("diskList", diskList);
            model.addAttribute("pid", pid);
        }
        String accessKey = "RFm_h8bmkdojVX76DF4_apTmzipL31DPvjcz93yX";
        String secretKey = "7OENSysbn5jCkjfN4L3rnoDvKWiX1FshY_RsSeD7";
        String bucket = "maxiaojie";

        Auth auth = Auth.create(accessKey,secretKey);
        StringMap putPolicy = new StringMap();

        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fileRealName\":$(fileRealName)}");
        putPolicy.put("callbackBodyType", "application/json");
        long expireSeconds = 3600;

        String upToken = auth.uploadToken(bucket,null,expireSeconds,putPolicy);
        model.addAttribute("upToken",upToken);

        return "SkyDrive/our";
    }

    @GetMapping("/newDir")
    @ResponseBody
    public AjaxStateJson newDir(String name,
                                @RequestParam(required = false,defaultValue = "0") Integer pid,
                                HttpSession session) {
        Account account = (Account) session.getAttribute("account");

        Disk disk = new Disk();
        disk.setName(name);
        disk.setAid(account.getId());
        disk.setPid(pid);

        skyDriverService.saveDiskDir(disk);
        List<Disk> diskList = skyDriverService.findByPid(pid);
        AjaxStateJson ajaxStateJson = new AjaxStateJson();
        ajaxStateJson.setState("success");
        ajaxStateJson.setMessage("保存成功");
        ajaxStateJson.setData(diskList);
        return ajaxStateJson;
    }


    @PostMapping("/fileUpload")
    @ResponseBody
    public AjaxStateJson fileUpload(Integer pid, MultipartFile file,
                                    HttpSession session) {

        if(file.isEmpty()){
            return new AjaxStateJson("error","缺少上传文件");
        }
        Account account = (Account) session.getAttribute("account");
        try {
            //把文件放入输入流
            InputStream inputStream = file.getInputStream();
            double fileSize = file.getSize();
            String fileName = file.getOriginalFilename();

            //把输入流，pid，accountId，size，fileName传给service层
            skyDriverService.uploadFile(inputStream,pid,account.getId(),fileSize,fileName);


        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Disk> diskList = skyDriverService.findByPid(pid);
        AjaxStateJson ajaxStateJson = new AjaxStateJson();
        ajaxStateJson.setState("success");
        ajaxStateJson.setMessage("保存成功");
        ajaxStateJson.setData(diskList);

        return ajaxStateJson;
    }

    @GetMapping("/downLoad")
    public void downLoad(String diskId,
                         String fileName,
                         @RequestParam(required = false,defaultValue = "") String yuLan,
                         HttpServletResponse response) {

        try {
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = skyDriverService.downFile(diskId);

            if(!"yes".equals(yuLan)){
                //下载
                response.setContentType("application/octet-stream");
                fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
                response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
            }

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }catch (IOException | ServiceException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 保存上传到七牛的数据
     * @param fileRealName
     * @param pid
     * @param qiniuFileName
     * @param fileSize
     * @return
     */
    @PostMapping("/qiniuUp")
    @ResponseBody
    public AjaxStateJson qiniuFileUpload(String fileRealName,String pid,String qiniuFileName,String fileSize) {
        //获取保存的文件名
        Disk disk = new Disk();
        disk.setName(fileRealName);
        disk.setSaveName(qiniuFileName);
        disk.setPid(Integer.parseInt(pid));
        disk.setFileSize(Double.parseDouble(pid));
        skyDriverService.saveQiniu(disk);

        AjaxStateJson ajaxStateJson = new AjaxStateJson("success","上传成功");
        List<Disk> diskList = skyDriverService.findByPid(Integer.parseInt(pid));
        ajaxStateJson.setData(diskList);
        return ajaxStateJson;
    }

    @GetMapping("/qiniuDownLoad")
    public void qiniuDownLoad(String diskId,
                              String fileName,
                              @RequestParam(required = false,defaultValue = "") String yuLan,
                              HttpServletResponse response) {
        try {
            OutputStream outputStream = response.getOutputStream();
            //InputStream inputStream = skyDriverService.downFile(diskId);
            InputStream inputStream = skyDriverService.downFromQiniu(diskId);

            if(!"yes".equals(yuLan)){
                //下载
                response.setContentType("application/octet-stream");
                fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
                response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
            }

            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }catch (IOException | ServiceException ex) {
            ex.printStackTrace();
        }


    }





}
