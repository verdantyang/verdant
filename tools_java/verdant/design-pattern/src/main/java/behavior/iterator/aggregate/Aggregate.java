package behavior.iterator.aggregate;

import behavior.iterator.iterator.Iterator;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   抽象集合
 */
public interface Aggregate {
    Iterator createIterator();
}
