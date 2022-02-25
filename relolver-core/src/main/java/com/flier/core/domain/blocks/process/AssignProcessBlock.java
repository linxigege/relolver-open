package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.domain.blocks.object.ObjectBlock;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

/**
 * 声明变量到全局域
 *
 * @author mac
 */
public class AssignProcessBlock extends ProcessBlock {
    private String key;
    private String value;
    private String template;

    public AssignProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.tag = Constants.TAG_ASSIGN;
        super.endTag = Constants.TAG_NO_END;
        this.init();
    }

    private void init() {
        this.template = RelolverUtil.subMarkToTemplate(super.topMark, super.leftMark, super.rightMark);
        this.template = RelolverUtil.formateAsNomal(this.template);
        String templateContent = RelolverUtil.formateAsNomal(this.template.replaceFirst(this.tag, ""));
        int index = templateContent.indexOf("=");
        this.key = RelolverUtil.formateAsNomal(templateContent.substring(0, index));
        this.value = RelolverUtil.formateAsNomal(templateContent.substring(index + 1));
    }

    @Override
    public Result render() {
        super.context.bindArgs(this.key, new ObjectBlock(context, RelolverUtil.bulidObjectTemplateFromTemplateContent(this.value)).renderObject().getResult());
        return new StringResult("");
    }

    @Override
    public String text() {
        return this.topMark;
    }
}
