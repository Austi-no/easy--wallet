package com.easywallet.service;


import com.easywallet.model.*;
import com.easywallet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OauthServiceImpl implements OauthService {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    PermissionRepository permissionRepository;


    @Autowired
    RoleRepository roleRepository;


    @Autowired
    RoleUserRepository roleUserRepository;


    @Autowired
    OauthClientDetailsRepo oauthClientDetailsRepo;


    @Override
    public User saveUser(User user) {
        return userDetailRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDetailRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDetailRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDetailRepository.findAll();
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleUser saveRoleUser(RoleUser roleUser) {
        return roleUserRepository.save(roleUser);
    }

    @Override
    public void deleteRoleUser(RoleUser user) {
        roleUserRepository.delete(user);
    }

    @Override
    public Optional<RoleUser> findRoleUserById(Long userId, Long roleId) {
        return roleUserRepository.findByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public OauthClientDetails saveClientDetails(OauthClientDetails details) {
        return oauthClientDetailsRepo.save(details);
    }

    @Override
    public void saveAllPermission(List<Permission> list) {
        permissionRepository.saveAll(list);
    }

    @Override
    public void saveAllRoles(List<Role> list) {
        roleRepository.saveAll(list);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userDetailRepository.deleteUserById(id);
    }

    @Override
    public void deleteUserRole(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userDetailRepository.findUsersById(userId);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }
}
