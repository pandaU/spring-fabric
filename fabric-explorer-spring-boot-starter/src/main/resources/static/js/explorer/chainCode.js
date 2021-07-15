const basePath = $('#baseURL').val() + '/' + $('#path').val() + '/';

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
                        return '<a href="'+basePath+'chainCode/historyVersion/index?chainCodeName='+row.chainCodeName+'">' + row.chainCodeName;
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
                        return '<a href="'+basePath+'chainCode/historyVersion/index?chainCodeName='+row.chainCodeName+'">历史版本';
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
        url : basePath + "chainCode?currentPage=" + currentPage + "&pageSize=" + query.pageSize + "&chainCodeName=" + chainCodeName,
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
    var query = $('#query').val();
    // disable button
    $('#loadMoreButton').prop("disabled", true);
    // add spinner to button
    $('#loadMoreButton')
        .html(
            '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ' + loading);
    reloadDatas(callback,query,0);
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
$('#uploadModal').on('hidden.bs.modal', function (e) {
    clear();
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
var originalSubmit = '新增';
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
                    $("#submit").attr("disabled",true)
                    $("#submit").html(
                        '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> ' + '新增');
                    var formData = new FormData();
                    formData.append("file",$("#file").get(0).files[0]);
                    formData.append("name", $("#name").val());
                    formData.append("version", $("#version").val());
                    formData.append("language", $("#language").val());
                    $.ajax({
                        url: basePath + 'chainCode/deploy',
                        dataType: "json",
                        type: "post",
                        data: formData,
                        processData: false,
                        contentType: false,
                        error: function (res) {
                            $("#submit").attr("disabled",false)
                            $('#loadMoreButton').prop("disabled", false);
                            $("#submit").html(originalSubmit);
                            swal("错误", res.errorMsg, "error");
                        },
                        success: function (res) {
                            $("#submit").attr("disabled",false)
                            $('#loadMoreButton').prop("disabled", false);
                            $("#submit").html(originalSubmit);
                            if (res.status === 1){
                                swal("新增成功","智能合约部署成功", "success");
                                $('#uploadModal').modal('hide');
                                clear();
                            }else {
                                swal("新增失败","智能合约部署失败", "error");
                            }
                        }
                    })
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
function clear() {
    $("#name").val("");
    $("#version").val("");
    $("#file").val("");
}
function searchChainCode() {
    var query = $('#query').val();
    reloadDatas(callback,query,1)
}
function turnChainCode() {
    window.location.href = basePath + 'chainCode/index';
}
function index() {
    window.location.href = basePath;
}