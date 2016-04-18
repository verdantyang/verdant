package structural.composite.component;

/**
 * Author: verdant
 * Create: 2016/4/12
 * Desc:   实例化组件
 */
public abstract class Component {
    protected String name = null;

    public Component(String name) {
        this.name = name;
    }

    /**
     * 添加子节点
     *
     * @param component
     */
    public abstract void add(Component component);

    /**
     * 删除子节点
     *
     * @param component
     */
    public abstract void remove(Component component);

    /**
     * 遍历子节点
     */
    public abstract void eachChild();
}
