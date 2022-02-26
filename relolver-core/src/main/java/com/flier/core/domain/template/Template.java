package com.flier.core.domain.template;

import com.flier.common.Constants;
import com.flier.common.StringConveyor;
import com.flier.core.domain.blocks.Block;
import com.flier.core.domain.blocks.process.EndProcessBlock;
import com.flier.core.domain.blocks.process.ProcessBlock;
import com.flier.core.domain.blocks.text.TextBlock;
import com.flier.core.infrastructure.config.Configuration;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.util.*;

/**
 * 默认模板
 *
 * @author user
 */
public class Template implements RelolverTemplate {
    private final Configuration configuration;
    private final TemplateParser templateParser;
    private final String source;
    private String contentType = Constants.CONTENT_TYPE;
    private List<Block> resultBlocks;
    private Header header;

    public Template(Configuration configuration, String source) {
        this.configuration = configuration;
        this.templateParser = new TemplateParser(this.configuration.getContext());
        this.source = source;
    }

    /**
     * init the blocks
     */
    private void init() {
        // load出模板文件内容
        String content = this.configuration.getResourcesLoader().load(source);
        // 初始化包装类对象
        StringConveyor conveyor = new StringConveyor(content);
        // 获取文件头部内容
        String headerContent = conveyor.getFromTo(Constants.HEADER_BLOCK.concat(Constants.Wrap()),
                Constants.HEADER_BLOCK.concat(Constants.Wrap())).result();
        content = conveyor.string();
        // 根据头部内容初始化对象
        this.header = new Header(headerContent);
        // 从模板内容中解析出多个 语法块
        List<Block> blocks = this.templateParser.parse(content);
        // 是否处理文本域 换行替换为空
//        blocks = handlerProcessTag(blocks);
        // 追加到结果块中
        this.resultBlocks = getResultBlocks(blocks);
    }

    @Override
    public void bind(String key, Object value) {
        this.configuration.getContext().bindArgs(key, value);
    }

    @Override
    public String render() {
        // 初始化并解析语法块
        this.init();
        StringBuilder resultText = new StringBuilder();
        // 解析到的结果块 执行对应块的解释渲染逻辑后，把渲染结果拼接起来
        resultBlocks.stream().map(block -> block.render().getResult()).forEach(res -> resultText.append(res));
        // 是否配置加载头文件
        if (this.header.available()) {
            this.bind(Constants.LAYOUT_CONTENT_KEY, resultText.toString());
            return this.header.template().render();
        }
        // 返回解析结果
        return resultText.toString();
    }

    /**
     * bulid blocks as a tree
     *
     * @param rootBlock
     * @param blockStack
     */
    private Block buildProcessBlock(ProcessBlock rootBlock, Stack<Block> blockStack) {
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            Block resultBlock = null;
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (tempBlock instanceof EndProcessBlock) {
                    EndProcessBlock end = (EndProcessBlock) processBlock;
                    if (end.isEndOf(rootBlock)) {
                        return end;
                    } else {
                        blockStack.push(end);
                        break;
                    }
                }
                if (processBlock.isNoEndBlock()) {
                    rootBlock.addChildBlock(tempBlock);
                    break;
                }
                resultBlock = buildProcessBlock((ProcessBlock) tempBlock, blockStack);
            }
            rootBlock.addChildBlock(tempBlock);

            if (resultBlock != null) {
                if (resultBlock instanceof EndProcessBlock) {
                    EndProcessBlock endProcessBlock = (EndProcessBlock) resultBlock;
                    if (endProcessBlock.isEndOf((ProcessBlock) tempBlock)) {
                        rootBlock.addChildBlock(resultBlock);
                    }
                }
            }
        }
        return null;
    }


    private List<Block> getResultBlocks(List<Block> blocks) {
        List<Block> resultBlocks = new ArrayList<>();
        Stack<Block> blockStack = new Stack<>();
        Collections.reverse(blocks);
        blockStack.addAll(blocks);
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            Block resultBlock = null;
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (!processBlock.isNoEndBlock()) {
                    resultBlock = this.buildProcessBlock((ProcessBlock) tempBlock, blockStack);
                }
            }
            resultBlocks.add(tempBlock);
            if (resultBlock != null) {
                if (resultBlock instanceof EndProcessBlock) {
                    EndProcessBlock endProcessBlock = (EndProcessBlock) resultBlock;
                    if (endProcessBlock.isEndOf((ProcessBlock) tempBlock)) {
                        resultBlocks.add(resultBlock);
                    }
                }
            }
        }
        return resultBlocks;
    }

    /**
     * 是否处理文本域 换行替换为空
     *
     * @param blocks
     * @return
     */
    private List<Block> handlerProcessTag(List<Block> blocks) {
        for (int i = 0; i < blocks.size() - 2; i++) {
            Block before = blocks.get(i);
            Block current = blocks.get(i + 1);
            Block after = blocks.get(i + 2);
            if (current instanceof ProcessBlock) {
                ProcessBlock currentProcess = (ProcessBlock) current;
                if (currentProcess.isDeleteBlank()) {
                    if (before instanceof TextBlock) {
                        TextBlock beforeTextBlock = (TextBlock) before;
                        // 注释解决下linux下 换行被去掉问题
//                        if (beforeTextBlock.textIs(Constants.LINE)) {
//                            beforeTextBlock.skip();
//                        }
                    }
                    if (after instanceof TextBlock) {
                        TextBlock afterTextBlock = (TextBlock) after;
//                        if (afterTextBlock.textIs(Constants.LINE)) {
//                            afterTextBlock.skip();
//                        }
                    }
                }
            }
        }
        return blocks;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 模板文件的头信息
     * ---
     * layout: home
     * title: Home
     * ---
     */
    class Header {
        private final String template;
        private final TemplateResolver layoutResolver;
        Map<String, String> params = new HashMap<String, String>();

        Header(String template) {
            this.template = template;
            this.layoutResolver = RelolverUtil.layoutResolver(configuration.getContext());
            this.analysis();
        }

        private void analysis() {
            StringConveyor conveyor = new StringConveyor(template);
            String content = conveyor.getBetween(Constants.HEADER_BLOCK.concat(Constants.Wrap()),
                    Constants.HEADER_BLOCK.concat(Constants.Wrap())).result();
            conveyor = new StringConveyor(content);
            while (conveyor.length() > 0) {
                String key = conveyor.getUntil(":", false).result();
                conveyor.getUntil(":", true);
                String value = conveyor.getUntil(Constants.Wrap(), false).result();
                conveyor.getUntil(Constants.Wrap(), true);
                params.put(key, value);
            }
        }

        boolean available() {
            return !params.isEmpty() && params.containsKey(Constants.LAYOUT) && !Constants.NULL_CONTENT.equals(params.get(Constants.LAYOUT));
        }

        RelolverTemplate template() {
            Map<String, String> page = new HashMap<>(params);
            page.remove(Constants.LAYOUT);
            page.entrySet().stream().forEach(e -> bind(e.getKey(), e.getValue()));
            String layoutTemplateName = this.params.get(Constants.LAYOUT).trim();
            RelolverTemplate layoutTemplate = layoutResolver.resolve(layoutTemplateName.trim());
            return layoutTemplate;
        }

    }
}
