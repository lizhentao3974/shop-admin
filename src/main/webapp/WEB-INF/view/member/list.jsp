<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/27 0027
  Time: 下午 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/commons/includeCss.jsp"/>
    <title>会员管理</title>
</head>
<body>
<jsp:include page="/nav.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">会员查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="productForm">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">登录名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="memberName" placeholder="请输入登录名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会员名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" placeholder="请输入会员名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生日</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="startBirthday">
                                    <span class="input-group-addon" id="sizing-addon2"><i
                                            class="glyphicon glyphicon-calendar"></i></span>
                                    <input type="text" class="form-control" id="endBirthday">
                                </div>
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search();"><i
                                    class="glyphicon glyphicon-search"></i> 查询
                            </button>
                            <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <table id="memberTable" class="table table-striped table-bordered" style="width:100%">
        <thead>
        <tr>
            <th>选择</th>
            <th>会员id</th>
            <th>登录名</th>
            <th>会员名</th>
            <th>生日</th>
            <th>手机</th>
            <th>邮箱</th>
            <th>地区</th>
        </tr>
        </thead>

        <tfoot>
        <tr>
            <th>选择</th>
            <th>会员id</th>
            <th>登录名</th>
            <th>会员名</th>
            <th>生日</th>
            <th>手机</th>
            <th>邮箱</th>
            <th>地区</th>
        </tr>
        </tfoot>
    </table>
</div>
<jsp:include page="/commons/includeScript.jsp"/>
<script>
    $(function () {
        initDate();
        initMemberTable();
    })

    function initDate() {
        $('#startBirthday').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });

        $('#endBirthday').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }

    function search() {
        var v_param = {};
        v_param.memberName = $("#memberName").val();
        v_param.realName = $("#realName").val();
        v_param.startBirthday = $("#startBirthday").val();
        v_param.endBirthday = $("#endBirthday").val();
        memberTable.settings()[0].ajax.data = v_param;
        memberTable.ajax.reload();
    }

    var memberTable;

    function initMemberTable() {
        memberTable = $('#memberTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5, 10, 15, 20],
            "serverSide": true,
            "ajax": {
                "url": "/member/findMemberPageList.jhtml",
                "type": "get"
            },
            "columns": [
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='" + data + "'/>"
                    }
                },
                {"data": "id"},
                {"data": "memberName"},
                {"data": "realName"},
                {"data": "birthday"},
                {"data": "phone"},
                {"data": "mail"},
                {"data": "areaName"}
            ]
        });
    }
</script>
</body>
</html>
