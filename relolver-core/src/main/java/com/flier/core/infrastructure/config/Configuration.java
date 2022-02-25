package com.flier.core.infrastructure.config;

import com.flier.common.Constants;
import com.flier.core.infrastructure.loader.TemplateResourcesLoader;
import com.flier.core.infrastructure.method.Method;
import com.flier.core.infrastructure.method.impl.*;
import com.flier.core.infrastructure.utils.Context;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 基础配置
 */
public class Configuration {
    private final Context context;

    public Configuration(TemplateResourcesLoader resourcesLoader) {
        this.context = new Context();
        this.context.setResourcesLoader(resourcesLoader);
        this.init();
    }

    public Configuration(Context context) {
        this.context = context;
    }

    public Configuration loadConfig(String filePath) {
        Properties configProperties = this.loadConfigProperties(filePath);
        return loadConfig(configProperties);
    }

    private Configuration loadConfig(Properties configProperties) {
        this.fillConfigOfConstants(configProperties, "template.suffix", Constants.TEMPLATE_SUFFIX);
        this.fillConfigOfConstants(configProperties, "template.prefix", Constants.TEMPLATE_PREFIX);
        return this;
    }

    /**
     * fill config if exits in config properties
     *
     * @param configProperties
     * @param key
     * @param constants
     */
    private void fillConfigOfConstants(Properties configProperties, String key, String constants) {
        if (configProperties.containsKey(key)) {
            constants = configProperties.getProperty(key);
        }
    }

    private Properties loadConfigProperties(String filePath) {
        Properties configProperties = new Properties();
        try {
            configProperties.load(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configProperties;
    }

    /**
     * 初始化一些配置信息
     */
    private void init() {
        this.context.bindMethod(Constants.APPEND, new AppendMethod());
        this.context.bindMethod(Constants.PREPEND, new PrependMethod());
        this.context.bindMethod(Constants.CONCAT, new ConcatMethod());
        this.context.bindMethod(Constants.LENGTH, new LengthMethod());
        this.context.bindMethod(Constants.RANGE, new RangeMethod());
        this.context.bindMethod(Constants.ABS, new AbsMethod());
        this.context.bindMethod(Constants.CAPITALIZE, new CapitalizeMethod());
        this.context.bindMethod(Constants.CEIL, new CeilMethod());
        this.context.bindMethod(Constants.SPLIT, new SplitMethod());
        this.context.bindMethod(Constants.DATE, new DateMethod());
        this.context.bindMethod(Constants.DEFAULT, new DefaultMethod());
        this.context.bindMethod(Constants.TIMES, new TimesMethod());
        this.context.bindMethod(Constants.UP_CASE, new UpcaseMethod());
        this.context.bindMethod(Constants.URL_ENCODE, new UrlEncodeMethod());
        this.context.bindMethod(Constants.URL_DECODE, new UrlDecodeMethod());
        this.context.bindMethod(Constants.UNIQ, new UniqMethod());
        this.context.bindMethod(Constants.JOIN, new JoinMethod());
        this.context.bindMethod(Constants.DOWN_CASE, new DowncaseMethod());
        this.context.bindMethod(Constants.ESCAPE, new EscapeMethod());
        this.context.bindMethod(Constants.NEWLINE_TO_BR, new NewlineToBrMethod());
        this.context.bindMethod(Constants.REPLACE, new ReplaceMethod());
        this.context.bindMethod(Constants.REPLACE_FIRST, new ReplaceFirstMethod());
        this.context.bindMethod(Constants.REVERSE, new ReverseMethod());
        this.context.bindMethod(Constants.RELATIVE_URL, new RelativeUrlMethod());
        this.context.bindMethod(Constants.TRIM, new TrimMethod());
        this.context.bindMethod(Constants.RIGHT_CUT, new RightCutMethod());
    }

    public Context getContext() {
        return context;
    }

    public TemplateResourcesLoader getResourcesLoader() {
        return this.context.getResourcesLoader();
    }

    public void bindMethod(String key, Method method) {
        this.context.bindMethod(key, method);
    }
}