package common;

import com.verdant.jtools.common.file.FileUtils2;
import org.junit.Test;

/**
 * FileUtils2测试用例
 *
 * @author verdant
 * @since 2016/06/15
 */
public class FileUtils2Test {
    @Test
    public void readFile() {
        byte[] bytes = FileUtils2.readStream(FileUtils2Test.class.getResourceAsStream("/readme.md"));
        String msg = new String(bytes);
        System.out.println(msg);
    }
}
