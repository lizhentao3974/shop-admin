<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/14
  Time: 15:25
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
<jsp:include page="/commons/includeScript.jsp"/>

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
                                    <input type="text" class="form-control" name="productName" id="productName"
                                           placeholder="请输入商品名称...">
                                </div>
                                <label class="col-sm-2 control-label">价格区间：</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="minPrice" id="minPrice"
                                               placeholder="请输入最小价格..." aria-describedby="basic-addon1">
                                        <span class="input-group-addon" id="basic-addon1"><i
                                                class="glyphicon glyphicon-jpy"></i> </span>
                                        <input type="text" class="form-control" name="maxPrice" id="maxPrice"
                                               placeholder="请输入最大价格..." aria-describedby="basic-addon1">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">品牌名：</label>
                                <div id="brandListSelectDiv" class="col-sm-4">

                                </div>
                                <label class="col-sm-2 control-label">日期范围：</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="minCreateDate" id="minCreateDate"
                                               placeholder="请选择开始时间...">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i> </span>
                                        <input type="text" class="form-control" name="maxCreateDate" id="maxCreateDate"
                                               placeholder="请选择结束时间...">
                                    </div>
                                </div>
                            </div>

                            <div style="text-align: center">
                                <button type="button" class="btn btn-primary" onclick="queryProduct()"><i
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
                    <button type="button" class="btn btn-success" onclick="toAddJsp()"><span
                            class="glyphicon glyphicon-plus"></span>添加商品
                    </button>
                    <button type="button" class="btn btn-danger" onclick="deleteProducts()"><i
                            class="glyphicon glyphicon-trash"></i>批量删除
                    </button>
                    <button type="button" class="btn btn-success" onclick="exportExcel()"><i
                            class="glyphicon glyphicon-download"></i>导出Excel
                    </button>
                    <button type="button" class="btn btn-success" onclick="uploadExcel()"><i
                            class="glyphicon glyphicon-upload"></i>导入Excel
                    </button>
                    <button type="button" class="btn btn-success" onclick="exportWord()"><i
                            class="glyphicon glyphicon-download"></i>导出Word
                    </button>
                    <button type="button" class="btn btn-success" onclick="exportPdf()"><i
                            class="glyphicon glyphicon-download"></i>导出PDF
                    </button>
                </div>
                <div class="panel-body">
                    <table id="example" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th></th>
                            <th>选择</th>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>是否热销</th>
                            <th>是否上架</th>
                            <th>价格</th>
                            <th>品牌</th>
                            <th>商品日期</th>
                            <th>录入时间</th>
                            <th>修改时间</th>
                            <th>商品图片</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="importExcelDiv" style="display: none">
    <form>
        <input type="file" id="excelInput" name="excelFile">
        <input type="text" id="excelHide">
    </form>
</div>
<script>
    var importExcelhtml;
    $(function () {
        initTable();
        brandListSelect();
        datetimepicker("minCreateDate");
        datetimepicker("maxCreateDate");
        initEvent();
        importExcelhtml = $("#importExcelDiv").html();
        initDataTables();
    })

    /*function initTypeList(id,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"post",
            url:"/type/findTypeByFid.jhtml",
            data:{"id":id},
            success:function (result) {
                console.log(result);
                if(result.code == 200){
                    var v_data = result.data;
                    if(v_data.length > 0){
                        var typeHtml = "<div class='col-sm-3'><select name='typeSelect' class='form-control' onchange='initTypeList(this.value,this)'><option value='-1'>--请选择--</option>";
                        for (var i = 0; i < v_data.length; i++) {
                            var datai = v_data[i];
                            typeHtml += "<option value='"+datai.id+"'>"+datai.typeName+"</option>";
                        }
                        typeHtml += "</select></div>";
                        $("#typeDiv").append(typeHtml);
                    }
                }
            }
        })
    }*/

    function ExcelFileInput() {
        $("#excelInput").fileinput({
            language: 'zh', //设置语言
            uploadUrl: "/file/cobyExcel.jhtml", //上传的地址
            allowedFileExtensions: ['xlsx'],//接收的文件后缀
            showCaption: true,
            dropZoneEnabled: true,
            browseClass: "btn btn-success", //按钮样式
            dropZoneEnabled: true//是否显示拖拽区域
        }).on("fileuploaded", function (e, data, previewiId, index) {
            var fileUrl = data.response.data;
            $("#excelHide", uploadDialog).val(fileUrl);
        });
    }

    var uploadDialog;

    function uploadExcel() {
        ExcelFileInput();
        uploadDialog = bootbox.dialog({
            title: "导入Excel",
            message: $("#importExcelDiv form"),
            size: "large",
            buttons: {
                cancel: {
                    label: "取消",
                    className: 'btn-danger',
                },
                ok: {
                    label: "提交",
                    className: 'btn-info',
                    callback: function () {
                        var v_excelUrl = $("#excelHide", uploadDialog).val();
                        $.ajax({
                            type: "post",
                            url: "/product/importExcel.jhtml",
                            data: {"filePath": v_excelUrl},
                            success: function (result) {
                                if (result.code == 200) {
                                    queryProduct();
                                }
                            }
                        })
                    }
                }
            },
        });
        $("#importExcelDiv").html(importExcelhtml);
    }

    function exportExcel() {
        var v_excel = document.getElementById("emport");
        v_excel.method = "post";
        v_excel.action = "/product/emportExcel.jhtml";
        v_excel.submit();
    }

    function exportPdf() {
        var v_pdfForm = document.getElementById("emport");
        v_pdfForm.method = "post";
        v_pdfForm.action = "/product/emportPdf.jhtml";
        v_pdfForm.submit();
    }

    function exportWord() {
        var v_wordForm = document.getElementById("emport");
        v_wordForm.method = "post";
        v_wordForm.action = "/product/exportWord.jhtml";
        v_wordForm.submit();
    }

    function deleteProducts() {
        if (ids.length < 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>请选择要删除的数据</b></font>',
                title: "<font color='#663399'>提示信息</font>"
            })
        } else {
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
                            data: {"ids": ids},
                            url: "/product/deleteAll.jhtml",
                            success: function (retult) {
                                if (retult.code == 200) {
                                    queryProduct();
                                }
                            }
                        })
                    }
                }
            })
        }
    }

    function initDataTables() {

        $('#example tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = productTable.row(tr);

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


    function format(d) {
        // `d` is the original data object for the row
        return '<div><table class="table table-bordered" style="width: 48%;float:left;margin: 5px">' +
            '<tr>' +
            '<td>商品名:</td>' +
            '<td>' + d.productName + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>价格:</td>' +
            '<td>' + d.price + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>品牌名:</td>' +
            '<td>' + d.brandName + '</td>' +
            '</tr>' +
            '</table>' + '<table class="table table-bordered" style="width: 48%;float:left;margin: 5px">' +
            '<tr>' +
            '<td>商品名:</td>' +
            '<td>' + d.productName + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>价格:</td>' +
            '<td>' + d.price + '</td>' +
            '</tr>' +
            '<tr>' +
            '<td>品牌名:</td>' +
            '<td>' + d.brandName + '</td>' +
            '</tr>' +
            '</table></div>';
    }

    var ids = [];

    function initEvent() {
        $("#example tbody").on("click", "tr", function () {
            var v_checkbox = $(this).find("input[name='ids']:checkbox");
            var v_checked = v_checkbox.prop("checked");

            if (v_checked) {
                v_checkbox.prop("checked", false);
                $(this).css("background", "");
                for (var i = ids.length - 1; i >= 0; i--) {
                    if (ids[i] == v_checkbox.val()) {
                        ids.splice(i, 1);
                    }
                }
                console.log(ids);
            } else {
                v_checkbox.prop("checked", true);
                $(this).css("background", "pink");
                ids.push(v_checkbox.val())
                console.log(ids);
            }

        })
    }

    function datetimepicker(dateParam) {
        $('#' + dateParam).datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
    }


    function toAddJsp() {
        location.href = "/product/toAdd.jhtml";
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

    function isExist(id) {
        for (var i = 0; i < ids.length; i++) {
            if (id == ids[i]) {
                return true;
            }
        }
        return false;
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
            "ordering": false,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/product/queryProductList.jhtml",
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
                    "data": "id", "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='" + data + "'>";
                    }
                },
                {"data": "id"},
                {"data": "productName"},
                {
                    "data": "isHot", "render": function (data, type, row, meta) {
                        return data == 1 ? "热销" : "非热销";
                    }
                },
                {
                    "data": "whether", "render": function (data, type, row, meta) {
                        return data == 1 ? "上架" : "下架";
                    }
                },
                {"data": "price"},
                {"data": "brandName"},
                {"data": "createDate"},
                {"data": "inputDate"},
                {"data": "modificationDate"},
                {
                    "data": "imgFile", "render": function (data, type, row, meta) {
                        return "<img src='" + data + "' width='60px'>";
                    }
                },
                {
                    "data": "id", "render": function (data, type, row, meta) {

                        var v_isHot = row.isHot;
                        var v_text = "";
                        var v_style = "";
                        var v_color = "";
                        var v_stauts;
                        if (v_isHot == 0) {
                            v_text = "热销";
                            v_style = "glyphicon glyphicon-fire";
                            v_color = "btn btn-success";
                            v_stauts = 1;
                        } else {
                            v_text = "非热销";
                            v_style = "glyphicon glyphicon-hand-down";
                            v_color = "btn btn-default";
                            v_stauts = 0;
                        }

                        var v_isup = row.whether;
                        var v_font = "";
                        var v_isColor = "";
                        var v_icon = "";
                        var stauts;
                        if (v_isup == 0) {
                            v_font = "上架";
                            v_isColor = "btn btn-success";
                            v_icon = "glyphicon glyphicon-hand-up";
                            stauts = 1;
                        } else {
                            v_font = "下架";
                            v_isColor = "btn btn-default";
                            v_icon = "glyphicon glyphicon-hand-down";
                            stauts = 0;
                        }
                        return '<button type="button" class="btn btn-danger" onclick="deleteProduct(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>' +
                            '<button type="button" class="btn btn-warning" onclick="edit(' + data + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
                            '<button type="button" class="' + v_color + '" onclick="updateStatus(' + data + ',' + v_stauts + ')"><span class="\n' + '' + v_style + '"></span>' + v_text + '</button>' +
                            '<button type="button" class="' + v_isColor + '" onclick="updateIsup(' + data + ',' + stauts + ')"><span class="\n' + '' + v_icon + '"></span>' + v_font + '</button>';
                    }
                }

            ],
            "drawCallback": function (s) {
                $("#example tbody tr").each(function () {
                    var v_checkbox = $(this).find("input[name='ids']:checkbox")[0];
                    var v_id = $(v_checkbox).val();
                    if (isExist(v_id)) {
                        $(this).css("background", "pink");
                        $(v_checkbox).prop("checked", true);
                    }
                })
            },
        });

    }


    function updateIsup(id, isup) {
        event.stopPropagation();
        $.ajax({
            type: "post",
            url: "/product/updateIsup.jhtml?id=" + id + "&isup=" + isup,
            success: function (result) {
                if (result.code == 200) {
                    queryProduct();
                }
            }
        })
    }

    function updateStatus(id, stauts) {
        event.stopPropagation();
        $.ajax({
            type: "post",
            url: "/product/updateStatus.jhtml?id=" + id + "&stauts=" + stauts,
            success: function (result) {
                if (result.code == 200) {
                    queryProduct();
                }
            }
        })
    }


    function queryProduct() {
        var v_param = {};
        v_param.productName = $("#productName").val();
        v_param.minPrice = $("#minPrice").val();
        v_param.maxPrice = $("#maxPrice").val();
        v_param.brandId = $("#selectListId").val();
        v_param.minCreateDate = $("#minCreateDate").val();
        v_param.maxCreateDate = $("#maxCreateDate").val();
        v_param.one = $($("select[name='typeSelect']")[0]).val();
        v_param.two = $($("select[name='typeSelect']")[1]).val();
        v_param.three = $($("select[name='typeSelect']")[2]).val();
        productTable.settings()[0].ajax.data = v_param;
        productTable.ajax.reload();
    }

    function mm(rowId) {
        alert(rowId.row);
    }

    function deleteProduct(id) {
        event.stopPropagation();
        bootbox.confirm({
            title: "删除用户",
            message: "请点击确认删除用户",
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
                        url: "/product/deleteProduct.jhtml",
                        type: "post",
                        data: {"id": id},
                        success: function (result) {
                            if (result.code == 200) {
                                queryProduct();
                            }
                        }
                    });
                }
            }
        })

    }

    //修改
    function edit(id) {
        event.stopPropagation();
        location.href = "/product/toUpdate.jhtml?id=" + id;
    }
</script>

</body>
</html>
