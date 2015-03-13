package gnete.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalHostIpManager {
	private static final Logger				logger		= LoggerFactory.getLogger(LocalHostIpManager.class);

	private static final String				KEY			= "hostIp";

	private static CacheMap<String, String>	hostIpCache	= new CacheMap<String, String>(5 * 60);		// 秒
	
	static{
		logger.info("初始化获得本机IP[{}]", getHostIp());
	}

	/** 
	 * 传入的ip是否与本机IP相同 
	 */
	public static boolean isSameIp(String cmpIp) {
		return cmpIp != null && cmpIp.equals(getHostIp());
	}

	/**
	 * 获得本机IP
	 */
	public static String getHostIp() {
		CacheMap<String, String>.ValueBean vb = hostIpCache.getValueBean(KEY);
		if (vb == null) {
			synchronized (hostIpCache) { // 防止并发
				vb = hostIpCache.getValueBean(KEY);
				if(vb==null){
					String ip = findHostIp();
					hostIpCache.put(KEY, ip);
					return ip;
				}else{
					return vb.getValue();
				}
			}
		} else {
			return vb.getValue();
		}
	}

	
	/** 查找本机IP */
	private static String findHostIp() {
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address
							&& StringUtils.isNotBlank(ip.getHostAddress())
							&& !ip.getHostAddress().equals("127.0.0.1")) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error("取主机IP异常!", e);
			return "";
		}
		logger.warn("未能获得主机IP!");
		return "";
	}
}
