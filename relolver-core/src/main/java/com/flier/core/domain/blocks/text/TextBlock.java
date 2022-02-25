package com.flier.core.domain.blocks.text;

import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;

/**
 * 文本块
 *
 * @author user
 */
public class TextBlock implements Block {
    private final String text;
    private boolean flag = true;
    private boolean skip = false;

    public TextBlock(String text) {
        this.text = text;
    }

    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public Result render() {
        if (!flag) {
            return new StringResult("");
        }
        return new StringResult(getText());
    }

    @Override
    public String text() {
        return getText();
    }

    public boolean textIs(String other) {
        return text.replaceAll(" ", "").equals(other);
    }

    public String getText() {
        if (skip) {
            return "";
        }
        return text;
    }

    @Override
    public String toString() {
        return "Text(" + text + ")";
    }

    public void skip() {
        this.skip = true;
    }
}
