/**
 * 去掉左右两边的空格.
 *
 */
function trimString(str){

	if(typeof(str) == "object") str = str.value;
	
	if(str !== '' && (!str.replace || !str.length)){ 
		return str; 
	}
	var re = (/^\s+|\s+$/g);
	return str.replace(re, '');
}

/**
 * 是否是IE浏览器
 *
 */
function isIEBrowser() {
	var ua = navigator.userAgent.toLowerCase();
	return ua.indexOf("msie") > -1;
}

/**
 * 是否是Firefox浏览器
 *
 */
function isFirefoxBrowser() {
	var ua = navigator.userAgent.toLowerCase();
	return ua.indexOf("firefox") > -1;
}

/**
 * 
 * 通过标签名称取得父元素.
 */
function getParentByTagName(obj, tagName){
	if(tagName && obj){
		tagName = tagName.toLowerCase();
		while( (obj = obj.parentNode) ){
			if(obj.tagName && obj.tagName.toLowerCase()==tagName){
				return obj;
			}
		}
		return null;
	}else{
		return obj ? obj : null;
	}
}

/**
 * 根据name获取元素
 * @param {Object} elemName
 */
function getElementObjectByName(elemName){
	if (typeof(elemName)=='object'){
		return elemName;
	}
	var elems = document.getElementsByName(elemName);
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

/**
 * 弹出校验消息
 * 
 * validateObj 校验对象，或者其名称
 * message 校验消息
 *
 */
function alertValidateMessage(message, validateObj) {
	alert(message);
	if (validateObj == undefined || validateObj == 'undefined' || validateObj == "" || validateObj == null || validateObj == "null") {
		return;
	}
	validateObj.focus();
}

var NextUUID = 1;

/**
 * 取得唯一ID
 *
 */ 
function getNextID() {
	NextUUID = NextUUID + 1;
	var id = [
		"ID_",
		new Date().getTime() + '',
		NextUUID + ''
		];
	return id.join('');
}

/**
 * 调整select宽度
 *
 */
function adjustSelectWidth(selectObj) {
	if (selectObj.options.length > 0) {
		var maxLength = 0;
		for(var i = 0; i < selectObj.options.length; i++) { 
			var length = getBytesLen(selectObj.options[i].text);
			if (length > maxLength) {
				maxLength = length;
			}
		}
		if (maxLength > 25) {
			selectObj.style.width = maxLength * 8 + "px";
		}
		
	} else {
		selectObj.style.width = "150px";
	}
}

/**
 * 获得str字符串的单字节长度,一个汉字算两个字符.
 * @param {Object} str
 */
function getBytesLen(str){
	if(str){
		var reg = /[^\x00-\xff]/g;
		return (str+'').replace(reg,"aa").length;
	}else{
		if(str==""){
			return 0;
		}
		return -1;
	}
}
