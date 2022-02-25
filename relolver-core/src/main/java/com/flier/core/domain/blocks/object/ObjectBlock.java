package com.flier.core.domain.blocks.object;

import com.flier.common.Constants;
import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.blocks.pipline.PiplineBlock;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ObjectBlock implements Block {
    private final String leftMark = Constants.OBJ_LEFTMARK;
    private final String rightMark = Constants.OBJ_RIGHTMARK;
    private final Context context;
    private String template;
    private String templateContent;
    private boolean flag = true;
    private String key;
    private List<PiplineBlock> piplines;

    public ObjectBlock(Context context, String template) {
        this.template = template;
        this.context = context;
        this.init();
    }


    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    private void init() {
        this.template = RelolverUtil.formateAsNomal(this.template);
        this.templateContent = RelolverUtil.subMarkToTemplate(template.trim(), leftMark, rightMark);
        List<String> items = Arrays.stream(this.templateContent.split("\\|")).collect(Collectors.toList());
        this.key = items.remove(0).trim();
        this.piplines = this.bulidPipLines(items);
    }

    private List<PiplineBlock> bulidPipLines(List<String> items) {
        return items.stream().map(item -> new PiplineBlock(item, context)).collect(Collectors.toList());
    }

    @Override
    public Result<String> render() {
        return new StringResult(renderObject().getResult().toString());
    }

    @Override
    public String text() {
        return this.template;
    }

    public Result renderObject() {
        if (!flag) {
            return new StringResult("");
        }
        return getResult();
    }

    private Result getResult() {
        Result value = RelolverUtil.getFromPlaceholderOrNot(this.context, this.key);
        for (PiplineBlock piplineBlock : this.piplines) {
            Result pipLineResult = piplineBlock.input(value.getResult()).render();
            value = pipLineResult;
        }
        return value;
    }

    @Override
    public String toString() {
        return "Object(templte=" + template + ")";
    }
}
