package org.bachelor.web.ztree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


@SuppressWarnings("unchecked")
public class ZTreeTreeUtil {

	
	public static List toZTreeNode(List domainList, ZTreeNodeAdapter adapter) {
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		if (domainList == null) {
			return nodeList;
		}
		for (Object domain : domainList) {
			ZTreeTreeNode node = adapter.toZTreeNodeModel(domain);
			Set childrenSet = adapter.getChildren(domain);
			if (childrenSet != null && childrenSet.size() > 0) {
				List children = new ArrayList(childrenSet);
				if (children == null || children.size() == 0) {
					node.setHasChildren(false);
					node.setIsParent(false);
				} else {
					node.setHasChildren(true);
					node.setIsParent(true);
					List<ZTreeTreeNode> childNode = toZTreeNode(children,
							adapter);
					node.setChildren(childNode);
				}
			}
			nodeList.add(node);
		}
		return nodeList;
	}

	public static List toZTreeNodeLite(List domainList,
			ZTreeNodeAdapter adapter) {
		List<ZTreeTreeNode> nodeList = new ArrayList<ZTreeTreeNode>();
		if (domainList == null) {
			return nodeList;
		}
		for (Object domain : domainList) {
			ZTreeTreeNode node = adapter.toZTreeNodeModel(domain);
			Set childrenSet = adapter.getChildren(domain);
			if (childrenSet != null && childrenSet.size() > 0) {
				List children = new ArrayList(childrenSet);
				if (children == null || children.size() == 0) {
					node.setHasChildren(false);
					node.setIsParent(false);
				} else {
					node.setHasChildren(true);
					node.setIsParent(true);
				}
			}
			nodeList.add(node);
		}
		return nodeList;
	}

}
