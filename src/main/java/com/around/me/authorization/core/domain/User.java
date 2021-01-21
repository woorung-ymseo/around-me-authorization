package com.around.me.authorization.core.domain;


import com.around.me.authorization.core.enums.user.GenderEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "user")
@Entity
@Getter
public class User implements Serializable {

    @Id @GeneratedValue
    @Setter
    private long userNo;

    @JsonIgnore
    @Setter
    private String userPassword;

    @Setter
    private String userName;

    @Setter
    private String userEmail;

    @Setter
    private String userMobile;

    @Setter
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Setter
    private String birth;

    @Setter
    private String userSort;

    @Setter
    private LocalDate lastLoginDate;

    @Setter
    private String lastPasswordModDate;

    @Setter
    private int loginTryCount;

    @Setter
    private String userStatus;

    @Setter
    private String blackListYn;

    @Setter
    private LocalDateTime regDatetime;

    @Setter
    private LocalDateTime modDatetime;

    @Setter
    private long regUserNo;

    @Setter
    private long modUserNo;
}
