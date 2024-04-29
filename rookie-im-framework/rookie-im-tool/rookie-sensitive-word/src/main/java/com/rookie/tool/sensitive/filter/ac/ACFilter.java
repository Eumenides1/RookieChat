package com.rookie.tool.sensitive.filter.ac;

import com.rookie.tool.sensitive.filter.SensitiveWordFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
public class ACFilter implements SensitiveWordFilter {

    private final static char mask_char = '*'; // 替代字符

    private static ACTrie ac_trie = null;

    /**
     * 有敏感词
     *
     * @param text 文本
     * @return boolean
     */
    public boolean hasSensitiveWord(String text) {
        if (StringUtils.isBlank(text)) return false;
        return !Objects.equals(filter(text), text);
    }

    /**
     * 敏感词替换
     *
     * @param text 待替换文本
     * @return 替换后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) return text;
        List<MatchResult> matchResults = ac_trie.matches(text);
        StringBuffer result = new StringBuffer(text);
        // matchResults是按照startIndex排序的，因此可以通过不断更新endIndex最大值的方式算出尚未被替代部分
        int endIndex = 0;
        for (MatchResult matchResult : matchResults) {
            endIndex = Math.max(endIndex, matchResult.getEndIndex());
            replaceBetween(result, matchResult.getStartIndex(), endIndex);
        }
        return result.toString();
    }

    private static void replaceBetween(StringBuffer buffer, int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            buffer.setCharAt(i, mask_char);
        }
    }

    /**
     * 加载敏感词列表
     *
     * @param words 敏感词数组
     */
    public void loadWord(List<String> words) {
        if (words == null) return;
        ac_trie = new ACTrie(words);
    }

}
