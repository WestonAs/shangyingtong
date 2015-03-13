/**
 * Paginater 分页
 * @author haichen.ma
 */
Paginater = {
	toPage: function(formIndex, pageIndex) {
		var searchForm = document.forms[formIndex];

		if (!searchForm) {
			return;
		}

		if (!searchForm.elements['pageNumber']) {
			searchForm.innerHTML += '<input type="hidden" name="pageNumber"/>';
		}

		if (!searchForm.elements['pageSize']) {
			searchForm.innerHTML += '<input type="hidden" name="pageSize"/>';
		}

		searchForm.pageNumber.value = pageIndex;
		searchForm.pageSize.value = this.getPageSize(formIndex);
		var canSubmit = true;
		
		if (searchForm.onsubmit) {
			canSubmit = searchForm.onsubmit();
		}

		if (canSubmit) {
			searchForm.submit();
		}
	},
	
	getPageSize: function(formIndex) {
		var pageSize = document.getElementById("pageSize_" + formIndex).value;;
		
		if (pageSize.trim().length == 0 || !pageSize.isNumber()) {
			return 20;
		}
		else {
			return pageSize;
		}
	},
	
	goPage: function(formIndex) {
		var pageIndex = document.getElementById("goPageIndex_" + formIndex).value;
	
		if (pageIndex.trim().length == 0 || !pageIndex.isNumber()) {
			return;
		}
		
		this.toPage(formIndex, pageIndex);
	},
	
	/**
	 * 回车跳转到某页.
	 * @param {} formIndex
	 */
	keyToPage: function(formIndex, event) {
		var eventObj=(event==null) ? window.event : event;      
		var keyCode = (eventObj.which) ? eventObj.which : eventObj.keyCode;   
		if (keyCode == 13) {
			this.goPage(formIndex);
		}
	}
}

String.prototype.isNumber = function() {
	if (this !== "") {
		return /^([0-9]*)$/.test(this);
	}
};

String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
};