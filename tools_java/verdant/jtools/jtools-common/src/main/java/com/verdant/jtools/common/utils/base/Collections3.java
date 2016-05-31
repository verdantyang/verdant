package com.verdant.jtools.common.utils.base;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Author: verdant
 * Desc: Collections工具集
 */
public class Collections3 {

	/**
	 * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔
	 * @param collection
	 * @param separator
     * @return
     */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入suffix
	 * @param collection
	 * @param prefix
	 * @param suffix
     * @return
     */
	public static String convertToString(final Collection collection, final String prefix, final String suffix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(suffix);
		}
		return builder.toString();
	}

	/**
	 * 判断是否为空.
	 * @param collection
	 * @return
     */
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 判断是否为空.
	 * @param collection
	 * @return
     */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	/**
	 * 统计List中重复元素的次数
	 * @param list
	 * @param <T>
     * @return
     */
	public static <T> Map<T, Integer> groupCountList(List<T> list) {
		Map<T, Integer> result = new HashMap<>();
		for (T elem : list) {
			if (result.containsKey(elem))
				result.put(elem, Integer.valueOf(result.get(elem)) + 1);
			else
				result.put(elem, 1);
		}
		return result;
	}

	/**
	 * 取得Collection的第一个元素，如果collection为空返回null.
	 * @param collection
	 * @param <T>
     * @return
     */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 获取Collection的最后一个元素 ，如果collection为空返回null.
	 * @param collection
	 * @param <T>
     * @return
     */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 当类型为List时，直接取得最后一个元素 。
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 其他类型通过iterator滚动到最后一个元素.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 拼接列表
	 * @param a
	 * @param b
	 * @param <T>
     * @return
     */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 获取差集
	 * @param a
	 * @param b
	 * @param <T>
     * @return
     */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 获取交集
	 * @param a
	 * @param b
	 * @param <T>
     * @return
     */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
