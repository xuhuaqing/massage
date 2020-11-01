package com.massageweb.controller;

import com.massagecommon.util.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class Server1 {
    public static final char END_CHAR = '}';
    private static final String ENCRYPTION_ALGORITHM_ECB1 = "AES/ECB/NoPadding";
    final String MVGN_BUTY = "mvgnButy";


    //存放连接过来的客户端以便后续的操作
    public static  List<Client> clients = new ArrayList<Client>();

    public void startService(String serverIP, int serverPort){
        try {
            //封装服务端地址
            InetAddress serverAddress = InetAddress.getByName(serverIP);
            //建立服务端
            try(ServerSocket service = new ServerSocket(serverPort, 20, serverAddress)){
                while (true) {
                    String s = "";
                    //接受一个连接，该方法会阻塞程序，直到一个链接到来
                    try{
                        Socket connect = service.accept();
                        Client client = new Client(connect);
                        clients.add(client);
                        new Thread(client).start();
                        System.err.println("链接成功！！");

                      /*
                       connect.setSoTimeout(4000);
                      //获得输入流
                        InputStream in = connect.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);

                        //解析输入流，遇到终止符结束，该输入流来自客户端
                        for (int c = bufferedInputStream.read(); c != END_CHAR; c = bufferedInputStream.read()) {
                            if(c ==-1){
                                break;
                            }
                            s = s + (char)c;
                        }
                        System.err.println("获取到信息："+s);
                        String response = facStatus(s);
                        System.err.println("返回给设备的加密信息："+response);
                        //获取输入流，并通过向输出流写数据的方式发送响应
                        OutputStream out = connect.getOutputStream();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);
                        bufferedOutputStream.write(response.getBytes());
                        //bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        out.close();
                        bufferedInputStream.close();
                        in.close();*/
//                        connect.close();
                    }catch (Exception e){
                        System.err.println("连接超时 天啊~~~~~~~~");
                    }finally {
                        System.err.println("连接关闭");
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    class Client implements Runnable{
      private BufferedInputStream bufferedInputStream = null;
      private BufferedOutputStream bufferedOutputStream = null;
      private volatile boolean isRuning = false;
      private String str = "";
      private Socket s = null;
      public Client(Socket socket) throws IOException {
          s = socket;
          bufferedInputStream = new BufferedInputStream(s.getInputStream());
          bufferedOutputStream = new BufferedOutputStream(s.getOutputStream());
          isRuning = true;
      }

        @Override
        public void run() {
              while (isRuning){
                  try {

                      for (int c = bufferedInputStream.read(); c != END_CHAR; c = bufferedInputStream.read()) {
                          if(c ==-1){
                              break;
                          }
                          str = str + (char)c;
                      }

                      System.err.println("获取到信息："+str);
                      String s = get(str);
                      String response = facStatus(s,this.s);
                      System.err.println("返回给设备的加密信息："+response);
                      bufferedOutputStream.write(response.getBytes());
                      bufferedOutputStream.flush();
                  } catch (IOException e) {

                      //下面的代码是释放资源
                      try {
                          isRuning = false;
                          if (bufferedInputStream!=null) {
                              bufferedInputStream.close();
                          }
                          if (bufferedOutputStream!=null) {
                              bufferedOutputStream.close();
                          }
                          if (s!=null){
                              s.close();
                          }
                      } catch (IOException ex) {
                          ex.printStackTrace();
                      }
                      e.printStackTrace();
                    }
                  }

              }
        }

    public  String get(String str){
        String[] split = str.split("\\{");
        return "{"+split[split.length -1 ];
    }


    private String facStatus(String msg, Socket s){
        String[] split = msg.split("=");
        String key = split[0].substring(1);
        String value = split[1];
        try {
            //获得解密的密码
            String decrypt = show1(this.MVGN_BUTY, key.substring(4));
            System.err.println("解密的密码是： = "+decrypt);
            String decrypt1 = Decrypt(value, decrypt);
            System.err.println("明文是："+decrypt1);
            String id = getString(decrypt1, "ID=");
            System.err.println("id="+id);

            String qst = getString(decrypt1, "QST=");
            System.err.println("qst="+qst);

            String qsn = getString(decrypt1, "QSN=");
            System.err.println("qsn="+qsn);

            String qcd = getString(decrypt1, "QCD=");
            System.err.println("qcd="+qcd);

            String qtm = getString1(decrypt1, "QTM=");
            System.err.println("qtm="+qtm);

            //获取iip getHostAddress
            redisUtil.set("ip"+id,s.getInetAddress().getHostName());
            if(!qcd.equals("4000000")) {
                redisUtil.set("returnMsg" + id, decrypt1);
            }
            Object o = redisUtil.get(id);
            if(o == null){
                List<String> list = redisUtil.lRange("lineUp"+id);
                if(!list.isEmpty()){
                    String s1 = list.get(0);
                    //redisUtil.removeRange("lineUp"+id,s1);
                    redisUtil.deleteList("lineUp" + id,1, s1);
                    s1 = s1.replace("nnnnnnnnxhq", qsn);
                    //1。把要返回的 数据进行加密
                    String encrypt = Encrypt(s1, "mvgnButy" + qsn.substring(qsn.length() - 8));
                    System.err.println("加密数据："+encrypt);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code",1000);
                    jsonObject.put("msg",encrypt);
                    return jsonObject.toString();
                }
                String value1 = "{ID="+id+",QSN="+qsn+",VTA=0,VTB=4000000,VTC=0}";
                //1。把要返回的 数据进行加密
                String encrypt = Encrypt(value1, "mvgnButy" + qsn.substring(qsn.length() - 8));
                System.err.println("加密数据："+encrypt);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",1000);
                jsonObject.put("msg",encrypt);
                return jsonObject.toString();
            }else {
                String string = o.toString();
                String vta = getString(string, "VTA=");
                String vtb = getString(string, "VTB=");
                Integer integer = Integer.valueOf(vta);
                Integer integer1 = Integer.valueOf(vtb);
                Integer qtm1 = Integer.valueOf(qtm);
                Integer qcd1 = Integer.valueOf(qcd);
                System.err.println("我获取了时间="+"当前设备使用时间="+qtm1+"我自己存储的时间="+integer);
                if(integer>0 && qtm1 >= integer && integer1.equals(qcd1) ){
                    redisUtil.remove(id);
                    String value1 = "{ID="+id+",QSN="+qsn+",VTA=0,VTB=4000000,VTC=0}";
                    //1。把要返回的 数据进行加密
                    String encrypt = Encrypt(value1, "mvgnButy" + qsn.substring(qsn.length() - 8));
                    System.err.println("加密数据："+encrypt);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code",1000);
                    jsonObject.put("msg",encrypt);
                    return jsonObject.toString();
                }

                System.err.println("返回的数据:"+string);
                string = string.replace("nnnnnnnnxhq", qsn);
                //1。把要返回的 数据进行加密
                String encrypt = Encrypt(string, "mvgnButy" + qsn.substring(qsn.length() - 8));
                System.err.println("加密数据："+encrypt);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",1000);
                jsonObject.put("msg",encrypt);
                return jsonObject.toString();
            }
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",1001);
            jsonObject.put("msg","错误信息");
            return jsonObject.toString();
        }
    };





    @Autowired
    private RedisUtil redisUtil;

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

    private String getString1(String string,String reg){
        String regex ="(?<=(" + reg + ")).*?(?=(}))";
        String result = "";
        Pattern p = Pattern.compile(regex);//将给定的正则表达式编译到模式中。
        Matcher m = p.matcher(string);//创建匹配给定输入与此模式的匹配器。
        while (m.find()) {
            result = m.group();
        }
        return result;
    }
    private String getString(String string,String reg){
        String regex ="(?<=(" + reg + ")).*?(?=(,))";
        String result = "";
        Pattern p = Pattern.compile(regex);//将给定的正则表达式编译到模式中。
        Matcher m = p.matcher(string);//创建匹配给定输入与此模式的匹配器。
        while (m.find()) {
            result = m.group();
        }
        return result;
    }

    public static String Decrypt(String content, String encodeKey) throws Exception {
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
            return new String(original);
        } catch (Exception e) {
            System.out.println("数据解密时发生异常...");
            e.printStackTrace();
        }
        return null;
    }
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

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
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

    private String show1(String s1,String s2) {
        if(s1.length()!=s2.length()) {
            return "字符串长度不一样";
        }
        StringBuilder srb=new StringBuilder();
        char[] ch1=s1.toCharArray(),ch2=s2.toCharArray();
        for (int i = 0; i < ch1.length;i++) {
            srb.append(ch1[i]).append(ch2[i]);
        }
        return srb.toString();
    }

}

