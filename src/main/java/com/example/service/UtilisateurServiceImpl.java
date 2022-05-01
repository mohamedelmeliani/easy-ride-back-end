package com.example.service;

import com.example.dao.RoleRepo;
import com.example.dao.UtilisateurRepo;
import com.example.entities.Role;
import com.example.entities.Utilisateur;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurRepo utilisateurRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder encoder;
    @Override
    public Utilisateur addNewUser(Utilisateur user) {
        try{
            user.setPassword(encoder.encode(user.getPassword()));
            return utilisateurRepo.save(user);
        }
        catch (ConstraintViolationException c){
            throw new RuntimeException("Email deja utilisé");
        }
    }

    @Override
    public Role addNewRole(Role role) {
        return  roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Utilisateur user=utilisateurRepo.findByEmail(email);
        Role role=roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Utilisateur loadUserByUsername(String email) {
         return  utilisateurRepo.findByEmail(email);
    }

    @Override
    public List<Utilisateur> listUsers() {
        return utilisateurRepo.findAll();
    }

    @Override
    public boolean checkPassword(String email, String password) {
        Utilisateur user=loadUserByUsername(email);
        return encoder.matches(password, user.getPassword());
    }
}
