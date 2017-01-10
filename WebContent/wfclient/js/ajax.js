/**
 * 创建一个xmlhttp,封装了跨浏览器的创建方法.
 * @return 返回xmlhttp对象.
 * @type XMLHttp
        [
        'MSXML2.XMLHTTP.3.0',
        'MSXML2.XMLHTTP',
        'Microsoft.XMLHTTP'
        ]
 */
function createXMLHttpRequest(){
		if (window.XMLHttpRequest) return(new XMLHttpRequest());
		/*
		var arr_t = [
		"MSXML2.XMLHTTP.4.0",
		"MSXML2.XMLHTTP.3.0",
		"MSXML2.XMLHTTP.2.6",
		"MSXML2.XMLHTTP",
		"Microsoft.XMLHTTP",
		"MSXML.XMLHTTP"
		];
		*/
		var arr_t = [
			'MSXML2.XMLHTTP.3.0',
			'MSXML2.XMLHTTP',
			'Microsoft.XMLHTTP'
        ]
		for(var i=0; i<arr_t.length; i++) {
			try {				
				return new ActiveXObject(arr_t[i]);
			}catch(e) {
			}
		}
		return null ;
}

/**
 * ajax方式方法调用
 *
 */
function callMethodByAjax(requestMethod, requestContextPath, className, methodName, args, callback) {
	var url = requestContextPath + "/wfclient/ajax.do";
	var requestHttpXml = createXMLHttpRequest();
	
	requestHttpXml.open(requestMethod, url, true);
	
	requestHttpXml.onreadystatechange = function(){
		onreadyCallback(requestHttpXml, callback);
	};
	var paramsString = "";
	if (args != null && typeof(args) == "object") {
		paramsString = args.toJSONString();
	}
	
	var domString = [
		'<root>',
		'    <class>' + className + '</class>',
		'    <method>' + methodName + '</method>',
		'    <params>' + paramsString + '</params>',
		'</root>'
		];
	requestHttpXml.send(domString.join('\n'));
}

/**
 * 返回值回调
 *
 */
function onreadyCallback(requestHttpXml, callback) {
	if (callback == undefined || callback == 'undefined' || callback == "" || callback == null || callback == "null") {
		return;
	}
	if (!requestHttpXml || requestHttpXml.readyState != 4) return;
	try {
		var returnValueText = requestHttpXml.responseText;
		if (typeof(callback) == 'function') {
			var returnValueObj = returnValueText.parseJSON();
			callback(returnValueObj);
		} else {
			eval(callback + "(" + returnValueText + ");");
		}
	} catch(e) {
		alert(e);
	}	
}