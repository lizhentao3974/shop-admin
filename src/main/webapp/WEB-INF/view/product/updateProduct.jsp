<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/14
  Time: 18:15
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
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品名称：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="productName" placeholder="请输入商品名称...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品价格：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="price" placeholder="请输入商品价格...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌名：</label>
                    <div id="brandListSelectDiv" class="col-sm-10">

                    </div>
                </div>
                <div class="form-group" id="typeDiv">
                    <label class="col-sm-2 control-label">商品类型：</label>

                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品日期：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="createDate">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像：</label>
                    <div class="col-sm-4">
                        <input type="hidden" id="newImagePath">

                        <input type="hidden" id="oldImagePath">
                        <input type="file" class="form-control" name="filePhoto" id="fileimg">
                    </div>
                </div>
                <div style="text-align: center">
                    <button type="button" class="btn btn-primary" onclick="updateProduct()"><i
                            class="glyphicon glyphicon-plus"></i>提交
                    </button>
                    <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(function () {
        editProduct();
        brandListSelect();
        datetimepicker("createDate");
    })

    function initFileInput(imageArr) {
        $("#fileimg").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            showCaption: false,
            dropZoneEnabled: true,
            initialPreview: imageArr,
            initialPreviewAsData: true
        }).on("fileuploaded", function (event, result, previewId, index) {
            var photo = result.response.data;
            $("#newImagePath").val(photo);
        })
    }

    function datetimepicker(elementsDate) {
        $("#" + elementsDate).datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
    }

    function brandListSelect() {
        $.ajax({
            url: "/brand/querySelectBrand.jhtml",
            type: "post",
            async: false,
            success: function (result) {
                var selectHtml = "<select class=\"form-control\" id='selectListId'><option value='-1'>--请选择--</option>";
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

    function initTypeList(id, obj) {
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type: "post",
            url: "/type/findTypeByFid.jhtml",
            data: {"id": id},
            success: function (result) {
                console.log(result);
                if (result.code == 200) {
                    var v_data = result.data;
                    if (v_data.length > 0) {
                        var typeHtml = "<div class='col-sm-3' name='seleDiv'><select name='typeSelect' class='form-control' onchange='initTypeList(this.value,this)'><option value='-1'>--请选择--</option>";
                        for (var i = 0; i < v_data.length; i++) {
                            var datai = v_data[i];
                            typeHtml += "<option value='" + datai.id + "'>" + datai.typeName + "</option>";
                        }
                        typeHtml += "</select></div>";
                        $("#typeDiv").append(typeHtml);
                    }
                }
            }
        })
    }

    var one;
    var two;
    var three;

    function editProduct() {
        var v_productId = ${param.id};
        $.ajax({
            url: "/product/editProduct.jhtml",
            type: "post",
            data: {"id": v_productId},
            success: function (result) {
                if (result.code == 200) {
                    var data = result.data;
                    $("#productName").val(data.productName);
                    $("#price").val(data.price);
                    $("#selectListId").val(data.brandId);
                    $("#createDate").val(data.createDate);
                    $("#oldImagePath").val(data.imgFile);
                    $("#typeDiv").append("<span id='nameSpan'>" + data.typeName + "</span>" + "<button type='button' class='btn btn-success' onclick='editSelect()'><i class='glyphicon glyphicon-pencil'></i><span id='editId'>编辑</span></button>");
                    var imgArr = [];
                    imgArr.push(data.imgFile);
                    console.log(data.imgFile);
                    initFileInput(imgArr);
                    one = data.one;
                    two = data.two;
                    three = data.three;
                }
            }
        });
    }

    var a = 0;
    var typeName;

    function editSelect() {
        if (a == 0) {
            typeName = $("#nameSpan").html();
            $("#nameSpan").html("");
            $("#editId").html("取消编辑");
            initTypeList(0);
            a = 1;
        } else {
            $("#nameSpan").html(typeName);
            $("#editId").html("编辑");
            $("#typeDiv div[name = 'seleDiv']").remove();
            a = 0;
        }
    }

    function updateProduct() {
        var v_productId = ${param.id};
        var v_productName = $("#productName").val();
        var v_price = $("#price").val();
        var v_brandId = $("#selectListId").val();
        var v_createDate = $("#createDate").val();

        var v_imgFile = $("#newImagePath").val();
        var v_oldImg = $("#oldImagePath").val();
        if ($("select[name='typeSelect']")[0]) {
            one = $("select[name='typeSelect']")[0].value;
        }
        if ($("select[name='typeSelect']")[1]) {
            two = $("select[name='typeSelect']")[1].value;
        }
        if ($("select[name='typeSelect']")[2]) {
            three = $("select[name='typeSelect']")[2].value;
        }

        $.ajax({
            url: "/product/updateProduct.jhtml",
            type: "post",
            data: {
                "id": v_productId,
                "productName": v_productName,
                "price": v_price,
                "brandId": v_brandId,
                "createDate": v_createDate,
                "imgFile": v_imgFile,
                "oldImgFile": v_oldImg,
                "one": one,
                "two": two,
                "three": three
            },
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/product/toList.jhtml";
                }
            }
        });
    }
</script>
</body>
</html>
