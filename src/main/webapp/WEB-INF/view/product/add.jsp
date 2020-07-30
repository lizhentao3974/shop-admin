<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/11
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>商品添加</title>
    <jsp:include page="/commons/includeCss.jsp"/>

</head>
<body>
<jsp:include page="/nav.jsp"/>
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
                    <label class="col-sm-2 control-label">商品类型：：</label>

                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品日期：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="createDate" placeholder="请选择商品日期...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像：</label>
                    <div class="col-sm-10">
                        <input type="file" class="form-control" name="filePhoto" id="fileimg">
                        <input type="hidden" id="phonePhoto">
                    </div>
                </div>
                <div style="text-align: center">
                    <button type="button" class="btn btn-primary" onclick="addProduct()"><i
                            class="glyphicon glyphicon-plus"></i>提交
                    </button>
                    <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/commons/includeScript.jsp"/>

<script>

    $(function () {
        brandListSelect();
        datetimepicker();
        initFileInput();
        initTypeList(0);
    })

    function initFileInput() {
        $("#fileimg").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            showCaption: false,
            dropZoneEnabled: true,
            minImageWidth: 50, //图片的最小宽度
            minImageHeight: 50,//图片的最小高度
        }).on("fileuploaded", function (event, result, previewId, index) {
            var photo = result.response.data;
            $("#phonePhoto").val(photo);
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
                        var typeHtml = "<div class='col-sm-3'><select name='typeSelect' class='form-control' onchange='initTypeList(this.value,this)'><option value='-1'>--请选择--</option>";
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

    function datetimepicker() {
        $("#createDate").datetimepicker({
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

    function addProduct() {
        v_productName = $("#productName").val();
        v_price = $("#price").val();
        v_brandId = $("#selectListId").val();
        v_createDate = $("#createDate").val();
        v_imgFile = $("#phonePhoto").val();
        var v_param = {};
        v_param.productName = v_productName;
        v_param.price = v_price;
        v_param.brandId = v_brandId;
        v_param.createDate = v_createDate;
        v_param.imgFile = v_imgFile;
        v_param.one = $($("select[name='typeSelect']")[0]).val();
        v_param.two = $($("select[name='typeSelect']")[1]).val();
        v_param.three = $($("select[name='typeSelect']")[2]).val();
        $.ajax({
            url: "/product/addProduct.jhtml",
            type: "post",
            data: v_param,
            success: function (result) {
                console.log(result);
                if (result.code == 200) {
                    location.href = "/product/toList.jhtml";
                }
            }
        })
    }
</script>
</body>
</html>
