package eightseconds.domain.category.controller;

import eightseconds.domain.category.dto.InterestCategoryRequest;
import eightseconds.domain.category.entity.UserCategory;
import eightseconds.domain.category.service.CategoryService;
import eightseconds.domain.chatmessage.dto.ChatMessageResponse;
import eightseconds.domain.chatmessage.service.ChatMessageService;
import eightseconds.domain.chatroom.dto.ChatRoomResponse;
import eightseconds.domain.chatroom.dto.CheckEntryRequest;
import eightseconds.domain.chatroom.dto.CheckEntryResponse;
import eightseconds.domain.chatroom.dto.DeleteChatRoomRequest;
import eightseconds.domain.chatroom.service.ChatRoomService;
import eightseconds.global.dto.DefaultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
@Api(tags = "Category API")
public class CategoryApiController {

    private final CategoryService categoryService;

    @ApiOperation(value = "유저의 관심 카테고리 변경", notes = "유저의 관심 카테고리를 변경합니다.")
    @PutMapping
    public ResponseEntity<DefaultResponse> putUserCategories(@Valid @RequestBody InterestCategoryRequest interestCategoryRequest) {
        return ResponseEntity.ok(categoryService.createUserCategories(interestCategoryRequest));
    }

}
