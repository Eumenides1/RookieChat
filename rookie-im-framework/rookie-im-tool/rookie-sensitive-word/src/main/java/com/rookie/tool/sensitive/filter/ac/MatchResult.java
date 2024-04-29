package com.rookie.tool.sensitive.filter.ac;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
@Getter
@Setter
@AllArgsConstructor
public class MatchResult {

    private int startIndex;

    private int endIndex;

    @Override
    public String toString() {
        return "MatchResult{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
