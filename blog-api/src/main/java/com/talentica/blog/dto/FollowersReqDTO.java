package com.talentica.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowersReqDTO {
    private Integer userIdFrom;
    private Integer userIdTo;
}
