$(function(){
    $("#loginIn").bind("click",function () {
        checkform();
    });

    // $("#loginIn").bind("click",function () {
    //       loginIn();
    // })
    $("#emailshow").bind("click",function(){
        email();
    })
    $("#emailloginIn").bind("click",function(){
        emailLogin();
    })
    $("#emailSend").bind("click",function(){
        emailSend();
        time(this);
    })
})

//切换成邮箱登陆
function email() {
    $("#login").hide()
    $("#emaillogin").show()
}

//发送验证码
//验证码禁用
var wait=10;
function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        //o.value="免费获取验证码";
        $(o).html("免费获取验证码");
        wait = 10;
    } else {
        o.setAttribute("disabled", true);
        //o.value="重新发送(" + wait + ")";
        $(o).html("重新发送(" + wait + ")");
        wait--;
        setTimeout(function() {
            time(o)
        }, 1000)
    }
}

function emailSend() {
    var email = $("#email").val();

    $.ajax({
        url: "/test/smsSend",
        type: "get",
        data:{"email":email},
        dataType: "json",
        success:function (data) {
            alert(data.info)
        }
    })

}

//邮箱登录
function emailLogin() {

}


//检测用户是否输入用户名密码
function checkform() {
    var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (document.getElementById('userName').value.length==0){
        alert('用户名不能为空');
        document.getElementById('userName').focus();
        return false;
    }
    if (!pattern.test($("#email").val())) {
        alert('邮箱不正确');
        return false;
    }
    if (document.getElementById('userPwd').value.length==0){
        alert('密码不能为空');
        document.getElementById('userPwd').focus();
        return false;
    } else {loginIn()}
}

//登陆方法
function loginIn() {
    var user ={};
    user.userName = $("#userName").val();
    user.userPwd = $("#userPwd").val();


    $.ajax({
        url:"/test/loginIn",
        type:"post",
        contentType:"application/json",
        data:JSON.stringify(user),
        dataType:"json",
        success:function (data) {
            if (data.info == "登录成功"){
                location.href = "/test/index";
            }else {
                alert(data.info);
            }
        }
    })

}