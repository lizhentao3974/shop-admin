<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/15
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<jsp:include page="/commons/includeScript.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">品牌列表
                    <button type="button" class="btn btn-success" onclick="addBrand()"><span
                            class="glyphicon glyphicon-plus"></span>添加
                    </button>
                    <button type="button" class="btn btn-danger" onclick="deleteBrandCache()"><i
                            class="glyphicon glyphicon-trash"></i>手工清缓存
                    </button>
                </div>
                <div class="panel-body">
                    <table id="example" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>品牌ID</th>
                            <th>LOGO</th>
                            <th>品牌名称</th>
                            <th>是否推荐</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <th>品牌ID</th>
                        <th>LOGO</th>
                        <th>品牌名称</th>
                        <th>是否推荐</th>
                        <th>操作</th>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        initTable();
    })

    function datetimepicker(dateParam) {
        $('#' + dateParam).datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
    }

    function deleteBrandCache() {
        $.ajax({
            type: "post",
            url: "/brand/deleteBrandCache.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    bootbox.alert({
                        message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>清除成功</b></font>',
                        title: "<font color='#663399'>提示信息</font>"
                    })
                }
            }
        })
    }


    var productTable;

    function initTable() {
        productTable = $('#example').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/brand/queryBrandList.jhtml",
                type: "post",
            },
            "columns": [
                {"data": "id"},
                {
                    "data": "logo", "render": function (data, type, row, meta) {
                        return "<img src='" + data + "' width='80px'>";
                    }
                },
                {"data": "brandName"},
                {
                    "data": "isRecommend", "render": function (data, type, row, meta) {
                        return data == "0" ? "不推荐" : "推荐";
                    }
                },
                {
                    "data": "id", "render": function (data, type, row, meta) {

                        var isRecommend = row.isRecommend;
                        var v_isRecommend_text = "";
                        var v_isRecommend_class = "";
                        var v_isRecommend_color = "";
                        if (isRecommend == "0") {
                            v_isRecommend_text = "推荐";
                            v_isRecommend_class = "glyphicon glyphicon-arrow-up";
                            v_isRecommend_color = "btn btn-success";
                            isRecommend = "1";
                        } else {
                            v_isRecommend_text = "不推荐";
                            v_isRecommend_class = "glyphicon glyphicon-arrow-down";
                            v_isRecommend_color = "btn btn-danger";
                            isRecommend = "0";
                        }


                        return '<button type="button" class="btn btn-warning" onclick="updateBrand(' + JSON.stringify(row).replace(/\"/g, "'") + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
                            '&nbsp;<button type="button" class="btn btn-danger" onclick="deleteBrand(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>' +
                            '<button type="button" class="' + v_isRecommend_color + '" onclick="updateIsRecommend(\'' + data + '\',\'' + isRecommend + '\')"><span class="' + v_isRecommend_class + '"></span>' + v_isRecommend_text + '</button>';
                    }
                },
            ]
        })
    }

    function search() {
        productTable.ajax.reload();
    }

    function updateIsRecommend(id, isRecommend) {
        $.ajax({
            type: "post",
            url: "/brand/updateIsRecommend.jhtml",
            data: {"id": id, "isRecommend": isRecommend},
            success: function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }

    function deleteBrand(brandId) {
        bootbox.confirm({
            title: "删除品牌",
            message: "请点击确认删除品牌",
            buttons: {
                cancel: {
                    label: "取消",
                    className: "btn-info"
                },
                confirm: {
                    label: "确认",
                    className: "btn-success"
                }
            },
            callback: function (result) {
                if (result) {
                    $.ajax({
                        url: "/brand/deleteBrand.jhtml",
                        type: "post",
                        data: {"id": brandId},
                        success: function (result) {
                            if (result.code == 200) {
                                productTable.ajax.reload();
                            }
                        }
                    })
                }
            }
        })

    }

    function addBrand() {
        bootbox.confirm({
            message: $("#addForm").html(),
            buttons: {
                confirm: {
                    label: "保存",
                    className: "btn-success"
                },
                cancel: {
                    label: "取消",
                    className: "btn-info"
                },
            },
            callback: function (result) {
                if (result) {
                    $.post({
                        url: "/brand/addBrand.jhtml",
                        data: $("#brandForm").serialize(),
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
                                productTable.ajax.reload();
                            }
                        },
                        error: function () {
                            alert("新增失败")
                        }
                    })
                }
                console.log('This was logged in the callback: ' + result);
            }
        });
        initFileInput();
    }

    function updateBrand(row) {
        $.ajax({
            url: "/brand/editBrand.jhtml",
            data: {
                "id": row.id,
                "brandName": row.brandName,
                "logo": row.logo
            },
            type: "post",
            success: function (result) {
                var logoArr = [];
                logoArr.push(row.logo);
                console.log(logoArr);
                initFileInput(logoArr);
                bootbox.dialog({
                    title: "修改品牌信息",
                    message: result,
                    size: "large",
                    buttons: {
                        cancel: {
                            label: "取消",
                            className: 'btn-danger',
                            callback: function () {
                                console.log('Custom cancel clicked');
                            }
                        },
                        ok: {
                            label: "修改",
                            className: 'btn-info',
                            callback: function () {
                                $.ajax({
                                    url: "/brand/updateBrand.jhtml",
                                    data: $("#brandForm").serialize(),
                                    dataType: "json",
                                    type: "post",
                                    success: function (result) {
                                        if (result.code == 200) {
                                            location.href = "/brand/toBrandList.jhtml";
                                        }
                                    }
                                })
                                console.log('Custom OK clicked');
                            }
                        }
                    },
                });
                initFileInput(logoArr);
            },
            error: function () {
                alert("用户信息获取异常");
            }
        })
    }

    function initFileInput(logoImage) {
        $("#brandLogo").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            showCaption: false,
            dropZoneEnabled: true,
            initialPreview: logoImage,
            initialPreviewAsData: true
        }).on("fileuploaded", function (event, result, previewId, index) {
            var photo = result.response.data;
            $("#logoPath").val(photo);
            $("#echoLogo").val(photo);
        })
    }
</script>

<script type="text/html" name="addBrand" id="addForm">
    <form class="form-horizontal" role="form" name="addBrands" id="brandForm">
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌名：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="brandName" placeholder="请输入品牌名...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌名：</label>
            <div class="col-sm-10">
                <input type="hidden" name="logo" id="logoPath">
                <input type="file" class="form-control" name="filePhoto" id="brandLogo">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">排序：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="sort" placeholder="请排序...">
            </div>
        </div>
    </form>


</script>


</body>
</html>
