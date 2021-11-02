package kusitms.global.dto;

import com.google.gson.annotations.SerializedName;
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