package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.entity.Lang;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRequest {

    private Long id;

    @NotNull
    private Lang lang;

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    private String url;

    private boolean isShared;

    private LocalDateTime created;

    private boolean isSerial;

}
