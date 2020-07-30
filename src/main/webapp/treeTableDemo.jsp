<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/2/23
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/commons/includeCss.jsp"/>
    <title>444</title>
</head>
<body>
<table id="example-basic">
    <caption>Basic jQuery treetable Example</caption>
    <thead>
    <tr>
        <th>Tree column</th>
        <th>Additional data</th>
    </tr>
    </thead>
    <tbody>
    <tr data-tt-id="1">
        <td>Node 1: Click on the icon in front of me to expand this branch.</td>
        <td>I live in the second column.</td>
    </tr>
    <tr data-tt-id="1.1" data-tt-parent-id="1">
        <td>Node 1.1: Look, I am a table row <em>and</em> I am part of a tree!</td>
        <td>Interesting.</td>
    </tr>
    <tr data-tt-id="1.1.1" data-tt-parent-id="1.1">
        <td>Node 1.1.1: I am part of the tree too!</td>
        <td>That's it!</td>
    </tr>
    <tr data-tt-id="2">
        <td>Node 2: I am another root node, but without children</td>
        <td>Hurray!</td>
    </tr>
    </tbody>
</table>

<jsp:include page="/commons/includeScript.jsp"/>

<script>
    $("#example-basic").treetable({expandable: true});
</script>
</body>
</html>
