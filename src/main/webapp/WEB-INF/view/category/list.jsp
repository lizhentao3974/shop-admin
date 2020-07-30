<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/28
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>商城首页</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12" id="cateDiv">
            <table id="cateTable" class="table table-bordered">
                <thead>
                <tr>
                    <th>分类名</th>
                    <th>类型名</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div style="display: none" id="cateHtmlDiv">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">分类名：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="cateName" placeholder="请输入分类名称...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">上级分类：</label>
            <div id="addCateSelectDiv" class="col-sm-5">

            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">关联类型列表：</label>
            <div id="addTypeSelectDiv" class="col-sm-10">

            </div>
        </div>
    </form>
</div>


<div style="display: none" id="updateCateHtmlDiv">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">分类名：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="updateCateName" placeholder="请输入分类名称...">
                <input type="hidden" class="form-control" id="oldUpdateCateName">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">上级分类：</label>
            <div id="updateCateSelectDiv" class="col-sm-5">

            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">关联类型列表：</label>
            <div id="updateTypeSelectDiv" class="col-sm-10">

            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">关联到子类：</label>
            <div class="col-sm-10">
                <input type="checkbox" id="relateCheckbox" checked="true"/>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/commons/includeScript.jsp"/>

<script>
    var cateHtml;
    var cateFormHtml;
    var updateCateDivHtml;
    $(function () {
        cateHtml = $("#cateDiv").html();
        cateFormHtml = $("#cateHtmlDiv").html();
        updateCateDivHtml = $("#updateCateHtmlDiv").html();
        initCateTable();
    })

    var cates = [];
    var resultArr = [];

    function initCateTable() {
        $.ajax({
            type: "post",
            url: "/category/findAll.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    cates = result.data;
                    resultArr = [];
                    sortCates(cates, resultArr, 0, 0);
                    buildCateTable(resultArr);
                    $("#cateTable").treetable({
                        expandable: true,
                        initialState: "expanded"
                    });
                }
            }
        })
    }

    function addCate(obj) {
        var v_id = $(obj).parents("tr").data("tt-id");

        buildSelect(resultArr, v_id);

        buildTypeRadioList();

        var areaDialog = bootbox.dialog({
            title: "添加分类",
            message: $("#cateHtmlDiv form"),
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
                        var v_param = {};
                        v_param.categoryName = $("#cateName", areaDialog).val();
                        v_param.fatherId = $("#cateSelect", areaDialog).val();
                        v_param.typeId = $("input[name='typeIds']:checked", areaDialog).val();
                        v_param.typeName = $("input[name='typeIds']:checked", areaDialog).attr("typeName");
                        console.log(v_param);
                        $.ajax({
                            type: "post",
                            data: v_param,
                            url: "/category/addCate.jhtml",
                            success: function (result) {
                                if (result.code == 200) {
                                    initCateTable();
                                }
                            }
                        })
                    }
                }
            },
        });
        $("#cateHtmlDiv").html(cateFormHtml);
    }

    function buildTypeRadioList() {
        $.ajax({
            type: "post",
            async: false,
            url: "/type/findTypeAll.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var v_data = result.data;
                    var v_html = "<input type='radio' value='-1' name='typeIds'/>无";
                    for (let data of v_data) {
                        v_html += "&nbsp;&nbsp;<input type='radio' typeName='" + data.typeName + "' name='typeIds' value='" + data.id + "'/>" + data.typeName;
                    }
                    $("#addTypeSelectDiv").html(v_html);
                }
            }
        })
    }

    function buildUpdateTypeRadioList() {
        $.ajax({
            type: "post",
            async: false,
            url: "/type/findTypeAll.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var v_data = result.data;
                    var v_html = "<input type='radio' value='-1' name='updateTypeIds'/>无";
                    for (let data of v_data) {
                        v_html += "&nbsp;&nbsp;<input type='radio' typeName='" + data.typeName + "' name='updateTypeIds' value='" + data.id + "'/>" + data.typeName;
                    }
                    $("#updateTypeSelectDiv").html(v_html);
                }
            }
        })
    }

    function buildSelect(areaArr, id) {
        var v_html = "<select id='cateSelect' class='form-control'><option value='-1'>==请选择==</option><option value='0'>分类根目录</option>";
        for (let arr of areaArr) {
            v_html += "<option value='" + arr.id + "'>" + buildLevel(arr.level) + arr.categoryName + "</option>";
        }
        v_html += "</select>";

        $("#addCateSelectDiv").html(v_html);
        $("#cateSelect").val(id);
    }

    function buildUpdateSelect(areaArr) {
        var v_html = "<select id='updateCateSelect' class='form-control'><option value='-1'>==请选择==</option><option value='0'>分类根目录</option>";
        for (let arr of areaArr) {
            v_html += "<option value='" + arr.id + "'>" + buildLevel(arr.level) + arr.categoryName + "</option>";
        }
        v_html += "</select>";

        $("#updateCateSelectDiv").html(v_html);
    }

    function buildLevel(level) {
        var res = "";
        for (var i = 0; i < level; i++) {
            res += "==";
        }

        return res;
    }

    function buildCateTable(resultArr) {
        $("#cateDiv").html(cateHtml);
        var v_html = "<tr data-tt-id='0'><td>分类根目录&nbsp;&nbsp;<button type='button' class='btn btn-link' onclick='addCate(this)'><span class='glyphicon glyphicon-plus'></span>新增下级</button></td><td></td><td></td></tr>";

        for (let cate of resultArr) {
            v_html += '<tr data-tt-id="' + cate.id + '" data-tt-parent-id="' + cate.fatherId + '"><td>' + cate.categoryName + '&nbsp;&nbsp;<button type="button" class="btn btn-link" onclick="addCate(this)"><span class="glyphicon glyphicon-plus"></span>新增下级</button></td><td>' + cate.typeName + '</td>' +
                '<td>' +
                '<button type="button" class="btn btn-danger" onclick="delCate(this)"><span class="glyphicon glyphicon-trash"></span>删除</button>\n' +
                '<button type="button" class="btn btn-warning" onclick="updateCate(this)"><span class="glyphicon glyphicon-pencil"></span>编辑</button></td>' +
                '</tr>';
        }

        $("#cateTable tbody").html(v_html);
    }

    function sortCates(cates, resultArr, id, level) {
        let childs = getChilds(cates, id);
        for (let c of childs) {
            c.level = level;
            resultArr.push(c);
            sortCates(cates, resultArr, c.id, c.level + 1);
        }
    }

    function updateCate(obj) {
        var v_id = $(obj).parents("tr").data("tt-id");

        buildUpdateSelect(resultArr);

        buildUpdateTypeRadioList();

        //ajax发起请求查询数据
        $.ajax({
            type: "post",
            data: {"id": v_id},
            url: "/category/find.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    //回填数据
                    var v_cate = result.data;
                    $("#updateCateName").val(v_cate.categoryName);
                    $("#updateCateSelect").val(v_cate.fatherId);
                    $("#oldUpdateCateName").val(v_cate.categoryName);
                    $("input[name='updateTypeIds']").each(function () {
                        if (v_cate.typeId == this.value) {
                            this.checked = true;
                        }
                    })
                    var updateCateDialog = bootbox.dialog({
                        title: "修改分类",
                        message: $("#updateCateHtmlDiv form"),
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
                                    //更新数据

                                    var v_param = {};

                                    v_param["category.id"] = v_id;
                                    v_param["category.categoryName"] = $("#updateCateName", updateCateDialog).val();
                                    v_param["category.oldCategoryName"] = $("#oldUpdateCateName", updateCateDialog).val();
                                    v_param["category.fatherId"] = $("#updateCateSelect", updateCateDialog).val();
                                    v_param["category.typeId"] = $("input[name='updateTypeIds']:checked", updateCateDialog).val();
                                    v_param["category.typeName"] = $("input[name='updateTypeIds']:checked", updateCateDialog).attr("typeName");
                                    v_param.relateFlag = $("#relateCheckbox", updateCateDialog).prop("checked") ? 1 : 0;

                                    var results = [];
                                    getSubTree(cates, results, v_id);
                                    let arrs = results.map(x = > x.id
                                )
                                    ;
                                    v_param.ids = arrs.length == 0 ? "" : arrs.join(",");
                                    console.log(v_param)
                                    $.ajax({
                                        type: "post",
                                        data: v_param,
                                        url: "/category/update.jhtml",
                                        success: function (result) {
                                            if (result.code == 200) {
                                                initCateTable();
                                            }
                                        }
                                    })
                                }
                            }
                        },
                    });
                    $("#updateCateHtmlDiv").html(updateCateDivHtml);
                }
            }
        })
    }

    function getChilds(cates, id) {
        let cateArr = [];
        for (let cate of cates) {

            if (cate.fatherId == id) {
                cateArr.push(cate);
            }
        }

        return cateArr;
    }

    function getSubTree(cates, resuleArr, id) {
        for (let cate of cates) {
            if (id == cate.fatherId) {
                resuleArr.push(cate);
                getSubTree(cates, resuleArr, cate.id);
            }
        }
    }


    function delCate(obj) {
        let v_id = $(obj).parents("tr").data("tt-id");
        let resultArr = [];
        getSubTree(cates, resultArr, v_id);
        var v_ids = [];
        for (let item of resultArr) {
            v_ids.push(item.id);
        }
        v_ids.push(v_id);
        console.log(v_ids);
        $.ajax({
            type: "post",
            url: "/category/delCate.jhtml",
            data: {"ids": v_ids},
            success: function (result) {
                if (result.code == 200) {
                    initCateTable();
                }
            }
        })
    }
</script>
</body>
</html>