package org.crawler.img;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgCrawler {
    public static void main(String[] args) throws IOException {
        //拿到网站的源码
        String html = getHtml("https://tieba.baidu.com/p/2256306796?red_tag=1781367364");
        String imgRegex="<img.*src=(.*?)[^>]*?>"; //img标签的正则
        String srcRegex="[a-zA-z]+://[a-zA-Z0-9\\./?;&_%=]+"; //src地址的正则
        //获得img标签的集合
        ArrayList<String> imgList = getImgList(html, imgRegex);
        //获得图片src的集合
        ArrayList<String> srcList = getSrcList(srcRegex, imgList);
        //System.out.println(imgList);
        //System.out.println(srcList.size());
        //System.out.println(srcList);
        //下载图片
        int name=1; //用作图片名字
        for (String ele : srcList) {
            HttpURLConnection connection = (HttpURLConnection) new URL(ele).openConnection();
            InputStream in = connection.getInputStream();
            byte[] bytes = new byte[1024 * 8];
            int len;
            FileOutputStream fOut= new FileOutputStream("D:\\aaa\\".concat(String.valueOf(name).concat(".jpg")));
            while ((len = in.read(bytes)) != -1) {
                fOut.write(bytes,0,len);
            }
            in.close();
            fOut.close();
            name++;
        }

    }

    //获得图片src的集合
    private static ArrayList<String> getSrcList(String srcRegex, ArrayList<String> imgList) {
        ArrayList<String> srcList = new ArrayList<>();
        Pattern compile = Pattern.compile(srcRegex);
        for(String ele:imgList){ //遍历img标签集合
            Matcher matcher = compile.matcher(ele);
            while (matcher.find()){
                String group = matcher.group();
                srcList.add(group);
            }
        }
        return srcList;
    }

    //获得img标签的集合
    private static ArrayList<String> getImgList(String html, String imgRegex) {
        ArrayList<String> imgList = new ArrayList<>();
        Pattern compile = Pattern.compile(imgRegex);
        Matcher matcher = compile.matcher(html);
        while (matcher.find()){
            String group = matcher.group();
            imgList.add(group);
        }
        return imgList;
    }

    //拿到网站源码的方法
    private static String getHtml(String address) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
        //拿到输入流
        InputStream in = connection.getInputStream();
        StringBuffer sb = new StringBuffer();
        byte[] bytes = new byte[1024];
        int len;
        while((len=in.read(bytes))!=-1){
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        return sb.toString();
    }
}
