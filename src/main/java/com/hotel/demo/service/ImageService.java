package com.hotel.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Service
public class ImageService {

    @Value("${server.path-file-update}")
    private String pathStoreImage;

    public String storeImage(MultipartFile multipartFile)  {
        try {
            if (Objects.isNull(multipartFile) || multipartFile.getBytes().length == 0) {
                return null;
            }
            File root = new File("");
            File file = new File(root.getAbsolutePath()+"\\"+this.pathStoreImage);
            multipartFile.transferTo(new File(file+"\\"+multipartFile.getOriginalFilename()));
            return "/images/"+multipartFile.getOriginalFilename();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}