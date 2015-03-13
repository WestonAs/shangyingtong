package flink.dao;

import java.util.List;

/**
 * 数据翻译接口.
 * 
 * @author haichen.ma
 *
 */
public interface Translater {
	List translate(List list);
}