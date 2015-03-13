/*
 * Jquery选择插件:支持单选、多选，支持ajax异步加载数据，支持json
 * 
 * 参数说明:
 * title: 选择框的标题
 * hidden: 隐藏域的ID 
 * radio: 是否启用单选模式
 * column: 选择框中显示几列，总长度720px
 * model: 是否已模态方式显示
 * data： 加载的json数据，数据格式如：
 * 			var data = 
			[
				{id: "10",name: "苹果"},
				{id: "20",name: "香蕉"},
				{id: "30",name: "西瓜"},
				{id: "40",name: "桃子"},
				{id: "50",name: "葡萄"},
				{id: "60",name: "山竹"}
			];
 * url: 异步加载的URL
 * 
 * author: 李恒
 * date: 2010-9-8
 * 
 */
(function($) {
	
	$.fn.mulitselector = function(options) {

		var $input = $(this);
		var ms_html;
		var settings = 
		{
			title: "请选择类别",
			//value: "点击选择类别",
			formId: null,
			hidden: 'default_hidden_id',
			radio: false,
			column: 4, 
			model: true,
			data: null, 
			url: null,
			param: null,
			page: null,
			callback: null,
			exitFunc:null
		};
		if (options){
			jQuery.extend(settings, options);
		}
		$input.addClass('cursor_pointer').click(show).attr('readonly', 'true');

		function init(){
			addHiddenInput();
		}
		
		function addHiddenInput(){
			if ($('#' + settings.hidden).length > 0){return;}
			var html = '<input type="hidden" id="'+ settings.hidden +'"/>';
			if (settings.formId == null) {
				$(html).appendTo('form:first');
			} else {
				$(html).appendTo('#' + settings.formId);
			}
		}
		
		function isShow(){
			return $("#mulitSelector").length != 0;
		}
		
		function show() {
			if (isShow()) return;
			hideSelect();
			
			if (settings.model) {
				showMask();
			}
			
			/*
			var offset = $input.offset();
			var divtop = 1 + offset.top + document.getElementById($input.attr("id")).offsetHeight + 'px';
			var divleft = offset.left + 'px';
			if ((offset.left + 746) > document.documentElement.clientWidth) {
				divleft = (document.documentElement.clientWidth - 756) + 'px';
			}*/
			var offset = $input.offset();
			var divtop = '100px';
			var divleft = '100px';
			if ((offset.left + 746) > document.documentElement.clientWidth) {
				divleft = (document.documentElement.clientWidth - 756) + 'px';
			}
			
			var popmask = document.createElement('div');
			var html = [];

			html.push('<div id="mulitSelector" style="display:block; top:'+divtop+';left:'+divleft+'; position: absolute; z-index: 1999;">');
			html.push('    <div id="pslayer"  class="alert_div sech_div ms_width">');
			html.push('        <div class="box">');
			html.push('            <h1><span id="psHeader">'+settings.title+'</span><a href="javascript:void(0);" class="butn3" id="ms_img_close"></a></h1>');
			html.push('			<div class="blk">');
			html.push('				<div id="divSelecting" class="sech_layt">');
			html.push('					<h3>');
			html.push('						<span id="selectingHeader" style="white-space:nowrap">');
			
			// 添加已选择的元素
			var content = $input.val();
			if (content != "") {
				var selectedArray = $('#' + settings.hidden).val().split(',');
				var contentArray = content.split(',');
				if (selectedArray != undefined && selectedArray != null) {
					for (var i = 0; i < selectedArray.length; i++){
						html.push('<li id="'+ selectedArray[i] +'"><a onclick="Popupselector.removeSelect(\''+ selectedArray[i] +'\');" href="javascript:void(0);">'+ contentArray[i] +'</a></li>');
					}
				}
			}
			
			html.push('						</span><b class="btn_fst"><input id="ms_bt_ok" name="" type="button" value="确定" />');
			html.push('						<input id="ms_bt_clear" name="" type="button" value="清空" /></b>');
			html.push('					</h3>');
			html.push('				</div><div class="clear"></div>');
			html.push('				<div class="sech_layb"> ');
			html.push('					<h2 id="subHeader1"></h2>');
			html.push('					<ol id="allItems1">');
			html.push('					</ol>');
			html.push('				</div>');
			html.push('			</div>');
			html.push('		</div>');
			html.push('   </div>');
			html.push('</div>');
			ms_html = $(html.join("")).appendTo('body');
			
			// 指定了data的模式
			if (settings.data != null){
				getContentByJson(settings.data);
			}
			// 指定了异步加载data的模式
			else if (settings.url != null){
				$('#allItems1').html(LOAD_IMAGE);
				$.post(CONTEXT_PATH + settings.url, param, function(jsonArray){
					getContentByJson(jsonArray);
				});
			}
			// 指定了自定义页面的模式
			else if (settings.page != null) {
				$('#allItems1').load(CONTEXT_PATH + settings.page, settings.param, function(){
					try {settings.callback();}catch (e){}
					SysStyle.setFormGridStyle();
					SysStyle.setButtonStyle();
				});
			}
			bindBtn();
		}
		
		function getContentByJson(dataArray){
			var li_width = 720 / settings.column + 'px';
			
			var len = dataArray.length;
			var html = [];
			for(var i=0; i<len; i++){
				var d = dataArray[i];
				var status = findStatus(d.name);
				html.push('						<li id=$'+d.id+' name=100 class="nonelay" style="width:'+ li_width +'">');
				html.push('							<a href="javascript:void(0);">');
				html.push('							<label for="e_'+d.id+'">');
				if (settings.radio) {
					html.push('							<input id="e_'+d.id+'" name="e_name" type="radio" '+status+' value="'+(d.id+ '@'+ d.name)+'" onclick="Popupselector.addSelect(\''+ d.id +'\', \''+ d.name +'\')"/>'+d.name+'</label>');
				} else {
					html.push('							<input id="e_'+d.id+'" type="checkbox" '+status+' value="'+(d.id+ '@'+ d.name)+'" onclick="Popupselector.addSelect(\''+ d.id +'\', \''+ d.name +'\')"/>'+d.name+'</label>');
				}
				html.push('							</a>');
				html.push('						</li>');
			}
			return $(html.join("")).appendTo('#allItems1');
		}
		
		function showMask(){
			var height = document.body.scrollHeight;
			var clientHeight = document.documentElement.clientHeight;
			if (height < clientHeight) {
				height = clientHeight;
			}
			var mask = '<div id="mask" class="mask_bg" style="top: 0pt; left: 0pt; position: absolute; z-index: 1000; height: '+ height +'px; display: block;"></div>';
			$(mask).appendTo('body');
		}
		
		function hideMask(){
			if (settings.model){
				$('#mask').remove();
			}
		}

		function bindBtn(){
			$("#ms_bt_ok").click(function() {
				showSelect();
				var result = "";
				var resultId = "";
				var obj = $("#selectingHeader li");
				for(var i=0; i<obj.length; i++) {
					result += (i==0?"":",") + $(obj[i]).children().text();
					resultId += (i==0?"":",") + $(obj[i]).attr('id');
				}
				$input.val(result);
				$('#' + settings.hidden).val(resultId);
				ms_html.remove();
				hideMask();
				try {settings.exitFunc();}catch (e){}
			});

			$("#ms_bt_clear").click(function() {
				showSelect();
				$input.val('');
				$('#' + settings.hidden).val('');
				$('#selectingHeader').empty();
				ms_html.remove();
				hideMask();
				try {settings.exitFunc();}catch (e){}
			});

			$("#ms_img_close").click(function() {
				showSelect();
				ms_html.remove();
				hideMask();
				try {settings.exitFunc();}catch (e){}
			});
		}

		function findStatus(d){
			var content = $input.val();
			if ($.trim(content) == ""){
				return "";
			}
			var obj = content.split(",");
			for(var i=0; i<obj.length; i++){
				if(obj[i] == d){
					return "checked";
				}
			}
		}
		
		function hideSelect(){
			$('select').hide();
		}
		function showSelect(){
			$('select').show();
		}

		init();
	}
})(jQuery);

(function($) {
	$.fn.removeMulitselector = function() {
		$(this).removeClass('cursor_pointer').unbind('click').removeAttr('readonly');
	}
})(jQuery);

/**
 * 选择器
 */
Popupselector = {
	addSelect: function(id, name){
		if ($('#e_' + id).attr('checked')) {
			if ($('#allItems1').find(':radio').length > 0) {
				$('#selectingHeader').empty();
			}
			var html = '<li id="'+ id +'"><a onclick="Popupselector.removeSelect(\''+ id +'\');" href="javascript:void(0);">'+ name +'</a></li>';
			$(html).appendTo('#selectingHeader');
		} else {
			$('#' + id).remove();
		}
	},

	addAllSelect: function(target, checkboxName){
		var obj = $("#selectingHeader li");
		
		var selectedIds = "";
		obj.each(function(){
			selectedIds += 'e_' + $(this).attr('id')+ ',';
		});
		
		$(':checkbox[name="' + checkboxName + '"]').each(function() {
			$(this).attr('checked', target.checked);
			
			var id = $(this).attr('id').replace('e_', '');
			//alert(id);
			var name = $(this).val();
			//alert(name);
			if ($(this).attr('checked')) {
				if ($('#allItems1').find(':radio').length > 0) {
					$('#selectingHeader').empty();
				}
				if(selectedIds.indexOf(id) == -1 ) {
					var html = '<li id="'+ id +'"><a onclick="Popupselector.removeSelect(\''+ id +'\');" href="javascript:void(0);">'+ name +'</a></li>';
					$(html).appendTo('#selectingHeader');
				}
			} else {
				$('#' + id).remove();
			}
		});
		
	},
	
	removeSelect:function(id){
		$('#' + id).remove();
		$('#e_' + id).removeAttr('checked');
	}, 
	
	initPageSelect:function(){
		var obj = $("#selectingHeader li");
		if (obj.length == 0) {return;}
		var ids = $(':checkbox[name="ids"]');
		if (ids.length == 10) {$('#selectAll').attr('checked', 'true');}
		var selectedIds = "";
		obj.each(function(){
			selectedIds += 'e_' + $(this).attr('id')+ ',';
		});
		$(':checkbox').each(function(){
			if (selectedIds.indexOf($(this).attr('id')) != -1){
				$(this).attr('checked', 'true');
			}
		});
		$(':radio').each(function(){
			if (selectedIds.indexOf($(this).attr('id')) != -1){
				$(this).attr('checked', 'true');
			}
		});
	}
}
