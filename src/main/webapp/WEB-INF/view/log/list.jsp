<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/31
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>
<jsp:include page="/nav.jsp"/>
<jsp:include page="/commons/includeScript.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">记录查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="emport">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="userName" id="userName"
                                           placeholder="请输入用户名...">
                                </div>
                                <label class="col-sm-2 control-label">真实姓名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="realName" id="realName"
                                           placeholder="请输入真实姓名...">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">记录内容：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="info" id="info"
                                           placeholder="请输入记录内容...">
                                </div>
                                <label class="col-sm-2 control-label">日期范围：</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="minCreateDate" id="minCreateDate"
                                               placeholder="请选择开始时间...">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i> </span>
                                        <input type="text" class="form-control" name="maxCreateDate" id="maxCreateDate"
                                               placeholder="请选择结束时间...">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否异常：</label>
                                <label class="radio-inline">
                                    <input type="radio" name="stutas" value="1" checked>正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stutas" value="0">异常
                                </label>
                            </div>
                            <div style="text-align: center">
                                <button type="button" class="btn btn-primary" onclick="queryLog()"><i
                                        class="glyphicon glyphicon-search"></i>查询
                                </button>
                                <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">日志记录</div>
                <div class="panel-body">
                    <table id="logTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>记录时间</th>
                            <th>记录内容</th>
                            <th>是否异常</th>
                            <th>记录详情</th>
                            <th>参数信息</th>
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
        initLogTable();
        datetimepicker("minCreateDate");
        datetimepicker("maxCreateDate");
    })

    function datetimepicker(dateParam) {
        $('#' + dateParam).datetimepicker({
            format: 'yyyy-mm-dd hh:mm:ss',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
    }

    var logTable;

    function initLogTable() {
        logTable = $('#logTable').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "ordering": false,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/log/queryLogList.jhtml",
                type: "post",
            },
            columns: [
                {"data": "id"},
                {"data": "userName"},
                {"data": "realName"},
                {"data": "insertTime"},
                {"data": "info"},
                {
                    "data": "stutas", "render": function (data, type, row, meta) {
                        return data == 1 ? "正常" : "异常";
                    }
                },
                {"data": "content"},
                {"data": "paramInfo"},

            ],

        });
    }

    function queryLog() {
        var v_param = {};
        v_param.userName = $("#userName").val();
        v_param.minDate = $("#minCreateDate").val();
        v_param.maxDate = $("#maxCreateDate").val();
        v_param.realName = $("#realName").val();
        v_param.info = $("#info").val();
        v_param.stutas = $("[name='stutas']:checked").val();
        logTable.settings()[0].ajax.data = v_param;
        logTable.ajax.reload();
    }

</script>

</body>
</html>
