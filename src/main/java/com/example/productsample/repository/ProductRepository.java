package com.example.productsample.repository;

import com.example.productsample.domain.Product;
import com.example.productsample.domain.ProductOption;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProName(String pName);

//    @Query("SELECT distinct p FROM Product p join fetch p.productOption")
//    public List<Product> findAllWithProductOptionUsingFetchJoin(Pageable pageable);

//    @Query("SELECT distinct p FROM Product p inner join ProductOption o on p.pid= o.pid where p.proName=:proName or p.created between :startCreated and :endCreated or o.qty between :price1 and :price2")
//    List<Product> findAllByProNameOrCreatedBetween(String proName, String startCreated, String endCreated, int price1, int price2, Pageable pageable);

//    @Query("SELECT distinct p FROM Product p join fetch p.productOptions")
//    List<Product> findAllByProNameOrCreatedBetween(String proName, String startCreated, String endCreated, Pageable pageable);

    List<Product> findAllByProNameOrCreatedBetween(String proName, String startCreated, String endCreated, Pageable pageable);
}
