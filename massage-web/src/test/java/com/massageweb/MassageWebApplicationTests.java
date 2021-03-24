package com.massageweb;

import com.alibaba.fastjson.JSONObject;
import com.massagecommon.util.RedisUtil;
import com.massagedao.mapper.EquipmentMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class MassageWebApplicationTests {
    private static final String IV_STRING = "16-Bytes--String";
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 返回数据
     *
     * @param
     * @return void
     * @methodName contextLoads
     * @author WuShunag
     * @date 20:21
     */
    @Test
    void contextLoads() throws Exception {
       /* String s ="{ID=idvvvvvvvv,QSN=nnnnnnnn,VTA=aaaaaaa,VTB=bbbbbb,VTC=cccccc}";
        StringBuffer s2 = new StringBuffer();
        //1。把要返回的 数据进行加密
        String encrypt = Encrypt(s, "mvgnButy" + "88888888");
        System.err.println("加密数据："+encrypt);
       *//* for (int i = 0; i < encrypt.length(); i++) {
            String substring =String.valueOf(encrypt.charAt(i));
            String sq = str2HexStr(substring);
            s2.append(sq);
        }*//*
        String sq = str2HexStr(encrypt);
      System.err.println("16进制"+sq);*/
//        String m2vcgdn6B7u2taye = Encrypt2("{ID=136026001,QST=1,QSN=4ae13d6c,QCD=0,QTM=0}", "m2vcgdn6B7u2taye");
////        System.err.println("加密后的数据："+m2vcgdn6B7u2taye);
////        byte[] m2vcgdn6B7u2tayes = decryptAES("E0CC163C773E55942E1022C9009E23A1FF818D83E15B89C77BEBF70A9038ED48D00E3030105228E7A8E7AE306E6EA99C0B7755150FD4C98BDD82BCCDADB784E6", "mvgnButy422d54dc");
////        System.err.println("解密信息："+new String(m2vcgdn6B7u2tayes));

       /* SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar1=Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("本月第一天: "+sdf.format(calendar1.getTime()));*/
       /* Object o = redisUtil.get("20591001017");
        System.err.println(o.toString());*/
        Integer integer = NumberUtils.createInteger("100");
        System.err.println(isOdd(integer));
    }
    public static int isOdd(int n)
    {
        return Math.max(n, 0);
    }


    public static String Encrypt2(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        //分块加密
        byte[] data = sSrc.getBytes();
        int black= data.length/16+1;
        byte[] out = new byte[black * 16];
        int b = 0;
        while(b < black){
            //计算偏移
            int offset = b * 16;
            //计算剩余长度
            int len = (data.length-16-offset) > 0 ? 16:(data.length - offset);
            byte[] input = new byte[16];
            System.arraycopy(data, offset, input, 0, len);
            byte[] output=cipher.doFinal(input);
            System.arraycopy(output, 0, out,offset, 16);
            b++;
        }
        return Bytes2HexString(out);


       /* //byte[] asciis = sSrc.getBytes();
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return  Bytes2HexString(encrypted);*/
    }


    public static String Bytes2HexString(byte[] b) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {

            String hex = Integer.toHexString(b[i] & 0xFF);

            if (hex.length() == 1) {

                hex = '0' + hex;

            }

            sb.append(hex.toUpperCase());

        }

        return sb.toString();

    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static final String encodeKey = "123456789";
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final String ENCRYPTION_ALGORITHM_ECB = "AES/ECB/PKCS7Padding";
    private static final String ENCRYPTION_ALGORITHM_ECB1 = "AES/ECB/NoPadding";


    /* 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    /**
     * 解密
     * @param content
     * @return
     */
    public static byte[] decryptAES( String content ,String encodeKey){
        //判断Key是否正确
        if (encodeKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        //判断Key是否为16位
        if (encodeKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] b = HexString2Bytes(content);
        try {
            byte[] raw = encodeKey.getBytes("ASCII");
            SecretKeySpec skp = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM_ECB1);
            cipher.init(Cipher.DECRYPT_MODE, skp);
            byte[] original = cipher.doFinal(b);
            return original;
        } catch (Exception e) {
            System.out.println("数据解密时发生异常...");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" –> byte[]{0x2B, 0×44, 0xEF,
     * 0xD9}
     *
     * @param src
     *            String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length()) {
            return null;
        }
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        System.out.printf("real key:{");
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
            System.out.printf("0x%x", (byte)ret[i]);
            if(i < (tmp.length / 2-1)) {
                System.out.printf(",");
            }
        }
        System.out.printf("}\n");

        return ret;
    }
    /**
     * 将两个ASCII字符合成一个字节； 如："EF"–> 0xEF
     *
     * @param src0
     *            byte
     * @param src1
     *            byte
     * @return byte
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }






    @Test
    void s() throws Exception {
        String s = hexStr2Str("2f4bedc8e234d376bfe6989445869f062475ccfa5510d553086e071bde9702f8cacf17eed6a30826b93a648245e0af42");
        System.err.println(s);
        String decrypt = Decrypt(s, "m2vcgdn6B7u2taye");
        System.err.println(decrypt);
    }


    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }


    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }


    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }


    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }


    @Test
    void q(){
       /* String o1 = "{\"code\":1000,\"msg\":\"{ID=idvvvvvvvv,QSN=nnnnnnnn,VTA=aaaaaaa,VTB=bbbbbb,VTC=cccccc,QTM=9}\"}";

        String s = regComp(o1);
        System.err.println(s);*/
        while (true){
            String q = "{ENCR368e0d66";
            q = q+"1";
            System.err.println(q);
        }
    }


    public static String regComp(String item) {
        String num = "";
        // 替换中文
        String reg = "[\\u4e00-\\u9fa5]+";
        //截取QTM=入后面数字
        String comp1 = "[\\s\\S]*([QTM=]\\d*)[\\s\\S]*";
        //截取波|第前面数字
        if (item.matches(comp1)) {
        num = item.replaceFirst(comp1, "$1").replaceAll(reg, "").replace("=","");
        }
        return num;
}

}
