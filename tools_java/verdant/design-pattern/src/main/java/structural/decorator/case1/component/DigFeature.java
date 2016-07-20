package structural.decorator.case1.component;

/**
 * 钻地特征
 *
 * @author verdant
 * @since 2016/07/20
 */
public class DigFeature implements Feature {
    @Override
    public void load() {
        System.out.println("Add drilling capacity");
    }
}
