<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/21
  Time: 15:49
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
                    <div class="panel-heading">用户查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="emportForm">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="userName" id="userName"
                                           placeholder="请输入用户名...">
                                </div>
                                <label class="col-sm-2 control-label">生日范围：</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="minBirthday" id="minBirthday"
                                               placeholder="请选择最小生日...">
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i> </span>
                                        <input type="text" class="form-control" name="maxBirthday" id="maxBirthday"
                                               placeholder="请选择最大生日...">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">真实姓名：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" name="realName" id="realName"
                                           placeholder="请输入真实姓名...">
                                </div>
                            </div>
                            <div class="form-group" id="areaDiv">
                                <label class="col-sm-2 control-label">地区：</label>
                            </div>
                            <input type="hidden" name="provincial" id="shengId">
                            <input type="hidden" name="city" id="shiId">
                            <input type="hidden" name="county" id="xianId">
                            <div style="text-align: center">
                                <button type="button" class="btn btn-primary" onclick="queryUserParam()"><i
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
                <div class="panel-heading">用户列表
                    <button type="button" class="btn btn-success" onclick="addUser()"><span
                            class="glyphicon glyphicon-plus"></span>添加用户
                    </button>
                    <button type="button" class="btn btn-danger" onclick="deleteUsers()"><i
                            class="glyphicon glyphicon-trash"></i>批量删除
                    </button>
                    <button type="button" class="btn btn-success" onclick="exportExcel()"><i
                            class="glyphicon glyphicon-download"></i>导出Excel
                    </button>
                    <button type="button" class="btn btn-success"><i class="glyphicon glyphicon-upload"></i>导入Excel
                    </button>
                    <button type="button" class="btn btn-success" onclick="importWord()"><i
                            class="glyphicon glyphicon-download"></i>导出Work
                    </button>
                    <button type="button" class="btn btn-success" onclick="exportPdf()"><i
                            class="glyphicon glyphicon-download"></i>导出PDF
                    </button>
                </div>
                <div class="panel-body">
                    <table id="userTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>ID</th>
                            <th>用户名</th>
                            <th>真实姓名</th>
                            <th>头像</th>
                            <th>性别</th>
                            <th>生日</th>
                            <th>地区</th>
                            <th>电话</th>
                            <th>邮箱</th>
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
        datetimepicker("minBirthday");
        datetimepicker("maxBirthday");
        initDelete();
        findAreaList(0);
    })

    function exportPdf() {
        var v_emportPdf = document.getElementById("emportForm");
        v_emportPdf.method = "post";
        v_emportPdf.action = "/user/emportPdf.jhtml";
        v_emportPdf.submit();
    }

    function importWord() {
        var v_word = document.getElementById("emportForm");
        v_word.method = "post";
        v_word.action = "/user/importWord.jhtml";
        v_word.submit();
    }

    function exportExcel() {
        var v_excel = document.getElementById("emportForm");
        v_excel.method = "post";
        v_excel.action = "/user/exportExcel.jhtml";
        $("#shengId").val($($("select[name='areaSelect']")[0]).val());
        $("#shiId").val($($("select[name='areaSelect']")[1]).val());
        $("#xianId").val($($("select[name='areaSelect']")[2]).val());
        v_excel.submit();
    }

    function findAreaList(id, obj) {
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type: "post",
            url: "/area/findAreaList.jhtml",
            data: {"areaId": id},
            success: function (result) {
                if (result.code == 200) {
                    if (result.data.length > 0) {
                        var v_data = result.data;
                        var areaHtml = "<div class='col-sm-3'><select class='form-control' name='areaSelect' onchange='findAreaList(this.value,this)'><option value='-1'>--请选择--</option>";
                        for (var i = 0; i < v_data.length; i++) {
                            var area = v_data[i];
                            areaHtml += "<option value='" + area.id + "'>" + area.areaName + "</option>";
                        }
                        areaHtml += "</select></div>";
                        $("#areaDiv").append(areaHtml)
                    }
                }
            }
        })
    }

    function deleteUsers() {
        if (ids.length < 1) {
            bootbox.alert({
                message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>请选择要删除的用户</b></font>',
                title: "<font color='#663399'>提示信息</font>"
            })
        } else {
            bootbox.confirm({
                title: "删除用户",
                message: "请点击是否删除",
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
                            type: "post",
                            data: {"ids": ids},
                            url: "/user/deleteUsers.jhtml",
                            success: function (retult) {
                                if (retult.code == 200) {
                                    queryUserParam();
                                }
                            }
                        })
                    }
                }
            })
        }
    }

    var userTable;

    function initTable() {
        userTable = $('#userTable').DataTable({
            //汉化
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "processing": true,
            "serverSide": true,
            "lengthMenu": [5, 10, 15, 20],
            "ajax": {
                url: "/user/queryUserList.jhtml",
                type: "post",
            },
            "drawCallback": function (s) {
                $("#userTable tbody tr").each(function () {
                    var v_checkbox = $(this).find("input[name='ids']:checkbox")[0];
                    var v_id = $(v_checkbox).val();
                    if (isExist(v_id)) {
                        $(this).css("background", "pink");
                        $(v_checkbox).prop("checked", true);
                    }
                })
            },
            columns: [
                {
                    "data": "id", "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='" + data + "'>";
                    }
                },
                {"data": "id"},
                {"data": "userName"},
                {"data": "realName"},
                {
                    "data": "userImage", "render": function (data, type, row, meta) {
                        return "<img src='" + data + "' width='60px'>";
                    }
                },
                {
                    "data": "sex", "render": function (data, type, row, meta) {
                        return data == 1 ? "男" : "女";
                    }
                },
                {"data": "birthday"},
                {"data": "areaName"},
                {"data": "telePhone"},
                {"data": "email"},
                {
                    "data": "id", "render": function (data, type, row, meta) {
                        return '<button type="button" class="btn btn-danger" onclick="deleteUser(' + data + ')"><span class="glyphicon glyphicon-trash"></span>删除</button>' +
                            '<button type="button" class="btn btn-warning" onclick="edit(' + data + ')"><span class="glyphicon glyphicon-pencil"></span>修改</button>';
                    }
                }

            ],
        });
    }

    function addUser() {
        location.href = "/user/toAdd.jhtml";
    }

    function queryUserParam() {
        var v_param = {};
        v_param.userName = $("#userName").val();
        v_param.realName = $("#realName").val();
        v_param.minBirthday = $("#minBirthday").val();
        v_param.maxBirthday = $("#maxBirthday").val();
        v_param.provincialId = $($("select[name='areaSelect']")[0]).val();
        v_param.cityId = $($("select[name='areaSelect']")[1]).val();
        v_param.countyId = $($("select[name='areaSelect']")[2]).val();
        userTable.settings()[0].ajax.data = v_param;
        userTable.ajax.reload();
    }

    function datetimepicker(dateParam) {
        $('#' + dateParam).datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
    }

    function deleteUser(id) {
        event.stopPropagation();
        bootbox.confirm({
            title: "删除用户",
            message: "请点击确认删除用户",
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
                        url: "/user/deleteUser.jhtml",
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

    function edit(id) {
        event.stopPropagation();
        location.href = "/user/toEdit.jhtml?id=" + id;
    }

    function isExist(id) {
        for (var i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                return true;
            }
        }
        return false;
    }

    var ids = [];

    function initDelete() {
        $("#userTable tbody").on("click", "tr", function () {
            var v_checkbox = $(this).find("input[name='ids']:checkbox");
            var v_checked = v_checkbox.prop("checked");
            if (v_checked) {
                v_checkbox.prop("checked", false);
                $(this).css("background", "");
                for (var i = ids.length - 1; i >= 0; i--) {
                    if (ids[i] == v_checkbox.val()) {
                        ids.splice(i, 1);
                    }
                }
            } else {
                v_checkbox.prop("checked", true);
                $(this).css("background", "pink");
                ids.push(v_checkbox.val());
                console.log(ids);
            }
        })
    }
</script>

</body>
</html>
