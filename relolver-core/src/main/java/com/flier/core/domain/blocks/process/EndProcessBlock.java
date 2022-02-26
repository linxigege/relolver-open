package com.flier.core.domain.blocks.process;

import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;

/**
 * 结束块
 *
 * @author user
 */
public class EndProcessBlock extends ProcessBlock {

    public EndProcessBlock(String endMark, Context context) {
        super(endMark, context);
    }

    @Override
    public Result render() {
        return new StringResult("");
    }

    public boolean isEndOf(ProcessBlock processBlock) {
        return processBlock.getEndTag().equals(this.getTag());
    }
}
