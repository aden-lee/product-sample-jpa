package com.example.productsample.repository;

import com.example.productsample.domain.Option;
import com.example.productsample.domain.Product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByProName(String pName);

    List<Product> findAllByProNameOrCreatedBetween(Specification<Product> productSpecification, String startCreated, String endCreated, Pageable pageable);

    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);

    @Query("select p from Product p join fetch p.options o")
    List<Product> getProductWithOption(Specification<Product> productSpecification, Pageable pageable);
}
