package com.quickshear.common.pay.tenpay.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.quickshear.common.pay.tenpay.TenpayConfig;

/**
 * xml工具类
 * @author miklchen
 *
 */
public class XMLUtil {

	public static String doXMLCreate(Map<String,String> map){
		
		return null;
	}
	public static void main(String[] args) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", TenpayConfig.app_id);
		packageParams.put("mch_id", TenpayConfig.mch_id);//商户号
		String noncestr = Sha1Util.getNonceStr();
		packageParams.put("nonce_str", noncestr);
//		packageParams.put("fee_type", TenpayConfig.fee_type); //币种 
		packageParams.put("notify_url", TenpayConfig.notify_url); //接收财付通通知的URL  
		packageParams.put("trade_type", TenpayConfig.trade_type); //接收财付通通知的URL  
		
//		packageParams.put("bank_type", TenpayConfig.bank_type); //银行通道类型 
//		packageParams.put("body", product_name); //商品描述   
//		packageParams.put("input_charset", TenpayConfig.input_charset); //字符编码
		System.out.println(map2Xml(packageParams));
		
	}
	public static String map2Xml(Map<String, String> map) {  
        Document document = DocumentHelper.createDocument();  
        Element nodeElement = document.addElement("xml");  
        for (Object obj : map.keySet()) {  
            Element keyElement = nodeElement.addElement(String.valueOf(obj));  
            keyElement.setText(String.valueOf(map.get(obj)));  
        }  
        return doc2String(document);  
    }  
	
	public static String doc2String(Document document) {  
        String s = "";  
        try {  
            // 使用输出流来进行转化  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
            // 使用UTF-8编码  
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");  
            XMLWriter writer = new XMLWriter(out, format);  
            writer.write(document);  
            s = out.toString("UTF-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
        return s;  
    }  
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,String> doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String,String> m = new HashMap<String,String>();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(in);
		org.jdom.Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			org.jdom.Element e = (org.jdom.Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				org.jdom.Element e = (org.jdom.Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取xml编码字符集
	 * @param strxml
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(in);
		in.close();
		return (String)doc.getProperty("encoding");
	}
	
	
}
