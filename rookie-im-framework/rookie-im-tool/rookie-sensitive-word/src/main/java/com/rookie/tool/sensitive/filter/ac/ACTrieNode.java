package com.rookie.tool.sensitive.filter.ac;

import lombok.Getter;
import lombok.Setter;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author eumenides
 * @description
 * @date 2024/4/29
 */
@Getter
@Setter
public class ACTrieNode {

    // 子节点
    private Map<Character, ACTrieNode> children = Maps.newHashMap();

    // 匹配过程中，如果模式串不匹配，模式串指针会回退到failover继续进行匹配
    private ACTrieNode failover = null;

    private int depth;

    private boolean isLeaf = false;

    /**
     * 如果当前节点的子节点中不存在字符 c 对应的节点，则创建一个新的 ACTrieNode 对象
     * @param c
     */
    public void addChildrenIfAbsent(char c) {
        children.computeIfAbsent(c, (key) -> new ACTrieNode());
    }

    /**
     * 返回当前节点的子节点中字符 c 对应的节点，如果不存在则返回 null。
     * @param c
     * @return
     */
    public ACTrieNode childOf(char c) {
        return children.get(c);
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    @Override
    public String toString() {
        return "ACTrieNode{" +
                "failover=" + failover +
                ", depth=" + depth +
                ", isLeaf=" + isLeaf +
                '}';
    }
}
