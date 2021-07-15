const basePath = $('#baseURL').val() + '/' + $('#path').val() + '/';
const chainCodeName = $('#codeName').val()
const viewTitle = $('#viewTitle').val();
const loading = $('#loading').val();
$(document).ready(function() {

    initDataTable();

    loadMore();
});

function initDataTable() {
    $('#dataTable').DataTable(
        {
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
                    "name" : "chainCodeName",
                    "data" : function(row) {
                        return '<a href="'+basePath+'chainCode/detail?chainCodeName='+row.chainCodeName+'">' + row.chainCodeName;
                    }
                },
                {
                    "name" : "chainCodeVersion",
                    "data" : function(row) {
                        return row.chainCodeVersion;
                    }
                },
                {
                    "name" : "chainCodeSequence",
                    "data" : function(row) {
                        return row.chainCodeSequence;
                    }
                } ,
                {
                    "name" : "chainCodePolicy",
                    "data" : function(row) {
                        return row.chainCodePolicy;
                    }
                },
                {
                    "name" : "chainCodeLanguage",
                    "data" : function(row) {
                        return row.chainCodeLanguage;
                    }
                },
                {
                    "name" : "chainCodePackageId",
                    "data" : function(row) {
                        return row.chainCodePackageId;
                    }
                },
                {
                    "name" : "createTime",
                    "data" : function(row) {
                        return row.createTime.substr(0,10);
                    }
                },
                {
                    "name" : "op",
                    "data" : function(row) {
                        return row.status === 1 ? "运行中" : "已下线"
                    }
                }]
        });

};

var currentPage = 1;
function reloadDatas(callback,chainCodeName,isQuery) {
    if (!chainCodeName){
        chainCodeName = ''
    }
    var query = {}
    query.pageSize = 5;
    query.currentPage = currentPage;
    $.ajax({
        url : basePath + "chainCode/historyVersion?currentPage=" + currentPage + "&pageSize=" + query.pageSize + "&chainCodeName=" + chainCodeName,
        type : 'GET',
        cache : false,
    }).then(function success(res) {
        currentPage = res.currentPage + 1;
        callback(res,isQuery);
    }, function fail(data, status) {

    });
}
var original = $('#loadMoreButton').html();
function loadMore() {
    // disable button
    $('#loadMoreButton').prop("disabled", true);
    // add spinner to button
    $('#loadMoreButton')
        .html(
            '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ' + loading);
    reloadDatas(callback,chainCodeName,0);
}
function callback(res,isQuery){
    var table = $('#dataTable').DataTable();
    if (isQuery === 1){
        currentPage = 1;
        table.clear()
    }
    table.rows.add(res.data);
    table.draw();

    // disable button
    $('#loadMoreButton').prop("disabled", false);
    // add spinner to button
    $('#loadMoreButton').html(original);

    $('[data-toggle="tooltip"]').tooltip();
}
function turnChainCode() {
    window.location.href = basePath + 'chainCode/index';
}
function index() {
    window.location.href = basePath;
}