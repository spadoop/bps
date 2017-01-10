package cn.edu.sysu.bpm.common;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Description: 静态方法类，如字符转换等
 * 
 * @author Zhong Chong-Jun
 * 
 */
public class StaticMethod {

 
	/**
	 * classpath标识
	 */
	public final static String CLASSPATH_FLAG = "classpath:";

	/**
	 * 将method对象转换成package.class.method(parametertypes)
	 * 
	 * @param method
	 *            方法对象
	 * @return package.class.method(parametertypes)
	 */ 
	 

	public static String method2class(Method method) {
		// 方法名称
		String methodName = method.getName();
		// 类名
		String clz = method.getDeclaringClass().getName();
		// 方法类型
		Class paramTypes[] = method.getParameterTypes();
		// 存放方法类型的临时对象
		String types = "";
		if (paramTypes != null && paramTypes.length > 0) {
			for (int i = 0; i < paramTypes.length; i++) {
				types += paramTypes[i].getName() + ",";
			}
			types = types.substring(0, types.length() - 1);
		}

		return clz + "." + methodName + "(" + types + ")";
	}

	/**
	 * classpath标识
	 */
 
	// 得到的是当前的classpath的绝对URI路径
	public final static URL URI = StaticMethod.class.getResource("/");

	/**
	 * 取filePath的绝对路径
	 * 
	 * @param filePath
	 *            文件路径
	 * @deprecated 尽量命名用getFileUrl及getFilePathForUrl替代
	 * @see StaticMethod#getClasspath()
	 * @return
	 */
	public static String getFilePath(String filePath) {
		if (filePath != null) {
			if (filePath.length() > CLASSPATH_FLAG.length()) {
				if (CLASSPATH_FLAG.equals(filePath.substring(0, CLASSPATH_FLAG
						.length()))) {
					filePath = getClasspath()
							+ filePath.substring(CLASSPATH_FLAG.length());
				}
			}
		}
		return filePath;
	}

	/**
	 * 取classpath路径
	 * 
	 * @return classpath路径
	 * @see StaticMethod#getFilePath(String)
	 * @see StaticMethod#getFileUrl(String)
	 */
	public final static String getClasspath() {
		// String classpath = Thread.currentThread().getContextClassLoader()
		// .getResource("").getPath()
		// + "";
		// return classpath.substring(1);
		return URI.getFile();

	}

	/**
	 * 获取filePath的url
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 *             创建url失败将抛出MalformedURLException
	 */
	public static URL getFileUrl(String filePath) throws FileNotFoundException {
		URL url = null;
		if (filePath != null) {
			if (filePath.length() > CLASSPATH_FLAG.length()) {
				if (CLASSPATH_FLAG.equals(filePath.substring(0, CLASSPATH_FLAG
						.length()))) {
					// url =
					// loader.getResource(filePath.substring(CLASSPATH_FLAG
					// .length()));
					try {
						// 创建url失败将抛出MalformedURLException
						// url = ClassLoaderUtil
						// .getExtendResource(getPathButClasspath(filePath));
						url = StaticMethod.class.getClassLoader().getResource(
								getPathButClasspath(filePath));
						URL url1 = StaticMethod.class
								.getResource(getPathButClasspath(filePath));
						URL url2 = Thread.currentThread()
								.getContextClassLoader().getResource(
										getPathButClasspath(filePath));
						// url = new URL(URI.toString()
						// + filePath.substring(CLASSPATH_FLAG.length()));
					} catch (Exception e) {
					    e.printStackTrace();
						throw new FileNotFoundException(filePath
								+ "is not found.");
					}

				} else {
					// TODO 有问题，需修改
				}
			}
		}
		return url;
	}

	/**
	 * web－inf同级路径
	 * 
	 * @return
	 */
	public static String getWebPath() throws FileNotFoundException {
		 

		// 因为类名为Application，因此 Application.class一定能找到
		String result = StaticMethod.class.getResource("StaticMethod.class")
				.toString();
		int index = result.indexOf("WEB-INF");
		if (index == -1) {
			index = result.indexOf("bin");
		}
		result = result.substring(0, index);
		if (result.startsWith("jar")) {
			// 当class文件在jar文件中时，返回”jar:file:/F:/ …”样的路径
			result = result.substring(10);
		} else if (result.startsWith("file")) {
			// 当class文件在jar文件中时，返回”file:/F:/ …”样的路径
			result = result.substring(6);
		}
		return result;

	}

	/**
	 * 去掉classpath
	 * 
	 * @param path
	 * @return
	 */
	private static String getPathButClasspath(String path) {
		return path.substring(CLASSPATH_FLAG.length());
	}

	/**
	 * 读java包时返回的路径
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getFilePathForUrl(String filePath)
			throws FileNotFoundException {
		URL url = getFileUrl(filePath);
		return url.getFile();
	}
  
	/**
	 * @see 字符处理方法：将首字符转换为大写
	 * @param string
	 * @return
	 */
	public static String firstToUpperCase(String string) {
		String post = string.substring(1, string.length());
		String first = ("" + string.charAt(0)).toUpperCase();
		return first + post;
	}

	/**
	 * @see 将中文格式转换成标准格式
	 * @see 例如：StaticMethod.getString("中文");
	 * @param para
	 *            String 中文字符串
	 * @return String para的标准格式的串
	 */
	public static String getString(String para) {
		String reStr = "";
		try {
			reStr = new String(para.getBytes("GB2312"), "ISO-8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * @see 修改iso到GB2312
	 * 
	 * @param para
	 *            String
	 * @return String
	 */

	public static String getPageString(String para) {
		String reStr = "";
		try {
			reStr = new String(para.getBytes("ISO-8859-1"), "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reStr;
	}

	public static String getPageUTF(String para) {
		String reStr = "";
		try {
			reStr = new String(para.getBytes("UTF-8"), "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * @see 将目标字符集转化成需要的字符集
	 * @param para
	 * @param srcCharset
	 * @param tarCharset
	 * reStr = new String(para.getBytes("ISO-8859-1"), "UTF-8");
	 * @return
	 */
	public static String getPageUTF(String para,String srcCharset, String tarCharset) {
		String reStr = "";
		try {
			reStr = new String(para.getBytes(srcCharset), tarCharset);
		} catch (Exception e) {
			reStr = "";
		}
		return reStr;
	}
	/**
	 * @see 得到一指定分隔符号分隔的vector 如：Vector nn =
	 *      StaticMethod.getVector("2003-4-5","-");
	 */
	public static Vector getVector(String string, String tchar) {
		StringTokenizer token = new StringTokenizer(string, tchar);
		Vector vector = new Vector();
		if (!string.trim().equals("")) {
			try {
				while (token.hasMoreElements()) {
					vector.add(token.nextElement().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vector;
	}

	/**
	 * @see 将一个字符串按照一定长度截为一个List
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static ArrayList getArrayList(String str, int size) {
		ArrayList vec = new ArrayList();
		String temp = "";
		str = str.trim();
		try {
			while (str.length() > size) {
				temp = str;
				vec.add(temp);
				str = str.substring(0, str.length() - size);
			}
			if (!str.equals("")) {
				vec.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}

	/**
	 * @see 得到一指定分隔符号分隔的ArrayList
	 * 
	 * @param string
	 * @param tchar
	 * @return
	 */
	public static ArrayList getArrayList(String string, String tchar) {
		StringTokenizer token = new StringTokenizer(string, tchar);
		ArrayList array = new ArrayList();
		if (!string.trim().equals("")) {
			try {
				while (token.hasMoreElements()) {
					array.add(token.nextElement().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return array;
	}

	/**
	 * @see 判断id值是否包含在数组中
	 * @param id
	 *            int id
	 * @param array
	 *            集合 数组
	 * @return
	 * @throws Exception
	 */
	public static boolean fHasId(int id, ArrayList array) throws Exception {

		boolean ret = false;
		int i = 0;
		try {
			while (i < array.size() && ret == false) {
				if (id == StaticMethod.nullObject2int(array.get(i))) {
					ret = true;
				}
				i++;
			}
		} catch (Exception e) {
			// BocoLog.error("StaticMethod.java", 0, "错误", e);
		}

		return ret;
	}

	/**
	 * @see 得到两个集合的交集，返回一个新的集合
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static ArrayList getArrayList(ArrayList array1, ArrayList array2) {

		ArrayList array = new ArrayList();
		try {
			for (int i = 0; i < array1.size(); i++) {
				int temp = StaticMethod.nullObject2int(array1.get(i));
				if (StaticMethod.fHasId(temp, array2) == true) {
					array.add(new Integer(temp));
				}
			}
		} catch (Exception e) {
			// BocoLog.error("StaticMethod.java", 0, "错误", e);
		}

		return array;
	}

	/**
	 * @see 得到两个集合的叉集，返回一个新的集合，即：在array1中，不在array2中
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static ArrayList getArrayList2(ArrayList array1, ArrayList array2) {

		ArrayList array = new ArrayList();
		int size1 = array1.size();
		int size2 = array2.size();
		if ((size2 == 0) || (size1 == 0)) {
			array = array1;
		} else {
			try {
				for (int i = 0; i < array1.size(); i++) {
					int temp = StaticMethod.nullObject2int(array1.get(i));
					if (StaticMethod.fHasId(temp, array2) == false) {
						array.add(new Integer(temp));
					}
				}
			} catch (Exception e) {
				// BocoLog.error("StaticMethod.java", 0, "错误", e);
			}
		}

		return array;
	}

	/**
	 * 字符转换函数
	 * 
	 * @param s
	 * @return output:如果字符串为null,返回为空,否则返回该字符串
	 */
	public static String nullObject2String(Object s) {
		String str = "";
		try {
			str = s.toString();
		} catch (Exception e) {
			str = "";
		}
		return str;
	}

	/**
	 * 将一个对象转换为String,如果
	 * 
	 * @param s
	 * @param chr
	 * @return
	 */
	public static String nullObject2String(Object s, String chr) {
		String str = chr;
		try {
			str = s.toString();
		} catch (Exception e) {
			str = chr;
		}
		return str;
	}

	/**
	 * 将一个对象转换为String,如果
	 * 
	 * @param s
	 * @return
	 */
	public static Integer nullObject2Integer(Object s) {
		return new Integer(StaticMethod.nullObject2int(s));
	}

	/**
	 * 将一个对象转换为整形
	 * 
	 * @param s
	 * @return
	 */
	public static int nullObject2int(Object s) {
		String str = "";
		int i = 0;
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 将对象转换为整形
	 * 
	 * @param s
	 * @param in
	 * @return
	 */
	public static int nullObject2int(Object s, int in) {
		String str = "";
		int i = in;
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = in;
		}
		return i;
	}

	public static Timestamp nullObject2Timestamp(Object s) {
		Timestamp str = null;
		try {
			str = Timestamp.valueOf(s.toString());
		} catch (Exception e) {
			str = null;
		}
		return str;
	}

	/**
	 * 字符转换函数如果字符串为null,返回为空,否则返回该字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String null2String(String s) {
		return s == null ? "" : s;
	}

	public static ArrayList getSubList(ArrayList list1, ArrayList list2) {
		ArrayList list = new ArrayList();
		int j = list1.size();
		int k = list2.size();
		if ((j > 0) && (k > 0)) {
			Collections.sort(list1);
			Collections.sort(list2);
			for (int i = 0; i < j; i++) {
				String temp = StaticMethod.nullObject2String(list1.get(i));
				for (int l = 0; l < k; l++) {
					String temp2 = StaticMethod.nullObject2String(list2.get(l));
					if (temp.equals(temp2)) {
						list.add(temp);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static Vector getSubVec(Vector list1, Vector list2) {
		Vector list = new Vector();
		int j = list1.size();
		int k = list2.size();
		if ((j > 0) && (k > 0)) {
			Collections.sort(list1);
			Collections.sort(list2);
			for (int i = 0; i < j; i++) {
				String temp = StaticMethod.nullObject2String(list1.get(i));
				for (int l = 0; l < k; l++) {
					String temp2 = StaticMethod.nullObject2String(list2.get(l));
					if (temp.equals(temp2)) {
						list.add(temp);
					}
				}
			}
		}
		return list;
	}
 
	/**
	 * 字符转换函数,如果字符串1为null,返回为字符串2,否则返回该字符串
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public static String null2String(String s, String s1) {
		return s == null ? s1 : s;
	}

	/**
	 * 字符转换函数:如果字符串1为null或者不能转换成整型,返回为0
	 * 
	 * @param s
	 * @return
	 */
	public static int null2int(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 字符转换函数:如果字符串1为null或者不能转换成整型,返回为0
	 * 
	 * @param s
	 * @return
	 */
	public static long null2Long(String s) {
		long i = 0;
		try {
			i = Long.parseLong(s);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 对象转换为long型
	 * 
	 * @param s
	 * @return
	 */
	public static long nullObject2Long(Object s) {
		long i = 0;

		String str = "";
		try {
			str = s.toString();
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = 0;
		}

		return i;
	}

	/**
	 * 将对象转换为long,如果无法转换则返回temp
	 * 
	 * @param s
	 * @param temp
	 * @return
	 */
	public static long nullObject2Long(Object s, long temp) {
		long i = temp;

		String str = "";
		try {
			str = s.toString();
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = temp;
		}

		return i;
	}

	/**
	 * 字符转换函数:如果字符串1为null或者不能转换成整型,返回为整型2
	 * 
	 * @param s
	 * @param in
	 * @return
	 */
	public static int null2int(String s, int in) {
		int i = in;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			i = in;
		}
		return i;
	}

	/**
	 * 字符串处理方法：
	 * 
	 * @param str
	 * @param dim
	 * @return
	 */
	public static ArrayList TokenizerString(String str, String dim) {
		return TokenizerString(str, dim, false);
	}

	/***************************************************************************
	 * 将输入的字符串str按照分割符dim分割成字符串数组并返回ArrayList字符串数组**** If the returndim flag is
	 * true, then the dim characters are also returned as tokens. Each delimiter
	 * is returned as a string of length one. If the flag is false, the
	 * delimiter characters are skipped and only serve as separators between
	 * tokens.
	 **************************************************************************/
	public static ArrayList TokenizerString(String str, String dim,
			boolean returndim) {
		str = null2String(str);
		dim = null2String(dim);
		ArrayList strlist = new ArrayList();
		StringTokenizer strtoken = new StringTokenizer(str, dim, returndim);
		while (strtoken.hasMoreTokens()) {
			strlist.add(strtoken.nextToken());
		}
		return strlist;
	}

	/**
	 * 字符串处理方法： 类似上面的方法,将输入的字符串str按照分割符dim分割成字符串数组,**** 并返回定长字符串数组
	 * 
	 * @param str
	 * @param dim
	 * @return
	 * @see TokenizerString
	 */

	public static String[] TokenizerString2(String str, String dim) {
		return TokenizerString2(str, dim, false);
	}

	/**
	 * 字符串处理方法：
	 * 
	 * @see TokenizerString
	 * @param str
	 * @param dim
	 * @param returndim
	 * @return
	 */
	public static String[] TokenizerString2(String str, String dim,
			boolean returndim) {
		ArrayList strlist = TokenizerString(str, dim, returndim);
		int strcount = strlist.size();
		String[] strarray = new String[strcount];
		for (int i = 0; i < strcount; i++) {
			strarray[i] = (String) strlist.get(i);
		}
		return strarray;
	}

	/**
	 * 
	 * @param v
	 * @param l
	 * @return
	 */
	public static String add0(int v, int l) {
		long lv = (long) Math.pow(10, l);
		return String.valueOf(lv + v).substring(1);
	}
 

	/**
	 * 编码处理方法：
	 * 
	 * @param s
	 * @return
	 */
	public static String fromScreen(String s) {
		if (s == null) {
			s = null2String(s);
		}
		s = fromBaseEncoding(s);
		s = toHtml(s);
		return s;
	}

	/**
	 * 编码处理方法：
	 * 
	 * @param s
	 * @return
	 */
	public static String toScreen(String s) {
		if (s == null) {
			s = null2String(s);
		}
		s = toBaseEncoding(s);
		s = fromHtml(s);
		return s;
	}

	/**
	 * 字符串处理方法：
	 * 
	 * @param s
	 * @param languageid
	 * @return
	 */
	public static String toScreenToEdit(String s, int languageid) {
		if (s == null) {
			s = null2String(s).trim();
		}
		s = toBaseEncoding(s);
		s = fromHtmlToEdit(s);
		return s;
	}

	/**
	 * 编码处理方法：转换GB2312到ISO-8859-1
	 * 
	 * @param s
	 * @return
	 */
	public static String toBaseEncoding(String s) {
		try {
			byte[] target_byte = s.getBytes("GB2312");
			return new String(target_byte, "ISO-8859-1");
		} catch (Exception ex) {
			return s;
		}
	}

	/**
	 * 编码处理方法：转换ISO-8859-1到GB2312
	 * 
	 * @param s
	 * @return
	 */
	public static String fromBaseEncoding(String s) {
		try {
			byte[] target_byte = s.getBytes("ISO-8859-1");
			return new String(target_byte, "GB2312");
		} catch (Exception ex) {
			return s;
		}
	}

	/**
	 * 字符串处理方法：转换html标签为可显示
	 * 
	 * @param s
	 * @return
	 */
	public static String toHtml(String s) {
		char c[] = s.toCharArray();
		char ch;
		int i = 0;
		StringBuffer buf = new StringBuffer();

		while (i < c.length) {
			ch = c[i++];

			if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == '\'') {
				buf.append("\\'");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '\n') {
				buf.append("<br>");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串处理方法：转换html标签为可显示
	 * 
	 * @param s
	 * @return
	 */
	public static String fromHtml(String s) {
		return StringReplace(s, "\\'", "\'");

	}

	/**
	 * 字符串处理方法：转换html标签为可显示
	 * 
	 * @param s
	 * @return
	 */
	public static String fromHtmlToEdit(String s) {
		return StringReplace(StringReplace(s, "<br>", ""), "\\'", "\'");
	}

	/**
	 * 数组处理方法：察看某数组对象是否有s对象
	 * 
	 * @param a
	 * @param s
	 * @return
	 */
	public static boolean contains(Object a[], Object s) {
		if (a == null || s == null) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != null && a[i].equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串处理方法：根据起始结束标记截断String c
	 * 
	 * @param c
	 * @param begin_tag
	 * @param end_tag
	 * @return
	 */
	public static String extract(String c, String begin_tag, String end_tag) {
		int begin = begin_tag == null ? 0 : c.indexOf(begin_tag);
		int len = begin_tag == null ? 0 : begin_tag.length();
		int end = end_tag == null ? c.length() : c
				.indexOf(end_tag, begin + len);

		if (begin == -1) {
			begin = 0;
			len = 0;
		}
		if (end == -1) {
			end = c.length();
		}
		return c.substring(begin + len, end);
	}

	/**
	 * 字符串处理：截取s1中的对象
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String remove(String s1, String s2) {
		int i = s1.indexOf(s2);
		int l = s2.length();
		if (i != -1) {
			return s1.substring(0, i) + s1.substring(i + l);
		}
		return s1;
	}

	/**
	 * 字符串处理：
	 * 
	 * @param s
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static String replaceChar(String s, char c1, char c2) {
		if (s == null) {
			return s;
		}

		char buf[] = s.toCharArray();
		for (int i = 0; i < buf.length; i++) {
			if (buf[i] == c1) {
				buf[i] = c2;
			}
		}
		return String.valueOf(buf);
	}

	/**
	 * 校验方法：是否含有@
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s) {
		int pos = 0;
		pos = s.indexOf("@");
		if (pos == -1) {
			return false;
		}
		return true;
	}

	/**
	 * 数组处理：将对象数组中的第i个对象与第j个对象对调
	 * 
	 * @param a
	 * @param i
	 * @param j
	 */
	public static void swap(Object a[], int i, int j) {
		Object t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/**
	 * 字符串处理方法：
	 * 
	 * @param sou
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String StringReplace(String sou, String s1, String s2) {
		int idx = sou.indexOf(s1);
		if (idx < 0) {
			return sou;
		}
		return StringReplace(sou.substring(0, idx) + s2
				+ sou.substring(idx + s1.length()), s1, s2);
	}

 

	/**
	 * 类型转换：字符串转换为整形
	 * 
	 * @param v
	 * @return
	 */

	public static int getIntValue(String v) {
		return getIntValue(v, -1);
	}

	/**
	 * 类型转换：字符串转换为整形如果例外则返回预给值def
	 * 
	 * @param v
	 * @return
	 */

	public static int getIntValue(String v, int def) {
		try {
			return Integer.parseInt(v);
		} catch (Exception ex) {
			return def;
		}
	}

	/**
	 * 类型转换：将给出的字符串v转换成浮点值返回，如果例外则返回预给值-1
	 * 
	 * @param v
	 * @return
	 */
	public static float getFloatValue(String v) {
		return getFloatValue(v, -1);
	}

	/**
	 * 类型转换：将给出的字符串v转换成浮点值返回，如果例外则返回预给值def
	 * 
	 * @param v
	 * @return
	 */
	public static float getFloatValue(String v, float def) {
		try {
			return Float.parseFloat(v);
		} catch (Exception ex) {
			return def;
		}
	}

 
 
 

	/**
	 * 时间处理方法：
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String addTime(String time1, String time2) {
		if (time1.equals("") || time2.equals("")) {
			return "00:00";
		} else {
			ArrayList timearray1 = TokenizerString(time1, ":");
			ArrayList timearray2 = TokenizerString(time2, ":");
			int hour1;
			int hour2;
			int min1;
			int min2;
			int hour;
			int min;
			hour1 = getIntValue((String) timearray1.get(0));
			min1 = getIntValue((String) timearray1.get(1));
			hour2 = getIntValue((String) timearray2.get(0));
			min2 = getIntValue((String) timearray2.get(1));
			if ((min1 + min2) >= 60) {
				hour = hour1 + hour2 + 1;
				min = min1 + min2 - 60;
			} else {
				hour = hour1 + hour2;
				min = min1 + min2;
			}
			if (hour < 10) {
				if (min < 10) {
					return "0" + hour + ":" + "0" + min;
				} else {
					return "0" + hour + ":" + "" + min;
				}
			} else {
				if (min < 10) {
					return "" + hour + ":" + "0" + min;
				} else {
					return "" + hour + ":" + "" + min;
				}
			}
		}
	}
	/**
	 * 根据时间 t1获得某段时间后的时间  
	 * @param t1       添加时间
	 * @param addtime  添加长度 比如1表示1（年 月 天）后的时间  
	 * @param addType  添加类型 年 月 天
	 * @return
	 */
	public static String addTime(String t1,int addtime,int addType){  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Calendar c1=Calendar.getInstance();   
        String time="";
        try {  
            c1.setTime(formatter.parse(t1));
            c1.add(addType,1);
            time=formatter.format(c1.getTime());
         } catch (Exception e) {  
            e.printStackTrace();  
            //System.out.print(e);
        }     
        return time;  
    } 
	/**
	 * 时间处理方法：
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String subTime(String time1, String time2) {
		if (time1.equals("") || time2.equals("")) {
			return "00:00";
		} else {
			ArrayList timearray1 = TokenizerString(time1, ":");
			ArrayList timearray2 = TokenizerString(time2, ":");
			int hour1;
			int hour2;
			int min1;
			int min2;
			int hour;
			int min;
			hour1 = getIntValue((String) timearray1.get(0));
			min1 = getIntValue((String) timearray1.get(1));
			hour2 = getIntValue((String) timearray2.get(0));
			min2 = getIntValue((String) timearray2.get(1));
			if ((min1 - min2) < 0) {
				hour = hour1 - hour2 - 1;
				min = min1 - min2 + 60;
			} else {
				hour = hour1 - hour2;
				min = min1 - min2;
			}
			if (hour < 10) {
				if (min < 10) {
					return "0" + hour + ":" + "0" + min;
				} else {
					return "0" + hour + ":" + "" + min;
				}
			} else {
				if (min < 10) {
					return "" + hour + ":" + "0" + min;
				} else {
					return "" + hour + ":" + "" + min;
				}
			}
		}
	}

	/**
	 * 获得每dimlen位的逗号
	 * 
	 * @param str
	 * @param dimlen
	 * @return
	 */
	public static String getFloatStr(String str, int dimlen) {
		int dicimalindex = str.indexOf(".");
		String decimalstr = "";
		if (dicimalindex != -1) {
			decimalstr = extract(str, ".", null);
		}
		String intstr = extract(str, null, ".");
		if (intstr.length() < (dimlen + 1)) {
			return str;
		}
		String beginstr = "";
		int thebeginlen = intstr.length() % dimlen;
		beginstr = intstr.substring(0, thebeginlen);
		intstr = intstr.substring(thebeginlen);
		int intstrcount = intstr.length() / dimlen;

		for (int i = 0; i < intstrcount; i++) {
			if (beginstr.equals("") || beginstr.equals("-")) {
				beginstr += intstr.substring(0, dimlen);
				intstr = intstr.substring(dimlen);
			} else {
				beginstr += "," + intstr.substring(0, dimlen);
				intstr = intstr.substring(dimlen);
			}
		}
		if (dicimalindex != -1) {
			return beginstr + "." + decimalstr;
		} else {
			return beginstr;
		}
	}

	/**
	 * 将字符串日期转成日历型
	 * 
	 * @param strDate
	 * @return
	 */
	public static GregorianCalendar String2Cal(String strDate) {
		GregorianCalendar calDate = new GregorianCalendar();
		strDate = StaticMethod.null2String(strDate).replaceAll("\\.0", "");
		Vector vecDate = StaticMethod.getVector(strDate, " ");
		if (vecDate.size() > 0 && vecDate.size() < 3) {
			if (vecDate.size() == 1) {
				Vector vecData = StaticMethod.getVector(String.valueOf(vecDate
						.elementAt(0)), "-");
				if (vecData.size() == 3) {
					int intYear = Integer.parseInt(String.valueOf(vecData
							.elementAt(0)));
					int intMonth = Integer.parseInt(String.valueOf(vecData
							.elementAt(1)));
					int intDay = Integer.parseInt(String.valueOf(vecData
							.elementAt(2)));
					calDate.set(Calendar.YEAR, intYear);
					calDate.set(Calendar.MONTH, intMonth - 1);
					calDate.set(Calendar.DATE, intDay);
				}
				calDate.set(Calendar.HOUR_OF_DAY, 0);
				calDate.set(Calendar.MINUTE, 0);
				calDate.set(Calendar.SECOND, 0);
			}
			if (vecDate.size() == 2) {
				Vector vecData = StaticMethod.getVector(String.valueOf(vecDate
						.elementAt(0)), "-");
				Vector vecTime = StaticMethod.getVector(String.valueOf(vecDate
						.elementAt(1)), ":");
				if (vecData.size() == 3) {
					int intYear = Integer.parseInt(String.valueOf(vecData
							.elementAt(0)));
					int intMonth = Integer.parseInt(String.valueOf(vecData
							.elementAt(1)));
					int intDay = Integer.parseInt(String.valueOf(vecData
							.elementAt(2)));
					calDate.set(Calendar.YEAR, intYear);
					calDate.set(Calendar.MONTH, intMonth - 1);
					calDate.set(Calendar.DATE, intDay);
				}
				if (vecTime.size() == 3) {
					int intHour = Integer.parseInt(String.valueOf(vecTime
							.elementAt(0)));
					int intMinute = Integer.parseInt(String.valueOf(vecTime
							.elementAt(1)));
					int intSecond = Integer.parseInt(String.valueOf(vecTime
							.elementAt(2)));
					calDate.set(Calendar.HOUR_OF_DAY, intHour);
					calDate.set(Calendar.MINUTE, intMinute);
					calDate.set(Calendar.SECOND, intSecond);
				}
			}
		}
		return calDate;
	}

	/**
	 * 将日历型转成日期字符串
	 * 
	 * @param calDate
	 * @return
	 */
	public static String Cal2String(GregorianCalendar calDate) {
		String strDate = "";
		strDate = String.valueOf(calDate.get(Calendar.YEAR));
		strDate = strDate + "-"
				+ String.valueOf(calDate.get(Calendar.MONTH) + 1);
		strDate = strDate + "-" + String.valueOf(calDate.get(Calendar.DATE));
		strDate = strDate + " "
				+ String.valueOf(calDate.get(Calendar.HOUR_OF_DAY));
		strDate = strDate + ":" + String.valueOf(calDate.get(Calendar.MINUTE));
		strDate = strDate + ":" + String.valueOf(calDate.get(Calendar.SECOND));
		return strDate;
	}

	/**
	 * @see 得到时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串 例如：返回'2003-8-10'
	 */
	public static String getDateString(int disday) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		String _yystr = "", _mmstr = "", _ddstr = "";
		int _yy = c.get(Calendar.YEAR);
		_yystr = _yy + "";
		int _mm = c.get(Calendar.MONTH) + 1;
		_mmstr = _mm + "";
		if (_mm < 10) {
			_mmstr = "0" + _mm;
		}
		int _dd = c.get(Calendar.DATE);
		_ddstr = _dd + "";
		if (_dd < 10) {
			_ddstr = "0" + _dd;
		}
		ls_display = "'" + _yystr + "-" + _mmstr + "-" + _ddstr + "'";
		return ls_display;
	}

	/**
	 * @see 得到时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串 例如：返回'2003-8-10'
	 */
	public static String getDateString(String strDateLimit, int disday) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date thisdate = dateFormat.parse(strDateLimit);
			c.setTime(thisdate);
		} catch (Exception e) {
		}
		c.set(Calendar.DATE, c.get(Calendar.DATE) + disday);
		String ls_display = dateFormat.format(c.getTime()).toString();
		return ls_display;
	}

	/**
	 * @see 得到时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串,例如：返回'2003-8-10 00:00:00'
	 */

	public static String getTimeString(int disday) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		int _yy = c.get(Calendar.YEAR);

		int _mm = c.get(Calendar.MONTH) + 1;

		int _dd = c.get(Calendar.DATE);

		ls_display = "'" + _yy + "-" + _mm + "-" + _dd + " 00:00:00'";
		return ls_display;
	}

	/**
	 * @see 得到时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串,例如：返回2003-8-10 00:00:00
	 */

	public static String getCurrentTimeString(int disday) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		int _yy = c.get(Calendar.YEAR);

		int _mm = c.get(Calendar.MONTH) + 1;

		int _dd = c.get(Calendar.DATE);

		ls_display = _yy + "-" + _mm + "-" + _dd + " 00:00:00";
		return ls_display;
	}

	/**
	 * @see 得到某一天开始的时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串,例如：返回'2003-8-10 00:00:00'
	 */

	public static String getTimeBeginString(int disday) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		int _yy = c.get(Calendar.YEAR);
		int _mm = c.get(Calendar.MONTH) + 1;
		int _dd = c.get(Calendar.DATE);
		ls_display = _yy + "-" + _mm + "-" + _dd + " 00:00:00";
		return ls_display;
	}

	/**
	 * @see 得到某一天结束的时间字符串
	 * @see 例如：StaticMethod.getDateString(-1),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串,例如：返回'2003-8-10 00:00:00'
	 */

	public static String getTimeEndString(int disday) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		int _yy = c.get(Calendar.YEAR);
		int _mm = c.get(Calendar.MONTH) + 1;
		int _dd = c.get(Calendar.DATE);
		ls_display = _yy + "-" + _mm + "-" + _dd + " 23:59:59";
		return ls_display;
	}

	/**
	 * @see 得到时间字符串
	 * @see 例如：StaticMethod.getTimeString(-1,16),可以返回昨天的时间字符串
	 * @param disday
	 *            int 和当前距离的天数
	 * @return String para的标准时间格式的串,例如：返回'2003-8-10 16:00:00'
	 */

	public static String getTimeString(int disday, int hour) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		String _hourstr = "";
		int _yy = c.get(Calendar.YEAR);

		int _mm = c.get(Calendar.MONTH) + 1;

		int _dd = c.get(Calendar.DATE);

		if (hour < 10) {
			_hourstr = "0" + hour;
		} else {
			_hourstr = "" + hour;
		}
		ls_display = "'" + _yy + "-" + _mm + "-" + _dd + " " + _hourstr
				+ ":00:00'";
		return ls_display;
	}

	/**
	 * 时间处理方法
	 * 
	 * @param disday
	 * @return
	 */
	public static Timestamp getTimestamp(int disday) {
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		return new Timestamp(realtime);
	}

	/**
	 * 时间串分割函数，将时间串分割成年、月、日、小时、分、秒。
	 * 
	 * @param time
	 * @return
	 */
	public static String[] subTimeString(String time) {

		String[] timeString = time.split(" ");
		String[] time1 = timeString[0].split("-");
		String[] time2 = timeString[1].split(":");
		int length = time1.length + time2.length;
		String[] Time = new String[length];

		for (int i = 0; i < time1.length; i++) {
			Time[i] = time1[i];
		}
		for (int j = 0; j < time2.length; j++) {
			Time[time1.length + j] = time2[j];
		}
		return Time;
	}

	/**
	 * 时间处理方法
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp getTimestamp(String str) {
		Timestamp ret = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			Date date = dateFormat.parse(str);
			long datelong = date.getTime();
			//System.out.println(datelong);
			ret = new Timestamp(datelong+111L);

		} catch (Exception e) {
		}
		return ret;
	}
    
	
	/**
	 * 获取基准时间 1970-01-01 00:00:01
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp getTimestamp(Long time) {
		return new Timestamp(time);
	}

	/**
	 * 时间处理方法
	 * 
	 * @param str
	 * @return
	 */
	public static String date2String(Date date) {
		String ret = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			ret = dateFormat.format(date);
		} catch (Exception e) {
		}
		return ret;
	}

	public static Timestamp getTimestamp() {
		return getTimestamp(0);
	}

	public static String getTimestampString(Timestamp sta) {
		String ret = "";
		try {
			String str = sta.toString();
			ret = str.substring(0, str.lastIndexOf('.'));
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}

	/**
	 * 动态域显示把
	 */
	public static String getTimestampString(String timestampStr) {
		String ret = "";
		try {
			if(timestampStr.indexOf(".")>0){ 
				ret = timestampStr.substring(0, timestampStr.lastIndexOf('.'));
			}
		} catch (Exception e) {
			
		}
		return ret;
	}
	/**
	 * @see 得到当前时刻的时间字符串
	 * @return String para的标准时间格式的串,例如：返回'2003-08-09 16:00:00'
	 */
	public static String getLocalString() {
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(currentDate);

		return date;
	}

	/**
	 * 时间处理方法：
	 * 
	 * @param day
	 * @return
	 */
	public static String getLocalString(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(c.getTime());

		return date;
	}

	/**
	 * 时间处理方法
	 * 
	 * @param hour
	 * @return
	 */
	public static String getLocalStringByHours(int hour) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, hour);

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(c.getTime());

		return date;
	}

	/**
	 * @see 根据参数str,得到标准的时间字符串
	 * @param str
	 * @returnpara的标准时间格式的串,例如：返回'2003-08-09 16:00:00'
	 */
	public static String getLocalString(String str) {
		String time = "";
		Vector temp = StaticMethod.getVector(str, " ");
		if (!temp.isEmpty()) {
			Vector first = null;
			Vector last = null;
			String year = "";
			String month = "";
			;
			String day = "";
			String hour = "";
			String minute = "";
			String second = "";
			if(null!=temp.get(0)&&!"".equals(temp.get(0)))
				first = StaticMethod.getVector(temp.get(0).toString(), "-");
			if(null!=temp.get(1)&&!"".equals(temp.get(1)))
				last = StaticMethod.getVector(temp.get(1).toString(), ":");
			if(null!=first&&null!=first.get(0)&&!"".equals(first.get(0)))
				year = first.get(0).toString();
			if(null!=first&&null!=first.get(1)&&!"".equals(first.get(1)))
				month = first.get(1).toString();
			if(null!=first&&null!=first.get(2)&&!"".equals(first.get(2)))
				day = first.get(2).toString();
			if(null!=last&&null!=last.get(0)&&!"".equals(last.get(0)))
				hour = last.get(0).toString();
			if(null!=last&&null!=last.get(1)&&!"".equals(last.get(1)))
				minute = last.get(1).toString();
			if(null!=last&&null!=last.get(2)&&!"".equals(last.get(2)))
				second = last.get(2).toString();

			if (null!=month&&month.length() == 1) {
				month = "0" + month;
			}
			if (null!=day&&day.length() == 1) {
				day = "0" + day;
			}
			if (null!=hour&&hour.length() == 1) {
				hour = "0" + hour;
			}
			if (null!=minute&&minute.length() == 1) {
				minute = "0" + minute;
			}
			if (null!=second&&second.length() == 1) {
				second = "0" + second;

			}
			time = year + "-" + month + "-" + day + " " + hour + ":" + minute
					+ ":" + second;
		}

		return time;
	}

	/**
	 * 时间转换方法
	 * 
	 * @param disday
	 * @param hour
	 * @return
	 */
	public static String getLocalString(int disday, int hour) {
		String ls_display = "";
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		c.setTimeInMillis(realtime);
		String _mmstr = "", _ddstr = "", _hourstr = "";
		int _yy = c.get(Calendar.YEAR);
		int _mm = c.get(Calendar.MONTH) + 1;
		_mmstr = _mm + "";
		if (_mm < 10) {
			_mmstr = "0" + _mm;
		}
		int _dd = c.get(Calendar.DATE);
		_ddstr = _dd + "";
		if (_dd < 10) {
			_ddstr = "0" + _dd;
		}
		if (hour < 10) {
			_hourstr = "0" + hour;
		} else {
			_hourstr = "" + hour;

		}
		ls_display = _yy + "-" + _mmstr + "-" + _ddstr + " " + _hourstr
				+ ":00:00";
		return ls_display;
	}

	/**
	 * @see 转换时间方法：转换格式，如时间“2002-1-12”转换成字符串“020112”
	 * @param DateStr
	 *            “2002-1-12”
	 * @return “020112”
	 */
	public static String getYYMMDD() {
		return getYYMMDD(getCurrentDateTime());
	}

	public static String getYYMMDD(String DateStr) {
		String YY, MM, DD;
		String ReturnDateStr;

		int s = DateStr.indexOf(" ");
		ReturnDateStr = DateStr.substring(0, s);

		Vector ss = getVector(ReturnDateStr, "-");
		YY = ss.elementAt(0).toString();
		YY = YY.substring(2, 4);
		MM = ss.elementAt(1).toString();
		if (Integer.valueOf(MM).intValue() < 10) {
			MM = "0" + Integer.valueOf(MM).intValue();
		}

		DD = ss.elementAt(2).toString();
		if (Integer.valueOf(DD).intValue() < 10) {
			DD = "0" + Integer.valueOf(DD).intValue();
		}
		ReturnDateStr = YY + MM + DD;

		return ReturnDateStr;
	}

	/**
	 * 时间处理方法：返回日期格式：20051201
	 * 
	 * @param DateStr
	 * @return
	 */
	public static String getYYYYMMDD(String DateStr) {

		String YY, MM, DD;
		String ReturnDateStr;

		int s = DateStr.indexOf(" ");
		ReturnDateStr = DateStr.substring(0, s);

		Vector ss = getVector(ReturnDateStr, "-");
		YY = ss.elementAt(0).toString();
		// YY = YY.substring(2, 4);
		MM = ss.elementAt(1).toString();
		DD = ss.elementAt(2).toString();

		ReturnDateStr = YY + MM + DD;

		return ReturnDateStr;
	}
 
	/**
	 * 
	 * 时间转换方法：根据输入的格式(String _dtFormat)得到当前时间格式得到当前的系统时间 Add By ChengJiWu
	 * 
	 * @param _dtFormat
	 * @return
	 */
	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentdatetime;
	}

	/**
	 * 时间转换方法：得到默认的时间格式为("yyyy-MM-dd HH:mm:ss")的当前时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param strDate
	 *            时间型的字符串
	 * @param _dtFormat
	 *            形如"yyyy-MM-dd HH:mm:ss"的字符串 把 strDate 时间字符串 转换为 _dtFormat 格式
	 * @return
	 */
	public static String getCurrentDateTime(String strDate, String _dtFormat) {
		String strDateTime;
		Date tDate = null;
		if (null == strDate) {
			return getCurrentDateTime();
		}
		SimpleDateFormat smpDateFormat = new SimpleDateFormat(_dtFormat);
		ParsePosition pos = new ParsePosition(0);
		tDate = smpDateFormat.parse(strDate, pos); // 标准格式的date类型时间
		strDateTime = smpDateFormat.format(tDate); // 标准格式的String 类型时间
		return strDateTime;
	}
 

 
	/**
	 * @see 时间处理方法
	 * @return
	 */
	public static synchronized int getSingleId() {
		int ret = 0;
		Date date = new Date();
		ret = new Long(date.getTime()).intValue();
		if (ret < 0)
			ret = -ret;
		return ret;
	}

	/**
	 * @see 时间处理方法：for applysheet 2005-09-27 add by chenyuanshu
	 * 
	 * @see 将millonSecond转换成时:分:秒
	 * @param millonSecond
	 *            long
	 * @return String
	 */
	public static String convert(long millonSecond) {
		int totalsecond = (int) millonSecond / 1000;
		int hour, min, sec;
		String minStr = "", hourStr = "";
		/*
		 * day=totalsecond /(60*60*24); dayStr=String.valueOf(day);
		 */
		hour = (totalsecond) / (60 * 60);
		hourStr = String.valueOf(hour);
		min = (totalsecond - hour * 60 * 60) / 60;
		minStr = String.valueOf(min);
		sec = totalsecond - min * 60 - hour * 60 * 60;
		if (min == 0)
			minStr = "00";
		if (hour == 0)
			hourStr = "00";
		return hourStr + ":" + minStr + ":" + sec;
	}

	/**
	 * @see 计算步骤之间历时
	 * @throws Exception
	 *             输入参数：如下所示： 参数名称 参数类型 参数说明 备注 historyTime String 历史步骤时间
	 *             nowTime String 当前步骤时间 输出：步骤之间历时（单位小时）
	 */
	public static int getTimeIntervals(String historyTime, String nowTime) {
		String[] timeHistory = subTimeString(historyTime);
		String[] timeNow = subTimeString(nowTime);
		int year1 = StaticMethod.getIntValue(timeHistory[0]);
		int year2 = StaticMethod.getIntValue(timeNow[0]);
		int month1 = StaticMethod.getIntValue(timeHistory[1]);
		int month2 = StaticMethod.getIntValue(timeNow[1]);
		int day1 = StaticMethod.getIntValue(timeHistory[2]);
		int day2 = StaticMethod.getIntValue(timeNow[2]);
		int hour1 = StaticMethod.getIntValue(timeHistory[3]);
		int hour2 = StaticMethod.getIntValue(timeNow[3]);
		int min1 = StaticMethod.getIntValue(timeHistory[4]);
		int min2 = StaticMethod.getIntValue(timeNow[4]);
		int sec1 = StaticMethod.getIntValue(timeHistory[5]);
		int sec2 = StaticMethod.getIntValue(timeNow[5]);
		int flag, minutes;
		GregorianCalendar localCalendar = new GregorianCalendar(year1, month1,
				day1, hour1, min1, sec1);
		GregorianCalendar limitCalendar = new GregorianCalendar(year2, month2,
				day2, hour2, min2, sec2);
		Date localDate = localCalendar.getTime();
		Date limitDate = limitCalendar.getTime();
		flag = historyTime.compareTo(nowTime);
		if (flag <= 0) {
			long oddTime = limitDate.getTime() - localDate.getTime();
			minutes = (int) (oddTime / (60 * 60 * 1000));

		} else {
			long oddTime = localDate.getTime() - limitDate.getTime();
			minutes = (int) (oddTime / (60 * 60 * 1000));
			minutes = 0 - minutes;
		}
		return minutes;
	}

	/**
	 * @see 计算步骤之间历时
	 * @throws Exception
	 *             输入参数：如下所示： 参数名称 参数类型 参数说明 备注 historyTime String 历史步骤时间
	 *             nowTime String 当前步骤时间 输出：步骤之间历时（单位分钟）
	 */
	public static int getTimeDistance(String historyTime, String nowTime) {
		String[] timeHistory = subTimeString(historyTime);
		String[] timeNow = subTimeString(nowTime);
		int year1 = StaticMethod.getIntValue(timeHistory[0]);
		int year2 = StaticMethod.getIntValue(timeNow[0]);
		int month1 = StaticMethod.getIntValue(timeHistory[1]);
		int month2 = StaticMethod.getIntValue(timeNow[1]);
		int day1 = StaticMethod.getIntValue(timeHistory[2]);
		int day2 = StaticMethod.getIntValue(timeNow[2]);
		int hour1 = StaticMethod.getIntValue(timeHistory[3]);
		int hour2 = StaticMethod.getIntValue(timeNow[3]);
		int min1 = StaticMethod.getIntValue(timeHistory[4]);
		int min2 = StaticMethod.getIntValue(timeNow[4]);
		int sec1 = StaticMethod.getIntValue(timeHistory[5]);
		int sec2 = StaticMethod.getIntValue(timeNow[5]);
		int flag, minutes;
		GregorianCalendar localCalendar = new GregorianCalendar(year1, month1,
				day1, hour1, min1, sec1);
		GregorianCalendar limitCalendar = new GregorianCalendar(year2, month2,
				day2, hour2, min2, sec2);
		Date localDate = localCalendar.getTime();
		Date limitDate = limitCalendar.getTime();
		flag = historyTime.compareTo(nowTime);
		if (flag <= 0) {
			long oddTime = limitDate.getTime() - localDate.getTime();
			minutes = (int) (oddTime / (60 * 1000));

		} else {
			long oddTime = localDate.getTime() - limitDate.getTime();
			minutes = (int) (oddTime / (60 * 1000));
			minutes = 0 - minutes;
		}
		return minutes;
	}

  
	/**
	 * @see 转换时间方法
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthLastDay(int year, int month) {

		if (month == 2) {
			if (year % 4 == 0)
				return 29;
			else
				return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		else
			return 31;

	}

	/**
	 * @see 获取当前时间（Date类型）
	 * @author qinmin
	 * @return
	 */
	public static Date getLocalTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		return date;

	}

	/**
	 * 字符类型转换函数（String->long）
	 * 
	 * @author qinmin
	 * @param str
	 * @return
	 */
	public static long getLongValue(String str) {
		long i = 0;
		try {
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

 

	 
  

	/**
	 * 删除尾部空格
	 * 
	 * @param str
	 *            字符串
	 * @param removedStr
	 *            要删除的尾部字符串
	 * @return
	 */
	public static String removeLastStr(String str, String removedStr) {
		String result = null;
		if (str.endsWith(removedStr)) {
			result = str.substring(0, str.length() - removedStr.length());
		}
		return result;
	}

 
	 
	public static int getByMonthToNum(String year, String month)
			throws SQLException {
		int num = 0;
		int m = Integer.parseInt(month);
		int y = Integer.parseInt(year);
		switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			num = 31;
			break;
		case 2:
			if (y % 4 == 0) {
				num = 29;
			} else {
				num = 28;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			num = 30;
			break;

		}
		return num;
	}

	 
	/**
	 * 根据时间字符串（yyyy-mm-dd hh:mm:ss） 加上一定的分钟数，返回一个加和后的时间字符串
	 * add by gongyfueng for duty
	 * @param str 时间字符串， int minute
	 * @return str
	 */
	public static String getDateForMinute (String str ,int minute){
		String time  = "";
		try{
		GregorianCalendar cal = String2Cal(str);
		cal.add(cal.MINUTE , minute);
		time  = String.valueOf(cal.get(cal.YEAR));
		time = time + "-"
				+ String.valueOf(cal.get(cal.MONTH) + 1);
		time = time + "-"
				+ String.valueOf(cal.get(cal.DATE));
		time = time + " "
				+ String.valueOf(cal.get(cal.HOUR_OF_DAY));
		time = time + ":"
				+ String.valueOf(cal.get(cal.MINUTE));
		time = time + ":"
				+ String.valueOf(cal.get(cal.SECOND));
		//System.out.println(time);
		}catch (Exception e){
			e.printStackTrace();
		}
		return time;
	}
  
   
	/**
	 * 比较时间
	 * @param t1 时间1
	 * @param t2 时间2
	 * @return 返回1大于 返回-1小于 返回0等于
	 */
	public static int timeCompare(String t1,String t2){  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Calendar c1=Calendar.getInstance();  
        Calendar c2=Calendar.getInstance();  
        try {  
            c1.setTime(formatter.parse(t1));  
            c2.setTime(formatter.parse(t2));  
        } catch (Exception e) {  
            e.printStackTrace();  
           // System.out.print(e);
        }  
        int result=c1.compareTo(c2);  
        return result;  
    }
	 
	/**
	 * 时间处理方法 获取基准归档时间
	 * 获取 ARCHIVE_BASE_DATE
	 * @param str
	 * @return
	 */
	public static Date getArchiveBaseDate() {
		Date tDate = new Date();
		try {
			SimpleDateFormat smpDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			tDate = smpDateFormat.parse("2099-12-31", pos); // 标准格式的date类型时间
		} catch (Exception e) {
		}
		return tDate;
	}
	
	
	/**
	 * 时间处理方法 获取基准归档时间
	 * 获取 ARCHIVE_BASE_DATE
	 * @param str 0000-00-00
	 * @return
	 */
	public static Date getArchiveBaseDate(String archiveBaseDate) {
		Date tDate = new Date();
		try {
			SimpleDateFormat smpDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ParsePosition pos = new ParsePosition(0);
			tDate = smpDateFormat.parse(archiveBaseDate, pos); // 标准格式的date类型时间
			
		} catch (Exception e) {
		}
		return tDate;
	}
	


	
	/**
	 * 数据库表 转hibernate对应MODEL/VO类
	 */
	public static String tableToModel(String string) {
		String hibernateStr = lowerCase(string);
		// 把_s转为S ABc_Efg_opq 转为 AbcEfgOpq
		String idx[] = hibernateStr.split("_");
		String temp = "";
		if (idx.length != 0) {

			for (int i = 0; i < idx.length; i++) {
				temp += firstToUpperCase(idx[i]);
			}
			hibernateStr = temp;
		} 
		return hibernateStr;
	}
	/**
	 * 数据库表 转jsp包路径
	 */
	public static String tableToPackage(String string) {
		// 把_s转为S ABc_Efg_opq 转为 abcefgopq 
		String temp = lowerCase(string);
		String packagePath = temp.replace("_", "");
		return packagePath;
	}
	/**
	 * 转小写
	 */
	public static String lowerCase(String str) {
		String ret = "";
		try {
			ret = str.toLowerCase();
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}	

	public static String removeBlank(String str){
	        if (str!=null&&!"".equals(str)) {
	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            Matcher m = p.matcher(str);
	            str = m.replaceAll("");
	        }
	        return str;
	}
	
	 /**
     * 保留小数点两位四舍五入
     */
    public static String reserved2Scale(String numStr)
    {
    	String result = numStr;
    	if(!StringUtils.isEmpty(numStr))
    	{
    		BigDecimal num = new BigDecimal(numStr);
    		result = String.valueOf(num.setScale(3,BigDecimal.ROUND_HALF_EVEN));
    	}
    	return result;
    }

	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}
    /**
	 * 替换字符串并让它的下一个字母为大写
	 * 
	 * @param srcStr
	 * @param org
	 * @param ob
	 * @return
	 * @author zhyoy
	 */
	public static String replaceUnderlineAndfirstToUpper(String srcStr) {
		String org = "_";
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first);
				srcStr = srcStr
						.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}
	
	/**
	 * @see null转成0
	 * @param obj
	 * @return
	 * @author zhyoy
	 */
	public static String null2Zero(String obj) {
		return ((obj == null || "".equals(obj)||"null".equals(obj)) ? "0" : obj);
	}
}
