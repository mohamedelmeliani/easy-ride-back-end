package com.example.service;

import com.example.entities.Role;
import com.example.entities.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur addNewUser(Utilisateur user);
    Role addNewRole(Role role);
    void addRoleToUser(String email,String roleName);
    Utilisateur loadUserByUsername(String email);
    List<Utilisateur> listUsers();
    boolean checkPassword(String email,String password);
}
