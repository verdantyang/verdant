package structural.composite.leaf;

import structural.composite.component.Component;
import utils.DebugLog;
import utils.DebugLogFactory;
import utils.DesignPatternEnum;

/**
 * Author: verdant
 * Create: 2016/4/18
 * Desc:   叶子节点
 */
public class Leaf extends Component {

    private static final DebugLog logger = DebugLogFactory.getLogger(Leaf.class, DesignPatternEnum.Composite);

    public Leaf(String name) {
        super(name);
    }

    // 叶子节点不具备添加、删除功能
    @Override
    public void add(Component c) {
    }

    @Override
    public void remove(Component c) {
    }

    // 叶子节点显示自己的执行结果
    @Override
    public void eachChild() {
        logger.log("Execute " + this.name);
    }
}
