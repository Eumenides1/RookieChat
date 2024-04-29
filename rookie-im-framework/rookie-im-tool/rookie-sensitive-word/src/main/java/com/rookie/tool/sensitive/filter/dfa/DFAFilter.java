package com.rookie.tool.sensitive.filter.dfa;

import com.rookie.tool.sensitive.filter.SensitiveWordFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
public final class DFAFilter implements SensitiveWordFilter {

    private DFAFilter() {
    }

    private static Word root = new Word(' '); // 敏感词字典的根节点
    private final static char replace = '*'; // 替代字符
    private final static String skipChars = " !*-+_=,，.@;:；：。、？?（）()【】[]《》<>“”\"‘’"; // 遇到这些字符就会跳过
    private final static Set<Character> skipSet = new HashSet<>(); // 遇到这些字符就会跳过

    static {
        for (char c : skipChars.toCharArray()) {
            skipSet.add(c);
        }
    }

    /**
     * 判断是否需要跳过当前字符
     *
     * @param c 待检测字符
     * @return true: 需要跳过, false: 不需要跳过
     */
    private boolean skip(char c) {
        return skipSet.contains(c);
    }

    public static DFAFilter getInstance() {
        return new DFAFilter();
    }


    @Override
    public boolean hasSensitiveWord(String text) {
        if (StringUtils.isBlank(text)) return false;
        return !Objects.equals(filter(text), text);
    }

    @Override
    public String filter(String text) {
        StringBuilder result = new StringBuilder(text);
        int index = 0;
        while (index < result.length()){
            char c = result.charAt(index);
            if (skip(c)) {
                index++;
                continue;
            }
            Word word = root;
            int start = index;
            boolean found = false;
            for (int i = index; i < result.length(); i++) {
                c = result.charAt(i);
                if (skip(c)) {
                    continue;
                }
                if (c >= 'A' && c <= 'Z') {
                    c += 32;
                }
                word = word.next.get(c);
                if (word == null) {
                    break;
                }
                if (word.end) {
                    found = true;
                    for (int j = start; j <= i; j++) {
                        result.setCharAt(j, replace);
                    }
                    index = i;
                }
            }
            if (!found) {
                index++;
            }
        }
        return result.toString();
    }

    @Override
    public void loadWord(List<String> words) {
        if (!CollectionUtils.isEmpty(words)) {
            Word newRoot = new Word(' ');
            words.forEach(word -> loadWord(word, newRoot));
            root = newRoot;
        }
    }


    /**
     * 加载敏感词
     *
     * @param word 词
     */
    public void loadWord(String word, Word root){
        if (StringUtils.isBlank(word)) {
            return;
        }
        Word current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // 如果是大写字母, 转换为小写
            if (c >= 'A' && c <= 'Z') {
                c += 32;
            }
            if (skip(c)) {
                continue;
            }
            Word next = current.next.get(c);
            if (next == null) {
                next = new Word(c);
                current.next.put(c, next);
            }
            current = next;
        }
        current.end = true;
    }

    private static class Word{

        // 当前字符
        private final char c;

        // 结束标识
        private boolean end;

        // 下一层级的敏感词字典
        private Map<Character, Word> next;

        public Word(char c) {
            this.c = c;
            this.next = new HashMap<>();
        }
    }


}
