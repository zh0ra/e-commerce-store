package com.coderain.ecommercestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderain.ecommercestore.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
