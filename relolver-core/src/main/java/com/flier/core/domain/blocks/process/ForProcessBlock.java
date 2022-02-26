package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.common.exception.ParamNotFoundException;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * for  控制流
 *
 * @author admin
 * @date 2022/02/18
 */
public class ForProcessBlock extends ProcessBlock {
    private String itemName;
    private String sourcesName;
    private Object sources;

    public ForProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.tag = Constants.TAG_FOR;
        super.endTag = Constants.TAG_FOR_END;
        this.getNames(topMark);
    }

    private void getNames(String topMark) {
        String forName = RelolverUtil.subMarkToTemplate(super.topMark, super.leftMark, super.rightMark).trim();
        String[] itemAndObject = forName.replaceFirst(Constants.TAG_FOR, "").split(" in ");
        this.itemName = itemAndObject[0].trim();
        this.sourcesName = itemAndObject[1].trim();
    }

    @Override
    public Result render() {
        if (!flag) {
            return new StringResult("");
        }
        Result<Object> sourcesResult = RelolverUtil.getFromPlaceholderOrNot(super.context, sourcesName);
        this.sources = sourcesResult.getResult();
        List<Result> childs = new ArrayList<>();
        if (this.sources instanceof Collection) {
            Collection collection = (Collection) this.sources;
            for (Object object : collection) {
                context.bindArgs(this.itemName, object);
                childs.addAll(super.childResult(true));
                context.unbindArgs(this.itemName);
            }
        } else if (this.sources.getClass().isArray()) {
            Object[] objects = (Object[]) this.sources;
            for (Object object : objects) {
                context.bindArgs(this.itemName, object);
                childs.addAll(super.childResult(true));
                context.unbindArgs(this.itemName);
            }
        } else {
            try {
                throw new ParamNotFoundException(sourcesName + " is not collection or array");
            } catch (ParamNotFoundException e) {
                e.printStackTrace();
            }
        }
        StringBuilder sBuilder = new StringBuilder();
        childs.stream().forEach(child -> sBuilder.append(child.getResult()));
        return new StringResult(sBuilder.toString());
    }
}