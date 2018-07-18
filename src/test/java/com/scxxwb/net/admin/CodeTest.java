package com.scxxwb.net.admin;

import com.scxxwb.net.admin.common.utils.R;
import com.scxxwb.net.admin.common.utils.ZXingCodeUtils;
import com.scxxwb.net.admin.modules.operation.controller.MerchantController;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;

public class CodeTest {
    public static void main(String[] args) throws IOException {
        String code = "121222";
        InputStream is = ZXingCodeUtils.drawLogoCodeForBuffer(null,"http://1mk6535653.iok.la/Pay/Trade/Pay?sn=" + code,"");
        byte[] data = new byte[is.available()];
        is.read(data);
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Code = encoder.encode(data);
        System.out.println(base64Code);
    }
}
