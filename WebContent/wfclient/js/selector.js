var currentSelectorInfo = {
			 ids:"",
			 names:"",
			 typeCodes:"",
			 maxCount:1,
			 textName:"",
			 needReturn:true,
			 callback:"",
			 callbackParam:""
			};

/**
 * 打开资源选择窗口
 *
 */
function openResourceSelectorWindow(requestContextPath, textName, loader, loaderParam, resourceTitle, maxCount, needReturn, callback, callbackParam) {
	if (maxCount <= 0) {
		maxCount = 1;
	}
	if (needReturn) {
		currentSelectorInfo.ids = "";
		currentSelectorInfo.names = "";
		currentSelectorInfo.typeCodes = "";
		var selectObj = getElementObject(document, textName);
		if (!selectObj || selectObj == null) {
			currentSelectorInfo.ids = getElementObject(document, textName + ".ids").value;
			currentSelectorInfo.names = getElementObject(document, textName + ".names").value;
			currentSelectorInfo.typeCodes = getElementObject(document, textName + ".typeCodes").value;
		} else {
			for (var i = 0; i < selectObj.options.length; i++) {
				var values = selectObj.options[i].value.split(",");	
					
				currentSelectorInfo.ids += "," + values[1];
				currentSelectorInfo.names += "," + selectObj.options[i].text;			
				currentSelectorInfo.typeCodes += "," + values[0];
			}
			if (currentSelectorInfo.ids.length > 0 && currentSelectorInfo.ids.charAt(0) == ",") {
				currentSelectorInfo.ids = currentSelectorInfo.ids.substring(1);
			}
			if (currentSelectorInfo.names.length > 0 && currentSelectorInfo.names.charAt(0) == ",") {
				currentSelectorInfo.names = currentSelectorInfo.names.substring(1);
			}
			if (currentSelectorInfo.typeCodes.length > 0 && currentSelectorInfo.typeCodes.charAt(0) == ",") {
				currentSelectorInfo.typeCodes = currentSelectorInfo.typeCodes.substring(1);
			}	
		}
	}
	
	currentSelectorInfo.maxCount = maxCount;
	currentSelectorInfo.textName = textName;
	currentSelectorInfo.needReturn = needReturn;
	currentSelectorInfo.callback = callback;
	currentSelectorInfo.callbackParam = callbackParam;
	if (isIEBrowser()) {
		var retrunValueSelector = window.showModalDialog(requestContextPath + "/wfclient/tag/resourceSelectorTag.jsp?loader=" + loader + "&loaderParam=" + encodeURI(encodeURI(loaderParam)) + "&title=" + encodeURI(encodeURI(resourceTitle)), 
						currentSelectorInfo, "dialogWidth:430px;dialogHeight:380px;center:yes;status:no;unadorned:no");	
		
		if (needReturn) {
			setSelector(document, textName, retrunValueSelector);
		}
		
		if (typeof(retrunValueSelector) == 'undefined') {
			return;
		}
		
		if (callback == undefined || callback == 'undefined' || callback == "" || callback == null || callback == "null") {
			return;
		}
		
		var retrunValueSelectorWrapper = new Array();
		retrunValueSelectorWrapper = retrunValueSelectorWrapper.concat(retrunValueSelector);
		
		eval(callback + "( " + retrunValueSelectorWrapper.toJSONString() + ", '" + callbackParam + "');");		

	} else {
		window.open(requestContextPath + "/wfclient/tag/resourceSelectorTag.jsp?loader=" + loader + "&loaderParam=" + encodeURI(encodeURI(loaderParam)) + "&title=" + encodeURI(encodeURI(resourceTitle)),"",
				"width=430px,height=380px,location=no,menubar=no,modal=yes");		
	}
}

/**
 * 返回值的回设
 *
 */
function setSelector(documentObj, textName, retrunValueSelectorArray) {
	if(typeof(retrunValueSelectorArray) == "object"){		
		var selectObj = getElementObject(documentObj, textName);
		if (!selectObj || selectObj == null) {
			var typeCodesObj = getElementObject(documentObj, textName + ".typeCodes");
			var namesObj = getElementObject(documentObj, textName + ".names");
			var idsObj = getElementObject(documentObj, textName + ".ids");
			
			var textNames = "";
			var textIDs = "";
			var textTypes = "";
			
			for (var i = 0; i < retrunValueSelectorArray.length; i++) {
				textTypes = textTypes + "," + retrunValueSelectorArray[i].typeCode;
				textNames = textNames + "," + retrunValueSelectorArray[i].name;
				textIDs = textIDs + "," + retrunValueSelectorArray[i].id;				
			}
			if (textNames.length > 0 && textNames.charAt(0) == ",") {
				textNames = textNames.substring(1);
			}
			if (textIDs.length > 0 && textIDs.charAt(0) == ",") {
				textIDs = textIDs.substring(1);
			}
			if (textTypes.length > 0 && textTypes.charAt(0) == ",") {
				textTypes = textTypes.substring(1);
			}
			
			typeCodesObj.value = textTypes;
			namesObj.value = textNames;
			idsObj.value = textIDs;
		} else {	
			for(var i = selectObj.options.length - 1; i >= 0; i--) {
				selectObj.remove(i);
			}			
			for (var i = 0; i < retrunValueSelectorArray.length; i++) {	
				var nodeValue = retrunValueSelectorArray[i].typeCode + "," + retrunValueSelectorArray[i].id;							
				var oOption = documentObj.createElement("OPTION");
				oOption.text = retrunValueSelectorArray[i].name;
				oOption.value = nodeValue;
				oOption.selected = true;
				selectObj.options.add(oOption);			
			}
			adjustSelectWidth(selectObj);
		}	
	}
}

/**
 * 取得元素名称所对应的对象
 *
 */
function getElementObject(documentObj, elemName){
	if (typeof(elemName)=='object'){
		return elemName;
	}
	var elems = documentObj.getElementsByName(elemName);
	if (!elems) {
		return null;
	}
	if(isIEBrowser){
		for(var i=0;i<elems.length;i++){
			if(elems[i].name == elemName){
				return elems[i];
			}
		}
	}else if(elems.length>0){
		return elems[0];
	}
	return null;
}