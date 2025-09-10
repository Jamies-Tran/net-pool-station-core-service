package net.pool.station.core.features.category.controller;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.features.category.controller.models.CategoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/category")
public interface CategoryPubApi {
    @GetMapping("/account-status")
    MyListResponse<CategoryResponse> findAccountStatusCategory(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search
    );

    @GetMapping("/log-type")
    MyListResponse<CategoryResponse> findLogTypeCategory(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search
    );
}
