package org.bachelor.web.ztree;

import java.util.Set;


public interface ZTreeNodeAdapter<T> {

	public ZTreeTreeNode toZTreeNodeModel(T t);
	
	public Set<T> getChildren(T t);
}
