package com.verdant.jtools.common.generic;

import java.io.Closeable;

/**
 * 第三方工具管理
 *
 * @author verdant
 * @since 2016/5/31
 */
public abstract class AbstractManager implements Closeable {

    public abstract void start();
}
