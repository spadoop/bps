
/**
 * 此类是为了修复IE9的流程图显示
 * 需要
 * 1.修改web.xml，增加
 *    <jsp-config>
 * <taglib>
 * <taglib-uri>http://www.kingmed.com</taglib-uri> 
 * <taglib-location>/WEB-INF/tlds/kingmed.tld</taglib-location> 
 * </taglib>
 * </jsp-config>
 *
 *  2. 增加kingmd.tld，内容为
 *  
 *  <?xml version="1.0" encoding="UTF-8"?>
 *<!DOCTYPE taglib PUBLIC "-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN" "http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd">
 *<taglib>
 *	<tlibversion>1.2</tlibversion>
 *	<jspversion>1.1</jspversion>
 *	<shortname>km</shortname>
 *	<uri>http://www.kingmed.com.cn</uri>
 *	<tag>
 *		<name>processGraphDecorate</name>
 *		<tagclass>com.eos.workflow.web.taglib.ProcessGraphDecorateTag</tagclass>
 *		<bodycontent>JSP</bodycontent>
 *	</tag>
 *</taglib>
 *   
 */
package cn.edu.sysu.bpm.taglib;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class ProcessGraphDecorateTag extends BodyTagSupport {

    //----标签开始时调用此方法-------
    public int doStartTag(){    	

    	return EVAL_BODY_BUFFERED;
    }
    /**--- 调用此方法-------
     * 在style的top left width height数值后加入px，以适应IE9
     */
    public int doAfterBody( ){
      try{
    	
 		BodyContent body =  getBodyContent(); 
		String code = body.getString(); 
		JspWriter out = body.getEnclosingWriter(); 
        if(code!=null) {
            out.print( code.replaceAll("(left:|top:|width:|height:)(\\d*\\.*\\d*)(;)","$1$2px$3"));
        }
		return SKIP_BODY;
      }catch(Exception e){
        e.printStackTrace();
        return SKIP_BODY;
      }
    }
    
    public static void main(String[] args) {
    	String str = "left:@left,[top:\"@top\"]";
    	str=str.replaceAll("(\"*)(@\\w+)(\"*)","null");
    	        
    	System.out.println(str);
    }
}