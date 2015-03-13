package gnete.card.web;


/**
 * 用于负载均衡简单检查本地服务器是否可用 <br/>
 */
public class CheckWebAppAction extends BaseAction {
	
	@Override
	public String execute() throws Exception {
		return null;
	}
	
	public void checkWebApp() throws Exception {
		//TODO
		this.respond("SUCCESS");
	}
}
