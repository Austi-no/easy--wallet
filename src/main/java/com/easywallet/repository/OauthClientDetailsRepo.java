package com.easywallet.repository;


import com.easywallet.model.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepo extends JpaRepository<OauthClientDetails, Long> {
}
