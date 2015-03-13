<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<!-- 
		<f:js src="/js/biz/cardTypeSet/membClass.js"/>
		 -->
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js" defer="defer"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>

		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="modify.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">会员类型</td>
						<td>
							<s:textfield name="membClassTemp.membClass" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入会员类型</span>
						</td>
						<td width="80" height="30" align="right">类型名称</td>
						<td>
							<s:textfield name="membClassTemp.className" maxlength="3"></s:textfield>
							<span class="field_tipinfo">请输入类型名称</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">机构</td>
						<td>
							<s:textfield name="membClassTemp.branchCode" ></s:textfield>
							<span class="field_tipinfo">请选择机构</span>
						</td>
						<td width="80" height="30" align="right">会员级别</td>
						<td>
							<s:textfield  name="membClassTemp.membLevel" cssClass="{required:true,digit:true}"></s:textfield>
							<span class="field_tipinfo">请输入会员级别</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">会员级别名称</td>
						<td>
							<s:textfield  id="Id_membClassName" name="membClassTemp.membClassName" cssClass="{required:true}"></s:textfield>
							<span class="field_tipinfo">请选择会员级别名称</span>
						</td>
						
						<td width="80" height="30" align="right">会员升级方式</td>
						<td>
							<s:select id="Id_membUpgradeMthd" name="membClassTemp.membUpgradeMthd" list="membUpgradeMthdTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择升级方式</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right" >会员升级条件</td>
						<td>
							<s:textfield id="Id_membUpgradeThrhd" name="membClassTemp.membUpgradeThrhd" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请选择会员升级条件</span>
						</td>
						<td width="80" height="30" align="right">会员保级方式</td>
						<td>
							<s:select id="Id_membDegradeMthd" name="membClassTemp.membDegradeMthd" list="membDegradeMthdTypeList" 
								headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请输入会员保级方式</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">会员保级条件</td>
						<td>
							<s:textfield id="Id_membDegradeThrhd" name="membClassTemp.membDegradeThrhd" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入会员保级条件</span>
						</td>
						<td width="80" height="30" align="right">绝对失效日期</td>
						<td>
							<s:textfield name="membClassTemp.mustExpirDate" onclick="WdatePicker({minDate:'%y-%M-%d'})" 
								cssClass="required:true"/>
							<span class="field_tipinfo">请输入绝对失效日期</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">更新用户名</td>
						<td>
							<s:textfield id="Id_updateBy" name="membClassTemp.updateBy" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入更新用户名</span>
						</td>
						<td width="80" height="30" align="right" >更新时间</td>
						<td >
							<s:textfield id="Id_updateTime" name="membClassTemp.updateTime" ></s:textfield>
							<span class="field_tipinfo">请输入更新时间</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/singleProduct/memb/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_SINGLEPRODUCT_MEMB_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
				<li class="showli_div">积分名称：最长支持64个中文或128位英文字母。例如潮州移动神州行积分。</li>
				<!-- 
				<li class="showli_div">积分类型简称：最长支持5个中文或10位英文字母，用于POS机显示积分名称。</li>
				<li class="showli_div">积分返利规则类型：定义积分兑换返利资金的规则类型，兑换率见积分折扣率。有两种兑换方法。
				<ul class="showCicleLi_div">
				<li class="showCicleLi_div">第一种是满积分起兑，即只受理兑换积分必须大于参考积分的兑换请求；
				<li class="showCicleLi_div">第二种是整数倍起兑，即每次兑换的积分必须是积分参数的整数倍。</li>
				</ul>
				<li class="showli_div"> 积分返利兑换率：积分兑换返利资金时按此兑换率兑换，积分交易也按此兑换率折算成金额进行清算。例如，积分返利兑换率为20%，那么做积分返利操作时100积分可以返利20元。</li>
				<li class="showli_div"> 积分使用方法：</li>
				<ul class="showCicleLi_div">
					<li class="showCicleLi_div">永久有效，积分可以永久累积，并随时使用；</li>
					<li class="showCicleLi_div">限期分期积分，本期积分不可使用，上期积分限定在几个月内(见积分使用有效期，必须小于积分使用有效期)进行兑换或使用，逾期失效；</li>
					<li class="showCicleLi_div">分期积分，随时使用，若干期（见积分使用有效期）以前积分失效；</li>
					<li class="showCicleLi_div">分期积分折旧积分，永久有效，随时使用，但若干期（见积分使用有效期）之前的积分在每期期切的时候都折旧（折旧率见积分折旧率）。 </li>
				</ul>
				<%--<li class="showli_div">兑换期(月数)：仅在积分使用方法为限期分期积分的时候有效。</li>
				--%>
				<li class="showli_div">积分分期方法：自然周期，积分周期从该自然周期第一天开始；使用周期，送积分的当天作为积分周期的第一天。</li>
				<li class="showli_div">积分累积周期(月数)：指定一个积分周期是多少个月</li>
				 -->
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>