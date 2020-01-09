<!DOCTYPE html>
<html>
<head lang="en">
    <title>Spring Boot Demo - FreeMarker</title>
    <link rel="stylesheet" href="/css/bootstrap.min.3.3.5.css"/>
    <script src="/js/jquery-1.11.2.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.js" type="text/javascript"></script>
</head>
<body>
    <table class="table">
        <caption>用户基本信息</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>姓名</th>
        </tr>
        </thead>
            <tr>
                <td>${user.id!}</td>
                <td>${user.nickName!}</td>
            </tr>
    </table>
</body>
</html>