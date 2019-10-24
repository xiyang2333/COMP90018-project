package com.unimelb.studypartner.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by xiyang on 2019/10/2
 */
@Service
public class PictureService {
    private static Logger logger = Logger.getLogger(PictureService.class);

    private static final String FILE_DIC = "./images";
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();

    PictureService(){
        File file = new File(FILE_DIC);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public String uploadPic(String pic) throws Exception{
        byte[] photo = decoder.decode(pic);
        String uuid = UUID.randomUUID().toString();
        String filePath = FILE_DIC + "/" + uuid;

        FileOutputStream out = new FileOutputStream(filePath);
        out.write(photo);
        out.flush();
        out.close();

        return filePath;
    }

    public String getPic(String filePath) throws Exception{
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Exception ex = new Exception("file not exists");
                throw ex;
            }
            int length = (int) file.length();
            byte[] data = new byte[length];
            new FileInputStream(file).read(data);

            return encoder.encodeToString(data);
        } catch (Exception ex){
            return null;
        }
    }
}
