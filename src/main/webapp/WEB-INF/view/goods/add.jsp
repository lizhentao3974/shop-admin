<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/2/25
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品名称</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="productName" placeholder="请输入商品名称...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">头像：</label>
                    <div class="col-sm-10">
                        <input type="file" class="form-control" name="filePhoto" id="fileimg">
                        <input type="hidden" id="phonePhoto">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">价格</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" id="price" placeholder="价格..." readonly>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">库存</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" id="stock" placeholder="库存..." readonly>
                    </div>
                </div>
                <div class="form-group" id="categorySelectDiv">
                    <label class="col-sm-2 control-label">分类</label>
                </div>
                <div id="relateInfoDiv">

                </div>
                <div style="text-align: center">
                    <button type="button" class="btn btn-primary" onclick="addGoods()"><i
                            class="glyphicon glyphicon-ok"></i>提交
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
        initCateSelect(0);
        initFileInput();
    })

    function initFileInput() {
        $("#fileimg").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            dropZoneEnabled: false,
            allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif']
        }).on("fileuploaded", function (event, s, previewId, index) {
            var result = s.response;
            if (result.code == 200) {
                $("#phonePhoto").val(result.data);
            }
        })
    }

    function addGoods() {
        var v_param = {};

        v_param["goodsCommon.productName"] = $("#productName").val();

        v_param["goodsCommon.price"] = $("#price").val();

        v_param["goodsCommon.stock"] = $("#stock").val();

        v_param["goodsCommon.mainImage"] = $("#phonePhoto").val();

        //v_param["goodsCommon.attrInfo"]
        //属性格式：[1:'材质',33:'不锈钢',34:'亮面';2:'使用场景',44:'室内',45:'户外']

        var attrInfoArr = [];
        $("select[name='attrSelect']").each(function () {
            if ($(this).val() > -1) {
                var v_optionInfo = $(this).find("option:selected");
                attrInfoArr.push(v_optionInfo.data("attr-name-id") + ":" + v_optionInfo.data("attr-name") + "," + $(this).val() + ":" + v_optionInfo.data("attr-value"));
            }
        })

        v_param["goodsCommon.attrInfo"] = attrInfoArr.length > 0 ? attrInfoArr.join(";") : "";

        //v_param["goodsCommon.specInfo"]
        //1:'颜色',33:'红色',34:'蓝色';2:'内存',44:'32G',45:'64G'
        var resultArr = [];
        for (let spec of specArr) {
            var specValueArr = [];

            var specValueList = [];
            $("input[name='specIds_" + spec.id + "']:checked").each(function () {
                var specValue = $(this).data("specvalue-info");
                specValueArr.push($(this).val() + ":" + specValue);
            })
            if (specValueArr.length > 0) {
                specValueList.push(spec.id + ":" + spec.specName);
                for (let specValue of specValueArr) {
                    specValueList.push(specValue);
                }
            }

            if (specValueList.length > 0) {
                resultArr.push(specValueList.join(","));
            }
        }


        v_param["goodsCommon.specInfo"] = resultArr.length > 0 ? resultArr.join(";") : "";

        v_param["goodsCommon.brandId"] = $("#brandSelect").val();
        v_param["goodsCommon.brandName"] = $("#brandSelect option:selected").data("brand-name");
        v_param["goodsCommon.cate1"] = $($("select[name='cateSelects']")[0]).val();
        v_param["goodsCommon.cate2"] = $($("select[name='cateSelects']")[1]).val();
        v_param["goodsCommon.cate3"] = $($("select[name='cateSelects']")[2]).val();
        v_param["goodsCommon.cateName"] = "|" + $($("select[name='cateSelects']")[0]).find("option:selected").data("cate-name") + "|->|" +
            $($("select[name='cateSelects']")[1]).find("option:selected").data("cate-name") + "|->|" +
            $($("select[name='cateSelects']")[2]).find("option:selected").data("cate-name") + "|";

        var v_priceArr = [];
        $("input[name='skuPrice']").each(function () {
            v_priceArr.push($(this).val());
        })
        v_param.prices = v_priceArr.length > 0 ? v_priceArr.join(",") : "";

        var v_stockArr = [];
        $("input[name='calSpuSock']").each(function () {
            v_stockArr.push($(this).val());
        })
        v_param.stocks = v_stockArr.length > 0 ? v_stockArr.join(",") : "";

        var specValues = [];
        $("input[name='specValueInfos']").each(function () {
            specValues.push(this.value);
        })
        v_param.specValueInfos = specValues.length > 0 ? specValues.join(";") : "";

        var v_goodsImageArr = [];

        $("input[name='mainImagePath']").each(function () {
            var v_colorId = $(this).data("color-id");
            var v_image = this.value;
            v_goodsImageArr.push(v_colorId + "|" + v_image);
        })

        v_param.goodsImages = v_goodsImageArr.length > 0 ? v_goodsImageArr.join(";") : "";
        console.log(v_param);


        $.ajax({
            type: "post",
            data: v_param,
            url: "/goods/addGoods.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/goods/toList.jhtml";
                }
            }
        })

    }

    function initCateSelect(id, obj) {
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        specArr = [];
        $.ajax({
            type: "post",
            data: {"id": id},
            url: "/category/findCateById.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var v_cate = result.data;
                    if (v_cate.length == 0) {
                        var typeId = $(obj).find("option:selected").data("type-id");
                        initTypeRelate(typeId);
                        return;
                    }
                    if (v_cate.length > 0) {
                        var v_html = '<div class="col-sm-3"><select class="form-control" name="cateSelects" onchange="initCateSelect(this.value,this)"><option value="-1">--请选择--</option>';

                        for (let cate of v_cate) {
                            v_html += '<option data-type-id="' + cate.typeId + '" data-cate-name="' + cate.categoryName + '" value="' + cate.id + '">' + cate.categoryName + '</option>';
                        }

                        v_html += '</select></div>';
                        $("#categorySelectDiv").append(v_html);
                    }
                }
            }
        })
    }

    function initTypeRelate(typeId) {
        if (!typeId || typeId == -1) {
            $("#relateInfoDiv").html("");
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>当前分类没有绑定类型，请重新绑定</b></font>',
                title: "<font color='#663399'>提示信息</font>"
            });
            return;
        }
        $.ajax({
            type: "post",
            url: "/type/findTypeRelate.jhtml?typeId=" + typeId,
            success: function (result) {
                if (result.code == 200) {
                    var v_data = result.data;
                    var brandList = v_data.brandList;
                    var attrVoList = v_data.attrVoList;
                    var specVoList = v_data.specVoList;

                    $("#relateInfoDiv").html("");

                    //渲染品牌下拉
                    initBrandList(brandList);
                    //渲染属性列表
                    initAttrList(attrVoList);
                    //渲染规格复选框
                    initSpecList(specVoList);
                }
            }
        })
    }

    var specArr = [];

    function initSpecList(specVoList) {

        for (let specVo of specVoList) {
            specArr.push(specVo.specName);
            var v_html = '<div class="form-group">';

            v_html += '<label class="col-sm-2 control-label">' + specVo.specName.specName + '</label><div class="col-sm-10">'


            var specValuesArr = specVo.specValues;

            for (let specValueInfo of specValuesArr) {
                v_html += '&nbsp;&nbsp;<input type="checkbox" data-spec-name="' + specVo.specName.specName + '" data-spec-id="' + specVo.specName.id + '" data-specvalue-info="' + specValueInfo.specValue + '" onclick="initTable()" name="specIds_' + specVo.specName.id + '" value="' + specValueInfo.id + '"/>' + specValueInfo.specValue;
            }

            v_html += '</div></div>';
            $("#relateInfoDiv").append(v_html);
        }
    }

    function initTable() {

        console.log(specArr);


        //每次生成表格前   都remove掉
        $("#specTableDiv").remove();
        $("div[name = 'imgDiv']").remove();


        //先组成一个二维数组

        var res = [];
        var headerArr = [];
        for (let spec of specArr) {
            var specid = spec.id;
            var tempArr = [];
            $("input[name='specIds_" + specid + "']:checked").each(function () {
                var v_specValueId = $(this).val();
                var v_specValueInfo = $(this).data("specvalue-info");
                tempArr.push(v_specValueId + ":" + v_specValueInfo);
            })
            if (tempArr.length > 0) {
                res.push(tempArr);
                //只有当复选框被选中的情况下，才会往表头里面放规格名
                headerArr.push(spec.specName);
            }
        }

        if (headerArr.length > 1 && headerArr[0] == "颜色") {

            var v_colorArr = res[0];
            var trArr = getData(res);
            var v_tableHtml = '<div class="row" id="specTableDiv"><div class="col-md-10 col-md-offset-2"><table class="table table-striped table-bordered">';

            var v_headerHtml = "<tr>";
            for (let head of headerArr) {
                v_headerHtml += '<th>' + head + '</th>';
            }
            v_headerHtml += '<th>价格</th><th>库存</th>';
            v_headerHtml += "</tr>";

            var v_bodyHtml = "<tbody>";

            for (let tr of trArr) {
                v_bodyHtml += '<tr>';

                var tdArr = tr.split(",");

                for (let td of tdArr) {
                    var specValue = td.split(":")[1];
                    v_bodyHtml += '<td>' + specValue + '</td>';
                }
                v_bodyHtml += '<td><input type="hidden" name="specValueInfos" value="' + tr + '"><input type="text" style="width: 130px" name="skuPrice" onblur="calPrice()" class="form-control" placeholder="价格..."></td><td><input type="text" name="calSpuSock" onblur="spuStock()" style="width: 120px" class="form-control" placeholder="库存..." value="0"></td>'
                //'<td><input name="specValueInfo" type="hidden" value="'+tr+'"><input type="text" class="form-control" name="skuPrice" onblur="calSPUPrice()" placeholder="价格..." style="width:120px"></td><td><input type="text" name="skuStock" style="width:120px" class="form-control"  onblur="calSPUStock();" placeholder="库存..." value="0"></td>'
                //'<td><input type="hidden" name="specValueInfos" value="'+tr+'"><input type="text" class="form-control" style="width: 130px" name="skuPrice" onblur="calPrice()" placeholder="价格..."></td><td><input type="text" name="calSpuSock" onblur="spuStock()" style="width: 130px" class="form-control" placeholder="库存..."></td>'
                v_bodyHtml += '</tr>';
            }
            v_bodyHtml += '</tbody>';
            v_tableHtml += v_headerHtml;
            v_tableHtml += v_bodyHtml;
            v_tableHtml += '</table></div></div>';


            $("#relateInfoDiv").append(v_tableHtml);


            buildUploadImage(v_colorArr);
        }
    }

    function buildUploadImage(v_colorArr) {
        console.log(v_colorArr);
        for (let color of v_colorArr) {
            var v_colorHtml = '<div class="row" name="imgDiv"><div class="col-md-10 col-md-offset-2">';
            var v_colorId = color.split(":")[0];
            var v_colorName = color.split(":")[1];

            v_colorHtml += '<div class="panel panel-default">\n' +
                '  <div class="panel-heading">' + v_colorName + '</div>\n' +
                '<input type="file" class="form-control" name="filePhotos" data-color-id="' + v_colorId + '" id="mainImage_' + v_colorId + '" multiple>' +
                '<input type="hidden" id="mainImagePath_' + v_colorId + '" name="mainImagePath" data-color-id="' + v_colorId + '">'
            '</div>';

            v_colorHtml += '</div></div>';
            $("#relateInfoDiv").append(v_colorHtml);

            var s = {
                language: 'zh',
                uploadAsync: false,
                uploadUrl: "/file/cobyFiles.jhtml",
                dropZoneEnabled: false,
                allowedFileExtensions: ['jpg', 'png', 'jpeg', 'gif']
            }

            $("#mainImage_" + v_colorId).fileinput(s).on("filebatchuploadsuccess", function (event, r, previewId, index) {
                var colorId = $(this).data("color-id");
                var result = r.response;
                if (result.code == 200) {
                    $("#mainImagePath_" + colorId).val(result.data);
                }

            })
        }
    }

    function spuStock() {
        var temp = 0;
        $("input[name='calSpuSock']").each(function () {
            temp += parseInt(this.value.length == 0 ? 0 : this.value);
        })
        $("#stock").val(temp);
    }

    function calPrice() {
        var spuPrice = [];
        $("input[name='skuPrice']").each(function () {
            spuPrice.push(parseInt(this.value.length == 0 ? 0 : this.value));
        })
        spuPrice.sort((x, y) = > x - y
    )
        ;
        $("#price").val(spuPrice[0]);
    }

    function getData(arrs) {
        if (arrs.length > 1) {
            var res = [];
            var base = arrs[0];
            arrs.splice(0, 1);
            var next = getData(arrs);
            for (var i = 0; i < base.length; i++) {
                for (var j = 0; j < next.length; j++) {
                    res.push(base[i] + "," + next[j]);
                }
            }
            return res;
        } else {
            return arrs[0];
        }
    }

    function initAttrList(attrVoList) {
        var v_html = '<div class="form-group"><label class="col-sm-2 control-label">属性：</label>';
        for (let attrVo of attrVoList) {

            var item = '<div class="col-sm-3">';

            item += '<div class="input-group"><span class="input-group-addon" id="basic-addon1">' + attrVo.attr.attrName + '</span>' +
                '<select class="form-control" name="attrSelect"><option value="-1">--请选择--</option>';

            var attrValueList = attrVo.attrValueList;

            for (let attrValue of attrValueList) {
                item += '<option data-attr-name="' + attrVo.attr.attrName + '" data-attr-name-id="' + attrVo.attr.id + '" data-attr-value="' + attrValue.attrValue + '" value="' + attrValue.id + '">' + attrValue.attrValue + '</option>';
            }
            item += '</select></div></div>';
            v_html += item;
        }

        v_html += '</div>'
        $("#relateInfoDiv").append(v_html);
    }

    function initBrandList(brandList) {
        var v_html = '<div class="form-group"><label class="col-sm-2 control-label">品牌：</label>' +
            '<div class="col-sm-3"><select class="form-control" id="brandSelect"><option value="-1">--请选择--</option>';
        for (let brand of brandList) {
            v_html += '<option data-brand-name="' + brand.brandName + '" value="' + brand.id + '">' + brand.brandName + '</option>';
        }

        v_html += '</select></div></div>';
        $("#relateInfoDiv").append(v_html);
    }
</script>
</body>
</html>
