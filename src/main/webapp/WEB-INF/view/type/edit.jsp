<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/1/16
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>类型修改</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>

<div class="container">
    <form>
        <div class="row">
            <div class="col-md-6" style="margin: 10px">
                <div class="form-group">
                    <label class="col-sm-2 control-label">类型名称:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="typeName" placeholder="请输入类型名称...">
                    </div>
                </div>
            </div>
            <div class="col-md-6">

            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">规格列表</div>
                    <table class="table table-bordered" id="specTable">
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">品牌列表</div>
                    <table class="table table-bordered" id="brandTable">
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <button type="button" class="btn btn-success" onclick="addAttr()"><i
                                class="glyphicon glyphicon-plus"></i>添加属性
                        </button>
                    </div>
                    <table class="table table-bordered" id="attrTable">
                        <thead>
                        <tr>
                            <th>删除</th>
                            <th>属性名</th>
                            <th>属性值</th>
                            <th>操作</th>
                        </tr>

                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div align="center">
            <button type="button" class="btn btn-primary" onclick="updateType()"><i class="glyphicon glyphicon-ok"></i>提交
            </button>
            <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i>重置</button>
        </div>
    </form>
</div>


<textarea id="attrDiv" style="display: none">
    <tr>
        <td></td>
       <td>
           <input type="text" class="form-control" name="attrName" placeholder="请输入属性名...">
       </td>
       <td>
           <input type="text" class="form-control" name="attrValue" placeholder="请输入属性值...">
       </td>
       <td>
           <button type="button" class="btn btn-link" onclick="deleteAttr(this)">删除</button>
       </td>
    </tr>
</textarea>

<textarea id="attrText" style="display: none">
    <tr>
        <td>
            <input type="checkbox" name="attrIds" value="##attrId##"/>
        </td>
       <td>
           <input type="text" class="form-control" name="attrName" value="##attrName##" placeholder="请输入属性名...">
       </td>
       <td>
           ##attrValue##<input type="hidden" name="attrValue" value="##attrValue##"/>
       </td>
       <td>
           <button type="button" class="btn btn-link" onclick="editAttr('##attrId##')">编辑</button>
       </td>
    </tr>
</textarea>
<script>
    $(function () {
        initBrandList();
        initSpecList();
        initTypeFindList();
    })

    function editAttr(attrId) {
        var v_typeId = '${param.id}';
        location.href = "/attr/toEdit.jhtml?id=" + attrId + "&typeId=" + v_typeId;
    }

    function initTypeFindList() {
        var v_id = '${param.id}';
        $.ajax({
            type: "post",
            data: {"id": v_id},
            url: "/type/findTypeById.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var v_data = result.data;
                    var typeName = v_data.type.typeName;
                    var specIdArr = v_data.specIdList;
                    var brandIdArr = v_data.brandIdList;
                    var attrArr = v_data.attrList;
                    $("#typeName").val(typeName);

                    $("input[name='brandIds']").each(function () {
                        brandCheckBox(this, brandIdArr);
                    })

                    $("input[name='specIds']").each(function () {
                        specCheckBox(this, specIdArr);
                    })

                    var v_attrHtml = $("#attrText").val();
                    for (var i = 0; i < attrArr.length; i++) {
                        var attr = attrArr[i];
                        var v_result = v_attrHtml.replace(/##attrName##/g, attr.attrName)
                            .replace(/##attrValue##/g, attr.attrValue)
                            .replace(/##attrId##/g, attr.id);
                        $("#attrTable tbody").append(v_result);
                    }


                }
            }
        })
    }

    function specCheckBox(obj, specIdArr) {
        for (var i = 0; i < specIdArr.length; i++) {
            if (obj.value == specIdArr[i]) {
                obj.checked = true;
            }
        }
    }

    function brandCheckBox(obj, brandIdArr) {
        for (var i = 0; i < brandIdArr.length; i++) {
            if (obj.value == brandIdArr[i]) {
                obj.checked = true;
            }
        }
    }

    function addAttr() {
        $("#attrTable tbody").append($("#attrDiv").val())
    }

    function initBrandList() {
        $.ajax({
            type: "post",
            async: false,
            url: "/brand/querySelectBrand.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var brandArr = result.data;

                    //总条数长度
                    var total = brandArr.length;
                    //每行多少条
                    var td_Size = 5;
                    //总共多少行的运算公式
                    var tr_Count = total % td_Size == 0 ? total / td_Size : Math.ceil(total / td_Size);
                    var v_html = "";
                    for (var i = 0; i < tr_Count; i++) {
                        v_html += "<tr>";

                        //每行的开始下标
                        var v_index = td_Size * i;

                        for (var j = 0; j < td_Size; j++) {
                            var brandInfo = brandArr[v_index + j];
                            if (brandInfo) {
                                v_html += "<td><input type='checkbox' name='brandIds' value='" + brandInfo.id + "'>" + brandInfo.brandName + "</td>";
                            } else {
                                v_html += "<td></td>";
                            }
                        }
                        v_html += "</tr>";
                    }
                    $("#brandTable tbody").html(v_html);
                }
            }
        })
    }

    function initSpecList() {
        $.ajax({
            type: "post",
            async: false,
            url: "/spec/findSpecList.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var specArr = result.data;
                    var total = specArr.length;
                    var td_Size = 2;
                    var tr_Count = total % td_Size == 0 ? total / td_Size : Math.ceil(total / td_Size);
                    var v_html = "";
                    for (var i = 0; i < tr_Count; i++) {
                        v_html += "<tr>";

                        var v_index = td_Size * i;

                        for (var j = 0; j < td_Size; j++) {
                            var specInfo = specArr[v_index + j];
                            if (specInfo) {
                                v_html += "<td><input type='checkbox' name='specIds' value='" + specInfo.id + "'>" + specInfo.specName + "</td>";
                            } else {
                                v_html += "<td></td>";
                            }
                        }
                        v_html += "</tr>";
                    }
                    $("#specTable tbody").html(v_html);
                }
            }
        })
    }

    function deleteAttr(obj) {
        $(obj).parents("tr").remove();
    }

    function updateType() {
        var v_typeId = '${param.id}';
        var typeName = $("#typeName").val();
        var brandIdsArr = [];
        var specIdsArr = [];

        $("input[name='brandIds']:checkbox:checked").each(function () {
            brandIdsArr.push(this.value);
        })

        $("input[name='specIds']:checkbox:checked").each(function () {
            specIdsArr.push(this.value);
        })

        var brandStr = brandIdsArr.join(",");
        var specStr = specIdsArr.join(",");
        var attrNameArr = [];
        var attrValueArr = [];
        $("input[name='attrName']").each(function () {
            var checkboxArr = $(this).parents("tr").find("input[name='attrIds']");
            if (!checkboxArr[0] || !checkboxArr[0].checked) {
                attrNameArr.push(this.value);
            }

        })
        $("input[name='attrValue']").each(function () {
            var checkboxArr = $(this).parents("tr").find("input[name='attrIds']");
            if (!checkboxArr[0] || !checkboxArr[0].checked) {
                attrValueArr.push(this.value);
            }
        })

        var attrNames = attrNameArr.join(",");
        var attrValues = attrValueArr.join(";");

        var param = {};
        param.typeId = v_typeId;
        param.typeName = typeName;
        param.brandIds = brandStr;
        param.specIds = specStr;
        param.attrNames = attrNames;
        param.attrValues = attrValues;
        console.log(param);
        $.ajax({
            type: "post",
            data: param,
            url: "/type/updateType.jhtml",
            success: function (result) {

                if (result.code == 200) {
                    location.href = "/type/toList.jhtml";
                }
            }
        })
    }
</script>

<jsp:include page="/commons/includeScript.jsp"/>
</body>
</html>
