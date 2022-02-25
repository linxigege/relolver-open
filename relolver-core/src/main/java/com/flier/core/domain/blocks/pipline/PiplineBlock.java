package com.flier.core.domain.blocks.pipline;

import com.flier.common.StringConveyor;
import com.flier.common.exception.MethodNotFoundException;
import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.domain.result.impl.WowResult;
import com.flier.core.infrastructure.method.Method;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PiplineBlock implements Block {
    private final Context context;
    private final String methodName;
    private final String[] args;
    protected Object input;

    public PiplineBlock(String methodName, Context context) {
        this.methodName = this.getMethod(methodName).trim();
        String argsTemplate = methodName.replaceAll(this.methodName, "").trim();
        if (this.methodName.endsWith(":")) {
            this.args = this.splitArgs(argsTemplate);
        } else {
            this.args = new String[0];
        }
        this.context = context;
    }

    private String[] splitArgs(String argsTemplate) {
        List<String> resultArgs = new ArrayList<String>();
        StringConveyor conveyor = new StringConveyor(argsTemplate).trimLeft();
        while (conveyor.length() > 0) {
            String result = "";
            if (conveyor.startWith("\"")) {
                result = conveyor.getFromTo("\"", "\"").result();
                conveyor.getUntil(",", true);
            } else {
                result = conveyor.getUntil(",", true).result();
            }
            resultArgs.add(result.trim());
        }
        return resultArgs.toArray(new String[resultArgs.size()]);
    }

    private String getMethod(String methodName) {
        return methodName.trim().split(" ")[0];
    }

    public PiplineBlock input(Object input) {
        this.input = input;
        return this;
    }

    @Override
    public Block setFlag(boolean flag) {
        return this;
    }

    @Override
    public Result render() {
        Method method = this.context.getMethod(this.getRealMethodName(methodName));
        Object result = null;
        if (null != method) {
            result = method.doFormat(this.input, this.getArgsResults());
        } else {
            try {
                throw new MethodNotFoundException(methodName);
            } catch (MethodNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (null == result) {
            return new WowResult(this.input + "");
        }
        return new StringResult(result);
    }

    @Override
    public String text() {
        return methodName;
    }

    private Object[] getArgsResults() {
        return Arrays.stream(this.args).map(key -> RelolverUtil.getFromPlaceholderOrNot(context, key).getResult()).toArray();
    }

    private String getRealMethodName(String methodName) {
        if (methodName.endsWith(":")) {
            return methodName.substring(0, methodName.length() - 1);
        }
        return methodName;
    }
}
