package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;

public class ElseProcessBlock extends ProcessBlock {
    public ElseProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.tag = Constants.TAG_ELSE;
        super.endTag = Constants.TAG_ELSIF_END;
    }

    @Override
    public Result render() {
        StringBuilder resultString = new StringBuilder();
        super.childResult(flag).stream().forEach(child -> {
            resultString.append(child.getResult().toString());
        });
        return new StringResult(resultString.toString());
    }

    @Override
    public String text() {
        return topMark;
    }
}
