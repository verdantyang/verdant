package com.verdant.jtools.common.tags;



import com.verdant.jtools.metadata.model.Page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class PagingTag extends TagSupport {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分页对象
     */
    private Page page;

    /**
     * 分页地址
     */
    private String url;

    /**
     * 当前页前后显示几页, 其余的用省略号
     */
    private int number = 2;

    /**
     * 设置number
     *
     * @param number 要设置的number。
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 设置page
     *
     * @param page 要设置的page。
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 设置url
     *
     * @param url 要设置的url。
     */
    public void setUrl(String url) {
        this.url = url;
    }

    private final String VOID_HREF = "javascript:void(0)";

    private final String NORMAL_PAGE_CLS = "page";

    private final String ACTIVE_PAGE_CLS = "active";

    private final String DISABLE_PAGE_CLS = "unfocus";

    @Override
    public int doStartTag() throws JspException {
        if (page == null)
            return SKIP_BODY;

        //模版
        String template = "<a href='%s' class='%s'>%s</a>%n";
        url += ".htm";
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        if (req.getQueryString() != null && !req.getQueryString().trim().equals("")) {
            url += "?" + req.getQueryString();
        }
        //首页 末页
        String first = String.format(template, url.replace("{0}", "1"), "", "首页");
        String last = String.format(template, url.replace("{0}", String.valueOf(page.getTotalPage()>0?page.getTotalPage():1)), "", "末页");

        //上一页
        String prev = "";
        if (page.getPageNo() <= 1) {
            prev = String.format(template, VOID_HREF, DISABLE_PAGE_CLS, "上一页");
        } else {
            prev = String.format(template, url.replace("{0}", String.valueOf(page.getPageNo() - 1)), "", "上一页");
        }

        //下一页
        String next = "";
        if (page.getPageNo() < page.getTotalPage()) {
            next = String.format(template, url.replace("{0}", String.valueOf(page.getPageNo() + 1)), "", "下一页");
        } else {
            next = String.format(template, VOID_HREF, DISABLE_PAGE_CLS, "下一页");
        }

        //中间起止  . 省略号
        boolean ellipsisFlag = false;
        int end = 0;
        int start = 0;
        if (page.getPageNo() - number <= 1) {
            start = 1;
            end = start + number + number <= page.getTotalPage() ? start + number + number : page.getTotalPage();
        } else {
            start = page.getPageNo() - number;
            end = page.getPageNo() + number <= page.getTotalPage() ? page.getPageNo() + number : page.getTotalPage();
        }
        ellipsisFlag = end <= page.getTotalPage() ? true : false;

        //拼接组成
        StringBuffer pageSb = new StringBuffer();
        pageSb.append(first);
        pageSb.append(prev);
        for (int i = start; i <= end; i++) {
            pageSb.append(String.format(template, url.replace("{0}", String.valueOf(i)), page.getPageNo() == i ? ACTIVE_PAGE_CLS : NORMAL_PAGE_CLS, i));
            if (i == end && ellipsisFlag) {
                pageSb.append(String.format(template, VOID_HREF, DISABLE_PAGE_CLS, "..."));
            }
        }
        pageSb.append(next);
        pageSb.append(last);
        try {
            pageContext.getOut().write(pageSb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;
    }

}
