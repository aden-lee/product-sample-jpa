package com.example.productsample.service.impl;

import com.example.productsample.common.exception.CustomException;
import com.example.productsample.common.response.ErrorCode;
import com.example.productsample.domain.Option;
import com.example.productsample.domain.Product;
import com.example.productsample.domain.ProductOption;
import com.example.productsample.dto.ProductOptionDto;
import com.example.productsample.dto.ProductRequest;
import com.example.productsample.dto.ProductResponse;
import com.example.productsample.repository.ProductOptionRepository;
import com.example.productsample.repository.ProductRepository;
import com.example.productsample.service.ProductService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import jakarta.persistence.criteria.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductOptionRepository productOptionRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Override
    public Object getProduct(long pid) {
        return productRepository.findById(pid);
    }

    @Override
    public Object getAllProduct(String proName, String startCreated, String endCreated, Integer qty, List<Integer> price, int page, int size) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(searchWith(proName, startCreated, endCreated), pageRequest);
        // return productRepository.findAllWithProductOptionUsingFetchJoin(pageRequest);
    }

    public static Specification<Product> searchWith(final String proName, final String startDate, final String endDate) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(proName)) {
                predicates.add(builder.like(root.get("proName"), "%" + proName + "%"));
            }
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
                builder.between(root.get("created"), startDate, endDate);
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Override
    @Transactional
    public Object saveProduct(ProductRequest request) throws Exception {
        Product product = new Product();
        List<ProductOption> productOptions = new ArrayList<>();
        setProductInfo(product, request);
        product.setCid(request.getCid());
        Product productResult = productRepository.save(product);

        setProductOptionInfo(productResult.getPid(), productOptions, request);
        productOptionRepository.saveAll(productOptions);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPid(productResult.getPid());
        return productResponse;
    }


    private void setProductInfo(Product product, ProductRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getProName())) {
            throw new CustomException(ErrorCode.INVALID_PARAM, "Product name");
        }

        if (StringUtils.isEmpty(request.getProDesc())) {
            throw new CustomException(ErrorCode.INVALID_PARAM, "Product description");
        }

        Optional<Product> productResult = productRepository.findByProName(request.getProName());
        if(productResult.isEmpty()) {
            product.setProName(request.getProName());
            product.setProDesc(request.getProDesc());
        } else {
            throw new CustomException(ErrorCode.PRODUCT_NAME_DUP);
        }
    }

    private void setProductOptionInfo(long pid, List<ProductOption> productOptions, ProductRequest request) {
        request.getOpt().forEach(opt -> {
            validationOption(opt);
            ProductOption productOption = new ProductOption();
            productOption.setPid(pid);
            productOption.setSize(opt.getSize());
            productOption.setQty(opt.getQty());
            productOption.setPrice(opt.getPrice());
            productOptions.add(productOption);
        });
    }

    private void validationOption(ProductOptionDto productOptionDto) {
        if (StringUtils.isEmpty(productOptionDto.getSize())) {
            throw new CustomException(ErrorCode.PRODUCT_INVALID_SIZE);
        }

        if (productOptionDto.getQty() < 1) {
            throw new CustomException(ErrorCode.PRODUCT_INVALID_QTY);
        }

        if (productOptionDto.getPrice() < 100) {
            throw new CustomException(ErrorCode.PRODUCT_INVALID_PRICE);
        }
    }
}
