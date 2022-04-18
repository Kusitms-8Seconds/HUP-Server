package eightseconds.infra.file.util;

import eightseconds.infra.file.exception.FileExtensionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum ImageExtension {
    JPG("jpg"), JPEG("jpeg"), PNG("png"), HEIC("heic");

    private final String value;

    public static void validateImageExtension(String extension) {
        try {
            ImageExtension.valueOf(extension.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new FileExtensionException();
        }
    }
}
