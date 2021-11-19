package eightseconds.domain.file.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import eightseconds.domain.file.entity.MyFile;
import eightseconds.domain.item.dto.RegisterItemResponse;
import eightseconds.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {

    private byte[] imageByte;

    public static ImageResponse from(byte[] imageByte) {
        return ImageResponse.builder()
                .imageByte(imageByte)
                .build();
    }
}
