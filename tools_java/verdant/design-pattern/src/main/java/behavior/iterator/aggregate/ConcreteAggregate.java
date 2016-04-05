package behavior.iterator.aggregate;

import behavior.iterator.iterator.Iterator;

/**
 * Author: verdant
 * Create: 2016/4/5
 * Desc:   实例化集合
 */
public class ConcreteAggregate implements Aggregate {
    private Object[] objArray = null;

    public ConcreteAggregate(Object[] objArray) {
        this.objArray = objArray;
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator();
    }

    /**
     * 内部成员类，具体迭代子类
     */
    private class ConcreteIterator implements Iterator
    {
        private int index = 0;
        private int size = 0;

        public ConcreteIterator(){
            this.size = objArray.length;
            index = 0;
        }

        @Override
        public void begin() {
            index = 0;
        }

        @Override
        public void next() {
            if(index < size)
            {
                index ++;
            }
        }

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public Object current() {
            return objArray[index];
        }
    }
}
