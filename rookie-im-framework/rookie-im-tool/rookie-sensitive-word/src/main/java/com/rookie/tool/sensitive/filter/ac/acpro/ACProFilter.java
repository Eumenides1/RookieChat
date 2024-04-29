package com.rookie.tool.sensitive.filter.ac.acpro;

import com.rookie.tool.sensitive.filter.SensitiveWordFilter;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Objects;

/**
 *@author CtrlCver
 *@date 2024/1/12
 *@description:  基于ACFilter的优化增强版本
 */
public class ACProFilter implements SensitiveWordFilter {

    private ACProTrie acProTrie;

    @Override
    public boolean hasSensitiveWord(String text) {
        if(StringUtils.isBlank(text)) return false;
        return !Objects.equals(filter(text),text);
    }

    @Override
    public String filter(String text) {
        return acProTrie.match(text);
    }

    @Override
    public void loadWord(List<String> words) {
        if (words == null) return;
        acProTrie = new ACProTrie();
        acProTrie.createACTrie(words);
    }
}
