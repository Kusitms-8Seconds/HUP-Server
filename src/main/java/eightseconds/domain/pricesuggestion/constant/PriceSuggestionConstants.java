package eightseconds.domain.pricesuggestion.constant;

import lombok.Getter;

public class PriceSuggestionConstants {

    @Getter
    public enum EPriceSuggestionApiController{
        eLocationIdPath("/{id}"),
        eGetMethod("get"),
        eDeleteMethod("delete"),
        eUpdateMethod("update"),
        ePageableZero(0);
        private String value;
        private int page;

        EPriceSuggestionApiController(String value) {this.value = value;}
        EPriceSuggestionApiController(int page) {this.page = page;}
    }
}
