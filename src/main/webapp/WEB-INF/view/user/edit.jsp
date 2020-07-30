<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/21
  Time: 18:48
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
                <label class="col-sm-2 control-label">真实姓名：</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="realName1" placeholder="请输入真实姓名...">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">性别：</label>
                <label class="radio-inline">
                    <input type="radio" name="sex" id="inlineRadio1" value="1">男
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
                    <input type="hidden" id="oldImage">
                    <input type="file" class="form-control" name="filePhoto" id="userPhoto">
                </div>
            </div>
            <div style="text-align: center">
                <button type="button" class="btn btn-primary" onclick="updateUser()"><i
                        class="glyphicon glyphicon-plus"></i>提交
                </button>
                <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置</button>
            </div>
        </form>
    </div>
</div>

<script>
    $(function () {
        datetimepicker("birthday1");
        editUser();

    })

    function initFileInput(imageArr) {
        $("#userPhoto").fileinput({
            language: 'zh',
            uploadUrl: "/file/cobyFile.jhtml",
            showCaption: false,
            dropZoneEnabled: true,
            initialPreview: imageArr,
            initialPreviewAsData: true
        }).on("fileuploaded", function (event, result, previewId, index) {
            var photo = result.response.data;
            $("#imgHidden").val(photo);
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

    var proId;
    var cId;
    var couId;

    function editUser() {
        var v_userId = ${param.id};
        $.ajax({
            url: "/user/editUser.jhtml",
            type: "post",
            data: {"id": v_userId},
            success: function (result) {
                if (result.code == 200) {
                    var data = result.data;
                    $("#name").val(data.userName);
                    $("#pwd").val(data.password);
                    $("#realName1").val(data.realName);
                    $("#birthday1").val(data.birthday);
                    $("#telePhone").val(data.telePhone);
                    $("#email").val(data.email);
                    proId = data.provincial;
                    cId = data.city;
                    couId = data.county;
                    $("#areaDiv").append("<span id='nameSpan'>" + data.areaName + "</span>" + "<button type='button' class='btn btn-success' onclick='editSelect()'><i class='glyphicon glyphicon-pencil'></i><span id='editId'>编辑</span></button>");
                    $("#oldImage").val(data.userImage);
                    $("[name='sex']").each(function () {
                        if (this.value == data.sex) {
                            this.checked = true;
                        }
                    })
                    var imgArr = [];
                    imgArr.push(data.userImage);
                    initFileInput(imgArr);

                }
            }
        });
    }

    var a = 0;
    var areaName;

    function editSelect() {
        if (a == 0) {
            areaName = $("#nameSpan").html();
            $("#nameSpan").html("");
            $("#editId").html("取消编辑");
            findAreaList(0);
            a = 1;
        } else {
            $("#nameSpan").html(areaName);
            $("#editId").html("编辑");
            $("#areaDiv div[name = 'selectDiv']").remove();
            a = 0;
        }
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
                        var areaHtml = "<div class='col-sm-3' name='selectDiv'><select class='form-control' name='areaSelect' onchange='findAreaList(this.value,this)'><option value='-1'>--请选择--</option>";
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


    function updateUser() {
        var v_userId = ${param.id};

        var v_userName = $("#name").val();
        var v_pwd = $("#pwd").val();
        var v_realName = $("#realName1").val();
        var v_birthday = $("#birthday1").val();
        var v_telePhone = $("#telePhone").val();
        var v_email = $("#email").val();
        var v_sex = $("[name='sex']:checked").val();

        var v_imgFile = $("#imgHidden").val();
        var v_oldImg = $("#oldImage").val();
        console.log($("select[name='areaSelect']")[2])
        if ($("select[name='areaSelect']")[0]) {
            proId = $("select[name='areaSelect']")[0].value;
        }
        if ($("select[name='areaSelect']")[1]) {
            cId = $("select[name='areaSelect']")[1].value;
        }
        if ($("select[name='areaSelect']")[2]) {
            couId = $("select[name='areaSelect']")[2].value;
        }


        $.ajax({
            url: "/user/updateUser.jhtml",
            type: "post",
            data: {
                "id": v_userId,
                "userName": v_userName,
                "password": v_pwd,
                "userImage": v_imgFile,
                "sex": v_sex,
                "birthday": v_birthday,
                "telePhone": v_telePhone,
                "email": v_email,
                "oldImgFile": v_oldImg,
                "realName": v_realName,
                "provincial": proId,
                "city": cId,
                "county": couId,
            },
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/user/toUserList.jhtml";
                }
            }
        });
    }
</script>
</body>
</html>
