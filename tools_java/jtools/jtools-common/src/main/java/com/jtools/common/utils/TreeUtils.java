package com.jtools.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {
	public static List parseTree(List list,String primary_nodeName,String parent_nodeName,String root_nodeValue,String[] extraFields,boolean expanded) throws Exception{
		Map map=null;
		List root=new ArrayList();
		if (list==null||list.isEmpty()) {
			return root;
		}
		Field fparent=list.get(0).getClass().getDeclaredField(parent_nodeName);
		fparent.setAccessible(true);
		Field fprimary=list.get(0).getClass().getDeclaredField(primary_nodeName);
		fprimary.setAccessible(true);
		Field ftemp=null;
		for (Object obj : list) {
			map=new HashMap();
			String parent =  String.valueOf(fparent.get(obj));
			String primary =  String.valueOf(fprimary.get(obj));
			if(parent.equals(root_nodeValue))
			{
//				System.out.println("primary:"+primary);
				//root 添加 对象
				List childList=extraChildNode(list, obj, primary_nodeName, parent_nodeName,extraFields,expanded);
				for (String fstr : extraFields) {
					ftemp=obj.getClass().getDeclaredField(fstr);
					ftemp.setAccessible(true);
					map.put(fstr,ftemp.get(obj));
				}
				map.put("expanded", expanded);
				map.put("iconCls","");
				map.put("leaf", false);
				map.put("children",childList);
			    root.add(map);
			}
		}
		fparent.setAccessible(false);
		fprimary.setAccessible(false);
		return root;
	}
	
	private static List extraChildNode(List list, Object object,String primary_nodeName,String parent_nodeName,String[] extraFields,boolean expanded)throws Exception{
		
		List child=new ArrayList();
		Field fparent=list.get(0).getClass().getDeclaredField(parent_nodeName);
		fparent.setAccessible(true);
		Field fprimary=list.get(0).getClass().getDeclaredField(primary_nodeName);
		fprimary.setAccessible(true);
		Field ftemp=null;
		String primary =  String.valueOf(fprimary.get(object));
		for (Object obj : list) {
			String parent =  String.valueOf(fparent.get(obj));
			if(primary.equals(parent)){
//				System.out.println(primary+"  child:"+fprimary.get(obj));
				Map map=new HashMap();
				List childList=extraChildNode(list, obj, primary_nodeName, parent_nodeName,extraFields,expanded);
				for (String fstr : extraFields) {
					ftemp=obj.getClass().getDeclaredField(fstr);
					ftemp.setAccessible(true);
					map.put(fstr,ftemp.get(obj));
					
				}
				map.put("expanded", expanded);
				map.put("iconCls","");
				map.put("leaf", childList.isEmpty());
				map.put("children",childList);
				child.add(map);
			    
			}
		}
		fparent.setAccessible(false);
		fprimary.setAccessible(false);
		return child;
	}
}
