<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/1/29
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>属性编辑</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<jsp:include page="/commons/includeScript.jsp"/>

<div class="container">
    <form>
        <div class="row">
            <div class="col-md-12" style="margin: 10px">
                <div class="form-group">
                    <label class="col-sm-2 control-label">属性名称:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="attrName">
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <button type="button" class="btn btn-success" onclick="addAttrValue()"><i
                                class="glyphicon glyphicon-plus"></i>添加属性值
                        </button>
                    </div>
                    <table class="table table-bordered" id="attrValueTable">
                        <thead>
                        <tr>
                            <th>删除</th>
                            <th>属性值</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
            <div align="center">
                <button type="button" class="btn btn-primary" onclick="updateAttr()"><i
                        class="glyphicon glyphicon-ok"></i>提交
                </button>
                <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i>重置</button>
            </div>
        </div>
    </form>
</div>

<textarea style="display: none" id="attrValueDiv">
    <tr>
        <td>
            <input type="checkbox" name="attrValueIds">
        </td>
        <td>
            <input type="text" class="form-control" name="attrValue" value="##attrValue##">
        </td>
        <td></td>
    </tr>
</textarea>


<textarea style="display: none" id="attrValueTrDiv">
    <tr>
        <td></td>
        <td>
            <input type="text" class="form-control" name="attrValue">
        </td>
        <td>
            <button type="button" class="btn btn-link" onclick="deleteAttrValue(this)">删除</button>
        </td>
    </tr>
</textarea>

<script>
    $(function () {
        initAttrList();
    })

    function addAttrValue() {
        $("#attrValueTable tbody").append($("#attrValueTrDiv").val());
    }

    function deleteAttrValue(obj) {
        $(obj).parents("tr").remove();
    }

    function updateAttr() {
        var v_param = {};
        v_param.attrName = $("#attrName").val();
        v_param.attrId = v_id;
        v_param.typeId = v_typeId;
        var attrValueArr = [];
        $("input[name='attrValue']").each(function () {
            var checkBoxArr = $(this).parents("tr").find("input[name='attrValueIds']");
            if (!checkBoxArr[0] || !checkBoxArr[0].checked) {
                attrValueArr.push(this.value);
            }
        })
        v_param.attrValues = attrValueArr.join(",");
        $.ajax({
            type: "post",
            url: "/attr/updateAttr.jhtml",
            data: v_param,
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/type/toList.jhtml";
                }
            }
        })
    }

    var v_id = '${param.id}';
    var v_typeId = '${param.typeId}';

    function initAttrList() {
        $.ajax({
            type: "post",
            url: "/attr/findAttr.jhtml?id=" + v_id,
            success: function (result) {
                if (result.code == 200) {
                    console.log(result);
                    var attrName = result.data.attr.attrName;

                    var attrValueArr = result.data.attrValueList;

                    $("#attrName").val(attrName);
                    var attrValueHtml = $("#attrValueDiv").val();
                    for (var i = 0; i < attrValueArr.length; i++) {
                        var attrValue = attrValueArr[i];
                        var v_result = attrValueHtml.replace(/##attrValue##/g, attrValue.attrValue);
                        $("#attrValueTable tbody").append(v_result);
                    }
                }
            }
        })
    }
</script>

</body>
</html>
