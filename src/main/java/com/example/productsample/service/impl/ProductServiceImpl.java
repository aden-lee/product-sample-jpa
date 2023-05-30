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
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import jakarta.persistence.criteria.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductOptionRepository productOptionRepository;

    // repository 주입
    public ProductServiceImpl(ProductRepository productRepository, ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Override
    public Object getProduct(long pid) {
        return productRepository.findById(pid);
    }

    //제품 검색조건으로 검색
    @Override
    public Object getAllProduct(String proName, String startCreated, String endCreated, Integer qty, List<Integer> priceList, int page, int size) throws Exception {
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        if (priceList.size() > 2) {
            throw new CustomException(ErrorCode.INVALID_PARAM, "가격");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(searchWith(proName, startCreated, endCreated, qty, priceList), pageRequest);
    }

    public static Specification<Product> searchWith(final String proName, final String startDate, final String endDate, Integer qty, List<Integer> priceList) {
        return ((root, query, builder) -> {
            Join<Product, Option> productOption = root.join("options");

            List<Predicate> predicates = new ArrayList<>();
            // 검색조건으로 제품이름이 있는경우 like검색 조건추가
            if (StringUtils.isNotEmpty(proName)) {
                predicates.add(builder.like(root.get("proName"), "%" + proName + "%"));
            }
            // 생성일자 검색하기위한 조건 ex) 시작일자 ~ 종료일자
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
                predicates.add(builder.between(root.get("created"), startDate, endDate));
            }
            // 가격대 검색 ex) 10000 ~ 40000
            if(Objects.nonNull(priceList) && priceList.get(0) > 0 && priceList.get(1) > 0) {
                predicates.add(builder.between(productOption.get("price"), priceList.get(0), priceList.get(1)));
            }
            // 재고수량이 몇개 이상인것 검색
            if(Objects.nonNull(qty) && qty > 0) {
                predicates.add(builder.ge(productOption.get("qty"), qty));
            }
            query.groupBy(root.get("pid")); // pid로 group by
            return builder.or(predicates.toArray(new Predicate[0])); // where 절 build
        });
    }

    @Override
    @Transactional
    public Object saveProduct(ProductRequest request) throws Exception {
        Product product = new Product();
        List<ProductOption> productOptions = new ArrayList<>();
        setProductInfo(product, request);
        product.setCid(request.getCid());
        Product productResult = productRepository.save(product); // 제품테이블에 저장

        setProductOptionInfo(productResult.getPid(), productOptions, request);
        productOptionRepository.saveAll(productOptions); // 제품 옵션 저장
        ProductResponse productResponse = new ProductResponse();
        productResponse.setPid(productResult.getPid());
        return productResponse;
    }


    private void setProductInfo(Product product, ProductRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getProName())) { // 제품이름이 비어있는경우 에러리턴
            throw new CustomException(ErrorCode.INVALID_PARAM, "Product name");
        }

        if (StringUtils.isEmpty(request.getProDesc())) { // 제품 설명이 없는 경우 에러리턴
            throw new CustomException(ErrorCode.INVALID_PARAM, "Product description");
        }

        Optional<Product> productResult = productRepository.findByProName(request.getProName()); // 제품이름으로 검색
        if(productResult.isEmpty()) {
            product.setProName(request.getProName());
            product.setProDesc(request.getProDesc());
        } else { // 제품명이 중복인경우 에러리턴
            throw new CustomException(ErrorCode.PRODUCT_NAME_DUP);
        }
    }

    private void setProductOptionInfo(long pid, List<ProductOption> productOptions, ProductRequest request) {
        request.getOpt().forEach(opt -> {
            validationOption(opt); // 제품 옵션 validation check
            ProductOption productOption = new ProductOption();
            productOption.setPid(pid);
            productOption.setSize(opt.getSize());
            productOption.setQty(opt.getQty());
            productOption.setPrice(opt.getPrice());
            productOptions.add(productOption);
        });
    }

    private void validationOption(ProductOptionDto productOptionDto) {
        if (StringUtils.isEmpty(productOptionDto.getSize())) { // 사이즈가 비어있는경우
            throw new CustomException(ErrorCode.PRODUCT_INVALID_SIZE);
        }

        if (productOptionDto.getQty() < 1) { // 재고가 1개보다 적은경우
            throw new CustomException(ErrorCode.PRODUCT_INVALID_QTY);
        }

        if (productOptionDto.getPrice() < 100) { // 가격이 100원 이하인경우
            throw new CustomException(ErrorCode.PRODUCT_INVALID_PRICE);
        }
    }
}
