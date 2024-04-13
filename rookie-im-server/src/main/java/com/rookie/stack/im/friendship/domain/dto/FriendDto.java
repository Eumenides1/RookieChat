package com.rookie.stack.im.friendship.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author eumenides
 * @description
 * @date 2024/4/13
 */
@Data
public class FriendDto {

    @NotBlank
    private String toId;

    private String remark;

    private String addSource;

    private String extra;

    private String addWording;




}
