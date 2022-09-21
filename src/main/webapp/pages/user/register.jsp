<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>尚硅谷会员注册页面</title>
    <%@include file="../../static/link.jsp" %>
    <script type="text/javascript">
        // 页面加载完成之后
        $(function () {

            let name = $(".itxt[name = 'username']")
            let password = $(".itxt[name = 'password']")
            let confirmPassword = $(".itxt[name = 'repwd']")
            let email = $(".itxt[name = 'email']")
            let code = $("#code")


            let VerifyItem = function () {

                let id = this.id
                let val = this.value;

                Verify(id, val)

            }

            name.blur(VerifyItem)
            password.blur(VerifyItem)
            confirmPassword.blur(VerifyItem)
            email.blur(VerifyItem)
            code.blur(VerifyItem)

            $("#sub_btn").click(function () {

                return Verify(name.attr("id"), name.val()) &&
                    Verify(password.attr("id"), password.val()) &&
                    Verify(confirmPassword.attr("id"), confirmPassword.val()) &&
                    Verify(email.attr("id"), email.val())
                    //&&
                    // Verify(code.attr("id"), code.val())
            })

            let Verify = function (id, val) {
                let str;
                let submitFlag = false;
                switch (id) {
                    case "username": {
                        str = /^\w{5,12}$/
                        if (!str.test(val)) {
                            $("span.errorMsg").text("用户名不合法")

                        } else {
                            $("span.errorMsg").text("")
                            submitFlag = true
                        }
                        break;
                    }
                    case "password": {
                        str = /^\w{5,12}$/
                        if (!str.test(val)) {
                            $("span.errorMsg").text("密码不合法")
                        } else {
                            $("span.errorMsg").text("")
                            submitFlag = true
                        }
                        break;
                    }
                    case "repwd": {
                        if (password.val() === "") {
                            $("span.errorMsg").text("原密码不为空")
                        } else if (password.val() !== val) {
                            $("span.errorMsg").text("确认密码与原密码不一致")
                        } else {
                            $("span.errorMsg").text("")
                            submitFlag = true
                        }
                        break;
                    }
                    case "email": {
                        str = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
                        if (!str.test(val)) {
                            $("span.errorMsg").text("邮箱不合法")
                        } else {
                            $("span.errorMsg").text("")
                            submitFlag = true
                        }
                        break;
                    }
                    // case "code": {
                    //     if (val.toLowerCase() !== "") {
                    //         $("span.errorMsg").text("验证码不正确")
                    //     } else {
                    //         $("span.errorMsg").text("")
                    //         submitFlag = true
                    //     }
                    //     break;
                    // }
                }
                return submitFlag;
            }

            $("#img_code").click(function (){
                this.src = "CaptchaServlet?d=" + new Date();
            })
        });

    </script>
    <style type="text/css">
        .login_form {
            height: 420px;
            margin-top: 25px;
        }

    </style>
</head>
<body>
<div id="login_header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif">
</div>

<div class="login_banner">

    <div id="l_content">
        <span class="login_word">欢迎注册</span>
    </div>

    <div id="content">
        <div class="login_form">
            <div class="login_box">
                <div class="tit">
                    <h1>注册尚硅谷会员</h1>
                    <span class="errorMsg">${requestScope.msg}</span>
                </div>
                <div class="form">
                    <form action="UserServlet" method="post">
                        <input type="hidden" name="action" value="register">
                        <label>用户名称：</label>
                        <input class="itxt" type="text" placeholder="请输入用户名"
                               autocomplete="off" tabindex="1" name="username" id="username"/>
                        <br/>
                        <br/>
                        <label>用户密码：</label>
                        <input class="itxt" type="password" placeholder="请输入密码"
                               autocomplete="off" tabindex="1" name="password" id="password"/>
                        <br/>
                        <br/>
                        <label>确认密码：</label>
                        <input class="itxt" type="password" placeholder="确认密码"
                               autocomplete="off" tabindex="1" name="repwd" id="repwd"/>
                        <br/>
                        <br/>
                        <label>电子邮件：</label>
                        <input class="itxt" type="text" placeholder="请输入邮箱地址"
                               autocomplete="off" tabindex="1" name="email" id="email"/>
                        <br/>
                        <br/>
                        <label>验证码：</label>
                        <input class="itxt" type="text" name="code" style="width: 100px;" id="code"/>
                        <img alt="" src="CaptchaServlet" id = "img_code" title="看不清?换一张"
                             style="float: right; margin-right: 40px; width:80px; height:28px  ">
                        <br/>
                        <br/>
                        <input type="submit" value="注册" id="sub_btn"/>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>

<%@include file="../../static/footer.jsp" %>
</body>
</html>