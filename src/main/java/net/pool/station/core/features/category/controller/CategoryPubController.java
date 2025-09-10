package net.pool.station.core.features.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.enums.ELogType;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.category.CategoryUseCase;
import net.pool.station.core.features.category.controller.models.CategoryResponse;
import net.pool.station.core.features.category.controller.models.CategoryResponseMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryPubController implements CategoryPubApi {
    CategoryUseCase useCase;

    CategoryResponseMapper responseMapper;

    @Override
    public MyListResponse<CategoryResponse> findAccountStatusCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAll(search, EAccountStatus.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findLogTypeCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAll(search, ELogType.class));

        return MyListResponse.success(responses);
    }
}
