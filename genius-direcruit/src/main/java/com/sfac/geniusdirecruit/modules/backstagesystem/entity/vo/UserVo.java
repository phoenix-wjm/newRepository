package com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserVo {
    private Integer userId;
    private String userName;

    //密码
    private String userPwd;

    //创建时间
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //电话
    @Column(length = 11)
    private String tel;

    //状态
    private Integer state;

    @Transient
    private List<Role> roles;

    private String code;
}
