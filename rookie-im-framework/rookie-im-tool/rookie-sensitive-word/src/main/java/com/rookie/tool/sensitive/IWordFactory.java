package com.rookie.tool.sensitive;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
public interface IWordFactory {
    /**
     * 返回敏感词数据源
     *
     * @return 结果
     * @since 0.0.13
     */
    List<String> getWordList();
}
