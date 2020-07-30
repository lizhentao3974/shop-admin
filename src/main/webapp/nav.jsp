<%--
  Created by IntelliJ IDEA.
  User: dangmengdi
  Date: 2019/12/29
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 导航栏 -->
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">商品后台管理系统</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li id="li_1"><a href="/product/toList.jhtml#1">商品管理</a></li>
                <li id="li_2"><a href="/category/toCateList.jhtml#2">分类管理</a></li>
                <li id="li_9"><a href="/goods/toList.jhtml#9">SPU商品管理</a></li>
                <li id="li_3"><a href="/brand/toBrandList.jhtml#3">品牌管理</a></li>
                <li id="li_4"><a href="/log/toLogList.jhtml#4">日志管理</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        系统管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li id="li_5"><a href="/user/toUserList.jhtml#5">用户管理</a></li>
                        <li id="li_6"><a href="/area/toIndex.jhtml#6">地区管理</a></li>
                        <li id="li_7"><a href="/spec/toList.jhtml#7">规格管理</a></li>
                        <li id="li_8"><a href="/type/toList.jhtml#8">类型管理</a></li>
                        <li id="li_10"><a href="/member/index.jhtml#10">会员管理</a></li>
                    </ul>
                </li>
            </ul>
        </div>

        <font style="margin-left:50px;color:#FFFFF0;font-size:20px;font-family:黑体">欢迎</font>
        <font style="color:#00FFFF;font-size:30px;font-family:楷体;font-style:italic">${user.realName}</font>

        <c:if test="${!empty user.loginDate}">
            <font style="margin-left:50px;color:pink;font-size:20px;font-family:楷体">上一次登陆时间</font>
            <font color="#ccffff"><fmt:formatDate value="${user.loginDate}"
                                                  pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></font>
        </c:if>
        <font style="margin-left:50px;color:#a6e1ec;font-size:20px;font-family:楷体">今天是第</font>
        <font color="#ff1493">${user.loginCount}</font>
        <font style="color:#a6e1ec;font-size:20px;font-family:楷体">次登录</font>

        <a href="/user/removeUser.jhtml"><font
                style="color:#00FFFF;margin-left:50px;font-size:15px;font-family:黑体;">退出</font></a>
    </div>
</nav>
<script src="/js/jquery.min.js"></script>

<script>
    $(function () {

        // 全局设置
        $.ajaxSetup({
            //设置ajax 请求结束后 的执行动作
            complete:
                function (XMLHttpRequest, textStatus) {
                    var header = XMLHttpRequest.getResponseHeader("fh_ajax_timeout");
                    // 发送的是ajax请求， session 超时 了
                    if (header && header == "timeout") {
                        window.location.href = "/";
                    }

                    var v_result = XMLHttpRequest.responseJSON;
                    if (v_result.code == -1) {
                        bootbox.alert({
                            message: '<font color="red"><i class="glyphicon glyphicon-exclamation-sign"></i><b>服务器内部错误</b></font>',
                            title: "提示信息",
                            size: "small"
                        })
                    }
                }
        });


        initNav();
    })

    function initNav() {
        var v_id = location.hash.substring(1);
        $("#li_" + v_id).addClass("active");
    }
</script>