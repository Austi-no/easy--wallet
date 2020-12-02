package com.easywallet.repository;

import com.easywallet.model.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {

    @Query(value = "SELECT c FROM RoleUser c where c.userId.id=?1 AND c.roleId.id=?2",nativeQuery = true)
    Optional<RoleUser> findByUserIdAndRoleId(Long userId, Long roleId);


}
