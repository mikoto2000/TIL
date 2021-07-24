package dev.mikoto2000.study.springboot.file.firststep;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUploadController
 */
@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public FileUploadResult upload(
            @PathVariable List<MultipartFile> files
            ) {
        System.out.println(files);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return new FileUploadResult(200);
    }

    public class FileUploadResult {
        private int status;
        public FileUploadResult(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
    }
}
