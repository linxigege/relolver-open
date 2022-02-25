package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

/**
 * 原生块
 *
 * @author user
 */
public class RawProcessBlock extends ProcessBlock {

    public RawProcessBlock(String topMark, Context context) {
        super(RelolverUtil.formateAsNomal(topMark), context);
        super.tag = Constants.TAG_RAW;
        super.endTag = Constants.TAG_RAW_END;
    }

    @Override
    public Result render() {
        return new StringResult(super.text());
    }
}
