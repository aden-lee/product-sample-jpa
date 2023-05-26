package com.example.productsample.repository;

import com.example.productsample.domain.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProName(String pName);

//    @Query("SELECT distinct p FROM product p join fetch p.productOptions")
//    public List<Product> findAllWithProductOptionUsingFetchJoin();
}
