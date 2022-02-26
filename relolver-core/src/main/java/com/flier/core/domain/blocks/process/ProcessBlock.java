package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.result.Result;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 流程控制语句块 公共处理
 *
 * @author user
 */
public abstract class ProcessBlock implements Block {
    protected String leftMark = Constants.PROCESS_LEFTMARK;
    protected String rightMark = Constants.PROCESS_RIGHTMARK;
    protected String tag;
    protected String endTag;
    protected String topMark;
    protected List<Block> childBlocks = new ArrayList<>();
    protected Context context;
    protected boolean flag = true;
    private boolean deleteBlank = false;

    public ProcessBlock(String topMark, Context context) {
        this.topMark = topMark;
        this.context = context;
        this.checkWhiteTag();
    }

    /**
     * 检查是否 {%- process -%}
     * 如果是，打标签并去掉 -
     */
    private void checkWhiteTag() {
        if (this.topMark.startsWith(this.leftMark.concat("-")) && this.topMark.endsWith("-".concat(this.rightMark))) {
            this.deleteBlank = true;
            String[] marks = this.topMark.split(" ");
            marks[0] = this.leftMark;
            marks[marks.length - 1] = this.rightMark;
            this.topMark = String.join(" ", marks);
        }
    }

    protected List<Result> childResult(boolean ifTrue) {
        return childBlocks.stream().map(child -> {
            if (child instanceof ElsIFProcessBlock || child instanceof ElseProcessBlock) {
                return child.setFlag(!ifTrue).render();
            } else {
                return child.setFlag(ifTrue).render();
            }
        }).collect(Collectors.toList());
    }

    public void addChildBlock(Block block) {
        this.childBlocks.add(block);
    }

    public boolean isDeleteBlank() {
        return deleteBlank;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public String getTag() {
        return tag;
    }

    public Block setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public String toString() {
        return "Process(" + topMark + ")";
    }

    @Override
    public String text() {
        List<String> childText = childBlocks.stream().map(child -> child.text()).collect(Collectors.toList());
        return (RelolverUtil.formateAsNomal(topMark).equals(
                Constants.PROCESS_LEFTMARK.concat(" ").concat(Constants.TAG_RAW).concat(" ").concat(Constants.PROCESS_RIGHTMARK)) ? "" : topMark).concat(String.join("", childText));
    }

    public boolean isNoEndBlock() {
        return Constants.TAG_NO_END.equals(this.endTag);
    }
}