package net.pool.station.core.features.role.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.role.Role;
import net.pool.station.core.domain.role.RoleUseCase;
import net.pool.station.core.features.role.controller.models.RoleResponse;
import net.pool.station.core.features.role.controller.models.RoleResponseMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RolesController implements RolesApi {
    RoleUseCase roleUseCase;

    RoleResponseMapper responseMapper;

    @Override
    public MyListResponse<RoleResponse> findAll(String search) {
        List<Role> roles = roleUseCase.findAll(MyObjectUtils.defaultValue(search, new TypeReference<>() {}));

        return MyListResponse.success(responseMapper.toModel(roles));
    }
}
