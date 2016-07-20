package structural.decorator.case1;

import structural.decorator.case1.component.Animal;
import structural.decorator.case1.component.DigFeature;
import structural.decorator.case1.component.FlyFeature;
import structural.decorator.case1.component.Rat;
import structural.decorator.case1.decorater.DecorateAnimal;

/**
 * 装饰者模式Case
 *
 * @author verdant
 * @since 2016/07/20
 */
public class Client {
    public static void main(String[] args) {
        //定义老鼠Jerry
        Animal Jerry = new Rat();
        //为Jerry 增加飞行能力
        Jerry = new DecorateAnimal(Jerry, FlyFeature.class);
        //Jerry 增加挖掘能力
        Jerry = new DecorateAnimal(Jerry, DigFeature.class);
        //Jerry 开始耍猫了
        Jerry.doStuff();
    }
}
