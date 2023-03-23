package com.bookstore.cart.feign.catalog;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "catalog-service", url = "http://localhost:8000/")
public interface CatalogFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products/{productCode}")
    Response getProduct(@PathVariable("productCode") String productCode);
}
