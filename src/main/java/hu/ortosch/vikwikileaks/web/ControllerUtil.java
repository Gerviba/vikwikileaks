package hu.ortosch.vikwikileaks.web;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import hu.ortosch.vikwikileaks.model.UserEntity;


public class ControllerUtil {

    public ControllerUtil() {}

    @Value("${resources.external:storage/resources/}")
    private String uploadPath = "/etc/vikwikileaks/resources/";

    public String uploadFile(String target, MultipartFile logo) {
        if (logo.isEmpty() || logo.getContentType() == null)
            return null;

        String path = !uploadPath.startsWith("/") ? System.getProperty("user.dir") + "/" + uploadPath : uploadPath;
        File dir = new File(path, target);
        dir.mkdirs();

        String fileName = new UUID(System.currentTimeMillis(), new Random().nextLong()).toString()
                + logo.getOriginalFilename().substring(logo.getOriginalFilename().contains(".")
                        ? logo.getOriginalFilename().lastIndexOf('.')
                        : 0);
        path += (path.endsWith("/") ? "" : "/") + target + "/" + fileName;

        try {
            logo.transferTo(new File(path));
        } catch (IOException e) {
            return null;
        }

        return fileName;
    }

    public UserEntity getUser(HttpServletRequest request) {
        return (UserEntity) request.getSession().getAttribute("user");
    }

    public String sha1(String in) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        return String.format("%040x", new BigInteger(1, digest.digest(in.getBytes(StandardCharsets.UTF_8))));
    }

    public boolean isValidFileName(String filename) {
        filename = filename.toLowerCase();
        return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".pdf");
    }

    public String getExtention(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1).toUpperCase();
    }
    
}
