package com.flier.core.domain.template;

import com.flier.common.Constants;
import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.blocks.object.ObjectBlock;
import com.flier.core.domain.blocks.text.TextBlock;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析模板
 *
 * @author user
 */
public class TemplateParser {
    private final Context context;

    public TemplateParser(Context context) {
        this.context = context;
    }

    /**
     * 解析语法块
     *
     * @param content
     * @return
     */
    public List<Block> parse(String content) {
        List<Block> blocks = new ArrayList<>();
        // 模板流，里面包装了StringBuilder
        TemplateFlow templateFlow = new TemplateFlow(content);
        while (templateFlow.isNotEmpty()) {
            Block block;
            // 解析对象块
            if (templateFlow.startWith(Constants.OBJ_LEFTMARK)) {
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.OBJ_RIGHTMARK)) {
                    tempObj.append(templateFlow.pull(1));
                }
                tempObj.append(templateFlow.pull(Constants.OBJ_RIGHTMARK.length()));
                block = new ObjectBlock(this.context, tempObj.toString());
            }
            // 解析表达式语句块
            else if (templateFlow.startWith(Constants.PROCESS_LEFTMARK)) {
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.PROCESS_RIGHTMARK)) {
                    tempObj.append(templateFlow.pull(1));
                }
                tempObj.append(templateFlow.pull(Constants.PROCESS_RIGHTMARK.length()));
                block = RelolverUtil.routeProcessBlock(tempObj.toString(), this.context);
            }
            // 解析文本块
            else {
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.OBJ_LEFTMARK) && !templateFlow.startWith(Constants.PROCESS_LEFTMARK) && templateFlow.isNotEmpty()) {
                    tempObj.append(templateFlow.pull(1));
                }
                block = new TextBlock(tempObj.toString());
            }
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * 模版流
     * 一个一个去读字符串，因为有大量字符串操作，所以用StringBuilder
     *
     * @author user
     */
    public static class TemplateFlow {
        StringBuilder contentBuilder;

        public TemplateFlow(String content) {
            this.contentBuilder = new StringBuilder(content);
        }

        public boolean startWith(String str) {
            return this.contentBuilder.indexOf(str) == 0;
        }

        public String pull(int length) {
            String result = contentBuilder.substring(0, length);
            contentBuilder.delete(0, length);
            return result;
        }

        public boolean isNotEmpty() {
            return this.contentBuilder.length() > 0;
        }
    }
}
