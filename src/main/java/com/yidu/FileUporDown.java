package com.yidu;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author:lishaolong
 * @dateTime: 2020/12/31 10:28
 * @version:1.0
 */
@Controller
public class FileUporDown {

    @RequestMapping("fileUpload")
    public String  fileUpload(String userName, MultipartFile file, HttpServletRequest request){
        //1.创建文件夹
        String realPath = request.getServletContext().getRealPath("/myFile");
        System.out.println("realPath"+realPath);
        File filePath=new File(realPath);
        if (!filePath.exists()){
            filePath.mkdir();
            System.out.println("创建成功!");
        }
        //2.得到文件的名字
        String filename = file.getOriginalFilename();
        System.out.println("filename："+filename);
        //3.根据地址与文件名创建空的文件
        File copyFile=new File(filePath.getAbsolutePath()+"/"+filename);
        //4.将用户传过来的文件复制到指定的文件夹
        try {
            file.transferTo(copyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.响应用户信息
        return "test.jsp";
    }

    @RequestMapping("download")
    public ResponseEntity<byte[]> downloadFile(String fileName,HttpServletRequest request) throws IOException {
        //1.找到地址
        String realPath = request.getServletContext().getRealPath("/myFile");
        //2.根据地址与文件名找到文件
        File file=new File(realPath+"/"+fileName);
        //3.创建头部对象
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment",fileName);
        //4.将文件转成字节数组对象
        byte[] fileByte= FileUtils.readFileToByteArray(file);
        //5.将字节数组对象设置响应对象中
        ResponseEntity<byte[]> responseEntity=new ResponseEntity<byte[]>(fileByte,headers, HttpStatus.OK);
        //6.响应结果
        return  responseEntity;
    }


}