/**
 * 
 */
package flink.util;

/**
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 雁联</p>
 * @author dhs
 * @version 1.0
 */
public class InfoView {

	private String infoCode;
	private String infoDesc;
	private String successPath;
	private String errorPath;
	/**
	 * 
	 */
	public InfoView() {
		super();
	}
	
	public InfoView(String desc)
	{
		super();
		this.setInfoDesc(desc);
	}

	public InfoView(String desc, String code)
	{
		super();
		this.setInfoDesc(desc);
		this.setInfoCode(code);
	}
	
	/**
	 * @return Returns the infoCode.
	 */
	public String getInfoCode() {
		return infoCode;
	}

	/**
	 * @param infoCode The infoCode to set.
	 */
	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}

	/**
	 * @return Returns the infoResult.
	 */
	public String getInfoDesc() {
		return infoDesc;
	}

	/**
	 * @param infoResult The infoResult to set.
	 */
	public void setInfoDesc(String infoResult) {
		this.infoDesc = infoResult;
	}

	/**
	 * @return Returns the successPath.
	 */
	public String getSuccessPath() {
		return successPath;
	}

	/**
	 * @param successPath The successPath to set.
	 */
	public void setSuccessPath(String successPath) {
		this.successPath = successPath;
	}

	/**
	 * @return Returns the errorPath.
	 */
	public String getErrorPath() {
		return errorPath;
	}

	/**
	 * @param errorPath The errorPath to set.
	 */
	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}

}
