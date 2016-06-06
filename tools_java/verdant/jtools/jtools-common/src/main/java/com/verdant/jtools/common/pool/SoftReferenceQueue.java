package com.verdant.jtools.common.pool;

import com.verdant.jtools.common.pool.AbstractClient;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * 软引用队列
 *
 * @author verdant
 * @since 2016/06/06
 */
class SoftReferenceQueue<T> implements Queue<T> {
    private Queue<SoftReference<AbstractClient>> delegate;

    public SoftReferenceQueue(Queue<?> delegate) {
        this.delegate = delegate;
    }

    public T poll() {
        while(true) {
            SoftReference ref;
            if((ref = (SoftReference)this.delegate.poll()) != null) {
                T res;
                if((res = (T)ref.get()) == null) {
                    continue;
                }

                return res;
            }

            return null;
        }
    }

    public boolean offer(T e) {
        return this.delegate.offer(new SoftReference(e));
    }

    public boolean add(T e) {
        return this.delegate.add(new SoftReference(e));
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    public void clear() {
        this.delegate.clear();
    }

    public boolean equals(Object o) {
        return this.delegate.equals(o);
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }

    public String toString() {
        return this.getClass().getSimpleName() + super.toString();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public T remove() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public T element() {
        throw new UnsupportedOperationException();
    }

    public T peek() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
