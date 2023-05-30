package com.example.productsample.jpa;

import com.example.productsample.domain.Product;
import com.example.productsample.domain.ProductOption;
import com.example.productsample.repository.ProductOptionRepository;
import com.example.productsample.repository.ProductRepository;
import com.example.productsample.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductJpaTest {

    @MockBean
    ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Test
    public void saveTest() {
        Product productEntity = Product.builder()
                .proName("TestProductSave")
                .proDesc("TestDescription")
                .build();

        Product savedGoodsEntity = productRepository.save(productEntity);
        Assertions.assertEquals("TestProductSave", savedGoodsEntity.getProName());
        Assertions.assertEquals("TestDescription", savedGoodsEntity.getProDesc());
    }


    @Test
    public void findByProductNameTest() {
        Product productEntity = Product.builder()
                .proName("TestProductFind")
                .proDesc("TestDescription")
                .build();

        productRepository.save(productEntity);
        Optional<Product> findResult = productRepository.findByProName("TestProductFind");

        Assertions.assertEquals("TestProductFind", findResult.get().getProName());
    }


    @Test
    public void searchAll() {
        Product productEntity = Product.builder()
                .proName("TestProductFind")
                .proDesc("TestDescription")
                .build();

        productRepository.save(productEntity);

        ProductOption productOptionEntity = ProductOption.builder()
                        .pid(productEntity.getPid()).size("L").price(1000).qty(100).build();
        List<ProductOption> productOptions = new ArrayList<>();
        productOptions.add(productOptionEntity);
        productOptionRepository.saveAll(productOptions);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findAll(ProductServiceImpl.searchWith("TestProductFind", null, null, 100, Arrays.asList(900,1100)), pageRequest);

        Assertions.assertEquals(1, products.getTotalPages());
        Assertions.assertEquals(1, products.getContent().size());
    }


}

