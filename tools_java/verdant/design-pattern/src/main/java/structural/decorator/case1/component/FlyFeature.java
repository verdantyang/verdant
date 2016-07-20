package structural.decorator.case1.component;

/**
 * 飞行特征
 *
 * @author verdant
 * @since 2016/07/20
 */
public class FlyFeature implements Feature {
    @Override
    public void load() {
        System.out.println("Add a pair of wings");
    }
}
