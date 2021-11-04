package eightseconds.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagination<T> {

    public int currentCount;
    public int page;
    public int perPage;
    public int totalCount;
    private T data;
}