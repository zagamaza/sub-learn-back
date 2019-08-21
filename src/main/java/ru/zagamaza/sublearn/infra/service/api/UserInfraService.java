package ru.zagamaza.sublearn.infra.service.api;

import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.UserDto;

import java.util.List;

public interface UserInfraService {

    UserDto get(Long id);

    List<UserDto> getAll(Pageable pageable);

    UserDto save(UserDto dto);

    void removeById(long id);

}
