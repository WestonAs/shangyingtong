package flink.web.tag;

public abstract class HtmlNavigatorFactory {
	public static final String CLASSICAL = "classical";
	public static final String GOOGLE = "google";
	public static final String IMAGE = "image";

	public static HtmlNavigator getInstance(String style) {
		if (CLASSICAL.equalsIgnoreCase(style)) {
			return new ClassicalHtmlNavigator();
		}
		else if (GOOGLE.equalsIgnoreCase(style)) {
			return new GoogleHtmlNavigator();
		}
		else if (IMAGE.equalsIgnoreCase(style)) {
			return new ImageHtmlNavigator();
		}
		else {
			try {
				return (HtmlNavigator) Class.forName(style).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new ClassicalHtmlNavigator();
		}
	}
}
