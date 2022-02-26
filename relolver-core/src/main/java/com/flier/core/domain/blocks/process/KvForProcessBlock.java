package com.flier.core.domain.blocks.process;

import com.alibaba.fastjson.JSONObject;
import com.flier.common.Constants;
import com.flier.common.exception.ParamInvalidException;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * for循环迭代【对象】键值对
 *
 * @author admin
 * @date 2022/02/18
 */
public class KvForProcessBlock extends ProcessBlock {
    private String KeyName;
    private String ValueName;
    private String sourcesName;
    private Object sources;

    public KvForProcessBlock(String topMark, Context context) {
        super(topMark, context);
        super.tag = Constants.TAG_KV_FOR;
        super.endTag = Constants.TAG_KV_FOR_END;
        this.getNames(topMark);
    }

    private void getNames(String topMark) {
        String forName = RelolverUtil.subMarkToTemplate(super.topMark, super.leftMark, super.rightMark).trim();
        String[] itemAndObject = forName.replaceFirst(Constants.TAG_KV_FOR, "").split(" in ");
        String item = itemAndObject[0].trim();
        String[] kv = item.split(",");
        this.KeyName = kv[0];
        this.ValueName = kv[1];
        this.sourcesName = itemAndObject[1].trim();
    }

    @Override
    public Result render() {
        if (!flag) {
            return new StringResult("");
        }
        Result<Object> sourcesResult = RelolverUtil.getFromPlaceholderOrNot(super.context, sourcesName);
        this.sources = sourcesResult.getResult();
        List<Result> child = new ArrayList<>();
        // 对象尝试转jsonObj进行迭代
        try {
            JSONObject object = (JSONObject) this.sources;
            object.keySet().stream().forEach(key -> {
                context.bindArgs(this.KeyName, key);
                context.bindArgs(this.ValueName, object.get(key));
                child.addAll(super.childResult(true));
                context.unbindArgs(this.KeyName);
                context.unbindArgs(this.ValueName);
            });
        } catch (Exception e) {
            throw new ParamInvalidException("参数异常:" + e.getMessage());
        }
        StringBuilder sBuilder = new StringBuilder();
        child.stream().forEach(c -> sBuilder.append(c.getResult()));
        return new StringResult(sBuilder.toString());
    }
}