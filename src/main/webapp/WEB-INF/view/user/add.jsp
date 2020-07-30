<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/21
  Time: 18:21
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
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" placeholder="请输入用户名...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">密码：</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="pwd" placeholder="请输入密码...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">真实姓名：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="realName1" placeholder="请输入真实姓名...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">性别：</label>
                <label class="radio-inline">
                    <input type="radio" name="sex" id="inlineRadio1" value="1" checked>男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="sex" id="inlineRadio2" value="0">女
                </label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">生日：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="birthday1" name="birthday" placeholder="请选择生日...">
                </div>
            </div>
            <div class="form-group" id="areaDiv">
                <label class="col-sm-2 control-label">地区：</label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电话：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="telePhone" placeholder="请输入电话...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">邮箱：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="email" placeholder="请输入邮箱...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">头像：</label>
                <div class="col-sm-10">
                    <input type="hidden" id="imgHidden">
                    <input type="file" class="form-control" name="filePhoto" id="userPhoto">
                </div>
            </div>
            <div style="text-align: center">
                <button type="button" class="btn btn-primary" onclick="addUser()"><i
                        class="glyphicon glyphicon-plus"></i>提交
                </button>
                <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(function () {
        initFileInput();
        datetimepicker("birthday1");
        findAreaList(0);
    })

    function initFileInput() {
        $("#userPhoto").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            showCaption: false,
            initialPreviewAsData: true
        }).on("fileuploaded", function (event, result, previewId, index) {
            var photo = result.response.data;
            console.log(photo);
            $("#imgHidden").val(photo);
        })
    }

    function addUser() {
        var v_param = {};
        v_param.userName = $("#name").val();
        v_param.password = $("#pwd").val();
        v_param.realName = $("#realName1").val();
        v_param.userImage = $("#imgHidden").val();
        v_param.sex = $("[name='sex']:checked").val();
        v_param.birthday = $("#birthday1").val();
        v_param.telePhone = $("#telePhone").val();
        v_param.email = $("#email").val();
        v_param.provincial = $($("select[name='areaSelect']")[0]).val();
        v_param.city = $($("select[name='areaSelect']")[1]).val();
        v_param.county = $($("select[name='areaSelect']")[2]).val();

        $.ajax({
            url: "/user/addUser.jhtml",
            type: "post",
            data: v_param,
            success: function (result) {
                console.log(result);
                if (result.code == 200) {
                    location.href = "/user/toUserList.jhtml";
                }
            }
        })
    }

    function datetimepicker(dateParam) {
        $('#' + dateParam).datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',
            clearBtn: true,
            minView: "decade"//设置只显示到月份
        });
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
</script>
</body>
</html>
