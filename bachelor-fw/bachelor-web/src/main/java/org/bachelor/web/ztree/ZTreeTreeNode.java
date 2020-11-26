package org.bachelor.web.ztree;

import java.util.List;

public class ZTreeTreeNode {

	private String id;
	private String name;
	private boolean hasChildren;
	private String icon;
	private String iconClose;
	private String iconOpen;
	private String iconSkin;
	private String isHidden;
	private boolean isParent;
	private String nodeType;
	private String url;
	private List<ZTreeTreeNode> children ;
	private String flag;
	 
	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
  
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ZTreeTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeTreeNode> children) {
		this.children = children;
	}
	
	
}
