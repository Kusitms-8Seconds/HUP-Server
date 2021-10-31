package kusitms.global.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagination<T> {

    @SerializedName("currentCount")
    public int currentCount;
    @SerializedName("page")
    public int page;
    @SerializedName("perPage")
    public int perPage;
    @SerializedName("totalCount")
    public int totalCount;
    @SerializedName("data")
    private T data;
}