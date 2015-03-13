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
		<f:js src="/js/biz/cardTypeSet/pointClass.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_ptDiscntRate').blur(function(){
				var rate = $('#id_ptDiscntRate').val();
				validateRate(rate);
			});
			$('#Id_ptDeprecRate').blur(function(){
				var rate = $('#Id_ptDeprecRate').val();
				validateRate(rate);
			});
			
			$('#Id_jinstType').change(jinstTypeChange);
		});
		
		function jinstTypeChange(){
			var jinstType = $('#Id_jinstType').val();
			if(jinstType=='2'){//商圈
				$('#merchGroup_1').show();
				$('#merchGroup_2').show();
				$('#Id_merchGroupId').removeAttr('disabled');
			}else if(jinstType=='0'){//通用
				$('#merchGroup_1').hide();
				$('#merchGroup_2').hide();
				$('#Id_merchGroupId').attr('disabled',true);
			}
		}
		
		function validateRate(rate){
			if(isEmpty(rate)){
				return;
			}
			// 0--100的整数
			if(!isDecimal(rate,"8,2")||rate<0 || rate>100){
				showMsg('请输入0至100的小数。');
				$(':submit').attr('disabled', 'true');
				return;
			} else {
				$(':submit').removeAttr('disabled');
			}
		}
		
			function check(){
				// 判断兑换期和积分分期数
				var ptClassParam1 = $('#Id_usage_2').val();
				var instmPeriod = $('#Id_instmPeriod').val();
				if (parseFloat(instmPeriod) < parseFloat(ptClassParam1)){
					showMsg('兑换期要小于等于积分分期数。');
					return;
				}
				return true;
			}
			function submitForm(){
				if(!check()){
					return false;
				}
				$("#inputForm").submit();
			}
		</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="add.do" id="inputForm" cssClass="validate">
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<tr>
						<td width="80" height="30" align="right">积分名称</td>
						<td>
							<s:textfield name="pointClassDef.className" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入名称</span>
						</td>
						<td width="80" height="30" align="right">积分类型简称</td>
						<td>
							<s:textfield name="pointClassDef.classShortName" maxlength="5" cssClass="{required:true}" ></s:textfield>
							<span class="field_tipinfo">请输入简称</span>
						</td>
					</tr>
					<tr>
					<s:if test="loginRoleType == 20">
						<td width="80" height="30" align="right">积分类型</td>
						<td>
							<s:select id="Id_jinstType" name="pointClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择积分类型</span>
						</td>
						</s:if>
						<td  id="merchGroup_1"  width="80" height="30" align="right" style="display:none">商圈</td>
						<td  id="merchGroup_2"  style="display:none">
							<s:select id="Id_merchGroupId"  disabled="true" name="pointClassDef.jinstId" list="merchGroupList" headerKey="" headerValue="--请选择--" listKey="groupId" listValue="groupName" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择商圈</span>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">积分返利规则类型</td>
						<td>
							<s:select id="Id_ptExchgRuleType" name="pointClassDef.ptExchgRuleType" list="ptExchgRuleTypeList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
							<span class="field_tipinfo">请选择规则类型</span>
						</td>
						<td width="80" height="30" align="right">参考积分</td>
						<td>
							<s:textfield id="Id_ptRef" name="pointClassDef.ptRef" cssClass="{required:true,digit:true}"></s:textfield>
							<span class="field_tipinfo">请输入积分</span>
						</td>
					</tr>
					<!--<s:if test="loginRoleType == 20">
					<tr>
						<td width="80" height="30" align="right">联名机构类型</td>
						<td>
							<s:select id="Id_jinstType" name="pointClassDef.jinstType" list="jinstTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="PointClass.jinstTypeChange('jinstName','Id_jinstId','Id_jinstType')"></s:select>
							<span class="field_tipinfo">请选择联名机构类型</span>
						</td>
						<td id="jinst_td1" width="80" height="30" align="right" style="display:none">联名机构</td>
						<td id="jinst_td2" style="display:none">
							<s:textfield id="jinstName" name="pointClassDef.jinstName" cssClass="{required:true}" disabled="true"></s:textfield>
							<s:hidden id="Id_jinstId" name="pointClassDef.jinstId" cssClass="{required:true}" disabled="true"></s:hidden>
							<span class="field_tipinfo">请选择联名机构</span>
						</td>
					</tr>
					</s:if>
					--><tr>
						<!--<td width="80" height="30" align="right">金额类型</td>
						<td>
							<s:select id="Id_amtType" name="pointClassDef.amtType" list="amtTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							<span class="field_tipinfo">请选择金额类型</span>
						</td>
						-->
						<td width="80" height="30" align="right">积分使用方法</td>
						<td>
							<s:select id="Id_ptUsage" name="pointClassDef.ptUsage" list="ptUsageList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" onchange="PointClass.ptUsageChange()"></s:select>
							<span class="field_tipinfo">请选择使用方法</span>
						</td>
					</tr>
					<tr id="usage_tr" style="display:none">
						<%--<td id="td_usage1_1" width="80" height="30" align="right" style="display:none">积分有效期数</td>
						--%>
						<td id="td_usage1_1" width="80" height="30" align="right" style="display:none">积分使用有效期(期数)</td>
						<td id="td_usage1_2" style="display:none">
							<s:textfield id="Id_ptValidityCyc1" name="pointClassDef.ptValidityCyc" cssClass="{required:true,digit:true,gt:0}" disabled="true"></s:textfield>
							<s:textfield id="Id_ptValidityCyc2" name="pointClassDef.ptValidityCyc" cssClass="{required:true,digit:true,min:0}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请输入整数期数</span>
						</td>
						<%--<td id="td_usage2_1" width="80" height="30" align="right" style="display:none">兑换期(月数)</td>
						--%>
						<td id="td_usage2_1" width="80" height="30" align="right" style="display:none">积分使用有效期(月数)</td>
						<td id="td_usage2_2" style="display:none">
							<s:textfield id="Id_usage_2" name="pointClassDef.ptClassParam1" cssClass="{required:true,digit:true,min:1}" disabled="true"></s:textfield>
							<span class="field_tipinfo"> <= 积分累积周期</span>
						</td>
						<%--<td id="td_usage3_1" width="80" height="30" align="right" style="display:none">折旧分期数(月数)</td>
						--%>
						<td id="td_usage3_1" width="80" height="30" align="right" style="display:none">积分使用有效期(月数)</td>
						<td id="td_usage3_2" style="display:none">
							<s:textfield id="Id_usage_4" name="pointClassDef.ptValidityCyc" cssClass="{required:true,digit:true,max:12,min:1}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请输入整数月数</span>
						</td>
					</tr>
					<tr id="usage_tr2" style="display:none">
						<td id="td_ptInsMthd_1" width="80" height="30" align="right" style="display:none">积分分期方法</td>
						<td id="td_ptInsMthd_2" style="display:none">
							<s:select id="Id_ptInstmMthd" name="pointClassDef.ptInstmMthd" list="ptInstmMthdList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}" disabled="true"></s:select>
							<span class="field_tipinfo">请选择方法</span>
						</td>
						<%--<td id="td_insPeriod_1" width="80" height="30" align="right" style="display:none">积分周期月数</td>
						--%>
						<td id="td_insPeriod_1" width="80" height="30" align="right" style="display:none">积分累积周期(月数)</td>
						<td id="td_insPeriod_2" style="display:none">
							<s:textfield id="Id_instmPeriod" name="pointClassDef.instmPeriod" cssClass="{required:true,digit:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请输入整数月数</span>
						</td>
					</tr>
					<!-- 保德积分专用 -->
					<tr id="id_baode_tr" style="display: none">
						<td id="td_baode_1" width="80" height="30" align="right">积分使用有效期(月数)</td>
						<td id="td_baode_2">
							<s:textfield name="" value="12" cssClass="readonly" readonly="true" ></s:textfield>
						</td>
					</tr>
					<tr id ="ptDeprecRate_tr" style="display:none">
						<td id="ptDeRate" width="80" height="30" align="right" style="display:none">积分折旧率</td>
						<td>
							<s:textfield id="Id_ptDeprecRate" name="pointClassDef.ptDeprecRate" cssClass="{required:true, num:true}" disabled="true"></s:textfield><span>%</span>
							<span class="field_tipinfo">请输入折旧率</span>
						</td>
					</tr>
					<tr  id ="ptDiscntRate_tr" >
						<td width="80" height="30" align="right">积分返利兑换率</td> 
						<td>
							<s:textfield id="id_ptDiscntRate" name="pointClassDef.ptDiscntRate" cssClass="{required:true, decimal:'8,2'}" ></s:textfield> <span>%</span>
							<span class="field_tipinfo">请输入兑换率（最多两位小数）</span>
						</td>
					</tr>
					<tr>
					<td width="80" height="30" align="right">积分用途列表</td>
						<td colspan="3">
							<s:iterator value="ptUseLimitList">
								<span>
								<input type="checkbox" name="ptUseLimitCodes" value="${value}"/>${name}<br />
								</span>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');PointClass.ptUsageChange()" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/pointClass/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_POINTCLASS_ADD"/>
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
					<li class="showli_div">积分类型简称：最长支持5个中文或10位英文字母，用于POS机显示积分名称。</li>
					<li class="showli_div">积分返利规则类型：定义积分兑换返利资金的规则类型，兑换率见积分折扣率。有两种兑换方法。
						<ul class="showCicleLi_div">
							<li class="showCicleLi_div">第一种是满积分起兑，即只受理兑换积分必须大于参考积分的兑换请求；</li>
							<li class="showCicleLi_div">第二种是整数倍起兑，即每次兑换的积分必须是积分参数的整数倍。</li>
						</ul>
					</li>
					<li class="showli_div"> 积分返利兑换率：积分兑换返利资金时按此兑换率兑换，积分交易也按此兑换率折算成金额进行清算。例如，积分返利兑换率为20%，那么做积分返利操作时100积分可以返利20元。</li>
					<li class="showli_div"> 积分使用方法：
						<ul class="showCicleLi_div">
							<li class="showCicleLi_div">永久有效，积分可以永久累积，并随时使用；</li>
							<li class="showCicleLi_div">限期分期积分，本期积分不可使用，上期积分限定在几个月内(见积分使用有效期，必须小于积分使用有效期)进行兑换或使用，逾期失效；</li>
							<li class="showCicleLi_div">分期积分，随时使用，若干期（见积分使用有效期）以前积分失效；</li>
							<li class="showCicleLi_div">分期积分折旧积分，永久有效，随时使用，但若干期（见积分使用有效期）之前的积分在每期期切的时候都折旧（折旧率见积分折旧率）。 </li>
						</ul>
					</li>
					<%--<li class="showli_div">兑换期(月数)：仅在积分使用方法为限期分期积分的时候有效。</li>
					--%>
					<li class="showli_div">积分分期方法：自然周期，积分周期从该自然周期第一天开始；使用周期，送积分的当天作为积分周期的第一天。</li>
					<li class="showli_div">积分累积周期(月数)：指定一个积分周期是多少个月</li>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>