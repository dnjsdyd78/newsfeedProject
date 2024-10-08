package com.sparta.newsfeedproject.domain;

import com.sparta.newsfeedproject.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted_at = SYSDATE() WHERE id = ?")
@Where(clause = "deleted_at is null")
public class User extends BaseTimestampEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "user_name")
    private String userName;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private LocalDate birth;

    @Column(nullable = false, name = "gender", length = 6)
    @Enumerated(value = EnumType.STRING)
    private UserGenderEnum gender;


    private LocalDateTime deletedAt;


    // token 용도
    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public void userUpdate(UserUpdateRequestDto userUpdateRequestDto) {
        this.email = (userUpdateRequestDto.getEmail() != null && !userUpdateRequestDto.getEmail().trim().isEmpty()) ? userUpdateRequestDto.getEmail() : this.email;
        this.userName = (userUpdateRequestDto.getUserName() != null && !userUpdateRequestDto.getUserName().trim().isEmpty()) ? userUpdateRequestDto.getUserName() : this.userName;
        this.phoneNumber = (userUpdateRequestDto.getPhoneNumber() != null && !userUpdateRequestDto.getPhoneNumber().trim().isEmpty()) ? userUpdateRequestDto.getPhoneNumber() : this.phoneNumber;
        this.birth = (userUpdateRequestDto.getBirth() != null) ? userUpdateRequestDto.getBirth() : this.birth;
        this.gender = (userUpdateRequestDto.getGender() != null) ? userUpdateRequestDto.getGender() : this.gender;
    }

}
