<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2020/6/4
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="js/css/index.css"/>
</head>
<body onkeydown="kekLogin()">
<img class="bgone" src="js/img/1.jpg"/>
<img class="pic" src="js/img/a.png"/>

<div class="table">
    <div class="wel"><font style="color: #0c0c0c;font-size: 40px;font-family: 楷体">电商后台登录</font></div>
    <div class="wel1" style="font-size: 15px">E-commerce background login</div>
    <div class="user">
        <div id="yonghu" style=""><img src="js/img/yhm.png"/></div>
        <input type="text" id="userName" placeholder="请输入用户名。。。"/>
    </div>
    <div class="password">
        <div id="yonghu"><img src="js/img/mm.png"/></div>
        <input type="password" id="password" placeholder="请输入密码。。。"/>
    </div>
    <input class="btn" type="button" value="登录" onclick="login()" id="log"/>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootbox/bootbox.min.js"></script>

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

    function kekLogin() {
        if (event.keyCode == 13)  //回车键的键值为13
            document.getElementById("log").click(); //调用登录按钮的登录事件
    }
</script>

</body>
</html>
