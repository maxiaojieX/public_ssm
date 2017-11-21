package com.ma.service.impl;

import com.ma.entity.Disk;
import com.ma.example.DiskExample;
import com.ma.exception.ServiceException;
import com.ma.mapper.DiskMapper;
import com.ma.service.SkyDriverService;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
@Service
public class SkyDriverServiceImpl implements SkyDriverService {

    private static final String DIR_TYPE = "dir";
    private static final String File_TYPE = "file";


    @Autowired
    private DiskMapper diskMapper;

    @Override
    public int saveDiskDir(Disk disk) {
        disk.setType(DIR_TYPE);
        diskMapper.insert(disk);
        return disk.getId();
    }

    @Override
    public List<Disk> findByPid(Integer pid) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPidEqualTo(pid);
        List<Disk> diskList = diskMapper.selectByExample(diskExample);
        return diskList;
    }

    @Override
    public Disk findById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    @Override
    public void uploadFile(InputStream inputStream, Integer pid, Integer aid, double size, String fileName) {
        Disk disk = new Disk();
        disk.setAid(aid);
        disk.setPid(pid);
        disk.setName(fileName);
        disk.setFileSize(size);
        disk.setType(File_TYPE);
        disk.setUpdateTime(new Date());
        disk.setDownloadTime(0);

        String saveName;
        if(fileName.contains(".")){
            saveName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        }else {
            saveName = UUID.randomUUID().toString();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(new File("F:/crm_skydrive", saveName));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }catch (IOException ex) {
            throw new ServiceException("文件上传错误");
        }

        //云存储
//        Properties properties = new Properties();
//        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.126.98:22122");
//        try {
////            初始化配置
//            ClientGlobal.initByProperties(properties);
//
//            //拿到存储服务器的链接
//            TrackerClient trackerClient = new TrackerClient();
//            TrackerServer trackerServer = trackerClient.getConnection();
//
//            //通过拿到的链接,创建一台存储服务器客户端
//            StorageClient storageClient = new StorageClient(trackerServer,null);
//            //InputStream inputStream2 = new FileInputStream("G:/imgtestt/1.jpg");
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//
//            String houzui = fileName.substring(fileName.lastIndexOf(".")+1);
//            String[] result = storageClient.upload_file(bytes,houzui,null);
//
//
//
//            for(String  str : result){
//                System.out.println(str);
//            }
//            inputStream.close();
//
//        } catch (MyException e) {
//            System.out.println(">>>>>>>>>>>MyException....");
//        } catch (IOException e) {
//            System.out.println(">>>>>>>>>>>云存储IOException....");
//            e.printStackTrace();
//        }

        disk.setSaveName(saveName);
        diskMapper.insertSelective(disk);

    }

    @Override
    public InputStream downFile(String diskId) throws IOException,ServiceException {
        Disk disk = diskMapper.selectByPrimaryKey(Integer.parseInt(diskId));

        if(disk == null || disk.getType().equals(DIR_TYPE)){
            throw new ServiceException("文件下载异常");
        }

        disk.setDownloadTime(disk.getDownloadTime()+1);
        diskMapper.updateByPrimaryKeySelective(disk);

        FileInputStream inputStream = new FileInputStream(new File("F:/crm_skydrive",disk.getSaveName()));
        return inputStream;
    }

    @Override
    public void saveQiniu(Disk disk) {
        disk.setType(File_TYPE);
        disk.setUpdateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    @Override
    public InputStream downFromQiniu(String diskId) throws IOException {
        Disk disk = diskMapper.selectByPrimaryKey(Integer.parseInt(diskId));
        String doMain = "http://ozpkbpsga.bkt.clouddn.com";
        String fileName = disk.getSaveName();
        String qiniuName = String.format("%s/%s",doMain,fileName);

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(qiniuName).openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        return inputStream;
    }
}
