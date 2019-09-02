package org.opens.fileupdload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller(value = "fileController")
@RequestMapping(value = "/file")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value = "/page")
    public String goPage() {
        return "fileTest";
    }

    private Map<String, Object> getResult(int flag, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", flag);
        res.put("message", message);
        return res;
    }

    @PostMapping(value = "/upload1")
    @ResponseBody
    public Map<String, Object> upload1(
            HttpServletRequest request
    ) {
        MultipartHttpServletRequest mhsr = null;
        if(request instanceof MultipartHttpServletRequest) {
            mhsr = (MultipartHttpServletRequest) request;
        } else {
            return getResult(300, "上传失败");
        }
        MultipartFile mf = mhsr.getFile("file");
        String fileName = null;
        if(mf != null) {
            fileName = mf.getOriginalFilename();
        }
        File file = null;
        if(fileName != null) {
            file = new File(fileName);
        } else {
            return getResult(301, "参数异常");
        }

        //mf.transferTo(file);

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
        logger.info("路径是: " + path);

        return getResult(200, "上传成功");
    }

}
