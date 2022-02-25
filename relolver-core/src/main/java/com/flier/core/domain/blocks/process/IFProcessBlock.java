package com.flier.core.domain.blocks.process;

import com.flier.common.Constants;
import com.flier.core.domain.blocks.process.item.IfControlItem;
import com.flier.core.domain.blocks.process.item.IfItemCompare;
import com.flier.core.domain.blocks.process.item.RelolverIfItem;
import com.flier.core.domain.result.Result;
import com.flier.core.domain.result.impl.NumResult;
import com.flier.core.domain.result.impl.StringResult;
import com.flier.core.domain.result.impl.WowResult;
import com.flier.core.domain.template.TemplateParser;
import com.flier.core.infrastructure.utils.Context;
import com.flier.core.infrastructure.utils.RelolverUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * if流程判断
 *
 * @author user
 */
public class IFProcessBlock extends ProcessBlock {
    private final List<IfItem> ifitems = new ArrayList<IfItem>();
    private final List<IfControlItem> controlItems = new ArrayList<IfControlItem>();
    private final List<ElsIFProcessBlock> elseIfBlock = new ArrayList<ElsIFProcessBlock>();

    public IFProcessBlock(String topMark, Context context) {
        super(RelolverUtil.formateAsNomal(topMark), context);
        super.tag = Constants.TAG_IF;
        super.endTag = Constants.TAG_IF_END;
        this.splitTemplateToIfItems();
    }

    private void splitTemplateToIfItems() {
        String itemsLine = RelolverUtil.subMarkToTemplate(super.topMark, super.leftMark, super.rightMark);
        TemplateParser.TemplateFlow templateFlow = new TemplateParser.TemplateFlow(itemsLine);
        while (templateFlow.isNotEmpty()) {
            if (templateFlow.startWith("and")) {
                templateFlow.pull(3);
                controlItems.add(new IfControlItem("and"));
            } else if (templateFlow.startWith("or")) {
                templateFlow.pull(2);
                controlItems.add(new IfControlItem("or"));
            } else {
                StringBuilder item = new StringBuilder();
                while (templateFlow.isNotEmpty()) {
                    if (!templateFlow.startWith("and") && !templateFlow.startWith("or")) {
                        item.append(templateFlow.pull(1));
                    } else {
                        break;
                    }
                }
                ifitems.add(new IfItem(item.toString().trim()));
            }
        }
    }

    @Override
    public Result render() {
        StringBuilder result = new StringBuilder();
        if (super.flag) {
            super.childsResult(this.valid()).stream().map(item -> item.getResult()).forEach(child -> {
                result.append(child.toString());
            });
        }
        return new StringResult(result);
    }

    /**
     * 验证条件是否为 true
     *
     * @return
     */
    private boolean valid() {
        IfItemCompare firstItem = this.ifitems.get(0).valid();
        for (int i = 0; i < controlItems.size(); i++) {
            IfControlItem control = controlItems.get(i);
            firstItem = control.compare(firstItem, ifitems.get(i + 1).valid());
        }
        return firstItem.flag;
    }

    /**
     * add else if block
     *
     * @param elseBlock
     */
    public void addElseIfBlock(ElsIFProcessBlock elseBlock) {
        this.elseIfBlock.add(elseBlock);
    }


    /**
     * 条件元素类
     */
    class IfItem extends RelolverIfItem {
        /**
         * 第一个对象
         */
        Result first;
        /**
         * 第二个对象
         */
        Result second;
        /**
         * 条件符号
         */
        String symbol;

        public IfItem(String template) {
            super(template.trim());
        }

        /**
         * 判断是 a == b ; a > b ; a < b
         * 还是 if a.b
         */
        void formate() {
            String templateText = template.replaceFirst(tag, "").replaceAll(" ", "");
            if (templateText.contains("=") || templateText.contains(">") || templateText.contains("<")) {
                String spliter = "";
                if (templateText.contains("==")) {
                    spliter = "==";
                } else if (templateText.contains("!=")) {
                    spliter = "!=";
                } else if (templateText.contains(">=")) {
                    spliter = ">=";
                } else if (templateText.contains("<=")) {
                    spliter = "<=";
                } else if (templateText.contains(">")) {
                    spliter = ">";
                } else if (templateText.contains("<")) {
                    spliter = "<";
                }
                String[] params = templateText.split(spliter);
                this.first = RelolverUtil.getFromPlaceholderOrNot(context, params[0]);
                this.second = RelolverUtil.getFromPlaceholderOrNot(context, params[1]);
                this.symbol = spliter;
            } else {
                this.first = RelolverUtil.getFromPlaceholderOrNot(context, templateText);
                this.second = null;
            }
        }

        /**
         * 判断一个条件是否成立
         *
         * @return
         */
        IfItemCompare valid() {
            this.formate();
            if (second == null) {
                if (first instanceof WowResult) {
                    return new IfItemCompare(false);
                } else {
                    return new IfItemCompare(true);
                }
            } else if (first instanceof Result && second instanceof Result) {
                if (first instanceof WowResult || second instanceof WowResult) {
                    return new IfItemCompare(false);
                } else if (this.getFirst() instanceof NumResult || this.getSecond() instanceof NumResult) {
                    BigDecimal bfrist = new BigDecimal(this.getFirst().getResult().toString());
                    BigDecimal bsecond = new BigDecimal(this.getSecond().getResult().toString());
                    if (this.symbol.equals("==")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 0
                        );
                    } else if (this.symbol.equals("!=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) != 0
                        );
                    } else if (this.symbol.equals(">")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 1
                        );
                    } else if (this.symbol.equals("<")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == -1
                        );
                    } else if (this.symbol.equals(">=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 1
                        ).orWith(
                                new IfItemCompare(bfrist.compareTo(bsecond) == 0)
                        );
                    } else if (this.symbol.equals("<=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == -1
                        ).orWith(
                                new IfItemCompare(bfrist.compareTo(bsecond) == 0)
                        );
                    }
                } else {
                    if (this.symbol.equals("==")) {
                        return new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()));
                    } else if (this.symbol.equals("!=")) {
                        return new IfItemCompare(!this.getFirst().getResult().equals(this.getSecond().getResult()));
                    } else if (this.symbol.equals(">")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) > 0
                        );
                    } else if (this.symbol.equals("<")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) < 0
                        );
                    } else if (this.symbol.equals(">=")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) > 0
                        ).orWith(
                                new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()))
                        );
                    } else if (this.symbol.equals("<=")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) < 0
                        ).orWith(
                                new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()))
                        );
                    }
                }
            }
            return new IfItemCompare(false);
        }

        public Result getFirst() {
            return first;
        }

        public Result getSecond() {
            return second;
        }
    }
}
