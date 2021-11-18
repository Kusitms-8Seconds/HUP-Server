package eightseconds.global.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaginationDto<T> {

    private int totalPage;
    private int currentPage;
    private Long totalElements;
    private int currentElements;
    private boolean hasPrevious;
    private boolean hasNext;
    private boolean isLast;
    private T data;

    public static <T, U> PaginationDto<T> of(Page<U> page, T data) {
        return PaginationDto.<T>builder()
                .totalPage(page.getTotalPages())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .currentElements(page.getNumberOfElements())
                .hasPrevious(page.hasPrevious())
                .hasNext(page.hasNext())
                .isLast(page.isLast())
                .data(data)
                .build();
    }

}