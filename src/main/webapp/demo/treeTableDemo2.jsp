<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/2/23
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/commons/includeCss.jsp"/>
</head>
<body>

<table id="areaTable" class="table table-bordered">
    <tr>
        <th>地区名</th>
        <th>邮编</th>
        <th>操作</th>
    </tr>
    <tr data-tt-id="0">
        <td>中国</td>
        <td>000</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
    <tr data-tt-id="01" data-tt-parent-id="0">
        <td>河南</td>
        <td>001</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
    <tr data-tt-id="011" data-tt-parent-id="01">
        <td>郑州</td>
        <td>0011</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
    <tr data-tt-id="0111" data-tt-parent-id="011">
        <td>金水区</td>
        <td>00111</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>

    <tr data-tt-id="02" data-tt-parent-id="0">
        <td>河北</td>
        <td>002</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
    <tr data-tt-id="021" data-tt-parent-id="02">
        <td>廊坊</td>
        <td>0021</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
    <tr data-tt-id="0211" data-tt-parent-id="021">
        <td>香河县</td>
        <td>00211</td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteProduct('+data+')"><span
                    class="glyphicon glyphicon-trash"></span>删除
            </button>
            <button type="button" class="btn btn-warning" onclick="edit('+data+')"><span
                    class="glyphicon glyphicon-pencil"></span>修改
            </button>
        </td>
    </tr>
</table>

<jsp:include page="/commons/includeScript.jsp"/>

<script>
    $("#areaTable").treetable({expandable: true});
</script>
</body>
</html>
