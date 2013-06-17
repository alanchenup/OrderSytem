package com.ordersystem.common.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


public class JsonUtil {
    private static final ObjectMapper cacheFactory = new ObjectMapper(); // 缓存性能较好
	/**
	 * obj转json格式字符串
	 * @param obj
	 * @return
	 */
    public static String toJson(Object obj) {
        ByteArrayOutputStream sos = new ByteArrayOutputStream();
        try {
        	Writer out= new BufferedWriter(new OutputStreamWriter(sos, "UTF-8"));
            cacheFactory.writeValue(out, obj);
            return sos.toString("UTF-8");
        } catch (Exception e) {
        	
        }
        return "";
    }
    /**
     * json格式字符串转回Obj对象
     * @param content
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T foJson(String content, TypeReference<T> type) {
    	if(content==null||content.equals(""))return null;
        try {
            return (T)(cacheFactory.readValue(content, type));
        } catch (Exception e) {        	
        }
        return null;
    }
    public static InputStream toZipJson(Object obj) {
        ByteArrayOutputStream entityStream = new ByteArrayOutputStream();
        DeflaterOutputStream zos = new DeflaterOutputStream(entityStream);
        try {
            cacheFactory.writeValue(zos, obj);
            zos.close();
            byte[] buffer = entityStream.toByteArray();
            entityStream.close();
            return new ByteArrayInputStream(buffer);
        } catch (Exception e) {
        	
        }
        return null;
    }
    @SuppressWarnings("unchecked")
	public static <T> T foZipJson(TypeReference<T> type, InputStream in) {
        try {
            in.mark(64 << 20);
            try {
                InflaterInputStream ios = new InflaterInputStream(in);
                Object obj = cacheFactory.readValue(ios, type);
                ios.close();
                in.close();
                return (T)obj;
            } catch (ZipException e) {
                in.reset();
                Object obj = cacheFactory.readValue(in, type);
                in.close();
                return (T)obj;
            }
        } catch (Exception e) {
        	
        }
        return null;
    }
}
