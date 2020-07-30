<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/2/29
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/commons/includeCss.jsp"/>
    <style>
        td.details-control {
            background: url('/js/DataTables/images/details_open.png') no-repeat center center;
            cursor: pointer;
        }

        tr.shown td.details-control {
            background: url('/js/DataTables/images/details_close.png') no-repeat center center;
        }
    </style>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">商品查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="emport">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">商品名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="productName" id="goodsName"
                                           placeholder="请输入商品名称...">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">品牌名：</label>
                                <div id="brandListSelectDiv" class="col-sm-4">

                                </div>
                            </div>

                            <div style="text-align: center">
                                <button type="button" class="btn btn-primary" onclick="search()"><i
                                        class="glyphicon glyphicon-search"></i>查询
                                </button>
                                <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">商品列表
                    <button type="button" class="btn btn-success" onclick="toAddGoods()"><i
                            class="glyphicon glyphicon-plus"></i>添加商品
                    </button>
                    <button type="button" class="btn btn-success" onclick="deleteHotProductCache()"><i
                            class="glyphicon glyphicon-plus"></i>手工清缓存
                    </button>
                </div>
                <div class="panel-body">
                    <table id="goodsTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th></th>
                            <th>选择</th>
                            <th>商品id</th>
                            <th>商品名</th>
                            <th>主图</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>品牌名</th>
                            <th>分类名</th>
                            <th>是否热销</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <th></th>
                        <th>选择</th>
                        <th>商品id</th>
                        <th>商品名</th>
                        <th>主图</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>品牌名</th>
                        <th>分类名</th>
                        <th>是否热销</th>
                        <th>状态</th>
                        <th>操作</th>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/commons/includeScript.jsp"/>

<script>

    $(function () {
        initTable();
        brandListSelect();
        initDataTables();
    })

    function deleteHotProductCache() {
        $.ajax({
            type: "post",
            url: "/goods/deleteHotProductCache.jhtml",
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

    function search() {
        var v_param = {};
        v_param.productName = $("#goodsName").val();
        v_param.brandId = $("#selectListId").val();
        goodsTable.settings()[0].ajax.data = v_param;
        goodsTable.ajax.reload();
    }

    function toAddGoods() {
        location.href = "/goods/toAdd.jhtml";
    }

    function brandListSelect() {
        $.ajax({
            url: "/brand/querySelectBrand.jhtml",
            type: "post",
            success: function (result) {
                var selectHtml = "<select class=\"form-control\" name='brandId' id='selectListId'><option value='-1'>--请选择--</option>";
                var data = result.data;
                for (var i = 0; i < data.length; i++) {
                    var v_selectBrand = data[i];
                    selectHtml += "<option value = '" + v_selectBrand.id + "'>" + v_selectBrand.brandName + "</option>";
                }
                selectHtml += "</select>";
                $("#brandListSelectDiv").html(selectHtml);
            }
        })
    }

    var goodsTable;

    function initTable() {
        goodsTable = $('#goodsTable').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "ordering": false,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/goods/findGoodsList.jhtml",
                type: "post",
            },
            columns: [
                {
                    "className": 'details-control',
                    "orderable": false,
                    "data": null,
                    "defaultContent": ''
                },
                {
                    "data": "goodsCommon.id", "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='" + data + "'>";
                    }
                },
                {"data": "goodsCommon.id"},
                {"data": "goodsCommon.productName"},
                {
                    "data": "goodsCommon.mainImage",
                    "render": function (data, type, row, meta) {
                        return '<img src="' + data + '" width="100px" height="100px"/>'
                    }
                },
                {"data": "goodsCommon.price"},
                {"data": "goodsCommon.stock"},
                {"data": "goodsCommon.brandName"},
                {"data": "goodsCommon.cateName"},
                {
                    "data": "goodsCommon.isHot", "render": function (data, type, row, meta) {
                        return data == "0" ? "非热销" : "热销"
                    }
                },
                {
                    "data": "goodsCommon.status", "render": function (data, type, row, meta) {
                        return data == "0" ? "下架" : "上架"
                    }
                },
                {
                    "data": "goodsCommon.id", "render": function (data, type, row, meta) {
                        var isHot = row.goodsCommon.isHot;
                        var v_isHot_text = "";
                        var v_isHot_class = "";
                        var v_isHot_color = "";
                        if (isHot == "0") {
                            v_isHot_text = "热销";
                            v_isHot_class = "glyphicon glyphicon-fire";
                            v_isHot_color = "btn btn-success";
                            isHot = "1";
                        } else {
                            v_isHot_text = "非热销";
                            v_isHot_class = "glyphicon glyphicon-floppy-remove";
                            v_isHot_color = "btn btn-danger";
                            isHot = "0";
                        }

                        var status = row.goodsCommon.status;
                        var v_status_text = "";
                        var v_status_class = "";
                        var v_status_color = "";
                        if (status == "0") {
                            v_status_text = "上架";
                            v_status_class = "glyphicon glyphicon-arrow-up";
                            v_status_color = "btn btn-success";
                            status = "1";
                        } else {
                            v_status_text = "下架";
                            v_status_class = "glyphicon glyphicon-arrow-down";
                            v_status_color = "btn btn-danger";
                            status = "0";
                        }

                        return '<button type="button" class="btn btn-danger" onclick="deleteGoods(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>' +
                            '<button type="button" class="btn btn-warning" onclick="edit(' + data + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
                            '<button type="button" class="' + v_isHot_color + '" onclick="updateHot(\'' + data + '\',\'' + isHot + '\')"><span class="' + v_isHot_class + '"></span>' + v_isHot_text + '</button>' +
                            '<button type="button" class="' + v_status_color + '" onclick="updateStatus(\'' + data + '\',\'' + status + '\')"><span class="' + v_status_class + '"></span>' + v_status_text + '</button>';
                    }
                }

            ],
        });

    }

    function deleteGoods(id) {
        bootbox.confirm({
            title: "删除商品",
            message: "请点击是否删除",
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
                        type: "post",
                        data: {"id": id},
                        url: "/goods/deleteGoodsById.jhtml",
                        success: function (retult) {
                            if (retult.code == 200) {
                                search();
                            }
                        }
                    })
                }
            }
        })
    }

    function updateHot(goodsCommonId, hot) {
        $.ajax({
            type: "post",
            url: "/goods/updateHot.jhtml",
            data: {"goodsCommonId": goodsCommonId, "hot": hot},
            success: function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }


    function updateStatus(goodsCommonId, status) {
        $.ajax({
            type: "post",
            url: "/goods/updateStatus.jhtml",
            data: {"goodsCommonId": goodsCommonId, "status": status},
            success: function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }

    function initDataTables() {

        $('#goodsTable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = goodsTable.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child(format(row.data())).show();
                tr.addClass('shown');
            }
        });
    }


    function format(row) {
        var goodsList = row.goodsList;
        var v_html = '<div>';
        for (let sku of goodsList) {
            v_html += '<table class="table table-bordered" style="width: 48%;float:left;margin: 5px">' +
                "<tr>" +
                "<td>主图:</td>" +
                "<td><img src='" + sku.mainImage + "' width='100px' height='100px'/> </td>" +
                "</tr>" +
                '<tr>' +
                '<td>SKU商品名:</td>' +
                '<td>' + sku.productName + '</td>' +
                '</tr>' +
                '<tr>' +
                '<td>价格:</td>' +
                '<td>' + sku.price + '</td>' +
                '</tr>' +
                '<tr>' +
                '<td>库存:</td>' +
                '<td>' + sku.stock + '</td>' +
                '</tr>' +
                '</table>';
        }

        v_html += '</div>';
        return v_html;
    }
</script>
</body>
</html>
