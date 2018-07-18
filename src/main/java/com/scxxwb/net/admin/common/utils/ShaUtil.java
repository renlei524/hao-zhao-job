package com.scxxwb.net.admin.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
* <p>Title: ShaUtil</p>  
* <p>Description: sha256加密生成加密因子</p>  
* @author kefan.qu_c  
* @date 2018年3月23日 下午5:16:57
 */
public class ShaUtil {
	
	public static final String SHA_256 = "SHA-256";
	
	/**
     * <b>Description:</b><br> SHA_256加密
     * 
     * <pre>
     * ShaUtil.encode(null, null, null) = null
     * ShaUtil.encode("1","2","GBK") = "6b51d431df5d7f141cbececcf79edf3dd86
     * 1c3b4069f0b11661a3eefacbba918"
     * </pre>
     * 
     * @param content jsonData数据
     * @param salt 随机因子接口的盐值字段
     * @param charset 字符编码
     * @return String
     *       <b>Author:</b> manhui.cao@chinapnr.com <br>
     *       <b>Date:</b> 2016-11-16 11:43:50
     */
    public static String encode(String content, String salt, String charset) {
        StringBuilder newContent=new StringBuilder();
        newContent.append(content).append(salt);
        MessageDigest md = null;
        String strDes = null;   
        try {
            byte[] bt = null;
            try {
                bt = newContent.toString().getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            md = MessageDigest.getInstance(SHA_256);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
        
    }

    /**
     * <b>Description:</b><br> byte
     * 
     * <pre>
     * ShaUtil.encode(null)      =  null
     * ShaUtil.encode(new byte[]{'a'}) = '61'
     * </pre>
     * 
     * @param bts byte
     * @return HexString 
     *       <b>Author:</b> manhui.cao@chinapnr.com <br>
     *       <b>Date:</b> 2016-11-16 11:43:50
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }        
    
}
