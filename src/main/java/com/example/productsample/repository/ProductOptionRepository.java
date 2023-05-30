package com.example.productsample.repository;

import com.example.productsample.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
}
