package net.pool.station.core.features.category.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EAccountStatus;
import net.pool.station.core.bootstrap.enums.EGameGenre;
import net.pool.station.core.bootstrap.enums.EGameStatus;
import net.pool.station.core.bootstrap.enums.EIntervalType;
import net.pool.station.core.bootstrap.enums.ELogType;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
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
    public MyListResponse<CategoryResponse> findAccountLogTypeCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAllType(search, "ACCOUNT", ELogType.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findLoginLogTypeCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAllType(search, "LOGIN", ELogType.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findGameGenreCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAll(search, EGameGenre.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findGameStatusCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAll(search, EGameStatus.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findResourceTypeCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAllType(search, EResourceType.class));

        return MyListResponse.success(responses);
    }

    @Override
    public MyListResponse<CategoryResponse> findIntervalTypeCategory(String search) {
        List<CategoryResponse> responses = responseMapper
                .toModel(useCase.findAll(search, EIntervalType.class));

        return MyListResponse.success(responses);
    }
}
