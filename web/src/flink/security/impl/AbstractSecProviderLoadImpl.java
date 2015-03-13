package flink.security.impl;

import java.security.Provider;
import java.security.Security;

import flink.security.ISecProviderLoad;

/**
 *  <p>证书安全规范实现供应商抽象类(由子类指明具体供应商)</p>
 *  <p>注意如果是第三方需要在Security.addProvider(XX)中指明</p>
  * @Project: Card
  * @File: AbstractSecProviderLoadImpl.java
  * @See:
  * @author: aps-zbw
  * @modified:
  * @Email: aps-zbw@cnaps.com.cn
  * @Date: 2010-12-8
  * @CopyEdition: 深圳雁联计算系统有限公司 支付一部 2010 版权所有
  * @version:  V1.0
 */
public abstract class AbstractSecProviderLoadImpl implements ISecProviderLoad {
	protected final Provider provider = getSupportProvider();

	protected AbstractSecProviderLoadImpl() {
		init();
	}

	public Provider getProvider() {
		return provider;
	}

	private void init() {
		Security.addProvider(provider);
	}

	protected abstract Provider getSupportProvider();

}
