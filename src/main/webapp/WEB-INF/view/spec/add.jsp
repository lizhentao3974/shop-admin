<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/1/13
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>规格管理</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>


<button type="button" class="btn btn-primary" onclick="addSpecName()"><i class="glyphicon glyphicon-plus"></i>添加规格
</button>
<button type="reset" class="btn btn-primary" onclick="insertSpec()"><i class="glyphicon glyphicon-ok"></i>提交</button>
<table class="table table-bordered" id="specTable">
    <tr>
        <td style="width: 100px;">规格名:</td>
        <td style="width: 200px;">
            <input type="text" class="form-control" style="width: 200px;" name="specName">
        </td>
        <td style="width: 100px;">规格排序:</td>
        <td style="width: 200px;">
            <input type="text" class="form-control" style="width: 100px;" name="nameSort">
        </td>
        <td>
            <button type="button" class="btn btn-info" onclick="addSpecValue(this)"><i
                    class="glyphicon glyphicon-plus"></i>添加规格值
            </button>
        </td>
    </tr>
</table>

<textarea id="specValueText" style="display: none">
    <tr>
        <td style="width: 100px;">规格值:</td>
        <td style="width: 200px;">
            <input type="text" class="form-control" style="width: 200px;" name="specValue">
        </td>
        <td style="width: 150px;">规格值排序:</td>
        <td style="width: 200px;">
            <input type="text" class="form-control" style="width: 150px;" name="valueSort">
        </td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteSpecvalue(this)"><i
                    class="glyphicon glyphicon-trash"></i>删除</button>
        </td>
    </tr>
</textarea>

<div style="display: none" id="specNameDiv">
    <table class="table table-bordered">
        <tr>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteSpecName(this)"><i
                        class="glyphicon glyphicon-trash"></i>删除
                </button>
            </td>
        </tr>
        <tr>
            <td style="width: 100px;">规格名:</td>
            <td style="width: 200px;">
                <input type="text" class="form-control" style="width: 200px;" name="specName">
            </td>
            <td style="width: 100px;">规格排序:</td>
            <td style="width: 200px;">
                <input type="text" class="form-control" style="width: 100px;" name="nameSort">
            </td>
            <td>
                <button type="button" class="btn btn-info" onclick="addSpecValue(this)"><i
                        class="glyphicon glyphicon-plus"></i>添加规格值
                </button>
            </td>
        </tr>
    </table>
</div>
<script>
    function insertSpec() {
        var specNameArr = [];
        var nameSortArr = [];
        $("[name='specName']").each(function () {
            if (this.value.length > 0) {
                specNameArr.push(this.value);
            }
        })

        $("[name='nameSort']").each(function () {
            if (this.value.length > 0) {
                nameSortArr.push(this.value);
            }
        })
        var v_specName = specNameArr.join(",")
        var v_nameSort = nameSortArr.join(",");
        var v_specValues = "";

        $("body > table").each(function () {
            var specValueArr = [];
            var valueSortArr = [];
            $(this).find("input[name='specValue']").each(function () {
                specValueArr.push(this.value);
            })

            $(this).find("input[name='valueSort']").each(function () {
                valueSortArr.push(this.value);
            })

            var v_temp = "";
            for (var i = 0; i < specValueArr.length; i++) {
                v_temp += "," + specValueArr[i] + "=" + valueSortArr[i]
            }
            if (v_temp.length > 0) {
                v_temp = v_temp.substring(1);
            }
            v_specValues += ";" + v_temp;
        })
        if (v_specValues.length > 0) {
            v_specValues = v_specValues.substring(1);
        }

        var param = {};
        param.specNames = v_specName;
        param.nameSorts = v_nameSort;
        param.specValues = v_specValues;
        $.ajax({
            type: "post",
            data: param,
            url: "/spec/addSpec.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/spec/toList.jhtml";
                } else {
                    alert(result.msg);
                }
            }
        })

    }


    function addSpecName() {
        $("body > table").last().after($("#specNameDiv").html());
    }

    function deleteSpecName(obj) {
        $(obj).parents("table").remove();
    }

    function addSpecValue(obj) {
        $(obj).parents("tbody").append($("#specValueText").val());
    }

    function deleteSpecvalue(obj) {
        $(obj).parents("tr").remove();
    }
</script>
<jsp:include page="/commons/includeScript.jsp"/>
</body>
</html>
