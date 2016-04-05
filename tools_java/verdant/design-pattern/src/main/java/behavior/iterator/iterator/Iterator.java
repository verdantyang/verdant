package behavior.iterator.iterator;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   抽象迭代器
 */
public interface Iterator {
    /**
     * 迭代方法：移动到第一个元素
     */
    void begin();
    /**
     * 迭代方法：移动到下一个元素
     */
    void next();
    /**
     * 迭代方法：是否存在下一个元素
     */
    boolean hasNext();
    /**
     * 迭代方法：返还当前元素
     */
    Object current();
}
