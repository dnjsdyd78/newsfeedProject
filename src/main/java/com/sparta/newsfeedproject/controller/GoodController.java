package com.sparta.newsfeedproject.controller;

import com.sparta.newsfeedproject.annotation.Auth;
import com.sparta.newsfeedproject.domain.Good;
import com.sparta.newsfeedproject.dto.request.UserTokenDto;
import com.sparta.newsfeedproject.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @PostMapping("/good")
    public ResponseEntity<String> goodfeed(@Auth UserTokenDto userTokenDto, @RequestParam Long feedId) {
        //사용자 ID, 피드ID로 좋아요 추가
        goodService.goodfeed(userTokenDto, feedId);
        return ResponseEntity.ok("좋아요 완료");
    }

    @PostMapping("/ungood")
    public ResponseEntity<String> ungoodfeed(@Auth UserTokenDto userTokenDto, @RequestParam Long feedId) {
        goodService.ungoodfeed(userTokenDto, feedId);//사용자 ID와 게시글 ID를 통해 좋아요 제거
        return ResponseEntity.ok("좋아요 취소 완료");
    }
}
