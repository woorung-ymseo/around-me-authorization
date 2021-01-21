package com.around.me.authorization.api.v1.authorization.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("회원 조회 파라미터")
@Getter
@Setter
public class UserParamDto {

    @ApiModelProperty(value = "회원번호")
    private long userNo;

    @ApiModelProperty(value = "회원메일")
    private String userEmail;

    @ApiModelProperty(value = "회원이름")
    private String userName;
}
