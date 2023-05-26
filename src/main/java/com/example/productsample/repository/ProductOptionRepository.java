package com.example.productsample.repository;

import com.example.productsample.domain.Product;
import com.example.productsample.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    @Query("select g from ProductOption g join fetch g.product")
    public List<Product> findAllWithProductUsingFetchJoin();
}
