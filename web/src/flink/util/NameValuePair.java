/**
 * 
 */
package flink.util;

/**
 * @author haichen.ma
 *
 */
public class NameValuePair implements NameValue {
	private String name;
	private String value;
	
	public NameValuePair() {
	}
	
	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see com.flink.model.NameValue#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.flink.model.NameValue#getValue()
	 */
	public String getValue() {
		return this.value;
	}

}
