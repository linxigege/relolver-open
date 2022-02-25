package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.common.StringConveyor;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.domain.template.Template;
import com.flier.core.domain.template.TemplateResolver;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

/**
 * 导入文件
 *
 * @author mac
 */
public class IncludeProcessBlock extends ProcessBlock {
    private final TemplateResolver resolver;
    private String url;
    private Template includeTemplate;
    private String templateContent;

    public IncludeProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.tag = Constants.TAG_INCLUDE;
        super.endTag = Constants.TAG_NO_END;
        this.resolver = RelolverUtil.includeResolver(super.context);
    }

    private void init() {
        String template = RelolverUtil.subMarkToTemplate(RelolverUtil.formateAsNomal(super.topMark), super.leftMark, super.rightMark);
        StringConveyor conveyor = new StringConveyor(template);
        conveyor.getUntil(super.tag, true);
        this.url = this.getIncludeTemplateUrl(conveyor);
        this.templateContent = conveyor.trimLeft().string();
        this.includeTemplate = this.resolver.resolve(this.url);
    }

    private String getIncludeTemplateUrl(StringConveyor conveyor) {
        String result;
        conveyor.trimLeft();
        if (conveyor.startWith("{{")) {
            String key = conveyor.getBetween("{{", "}}").result().trim();
            result = RelolverUtil.getFromPlaceholderOrNot(context, key).getResult().toString();
            conveyor.getUntil("}}", true);
        } else {
            result = conveyor.getUntil(" ", false).result().trim();
        }
        return result;
    }

    @Override
    public Result render() {
        this.init();
        this.bind();
        return new StringResult(this.includeTemplate.render());
    }

    /**
     * 解析 include 中的参数并绑定到 context 中
     */
    private void bind() {
        StringConveyor conveyor = new StringConveyor(templateContent);
        while (conveyor.length() > 0) {
            String key = conveyor.getUntil("=", false).result().trim();
            conveyor.getUntil("=", true);
            conveyor.trimLeft();
            Object value = null;
            if (conveyor.startWith("\"")) {
                value = conveyor.getFromTo("\"", "\"").result();
            } else if (conveyor.trimLeft().startWith("{{")) {
                value = conveyor.getBetween("{{", "}}").result().trim();
                value = RelolverUtil.getFromPlaceholderOrNot(super.context, value.toString());
                value = ((Result) value).getResult();
            }
            super.context.bindArgs(key.trim(), value);
        }
    }
}
