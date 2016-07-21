package behavior.template.case1.tmpl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 抽象模板
 *
 * @author verdant
 * @since 2016/07/21
 */
public abstract class AbsPopulator {

    public final void dataInitialing() throws Exception {
        Method[] methods = getClass().getMethods();
        for (Method m : methods) {
            //判断是否是数据初始化方法
            if (isInitDataMethod(m))
                m.invoke(this);
        }
    }

    //判断是否是数据初始化方法，基本方法鉴别器
    private boolean isInitDataMethod(Method m) {
        return m.getName().startsWith("init")          //init开始
                && Modifier.isPublic(m.getModifiers())  //public方法
                && m.getReturnType().equals(Void.TYPE)  //返回为void
                && !m.isVarArgs()                       //输入参数为空
                && Modifier.isAbstract(m.getModifiers()); //非抽象方法
    }
}