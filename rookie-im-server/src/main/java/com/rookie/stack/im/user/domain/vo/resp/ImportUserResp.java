package com.rookie.stack.im.user.domain.vo.resp;

import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@Data
public class ImportUserResp {

    private List<Integer> successId;

    private List<Integer> errorId;

}
