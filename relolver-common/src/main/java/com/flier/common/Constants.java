package com.flier.common;


public class Constants {

    public static final String LAYOUT_CONTENT_KEY = "content";
    public static final String LAYOUT_BASE_PATH = "_layouts/";
    public static final String LAYOUT = "layout";
    public static final String CHAR_SET_UTF_8 = "UTF-8";
    public static final String KEY_TEMPLATE_CACHE = "template_cache";
    public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    public static final String TAG_RAW = "raw";
    public static final String TAG_RAW_END = "endraw";
    public static final String TAG_NO_END = "No";
    public static final String TAG_ASSIGN = "assign";
    public static final String TAG_INCLUDE = "include";
    public static final String TAG_FOR = "for";
    public static final String TAG_FOR_END = "endfor";
    public static final String TAG_IF = "if";
    public static final String TAG_IF_END = "endif";
    public static final String TAG_ELSE_IF = "elsif";
    public static final String TAG_ELSIF_END = "end of ensif hahahah$#%#$%";
    public static final String TAG_ELSE = "else";
    public static String INCLUDE_PATH = "_includes/";
    public static String TEMPLATE_SUFFIX = "";
    public static String TEMPLATE_PREFIX = "";
    /**
     * 是否缓存模板
     */
    public static Boolean TEMPLATE_CACHE = true;
    /**
     * 10s
     */
    public static long KEY_TEMPLATE_TIMEOUT_MILLISECOND = 10000;
    public static String OBJ_LEFTMARK = "{{";
    public static String OBJ_RIGHTMARK = "}}";
    public static String PROCESS_LEFTMARK = "{%";
    public static String PROCESS_RIGHTMARK = "%}";
    public static String HEADER_BLOCK = "---";
    public static String LINE = "\n";
    public static String NULL_CONTENT = "\n";

    /**
     * method name
     */
    public static String APPEND = "append";
    public static String PREPEND = "prepend";
    public static String CONCAT = "concat";
    public static String LENGTH = "length";
    public static String RANGE = "range";
    public static String ABS = "abs";
    public static String CAPITALIZE = "capitalize";
    public static String CEIL = "ceil";
    public static String SPLIT = "split";
    public static String DATE = "date";
    public static String DEFAULT = "default";
    public static String TIMES = "times";
    public static String UP_CASE = "upcase";
    public static String URL_ENCODE = "url_encode";
    public static String URL_DECODE = "url_decode";
    public static String UNIQ = "uniq";
    public static String JOIN = "join";
    public static String DOWN_CASE = "downcase";
    public static String ESCAPE = "escape";
    public static String NEWLINE_TO_BR = "newline_to_br";
    public static String REPLACE = "replace";
    public static String REPLACE_FIRST = "replace_first";
    public static String REVERSE = "reverse";
    public static String RELATIVE_URL = "relative_url";
    public static String TRIM = "trim";
    public static String RIGHT_CUT = "right_cut";

    /**
     * 换行符
     *
     * @return
     */
    public static String Wrap() {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return "\r\n";
        } else {
            return "\n";
        }
    }
}
