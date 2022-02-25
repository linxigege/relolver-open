package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.infrastructure.utils.Context;

public class ElsIFProcessBlock extends IFProcessBlock {

    public ElsIFProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.endTag = Constants.TAG_ELSIF_END;
        super.tag = Constants.TAG_ELSE_IF;
    }
}
