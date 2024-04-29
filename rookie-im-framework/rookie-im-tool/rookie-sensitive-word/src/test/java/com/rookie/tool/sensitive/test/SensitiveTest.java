package com.rookie.tool.sensitive.test;

import com.rookie.tool.sensitive.filter.ac.ACFilter;
import com.rookie.tool.sensitive.filter.ac.acpro.ACProFilter;
import com.rookie.tool.sensitive.filter.dfa.DFAFilter;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
public class SensitiveTest {
    @Test
    public void DFA() {
        List<String> sensitiveList = Arrays.asList("abcd", "abcbba", "adabca");
        DFAFilter instance = DFAFilter.getInstance();
        instance.loadWord(sensitiveList);
        System.out.println(instance.hasSensitiveWord("adabcd"));
    }

    @Test
    public void AC() {
        List<String> sensitiveList = Arrays.asList("abcd", "abcbba", "adabca");
        ACFilter instance = new ACFilter();
        instance.loadWord(sensitiveList);
        instance.hasSensitiveWord("adabcd");
    }

    @Test
    public void ACMulti() {
        List<String> sensitiveList = Arrays.asList("你是白痴","你是");
        ACFilter instance = new ACFilter();
        instance.loadWord(sensitiveList);
        System.out.println(instance.filter("你是白痴吗"));
    }

    @Test
    public void ACPro()
    {
        List<String> sensitiveList = Arrays.asList("白痴", "你是白痴", "白痴吗");
        ACProFilter acProFilter=new ACProFilter();
        acProFilter.loadWord(sensitiveList);
        System.out.println(acProFilter.filter("你是白痴吗"));
    }
}
