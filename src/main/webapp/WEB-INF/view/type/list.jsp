<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/1/20
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>类型管理</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<jsp:include page="/commons/includeScript.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">类型列表
                    <button type="button" class="btn btn-success" onclick="addType()"><span
                            class="glyphicon glyphicon-plus"></span>添加
                    </button>
                </div>
                <div class="panel-body">
                    <table id="typeTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        initTable();
    })

    function addType() {
        location.href = "/type/toAdd.jhtml";
    }

    function deleteType(id) {
        event.stopPropagation();
        bootbox.confirm({
            title: "删除类型",
            message: "请点击确认删除类型",
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
                        url: "/type/deleteType.jhtml",
                        type: "post",
                        data: {"id": id},
                        success: function (result) {
                            if (result.code == 200) {
                                search();
                            }
                        }
                    });
                }
            }
        })

    }

    function search() {
        typeTable.ajax.reload();
    }

    var typeTable;

    function initTable() {
        typeTable = $('#typeTable').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/type/queryPageList.jhtml",
                type: "post",
            },
            "columns": [
                {"data": "id"},
                {"data": "typeName"},
                {
                    "data": "id", "render": function (data, type, row, meta) {
                        return '<button type="button" class="btn btn-warning" onclick="updateType(' + data + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
                            '&nbsp;<button type="button" class="btn btn-danger" onclick="deleteType(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>';
                    }
                },
            ]
        })
    }


    function updateType(id) {
        location.href = "/type/toEdit.jhtml?id=" + id;
    }
</script>
</body>
</html>
