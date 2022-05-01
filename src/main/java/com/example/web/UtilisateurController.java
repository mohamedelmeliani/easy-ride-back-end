package com.example.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.dao.UtilisateurRepo;
import com.example.entities.Role;
import com.example.entities.Utilisateur;
import com.example.service.UtilisateurService;
import com.example.utilitis.JWTUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UtilisateurController {
    private UtilisateurService service;
    private UtilisateurRepo utilisateurRepo;

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authToken= request.getHeader(JWTUtilities.AUTH_HEADER);
        if(authToken!=null&& authToken.startsWith(JWTUtilities.PREFIX)){
            try{
                String jwt=authToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256(JWTUtilities.SECRET);
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                jwtVerifier.verify(jwt);
                DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                String username=decodedJWT.getSubject();
                Utilisateur utilisateur=service.loadUserByUsername(username);
                String access_token= JWT.create()
                        .withSubject(utilisateur.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtilities.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",utilisateur.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens=new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",jwt);
                response.setContentType("application/json");
                System.out.println("success");
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch(Exception e){
                response.setHeader("Error_Message",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }else
            throw new RuntimeException("Error");
    }

    @GetMapping(path = "/utilitsateurs")
    public List<Utilisateur> getAllUsers(){
        return service.listUsers();
    }

    @PostMapping(path="/confirmUser")
    public boolean confirmUser(Principal user,@RequestBody String token){
        if (service.loadUserByUsername(user.getName()).getToken().equals(token)){
            service.loadUserByUsername(user.getName()).setEnabled(true);
        }
        return service.loadUserByUsername(user.getName()).getToken().equals(token);
    }

    @GetMapping(path = "/utilisateur/{email}")
    public Utilisateur getUserByEmail(@PathVariable String email){
        return service.loadUserByUsername(email);
    }

    @GetMapping(path = "/profile")
    public Utilisateur getLogedUser(Principal user){
        return service.loadUserByUsername(user.getName());
    }

    @PostMapping(path = "/utilisateur")
    public Utilisateur addUserWithoutImage(@RequestBody Utilisateur u) {
        return service.addNewUser(u);
    }

    @PostMapping(path = "/role")
    public Role addRole(@RequestBody Role role) {
        return service.addNewRole(role);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm form) {
        service.addRoleToUser(form.getEmail(),form.getRole());
    }

    @PostMapping(path = "/passwordForgotten")
    public ResponseEntity<?> changePassword(Principal user, @RequestBody PasswordChangeForm passwordChangeForm){
        Utilisateur utils=service.loadUserByUsername(user.getName());
        System.out.println(user.getName());
        if(service.checkPassword(user.getName(),passwordChangeForm.getOldPassword())){
            utils.setPassword(passwordChangeForm.getNewPassword());
            service.addNewUser(utils);
            return ResponseEntity.status(HttpStatus.OK).body("Mot de passe chnagé avec succès");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Action non autorisé");
    }

    @PutMapping(path = "/utilisateur/{id}")
    public Utilisateur modifyUtilisateur(@PathVariable Long id,@RequestBody Utilisateur u){
        u.setId(id);
        return utilisateurRepo.save(u);
    }

    @DeleteMapping(path = "/utilisateur/{id}")
    public void deleteUser(@PathVariable Long id){
        utilisateurRepo.deleteById(id);
    }

/*    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }*/

}
@Data
class RoleUserForm{
    private String role;
    private String email;
}

@Data
class PasswordChangeForm{
    private String oldPassword;
    private String newPassword;
}