package flink.security.impl;

import java.security.Provider;
import flink.security.ISecProviderLoad;

/**
 * <p>本项目将使用bouncycastle作为证书等解析等缺省的供应商</p>
 * @Project: Card
 * @File: CardSecProviderImpl.java
 * @See:
 * @author: aps-zbw
 * @modified:
 * @Email: aps-zbw@cnaps.com.cn
 * @Date: 2010-12-8
 * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
 * @version: V1.0
 */
public class CardSecProviderImpl extends AbstractSecProviderLoadImpl {
	private final static ISecProviderLoad instance = new CardSecProviderImpl();

	private CardSecProviderImpl() {
		super();
	}

	public static ISecProviderLoad getInstance() {
		return instance;
	}

	@Override
	protected Provider getSupportProvider() {
		return new org.bouncycastle.jce.provider.BouncyCastleProvider();
	}

}
