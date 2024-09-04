package com.sparta.newsfeedproject.controller;


import com.sparta.newsfeedproject.annotation.Auth;
import com.sparta.newsfeedproject.config.JwtConfig;
import com.sparta.newsfeedproject.domain.User;
import com.sparta.newsfeedproject.dto.UserWithdrawalRequestDto;
import com.sparta.newsfeedproject.dto.request.UserLoginRequestDto;
import com.sparta.newsfeedproject.dto.request.UserUpdateRequestDto;
import com.sparta.newsfeedproject.dto.response.CommonResponseDto;
import com.sparta.newsfeedproject.dto.response.UserResponseDto;
import com.sparta.newsfeedproject.service.JwtService;
import com.sparta.newsfeedproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "userLoginController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;


    @PatchMapping("/profile/update")
    public ResponseEntity<CommonResponseDto<UserResponseDto>> updateProfile(@Auth User tokenUser, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

        UserResponseDto userResponseDto = userService.updateUser(tokenUser, userUpdateRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), userResponseDto), HttpStatus.OK);

    }

    @PostMapping("/user/login")
    public ResponseEntity<CommonResponseDto<UserResponseDto>> userLogin(HttpServletResponse res, @Valid @RequestBody UserLoginRequestDto userLoginRequestDto){

        UserResponseDto userResponseDto = userService.login(userLoginRequestDto);

        String token = jwtService.createToken(userResponseDto.getId(), userResponseDto.getEmail());

        return ResponseEntity.ok()
                .header(JwtConfig.AUTHORIZATION_HEADER, token)
                .body(new CommonResponseDto<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), userResponseDto));

    }

    @PostMapping("/user/withdrawal")
    public ResponseEntity<CommonResponseDto<Void>> userWithdrawal(@Auth User tokenUser, @Valid @RequestBody UserWithdrawalRequestDto userWithdrawalRequestDto){

        userService.userWithdrawal(tokenUser, userWithdrawalRequestDto);

        return new ResponseEntity<>(new CommonResponseDto<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null), HttpStatus.OK);
    }


}
