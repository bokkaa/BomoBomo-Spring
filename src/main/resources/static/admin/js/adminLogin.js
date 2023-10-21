$(".loginBtn").click(function() {

    let id = $("#adminId").val();
    let pw = $("#adminPassword").val();
    if(id == "" || id == undefined) {
        $('.labelId').css("display","block");
        $('.labelId').css("color","red");
        $('.labelPw').css("display","none");
        $('.labelFailed').css("display","none");
        return false;
    } else if(pw == "" || pw == undefined) {
        $('.labelPw').css("display","block");
        $('.labelPw').css("color","red");
        $('.labelId').css("display","none");
        $('.labelFailed').css("display","none");
        return false;
    }
    return true;
});












