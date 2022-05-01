package com.example.web;

import com.example.entities.Attachment;
import com.example.entities.Utilisateur;
import com.example.service.AttachmentService;
import com.example.service.UtilisateurService;
import com.example.utilitis.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class AttachmentController {

    private AttachmentService attachmentService;
    private UtilisateurService service;


    @RequestMapping(path="/upload",method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseData uploadFile(Principal user, @RequestParam("file") MultipartFile file) throws Exception {
        Utilisateur utilisateur=service.loadUserByUsername(user.getName());
        Attachment attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        utilisateur.setPhotoProfile(attachment);
        service.addNewUser(utilisateur);
        return new ResponseData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}