package common;

import com.verdant.jtools.common.utils.MailSender;

/**
 * <p>文件名称：MailUtilTest.java</p>
 * <p>文件描述：</p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 优行科技 </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 * <p>创建日期：2016/11/22</p>
 *
 * @author congyu.yang@geely.com
 * @version 1.0
 */
public class MailUtilTest {
    private static final String DEFAULT_FROM = "caocao_alarm@163.com";

    public static void main(String[] args) {
        MailSender mailSender = new MailSender();
        mailSender.sendEmail("测试邮件", "邮件内容", DEFAULT_FROM, "congyu.yang@geely.com");
    }
}
