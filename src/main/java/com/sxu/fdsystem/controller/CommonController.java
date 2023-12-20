package com.sxu.fdsystem.controller;

import com.sxu.fdsystem.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    //读取配置文件的自定义参数
    @Value("${image.path}")
    private String basePath;

    //文件上传
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要将该文件转存至目录中，否则本次请求执行过后file将删除
        log.info(file.toString());

        //原始文件名 获取后缀
        String originFileName = file.getOriginalFilename();

        //创建一个目录对象 如果没有目录则创建目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        try{
            //将临时文件转存到指定目录
            file.transferTo(new File(basePath + originFileName));
        }catch (Exception e){

        }

        return R.success(originFileName);
    }

    //文件下载
    @GetMapping("/download/{filename}")
    public void download(@PathVariable("filename") String name, HttpServletResponse response){

        try{
            //通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //通过输出流将内容写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            //设置响应的ContentType
            response.setContentType("image/jpeg");
            //读取文件
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭输出输入流
            outputStream.close();
            fileInputStream.close();

        }catch (Exception e){
            System.out.println("出现异常！" + e);
        }

    }
}
