package com.verdant.jtools.common.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.verdant.jtools.common.spring.utils.PropUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Html Exporter
 *
 * @author verdant
 * @since 2016/08/05
 */
public class HtmlExporterUtils {

    /**
     * 将图片转换为 PDF (without cookie)
     *
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convert2Image(String url, Class clazz, Integer width, Integer height) {
        return convert2Image(url, null, clazz, width, height);
    }

    /**
     * 将图片转换为 PDF
     *
     * @param url
     * @param cookie
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings(value = {"unchecked"})
    public static <T> T convert2Image(String url, Cookie cookie, Class clazz, Integer width, Integer height) {
        OutputType outputType;
        if (clazz.isAssignableFrom(File.class)) {
            outputType = OutputType.FILE;
        } else if (clazz.isAssignableFrom(String.class)) {
            outputType = OutputType.BASE64;
        } else if (clazz.isAssignableFrom(byte[].class)) {
            outputType = OutputType.BYTES;
        } else {
            return null;
        }
        return (T) prepare(url, cookie, width, height).getScreenshotAs(outputType);
    }

    /**
     * 将 字符数组 转化为文件
     * 临时测试 后续删除
     *
     * @param bytes
     * @param file
     */
    @Deprecated
    public static void byte2File(byte[] bytes, File file) {
        try (OutputStream out = new FileOutputStream(file)) {
//            FileUtils.writeByteArrayToFile(file, bytes);  // same as next line
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片转换为 A4格式的 PDF
     *
     * @param image
     * @return
     */
    public static byte[] image2pdfA4(byte[] image) {
        return image2pdf(image, null, null, null, null, null);
    }

    /**
     * image转pdf 复用
     *
     * @param image
     * @return
     */
    public static byte[] image2pdf(byte[] image) {
        return image2pdf(image, getSizeFromImage(image), null, null, null, null);
    }

    /**
     * 将图片转换为 PDF
     *
     * @param image
     * @param pageSize     supported type will be found in com.itextpdf.text.PageSize, like A2, A3, A4, LETTER_LANDSCAPE etc.
     * @param marginLeft   0f
     * @param marginRight  0f
     * @param marginTop    0f
     * @param marginBottom 0f
     * @return
     */
    public static byte[] image2pdf(byte[] image, Rectangle pageSize, Float marginLeft, Float marginRight,
                                   Float marginTop, Float marginBottom) {

        Document document = new Document(pageSize == null ? PageSize.A4 : pageSize,
                marginLeft == null ? 0f : marginLeft,
                marginRight == null ? 0f : marginRight,
                marginTop == null ? 0f : marginTop,
                marginBottom == null ? 0f : marginBottom);
        PdfWriter pdfWriter;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.add(Image.getInstance(image, true));
            // need close document and pdfWriter before convert byte array!
            document.close();
            pdfWriter.close();
            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化配置 PhantomJS Driver
     *
     * @param url
     * @param addedCookie
     * @return
     */
    public static PhantomJSDriver prepare(String url, Cookie addedCookie, Integer width, Integer height) {
//        System.setProperty("webdriver.chrome.driver",
//                DirUtils.RESOURCES_PATH.concat(
//                        PropUtil.get("html.exporter.webdriver.chrome.driver")));

        DesiredCapabilities phantomCaps = DesiredCapabilities.chrome();
        phantomCaps.setJavascriptEnabled(true);
        phantomCaps.setCapability("phantomjs.page.settings.userAgent",
                PropUtils.get("html.exporter.user.agent"));

        PhantomJSDriver driver = new PhantomJSDriver(phantomCaps);
//        driver.manage().timeouts().implicitlyWait(Integer.valueOf(PropUtils.getInstance()
//                .getProperty("html.exporter.driver.timeouts.implicitly.seconds")), TimeUnit.SECONDS);
//        driver.manage().timeouts().pageLoadTimeout(Integer.valueOf(PropUtils.getInstance()
//                .getProperty("html.exporter.driver.timeouts.page.load.seconds")), TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(Integer.valueOf(PropUtils.getInstance()
//                .getProperty("html.exporter.driver.timeouts.script.seconds")), TimeUnit.SECONDS);

        if (width == null || height == null) {
            driver.manage().window().maximize();
        } else {
            driver.manage().window().setSize(new Dimension(width, height));
        }

        if (addedCookie != null)
            driver.manage().addCookie(addedCookie);

        driver.get(url);
        try {
            // timeout is not work, so fix it by sleeping thread
            Thread.sleep(Integer.valueOf(PropUtils.get("html.exporter.driver.timeouts.implicitly.seconds")) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver;
    }

    /**
     * 获取 [文件]形式 的图片大小
     *
     * @param file
     * @return
     */
    public static Rectangle getSizeFromImage(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            return new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 [InputStream]形式 的图片大小
     *
     * @param inputStream
     * @return
     */
    public static Rectangle getSizeFromImage(InputStream inputStream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 [字符数组]形式 的图片大小
     *
     * @param bytes
     * @return
     */
    public static Rectangle getSizeFromImage(byte[] bytes) {
        try {
//            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
//            return new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
            return getSizeFromImage(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 [Base64]形式 的图片大小
     *
     * @param base64
     * @return
     */
    public static Rectangle getSizeFromImage(String base64) {
        return getSizeFromImage(base64.getBytes());
    }

}
