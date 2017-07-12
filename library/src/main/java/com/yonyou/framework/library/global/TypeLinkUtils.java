package com.yonyou.framework.library.global;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;

/**
 * Created by zj on 2017/7/12.
 * 邮箱：zjuan@yonyou.com
 * 描述：自定义超链接工具类
 * ClickableSpan：这是超链接相关的样式控制和点击事件的控制类，我们需要对它进行继承，然后修改成我们需要的样式。
 * 其中，可以对超链接文本字符进行颜色、字体大小、字体、下划线、点击事件的自定义控制。
 */
public class TypeLinkUtils {
    // 确定可点区域，并设置点击事件
    private static void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,
                                         final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置超链接字体颜色
                ds.setColor(Color.parseColor("#45acfb"));
                // 保留下划线
                ds.setUnderlineText(true);
            }
        };

        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    // 为所有超链接设置样式
    public static CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        // 获取所有超链接
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            // 为每个超链接样式设置
            setLinkClickable(clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }
}
