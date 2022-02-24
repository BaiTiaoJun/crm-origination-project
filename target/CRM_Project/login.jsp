<%--
  Created by IntelliJ IDEA.
  User: 27310
  Date: 2022/1/31
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/jQuery-3.6.0.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            if (window.top !== window) {
                window.top.location = window.location;
            }

            $('#user').val("").focus();
            $('#submit').click(function () {
                login();
            })

            $(window).keydown(function (event) {
                if (event.keyCode === 13) {
                    login();
                }
            })
        })

        function login() {
            let userVal = $.trim($('#user').val());
            let passVal = $.trim($('#pass').val());
            if (userVal === "" || passVal === "") {
                $('#msg').html("用户或密码不能为空");
                return false;
            }

            $.ajax({
                url: "settings/user/login.do",
                data: {"userName":userVal,
                        "pass":passVal
                },
                type: "post",
                dataType: "json",
                success: function(data) {
                    if (data.success) {
                        window.location.href="workbench/index.jsp";
                    } else {
                        $('#msg').html(data.msg);
                    }
                }
            })
        }
    </script>
</head>
<body/>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 20px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman',serif">CRM &nbsp;</div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="user">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="pass">
                </div>
                <div class="checkbox"  style="position: relative; top: 30px; left: 10px; color: red">

                    <span id="msg"></span>

                </div>
                <button type="button" id="submit" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
            </div>
        </form>
    </div>
</div>
</bodyid>
</html>
