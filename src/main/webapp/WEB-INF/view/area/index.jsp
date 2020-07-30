<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/16
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>地区ztree树</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<jsp:include page="/commons/includeScript.jsp"/>

<div class="panel panel-primary">
    <div class="panel-heading">地区树展示
        <button type="button" class="btn btn-success" onclick="addArea()"><span class="glyphicon glyphicon-plus"></span>添加
        </button>
        <button type="button" class="btn btn-warning" onclick="updateArea()"><span
                class="glyphicon glyphicon-pencil"></span>修改
        </button>
        <button type="button" class="btn btn-danger" onclick="deleteArea()"><span
                class="glyphicon glyphicon-trash"></span>删除
        </button>
    </div>
    <div class="panel-body">
        <ul id="areaTree" class="ztree"></ul>
    </div>
</div>

<div id="addAreaDiv" style="display: none">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">地区名：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="AddareaName" placeholder="请输入地区名...">
            </div>
        </div>
    </form>
</div>
<div id="updateAreaDiv" style="display: none">
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">地区名：</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="updateAreaName" placeholder="请输入地区名...">
            </div>
        </div>
    </form>
</div>

<SCRIPT type="text/javascript">

    var areaDivHtml;
    var updateDivHtml;
    $(function () {
        areaZtree();
        areaDivHtml = $("#addAreaDiv").html();
        updateDivHtml = $("#updateAreaDiv").html();
    })

    function addArea() {
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();
        var addNode = nodes[0];
        if (nodes.length > 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>只能选择一个节点</b></font>',
                title: "<font color='#663399'>选择节点添加地区</font>"
            })
        }
        if (nodes.length < 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>请选择一个节点</b></font>',
                title: "<font color='#663399'>选择节点添加地区</font>"
            })
        }
        if (nodes.length == 1) {
            var areaDialog = bootbox.dialog({
                title: "添加地区",
                message: $("#addAreaDiv form"),
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
                            var nodesId = nodes[0].id;
                            var param = {};
                            var v_areaName = $("#AddareaName", areaDialog).val();
                            param.areaName = v_areaName
                            param.fId = nodesId;
                            $.ajax({
                                type: "post",
                                url: "/area/addArea.jhtml",
                                data: param,
                                success: function (result) {
                                    if (result.code == 200) {
                                        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
                                        var newNode = {id: result.data, areaName: v_areaName, fId: nodes};
                                        newNode = treeObj.addNodes(addNode, newNode);
                                    }
                                }
                            })
                        }
                    }
                },
            });
            $("#addAreaDiv").html(areaDivHtml);
        }

    }


    function deleteArea() {
        var ids = [];
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();
        var nodesArr = treeObj.transformToArray(nodes);
        for (var i = 0; i < nodesArr.length; i++) {
            ids.push(nodesArr[i].id);
        }
        if (nodes.length == 0) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>请选择节点</b></font>',
                title: "<font color='#663399'>选择节点删除地区</font>"
            })
        } else {
            $.ajax({
                type: "post",
                url: "/area/deleteAreas.jhtml",
                data: {"ids": ids},
                success: function (result) {
                    if (result.code == 200) {
                        for (var i = 0, l = nodes.length; i < l; i++) {
                            treeObj.removeNode(nodes[i]);
                        }
                    }
                }
            })
        }
    }

    function updateArea() {
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();

        if (nodes.length > 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>只能选择一个节点</b></font>',
                title: "<font color='#663399'>选择节点修改地区</font>"
            })
        }
        if (nodes.length < 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>请选择一个节点</b></font>',
                title: "<font color='#663399'>选择节点修改地区</font>"
            })
        }
        if (nodes.length == 1) {
            var v_areaId = nodes[0].id;
            $.ajax({
                type: "post",
                url: "/area/findArea.jhtml",
                data: {"id": v_areaId},
                success: function (result) {
                    var v_data = result.data;
                    $("#updateAreaName").val(v_data.areaName);
                    if (result.code == 200) {
                        var updateDialog = bootbox.dialog({
                            title: "添加地区",
                            message: $("#updateAreaDiv form"),
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
                                        var v_areaName = $("#updateAreaName", updateDialog).val();
                                        $.ajax({
                                            type: "post",
                                            url: "/area/updateArea.jhtml",
                                            data: {"id": v_areaId, "areaName": v_areaName},
                                            success: function (result) {
                                                if (result.code == 200) {
                                                    nodes[0].areaName = v_areaName;
                                                    treeObj.updateNode(nodes[0]);
                                                }
                                            }

                                        })
                                    }
                                }
                            },
                        });
                        $("#updateAreaDiv").html(updateDivHtml);
                    }
                }
            })
        }

    }

    function areaZtree() {
        $.ajax({
            type: "post",
            url: "/area/queryAreaZtree.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    $.fn.zTree.init($("#areaTree"), setting, result.data);
                }
            }
        })
    }

    var setting = {
        data: {
            simpleData: {
                enable: true,
                pIdKey: "fId"
            },
            key: {
                name: "areaName",
            }
        }
    };


</SCRIPT>
</body>
</html>
