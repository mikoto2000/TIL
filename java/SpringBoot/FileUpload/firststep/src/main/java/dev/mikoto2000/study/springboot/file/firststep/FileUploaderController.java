package dev.mikoto2000.study.springboot.file.firststep;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUploaderController
 */
@Controller
public class FileUploaderController {

    @GetMapping("/uploader")
    public String uploader() {
        return "uploader";
    }
}

