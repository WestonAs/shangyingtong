package gnete.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ResourceUtil {
	public static InputStream getResourceAsStream(String path){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	public static File getResourceAsFile(String path){
		try {
			return new File(URLDecoder.decode(Thread.currentThread().getContextClassLoader().getResource(path).getFile(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
