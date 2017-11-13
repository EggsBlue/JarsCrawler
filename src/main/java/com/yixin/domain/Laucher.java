package com.yixin.domain;

import com.yixin.entity.Jars;
import com.yixin.service.impl.NutzDaoService;
import com.yixin.utils.HCUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Start Class
 * @Author 蛋蛋
 * @DATE 2017年11月11日15:41:11
 *
 */
public class Laucher {
    private static Dao dao;

    private static String TARGETURL = "http://maven.aliyun.com/nexus/content/groups/public/abbot";

    public void run(){
        Log log = Logs.get();
        dao = new NutzDaoService().getDao();
        final Laucher laucher = new Laucher();
        List<String> urls = new ArrayList<String>(0);
        try {
            urls = laucher.getUrls();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if(urls.size()==0){
            log.debug("未查到爬取地址,请检查配置文件!");
            return;
        }
        ExecutorService executorService= Executors.newFixedThreadPool(10); // 创建ExecutorService 连接池创建固定的10个初始线程
        for(final String url : urls){
            if(url.length() < 10){
                continue;
            }
            //使用线程池提高效率
            executorService.execute(new Runnable() {//开启对应线程
                public void run() {
                    laucher. crawleing(url);
                }
            });
        }
    }


    public static void main(String[] args) {
        new Laucher().run();
    }

    /**
     * 获取a标签集合
     * @param tarurl
     * @return
     */
    public Elements getElemgs(String tarurl){
        CloseableHttpClient client = HCUtils.getClient();
        HttpGet get = HCUtils.getHttpGet(tarurl);
        try {
            CloseableHttpResponse response = client.execute(get);
            String s = EntityUtils.toString(response.getEntity(), "utf-8");
//            System.out.println(s);
            Document doc = Jsoup.parse(s);
            Elements elements = doc.getElementsByAttributeValue("cellspacing", "10");
            if(elements== null || elements.size() == 0){
                return null;
            }
            return elements.get(0).getElementsByTag("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getUrls() throws FileNotFoundException {
        URL resource = this.getClass().getClassLoader().getResource("crawlers.text");
        String s = Lang.readAll(new InputStreamReader(new FileInputStream(resource.getPath())));
        if(!Strings.isBlank(s)){
            System.out.println(s);
            String[] split = s.split("\r\n");
            return Lang.array2list(split);
        }
        return null;
    }

//    public Elements level2(String tarurl){
//
//    }



    public void crawleing(String url){
        Log log = Logs.get();
        Stopwatch sw = Stopwatch.begin();
        int totalCount = 0;
        log.debug("抓取地址:"+url);
        Elements projects = getElemgs(url);
        log.debug("发现 ["+projects.size()+"] 个开源项目");
        if(projects!=null){
//            System.out.println(projects.size());
            for (int i = 0; i < projects.size(); i++) {
                Element element = projects.get(i);
                if((element.html()).indexOf("Directory") >= 0 || (element.html()).indexOf(".") >= 0){
                    continue;
                }
                log.debug("开始爬取第"+i+"个项目,名称: ["+element.html()+"]...");
                Elements modules = getElemgs(element.attr("href"));
                log.debug("项目: ["+element.html()+"] 下有 ["+modules.size()+"] 个子项目...");
                for (int j = 0; j < modules.size(); j++) {
                    Element model = modules.get(j);
                    if(model.html().indexOf("Directory") >= 0){
                        continue;
                    }
                    if(model.html().indexOf(".") >= 0){  //说明直接就是版本号,没有子项目
                        if(model.html().indexOf("Directory") >= 0 || model.html().indexOf("../") >= 0){
                            continue;
                        }
                        log.debug("开始爬取第 ["+j+"]个版本,版本号: ["+model.html()+"]");
                        Elements jars = getElemgs(model.attr("href"));
                        if(jars == null){
                            continue;
                        }
                        log.debug("该版本下发现: ["+jars.size()+"] 个文件");
                        int jarCount =0;
                        int outher = 0;
                        for (int l = 0; l < jars.size(); l++) {
                            Element jar = jars.get(l);
                            String jarName = jar.html();
                            if(jarName.indexOf("Directory") >= 0 || jarName.indexOf("../") >= 0){
                                continue;
                            }

                            if(jarName.length()<=3){
                                outher++;
                                continue;
                            }
                            String s = jarName.substring(jarName.length() - 3, jarName.length());
//                            System.out.println("========="+s);
                            if("jar".equals(s)){//jar包
                                log.debug("发现文件:"+jar.html());

                                Jars jarEntity = new Jars(jar.html(),"jar包",jar.attr("href"),new Timestamp(System.currentTimeMillis()));
                                dao.insert(jarEntity);
                                jarCount++;
                                totalCount++;
                            }else{
//                                System.out.println("");
                                outher++;
                            }
//                            System.out.println(jar.html());
//                            if(jar.html().lastIndexOf("sha1") > 0 ||jar.html().lastIndexOf("Parent") > 0){
//
//                            }
                            log.debug("项目: ["+element.html()+"] 下的第 ["+j+"]个子项目,名称: ["+model.html()+"] 下的第 ["+j+"]个版本,名称为: ["+ jar.html()+"] 下共有: ["+jarCount+"] 个jar包,其他文件: ["+outher+"] 个");
                        }

                    }else{//获取子项目...
                        log.debug("开始爬取 ["+element.html()+"] 项目下第["+j+"]个子项目,名称: ["+model.html()+"]");
                        Elements verions = getElemgs(model.attr("href"));
                        log.debug("子项目: ["+model.html()+"] 下有 ["+verions.size()+"] 个版本");
                        for (int k = 0; k < verions.size(); k++) {
                            Element version = verions.get(k);
                            if(version.html().indexOf("Directory") >= 0 || version.html().indexOf("../") >= 0){
                                continue;
                            }
                            log.debug("开始爬去第 ["+k+"]个版本,版本号: ["+version.html()+"]");
                            Elements jars = getElemgs(version.attr("href"));
                            if(jars == null){
                                continue;
                            }
                            log.debug("该版本下发现: ["+jars.size()+"] 个文件");
                            int jarCount =0;
                            int outher = 0;
                            for (int l = 0; l < jars.size(); l++) {
                                Element jar = jars.get(l);
                                String jarName = jar.html();
                                if(jarName.indexOf("Directory") >= 0 || jarName.indexOf("../") >= 0){
                                    continue;
                                }

                                if(jarName.length()<=3){
                                    outher++;
                                    continue;
                                }
                                String s = jarName.substring(jarName.length() - 3, jarName.length());
//                            System.out.println("========="+s);
                                if("jar".equals(s)){//jar包
                                    log.debug("发现文件:"+jar.html());

                                    Jars jarEntity = new Jars(jar.html(),"jar包",jar.attr("href"),new Timestamp(System.currentTimeMillis()));
                                    dao.insert(jarEntity);
                                    jarCount++;
                                    totalCount++;
                                }else{
//                                System.out.println("");
                                    outher++;
                                }
//                            System.out.println(jar.html());
//                            if(jar.html().lastIndexOf("sha1") > 0 ||jar.html().lastIndexOf("Parent") > 0){
//
//                            }
                                log.debug("项目: ["+element.html()+"] 下的第 ["+j+"]个子项目,名称: ["+model.html()+"] 下的第 ["+k+"]个版本,名称为: ["+ jar.html()+"] 下共有: ["+jarCount+"] 个jar包,其他文件: ["+outher+"] 个");
                            }
                        }
                    }

                }
            }
        }
        sw.stop();
        log.debug("爬取任务完成,耗时:"+sw.toString()+",总共爬取jar包数量: ["+totalCount+"] 个");
    }

}
