/**
 * 功能：按钮点击事件,扩展和收缩菜单
 * 
 */
function doExpandOrShink(requestContextpath, imgObj, ChildNodesDivID, isRoot, nodeType, nodeValue, childLoader, loaderParam) {
	   
	var divObj = (document.getElementById(ChildNodesDivID) ? document.getElementById(ChildNodesDivID) : ChildNodesDivID);
	
	if(divObj.style.display == "none") {
		divObj.style.display = "";
		if (isRoot || isRoot == 'true'){
			imgObj.src = requestContextpath + "/wfclient/images/tree/root_tree_icon.gif"; 
		} else {
			imgObj.src = requestContextpath + "/wfclient/images/tree/subtree_extend_icon.gif"; 
		}  
	} else {  
		divObj.style.display = "none";
		if (isRoot || isRoot == 'true') {
			imgObj.src = requestContextpath + "/wfclient/images/tree/root_tree_icon.gif"; 
		} else {
			imgObj.src = requestContextpath + "/wfclient/images/tree/subtree_fold_icon.gif"; 
		}
	}	
	
	if (!divObj.getElementsByTagName("SPAN") || divObj.getElementsByTagName("SPAN") == null || divObj.getElementsByTagName("SPAN").length == 0) {
		var args = new Array(requestContextpath, isRoot, nodeType, nodeValue, loaderParam);
		var imgOldSrc = imgObj.src;
		imgObj.src = requestContextpath + "/wfclient/images/tree/loading.gif"; 
		callMethodByAjax("post", requestContextpath, childLoader, "loadChild", args, function(returnChildernNodesArray) {
			if (returnChildernNodesArray.success) {
				createAllChildren(divObj, returnChildernNodesArray.returnValue);				
			} else {
				alertValidateMessage(returnChildernNodesArray.exception);
			}
			imgObj.src = imgOldSrc; 
		});
	}
} 

/**
 * 资源节点信息
 *
 */
function ResourceTreeNode() {
	this.leafNode = true;
	this.nodeType = "";
	this.nodeLabel = "";
	this.nodeValue = "";
	this.rootNode = false;
	this.icon = "";
	this.requestContextPath = "";
	this.onClickFunc = "";
	this.onDblclickFunc = "";
	this.displayRoot = true;
	this.childLoader = "";
}

/**
 * 创建所有子节点
 *
 */
function createAllChildren(parentDivObj, childNodesArray) {
	if (!parentDivObj || parentDivObj == null) {
		return null;
	}
	if (!childNodesArray || childNodesArray == null || typeof(childNodesArray) != 'object') {
		return null;
	}
	var ULObj = document.createElement("UL");
	ULObj.style.cssText = "margin:0;padding-left:20px;list-style:none;";
	for (var i = 0; i < childNodesArray.length; i++) {
		var LIObj = document.createElement("LI");
		createChildNode(LIObj, childNodesArray[i]);
		ULObj.appendChild(LIObj);
	}
	parentDivObj.appendChild(ULObj);
}

/**
 * 创建一个子节点
 *
 */
function createChildNode(parentObj, childNode) {
	if (!parentObj || parentObj == null) {
		return null;
	}
	if (!childNode || childNode == null) {
		return null;
	}
	var childDivObj = document.createElement("DIV");
	var divInnerHtml = "";
	var childDivID = getNextID();	
	if (childNode.leafNode) {
		divInnerHtml += getNodeSpanString(childNode);
	} else {			
		divInnerHtml = " <img onclick=\"doExpandOrShink('" + childNode.requestContextPath + "', this, '" + childDivID + "', false, '" + childNode.nodeType + "', '" + childNode.nodeValue + "', '" + childNode.childLoader + "');\"";
		divInnerHtml += " src=\"" + childNode.requestContextPath + "/wfclient/images/tree/subtree_fold_icon.gif\">";
		divInnerHtml += getNodeSpanString(childNode);
		divInnerHtml += " <div id=\"" + childDivID + "\" style=\"display:none\"/>";
	}
	
	childDivObj.innerHTML = divInnerHtml;
	
	parentObj.appendChild(childDivObj);
	
	return document.getElementById(childDivID);
}

/**
 * 一个子节点的表示
 *
 */
function getNodeSpanString(resourceNode) {
	var spanString = " <span";
	if (trimString(resourceNode.onClickFunc).length > 0) {
		spanString += " onClick=\"" + resourceNode.onClickFunc + "('" + resourceNode.nodeType + "', '" + resourceNode.nodeLabel + "', '" + resourceNode.nodeValue + "')\"";
	}
	if (trimString(resourceNode.onDblclickFunc).length > 0) {
		spanString += " onDblClick=\"" + resourceNode.onDblclickFunc + "('" + resourceNode.nodeType + "', '" + resourceNode.nodeLabel + "', '" + resourceNode.nodeValue + "')\"";
	}
	spanString += ">";
	if (trimString(resourceNode.icon).length > 0) {
		spanString += " <img src=\"" + resourceNode.icon + "\">";
	}
	if (trimString(resourceNode.nodeLabel).length > 0) {
		spanString += resourceNode.nodeLabel;
	} else {
		spanString += resourceNode.nodeValue;
	}
	spanString += " </span>";
	return spanString;
}