package com.yixin.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

public class WuaiTest {

    @Test
    public void qdTest() throws IOException {
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet get = new HttpGet("https://www.52pojie.cn");
        get.setHeader("Cookie","_uab_collina=151027458192280992381585; htVD_2132_saltkey=xX9FoFta; htVD_2132_lastvisit=1510270965; htVD_2132_client_created=1510274569; htVD_2132_client_token=773E02A829582276F0008BBC4BF459D3; htVD_2132_auth=060awd39ozTWQzS4v3SvmAE9vIRTfCfbl9RwEEqZnvVBQ9WNO2EbV4WoakdFdAj%2FLvbCwy1VyUSeHYvrduPGQip1v0U; htVD_2132_connect_login=1; htVD_2132_connect_uin=773E02A829582276F0008BBC4BF459D3; htVD_2132_nofavfid=1; htVD_2132_lip=219.146.132.30%2C1510274569; htVD_2132_pc_size_c=0; htVD_2132_sid=q2MMNS; htVD_2132_ulastactivity=1510366299%7C0; htVD_2132_lastviewtime=433299%7C1510366567; htVD_2132_lastcheckfeed=433299%7C1510366568; htVD_2132_st_p=433299%7C1510366573%7C501af98ad33c619805516b9c3d4656a0; htVD_2132_visitedfid=10D16D65D32; htVD_2132_viewid=tid_659915; htVD_2132_smile=1D1; Hm_lvt_46d556462595ed05e05f009cdafff31a=1510274582,1510366330; Hm_lpvt_46d556462595ed05e05f009cdafff31a=1510366595; htVD_2132_lastact=1510366592%09connect.php%09check; htVD_2132_connect_is_bind=1");
        get.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(get);
//        System.out.println(cookies);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity, "UTF-8");
        Document doc = Jsoup.parse(s);
        Element divUm = doc.getElementById("um");
//        System.out.println(divUm.html());
        Elements p = divUm.getElementsByTag("p");
        Element element = p.get(p.size() - 1);
        Elements a = element.getElementsByTag("a");
        Element element1 = a.get(0);
        String href = element1.attr("href");
//        System.out.println(href);
        String url = "https://www.52pojie.cn/"+href;
        System.out.println(url);

//        System.out.println(a.html());
//        System.out.println(element.html());
//        System.out.println(element1.html());
//        Element elemts = element.getElementsByIndexEquals(0).get(0);
//        System.out.println(s);
        httpClient.close();

        CloseableHttpClient httpClient3= HttpClients.createDefault();
        HttpGet get2 = new HttpGet(url);
        get2.setHeader("Cookie","_uab_collina=151027458192280992381585; htVD_2132_saltkey=xX9FoFta; htVD_2132_lastvisit=1510270965; htVD_2132_client_created=1510274569; htVD_2132_client_token=773E02A829582276F0008BBC4BF459D3; htVD_2132_auth=060awd39ozTWQzS4v3SvmAE9vIRTfCfbl9RwEEqZnvVBQ9WNO2EbV4WoakdFdAj%2FLvbCwy1VyUSeHYvrduPGQip1v0U; htVD_2132_connect_login=1; htVD_2132_connect_uin=773E02A829582276F0008BBC4BF459D3; htVD_2132_nofavfid=1; htVD_2132_lip=219.146.132.30%2C1510274569; htVD_2132_pc_size_c=0; htVD_2132_sid=q2MMNS; htVD_2132_ulastactivity=1510366299%7C0; htVD_2132_lastviewtime=433299%7C1510366567; htVD_2132_lastcheckfeed=433299%7C1510366568; htVD_2132_st_p=433299%7C1510366573%7C501af98ad33c619805516b9c3d4656a0; htVD_2132_visitedfid=10D16D65D32; htVD_2132_viewid=tid_659915; htVD_2132_smile=1D1; Hm_lvt_46d556462595ed05e05f009cdafff31a=1510274582,1510366330; Hm_lpvt_46d556462595ed05e05f009cdafff31a=1510366595; htVD_2132_lastact=1510366592%09connect.php%09check; htVD_2132_connect_is_bind=1");
        get2.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        CloseableHttpResponse response3 = httpClient3.execute(get2);
        HttpEntity entity2 = response3.getEntity();
        String s2 = EntityUtils.toString(entity2, "UTF-8");
        System.out.println(s2);
        httpClient3.close();
    }

}
