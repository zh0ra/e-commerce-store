package com.coderain.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderain.ecommercestore.domain.Authority;
import com.coderain.ecommercestore.domain.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(AuthorityName authName);
}
