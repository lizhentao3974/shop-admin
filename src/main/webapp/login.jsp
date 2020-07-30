<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/24
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">用户名：</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="userName" placeholder="请输入用户名...">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码：</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" placeholder="请输入密码...">
                    </div>
                </div>
                <div style="text-align: center">
                    <button type="button" class="btn btn-primary" onclick="login()"><i
                            class="glyphicon glyphicon-ok-sign"></i>登录
                    </button>
                    <button type="reset" class="btn btn-info"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="/js/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script>
    function login() {
        var v_userName = $("#userName").val();
        var v_password = $("#password").val();
        $.ajax({
            type: "post",
            url: "/user/login.jhtml",
            data: {
                "userName": v_userName,
                "password": v_password
            },
            success: function (result) {
                if (result.code == 200) {
                    location.href = "/product/toList.jhtml";
                } else {
                    alert(result.msg);
                }
            }
        });
    }
</script>

</body>
</html>
