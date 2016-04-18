package structural.composite.composite;

import structural.composite.component.Component;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化组件
 */
public class Composite extends Component {

    private static final DebugLog logger = DebugLogFactory.getLogger(Composite.class, DesignPatternEnum.Composite);

    public Composite(String name) {
        super(name);
    }

    // 用来保存节点的子节点
    List<Component> list = new ArrayList<Component>();

    // 添加节点
    @Override
    public void add(Component c) {
        list.add(c);
    }

    // 删除节点
    @Override
    public void remove(Component c) {
        list.remove(c);
    }

    // 遍历子节点
    @Override
    public void eachChild() {
        logger.log("Execute " + this.name);
        for (Component c : list) {
            c.eachChild();
        }
    }
}
