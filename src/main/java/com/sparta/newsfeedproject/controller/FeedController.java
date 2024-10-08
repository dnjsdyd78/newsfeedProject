package com.sparta.newsfeedproject.controller;

import com.sparta.newsfeedproject.annotation.Auth;
import com.sparta.newsfeedproject.domain.User;
import com.sparta.newsfeedproject.dto.request.UserTokenDto;
import com.sparta.newsfeedproject.dto.response.CommonResponseDto;
import com.sparta.newsfeedproject.dto.request.FeedRequestDto;
import com.sparta.newsfeedproject.dto.response.FeedResponseDto;
import com.sparta.newsfeedproject.service.FeedService;
import com.sparta.newsfeedproject.dto.request.FeedSaveRequestDto;
import com.sparta.newsfeedproject.dto.response.FeedDetailResponseDto;
import com.sparta.newsfeedproject.dto.response.FeedSaveResponseDto;
import com.sparta.newsfeedproject.dto.response.FeedSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    //feed 작성
    @PostMapping("/save")
    public ResponseEntity<FeedSaveResponseDto> saveFeed(@Auth UserTokenDto tokenUser, @RequestBody FeedSaveRequestDto feedSaveRequestDto) {
        FeedSaveResponseDto response = feedService.saveFeed(tokenUser, feedSaveRequestDto);
        return ResponseEntity.ok(response);
    }

    //특정 유저의 feed 목록 조회
    @GetMapping
    public ResponseEntity<Page<FeedSimpleResponseDto>> getFeeds(
            @Auth UserTokenDto tokenUser,
            //@PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page) {
        Page<FeedSimpleResponseDto> feeds = feedService.getFeeds(tokenUser, page);
        return ResponseEntity.ok(feeds);
    }

    //개별 피드 조회
    @GetMapping("/{feedId}")
    public ResponseEntity<FeedDetailResponseDto> getFeedDetail(@PathVariable Long feedId) {
        FeedDetailResponseDto feed = feedService.getFeedDetail(feedId);
        return ResponseEntity.ok(feed);
    }

    //팔로우한 사람들의 뉴스피드 조회
    @GetMapping("/followed")
    public ResponseEntity<Page<FeedSimpleResponseDto>> getFollowFeeds(
            @Auth UserTokenDto tokenUser,
            //@PathVariable Long userId,
            @RequestParam(defaultValue = "0")int page){
        Page<FeedSimpleResponseDto> feeds = feedService.getFollowFeeds(tokenUser, page);
        return ResponseEntity.ok(feeds);
    }

    //게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponseDto> updateFeed(@PathVariable Long id, @Auth UserTokenDto user, @RequestBody FeedRequestDto requestDto) throws AccessDeniedException{
        FeedResponseDto responseDto = feedService.updateFeed(id, user, requestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(200, "success", responseDto), HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponseDto> deleteFeed(@PathVariable Long id, @Auth UserTokenDto user) throws AccessDeniedException{
        feedService.deleteFeed(id, user);

        return new ResponseEntity<>(new CommonResponseDto<>(200, "success", null), HttpStatus.OK);

    }

}
