const basePath = $('#baseURL').val() + '/' + $('#path').val() + '/';

const viewTitle = $('#viewTitle').val();
const loading = $('#loading').val();
$(document).ready(function() {

	initDataTable();

	queryBlockchainInfo();

	loadMore();

	$('#query').bind('keypress keydown keyup', function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			performSearch();
		}
	});

	$('[data-toggle="tooltip"]').tooltip();
});

function queryBlockchainInfo() {
	$.ajax({
		url : basePath + "query/ledger",
		type: "POST"
	}).then(
			function success(res) {
				if (res.status > 0) {
					$('#ledgerHeight').html(res.data.height);
					$('#peerInfo').html(res.data.peers);
					$('#orgInfo').html(res.data.orgs.length);
					$('#orgInfo').attr("title", res.data.orgs.toString());
					$('#orgInfo').attr("data-original-title",
							res.data.orgs.toString());
					$('#commonInfo').html(res.data.channel);
					$('#chaincodeInfo').html(res.data.chaincode);
					$('#chaincodeInfo').attr("title", res.data.chaincodeName);
					$('#chaincodeInfo').attr("data-original-title",
							res.data.chaincodeName);

					$('[data-toggle="tooltip"]').tooltip();
				}
			}, function fail(data, status) {

			});
}

function initDataTable() {
	$('#dataTable').DataTable(
			{
				// "ajax" : function(data, callback) {
				// reloadDatas(callback);
				// },
				"paging" : false,
				"ordering" : false,
				"info" : false,
				"searching" : false,
				"processing" : true,
				"serverSide" : false,
				"scrollY" : '50vh',
				"scrollCollapse" : true,
				"scroller" : {
					"loadingIndicator" : true
				},
				"language" : {
					"emptyTable" : "-",
					"processing" : "Loading……"
				},
				"columns" : [
						{
							"name" : "blockNumber",
							"data" : function(row) {
								return '#' + row.blockNumber;
							}
						},
						{
							"name" : "currentHash",
							"data" : function(row) {
								return '<div data-toggle="tooltip" title="'
										+ row.currentHash + '">'
										+ row.currentHash.substring(0, 40)
										+ '...</div>';
							}
						},
						{
							"name" : "dataHash",
							"data" : function(row) {
								return '<div data-toggle="tooltip" title="'
										+ row.dataHash + '">'
										+ row.dataHash.substring(0, 40)
										+ '...</div>';
							}
						},
						{
							"name" : "transactionCount",
							"data" : function(row) {
								return '<a href="' + basePath + 'block?height='
										+ row.blockNumber
										+ '" data-toggle="tooltip" title="'
										+ viewTitle + '">'
										+ row.transactionCount + '</a>';
							}
						} ]
			});

};

var refreshDataTable = function() {
	$('#dataTable').DataTable().ajax.reload();
};

var bookmark;

function reloadDatas(callback) {
	var query = {}
	query.pageSize = 5;
	query.bookmark = bookmark;
	$.ajax({
		url : basePath + "query/blockpage",
		type : 'POST',
		cache : false,
		data : JSON.stringify(query),
		contentType : "application/json;charset=utf-8",
	}).then(function success(res) {
		bookmark = res.bookmark;
		callback(res);
	}, function fail(data, status) {

	});
}

function loadMore() {
	// disable button
	$('#loadMoreButton').prop("disabled", true);
	// add spinner to button
	var original = $('#loadMoreButton').html();
	$('#loadMoreButton')
			.html(
					'<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ' + loading);
	reloadDatas(function(res) {
		var table = $('#dataTable').DataTable();
		table.rows.add(res.data);
		table.draw();

		// disable button
		$('#loadMoreButton').prop("disabled", false);
		// add spinner to button
		$('#loadMoreButton').html(original);

		$('[data-toggle="tooltip"]').tooltip();
	});
}

function performSearch() {
	var query = $('#query').val();
	if (isNaN(query)) {
		window.location.href = basePath + 'tx?txid=' + query;
	} else {
		window.location.href = basePath + 'block?height=' + query;
	}
}

$('#uploadModal').on('hidden.bs.modal', function (e) {
	$("#name").val("");
	$("#version").val("");
	$("#file").val("");
})
function validFile() {
	var file = $("#file")[0].files[0]
	var fileSize = file.size/(1024*1024);
	var filePath = $("#file").val();
	if(fileSize>30){
		swal("错误", "上传单个文件大小不能超过30MB!", "warning");
		return false;
	}
	var fix = filePath.substr(filePath.lastIndexOf('.'));
	var lowFix = fix.toLowerCase();
	if (lowFix !== '.zip'){
		swal("错误", "文件格式不合法,请选择zip", "warning");
	}

}
(function() {
	'use strict';
	window.addEventListener('load', function() {
		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.getElementsByClassName('needs-validation');
		// Loop over them and prevent submission
		var validation = Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}
				if (form.checkValidity()){
					event.preventDefault();
					event.stopPropagation();
					var formData = new FormData();
					formData.append("file",$("#file").get(0).files[0]);
					formData.append("name", $("#name").val());
					formData.append("version", $("#version").val());
					formData.append("language", $("#language").val());
					$.ajax({
						url: 'manager/chainCode/deploy',
						dataType: "json",
						type: "post",
						data: formData,
						processData: false,
						contentType: false,
						error: function (res) {
							swal("错误", res.errorMsg, "error");
						},
						success: function (res) {
							swal("新增成功","智能合约部署成功", "success");
							$('#uploadModal').modal('hide')
						}
					})
				}
				form.classList.add('was-validated');
			}, false);
		});
	}, false);
})();

