package com.flier.core.domain.blocks.process.item;

/**
 * 逻辑判断器
 *
 * @author user
 */
public class IfItemCompare {
    public Boolean flag;

    public IfItemCompare(Boolean flag) {
        this.flag = flag;
    }

    IfItemCompare andWith(IfItemCompare other) {
        return new IfItemCompare(this.flag && other.flag);
    }

    public IfItemCompare orWith(IfItemCompare other) {
        return new IfItemCompare(this.flag || other.flag);
    }
}