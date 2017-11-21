package com.ma.service;

import com.ma.entity.Disk;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public interface SkyDriverService {

    int saveDiskDir(Disk disk);

    List<Disk> findByPid(Integer pid);

    Disk findById(Integer id);

    void uploadFile(InputStream inputStream, Integer pid, Integer id, double size, String fileName);

    InputStream downFile(String diskId) throws IOException;

    void saveQiniu(Disk disk);

    InputStream downFromQiniu(String diskId) throws IOException;
}
