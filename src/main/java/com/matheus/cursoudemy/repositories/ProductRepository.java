package com.matheus.cursoudemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheus.cursoudemy.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
