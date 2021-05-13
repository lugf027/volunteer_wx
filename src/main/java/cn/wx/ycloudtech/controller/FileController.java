package cn.wx.ycloudtech.controller;

import cn.wx.ycloudtech.config.UploadConfig;
//import cn.wx.ycloudtech.domain.File;
import cn.wx.ycloudtech.service.ActivityService;
import cn.wx.ycloudtech.util.FileUtil;
import cn.wx.ycloudtech.util.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user/file")
@Component("FileController")
public class FileController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UploadConfig uploadConfig;

    @PostMapping("/upload")
    public HashMap<String, String> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) {
        HashMap<String, String> response = new HashMap<>();
        if (files != null && files.length >= 1) {
            try {
                for (MultipartFile file : files) {
                    // String fileName = file.getOriginalFilename();
                    // 文件后缀与文件名
                    String type = FileUtil.getFileTypePostFix(Objects.requireNonNull(file.getOriginalFilename()));
                    String fileName = UUID.randomUUID().toString().substring(0, 10) + type;
                    // 按用户ID，分类建文件夹
                    String userId = request.getParameter("userId");
                    String userIdAndDateToday = userId + "/" + MyConstants.DATE_FORMAT.format(new Date()) + "/";
                    String filePath = uploadConfig.getUploadPath() + userIdAndDateToday;
                    FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                    // 数据库相关
//                    String linkId = request.getParameter("linkId");
//                    String linkType = request.getParameter("linkType");
                    cn.wx.ycloudtech.domain.File fileDomain = new cn.wx.ycloudtech.domain.File();
                    fileDomain.setFileId(UUID.randomUUID().toString());
                    fileDomain.setFileTime(MyConstants.DATETIME_FORMAT.format(new Date()));
                    fileDomain.setFileAddr(uploadConfig.getStaticPath()+"/"+userIdAndDateToday+fileName);
                    fileDomain.setLinkId(request.getParameter("linkId"));
                    fileDomain.setLinkType(request.getParameter("linkType"));
                    activityService.addFileNew(fileDomain);

                    response.put("code", MyConstants.APPLY_SUCCESS.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.put("state", MyConstants.APPLY_FAIL.toString());
            }
        }
        return response;
    }
}
