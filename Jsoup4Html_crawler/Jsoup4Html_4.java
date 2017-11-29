package pachong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jsoup4Html_4 {
    /**
     * 笔记
     */
    @Test
    public void Test() {
        try {
            //设置抓取网页的链接和超时时间（单位是毫秒）,拿到整个页面的Document对象
            Document document = Jsoup.connect("http://blog.csdn.net/tuesdayma/article/list/1").timeout(10000).get();
            //获取html中的所有纯文本内容
            String string = document.text();
            //可以获得所有li标签下的内容
            Elements elements = document.getElementsByTag("li");
            /**
             *  document.select()类似于jq中的标签选择器
             */
            //document.select("li")=document.getElementsByTag("li")，也是获取li标签下的所有内容
            Elements elements1 = document.select("li");
            //获取html中id为btnContents的控件下的所有内容
            Elements str = document.select("#btnContents");
            //获取html中所有class为tracking-ad的控件下的所有内容
            Elements str2 = document.select(".tracking-ad");
            //获取既有list_item类样式又有article_item类样式的所有内容
            Elements str8 = document.select(".list_item").select(".article_item");
            //获取div控件下又有list_item类样式的所有内容
            Elements str7 = document.select("div.list_item");
            //获取html中所有带有href的a标签下的所有内容,不管href的位置，只要在a标签里面即可
            Elements str3 = document.select("a[href]");
            for (Element string1 : str3) {
                //获取每个a标签下的href的值,如果不加这个"abs:"获取的就是相对路径的链接，加上获取的是绝对路径
                System.out.println(string1.attr("abs:href"));
            }
            //获取html中的所有图片
            Elements str4 = document.select("img");
            //获取html中的所有gif格式的图片
            Elements str5 = document.select("img[src$=.gif]");
            //获取html中的gif格式图片的第1张（下标从1开始）
            Element str6 = document.select("img[src$=.gif]").first();
            //删除a标签以外的内容，即留下来的只有a标签里的内容
            Elements str9 = document.select("a").remove();
            System.out.println(str7);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 抓取我的csdn发表文章的所有标题，链接，摘要，发表时间，阅读数和评论数
     */
    @Test
    public void doTest() {
        int j = 0;
        Document document = null;
        List list = new ArrayList();
        for (int i = 1; i < 100; i++) {
            try {
                String string = "http://blog.csdn.net/tuesdayma/article/list/" + i;
                document = Jsoup.connect(string).timeout(1000).get();
                //比如我只有3页，那么第4页和第5页的body部分就是一样的，那么久退出循环，阻止继续爬数据
                if (list.contains(document.select("body").text())) {
                    break;
                } else {
                    list.add(document.select("body").text());
                }
                //抓取div中class为list_item_new的所有内容
                Elements elements = document.select(".list_item").select(".article_item");
                //遍历每个element
                for (Element e : elements) {
                    HashMap hashMap = new HashMap();
                    Elements elements1 = e.select("h1").select("a");
                    //获取标题
                    String strname = elements1.text();
                    //链接
                    String strhref = elements1.attr("href");
                    //摘要
                    String strzy = e.select(".article_description").text();
                    //发表时间
                    String strtime = e.select(".link_postdate").text();
                    //阅读数
                    Elements elements2 = e.select(".link_view");
                    /**
                     * 为了删除<span class="link_view" title="阅读次数"><a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>(123)</span>
                     * 中的<a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>部分
                     * 从而获得<span class="link_view" title="阅读次数">(123)</span>部分
                     */
                    //遍历link_view类样式下的元素（其实只有一个元素，就是
                    // span class="link_view" title="阅读次数"><a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>(123)</span>）
                    for (Element ele : elements2) {
                        //获取span class="link_view" title="阅读次数"><a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>(123)</span>的第一个子节点里的所有元素
                        //其实这个子节点下的元素也只有一个，即<a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>
                        Elements element = ele.children();
                        for (Element ese : element) {
                            //判断这个控件是不是a标签，是的话就删除，从而达到删除<a href="/tuesdayma/article/details/77719538" title="阅读次数">阅读</a>部分的目的
                            if (ese.tagName().equals("a")) {
                                ese.remove();
                            }
                        }
                    }
                    //经过这个for循环后，elements2为：<span class="link_view" title="阅读次数">(123)</span>
                    //正则匹配删除"（"和"）",从而获得纯数字
                    String strreadnumber = GetNumber4String(elements2.text());
                    //评论次数
                    Elements elements3 = e.select(".link_comments");
                    for (Element ele : elements3) {
                        Elements element = ele.children();
                        for (Element ese : element) {
                            if (ese.tagName().equals("a")) {
                                ese.remove();
                            }
                        }
                    }
                    String strcommentnumber = GetNumber4String(elements3.text());
                    hashMap.put("strnam", strname);
                    hashMap.put("strhref", strhref);
                    hashMap.put("strzy", strzy);
                    hashMap.put("strtime", strtime);
                    hashMap.put("strreadnumber", strreadnumber);
                    hashMap.put("strcommentnumber", strcommentnumber);
                    //将内容写入文件中
                    DoWrite(hashMap);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            j++;
        }
        System.out.println(j);
    }

    /**
     * 正则匹配---从字符串中获得纯数字
     */
    public String GetNumber4String(String str) {
        String str_return = "";
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str_return = matcher.group(1) == null ? "" : matcher.group(1);
        }
        return str_return;
    }

    /**
     * 将数据写入txt文件中
     *
     * @param hashMap
     */
    public void DoWrite(HashMap hashMap) {
        try {
            //false表示覆盖原来的内容,true表示不覆盖原来的，继续写下去
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("My_csdn.txt"), true), "UTF-8"));
            String string = "";
            for (Object object : hashMap.entrySet()) {
                //将object转换为entry
                Map.Entry entry = (Map.Entry) object;
                string += entry.getKey().toString() + ":" + entry.getValue() + "\n";
            }
            string = string + "\n\n";

            bf.write(string);
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
