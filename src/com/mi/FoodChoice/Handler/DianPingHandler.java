package com.mi.FoodChoice.Handler;

import com.mi.FoodChoice.data.Constants;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DianPingHandler {

    private static String URL_FIND_BUSINESS = "http://api.dianping.com/v1/business/find_businesses?";
    private static String URL_SINGLE_SHOP = "http://api.dianping.com/v1/business/get_single_business";
    private static String URL_BATCH_DEALS = "http://api.dianping.com/v1/deal/get_batch_deals_by_id?";
    private static String URL_SHOP_DEAL = "http://api.dianping.com/v1/deal/get_deals_by_business_id?";

    public static String searchNearShop(Map<String, String> paramMap) {
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        return doQuery(URL_FIND_BUSINESS, paramMap);
    }

    public static String searchDealsByBusinessId(String businessId) {
        final Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("city", "北京");
        paramMap.put("business_id", businessId);
        return doQuery(URL_SHOP_DEAL, paramMap);
    }

    public static String searchBatchDealsById(String ids) {
        final Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("deal_ids", ids);
        return doQuery(URL_BATCH_DEALS, paramMap);
    }

    public static String searchShopById(String id) {
        final Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("business_id", id);
        return doQuery(URL_SINGLE_SHOP, paramMap);
    }

    private static String doQuery(String apiUrl, Map<String, String> paramMap) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        stringBuilder.append(Constants.APP_KEY);
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        String codes = stringBuilder.append(Constants.APP_SECRET).toString();
        // SHA-1编码， 这里使用的是Apache-codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
        String sign = new String(Hex.encodeHex(DigestUtils.sha1(codes))).toUpperCase();

        // 添加签名
        stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(Constants.APP_KEY).append("&sign=").append(sign);
        BufferedReader reader = null;
        try {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String queryString = stringBuilder.toString();
            URL url = new URL(apiUrl + queryString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("GET");
            conn.connect();
            StringBuffer response = new StringBuffer();
            reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.getProperty("line.separator"));
            }
            return response.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return null;
    }
}
