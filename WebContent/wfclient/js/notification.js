function get(id){
	if(id){
		return document.getElementById(id) || {};
	}else{
		return {};
	}
}

