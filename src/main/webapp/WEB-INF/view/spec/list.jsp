<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/1/14
  Time: 19:12
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
<jsp:include page="/commons/includeScript.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">规格列表
                    <button type="button" class="btn btn-success" onclick="addSpec()"><span
                            class="glyphicon glyphicon-plus"></span>添加
                    </button>
                </div>
                <div class="panel-body">
                    <table id="specTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>规格名</th>
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

    function addSpec() {
        location.href = "/spec/toAdd.jhtml";
    }

    function deleteSpec(id) {
        event.stopPropagation();
        bootbox.confirm({
            title: "删除规格",
            message: "请点击确认删除规格",
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
                        url: "/spec/deleteSpec.jhtml",
                        type: "post",
                        data: {"id": id},
                        success: function (result) {
                            if (result.code == 200) {
                                queryUserParam();
                            }
                        }
                    });
                }
            }
        })

    }

    var specTable;

    function initTable() {
        specTable = $('#specTable').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/spec/queryPageList.jhtml",
                type: "post",
            },
            "columns": [
                {"data": "id"},
                {"data": "specName"},
                {
                    "data": "id", "render": function (data, type, row, meta) {
                        return '<button type="button" class="btn btn-warning" onclick="updateSpec(' + data + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
                            '&nbsp;<button type="button" class="btn btn-danger" onclick="deleteSpec(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>';
                    }
                },
            ]
        })
    }

    function queryUserParam() {
        specTable.ajax.reload();
    }

    function updateSpec(id) {
        location.href = "/spec/toEdit.jhtml?id=" + id;
    }
</script>
</body>
</html>
