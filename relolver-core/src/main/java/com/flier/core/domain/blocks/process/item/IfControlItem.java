package com.flier.core.domain.blocks.process.item;

/**
 * 逻辑控制类
 *
 * @author user
 */
public class IfControlItem extends RelolverIfItem {

    private final String AND = "and";
    private final String OR = "or";

    public IfControlItem(String template) {
        super(template.trim().toLowerCase());
    }

    public IfItemCompare compare(IfItemCompare before, IfItemCompare after) {
        if (AND.equals(super.template)) {
            return before.andWith(after);
        } else if (OR.equals(super.template)) {
            return before.orWith(after);
        }
        return new IfItemCompare(false);
    }
}