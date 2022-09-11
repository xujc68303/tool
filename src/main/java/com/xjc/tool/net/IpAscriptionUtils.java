package com.xjc.tool.net;

import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * AddressUtils.java
 *
 * @author Xujc
 * @date 2022/9/11
 */
public class IpAscriptionUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpAscriptionUtils.class);

    private static byte[] buff;

    private static volatile IpAscriptionUtils ipAscriptionUtils;

    private static final String dbPath = "xdb/ip2region.xdb";

    public static IpAscriptionUtils getInstance() {
        if (null == ipAscriptionUtils) {
            ipAscriptionUtils = new IpAscriptionUtils();
            synchronized (IpAscriptionUtils.class) {
                if (null == ipAscriptionUtils) {
                    ipAscriptionUtils = new IpAscriptionUtils();
                }
            }
        }
        return ipAscriptionUtils;
    }

    public AscriptionInfo getAscription(String ip) {
        AscriptionInfo ascriptionInfo = new AscriptionInfo();
        String addressStr = getAddressStr(ip);
        if (StringUtils.isBlank(addressStr)) {
            return ascriptionInfo;
        }
        // 国家|区域|省份|城市|ISP
        String[] split = addressStr.split("\\|");
        if (split.length >= 5) {
            ascriptionInfo.setCountry(split[0]);
            ascriptionInfo.setRegion(split[1]);
            ascriptionInfo.setProvince(split[2]);
            ascriptionInfo.setCity(split[3]);
            ascriptionInfo.setIsp(split[4]);
        }
        return ascriptionInfo;
    }

    private IpAscriptionUtils() {
        try {
            URL resource = ClassLoader.getSystemClassLoader().getResource(dbPath);
            if (null != resource) {
                buff = Searcher.loadContentFromFile(resource.getPath());
            }
        } catch (Exception e) {
            logger.error("failed to load content from dbPath={}, e={}", dbPath, e.getMessage());
        }
    }

    private String getAddressStr(String ip) {
        Searcher searcher = null;
        try {
            if (null != buff) {
                getInstance();
            }
            searcher = Searcher.newWithBuffer(buff);
            return searcher.search(ip);
        } catch (Exception e) {
            logger.error("failed to create content cached searcher, e={}", e.getMessage());
        } finally {
            if (null != searcher) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    logger.error("failed to create content cached searcher close, e={}", e.getMessage());
                }
            }
        }
        return null;
    }

}
