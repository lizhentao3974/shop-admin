<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/12/15
  Time: 20:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form class="form-horizontal" role="form" id="brandForm">
    <input type="hidden" name="id" value="${brand.id}">
    <div class="form-group">
        <label class="col-sm-2 control-label">品牌名：</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" name="brandName" placeholder="请输入品牌名..." value="${brand.brandName}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">品牌名：</label>
        <div class="col-sm-10">
            <input type="hidden" name="logo" id="echoLogo">
            <input type="hidden" name="oldLogo" id="oldLogo" value="${brand.logo}">
            <input type="file" class="form-control" name="filePhoto" id="brandLogo">
        </div>
    </div>
</form>
</body>
</html>
