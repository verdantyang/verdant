package structural.decorator.case1.component;

/**
 * 老鼠
 *
 * @author verdant
 * @since 2016/07/20
 */
public class Rat implements Animal {
    @Override
    public void doStuff() {
        System.out.println("Jerry will play with Tom.");
    }
}
