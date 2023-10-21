$(".joinBtn").click(function() {
 
    let id = $("#userId").val();
    let pw = $("#userPassword").val();
    let pwCh = $("#userPasswordCh").val();
    let name = $("#userName").val();
    let email = $("#userEmail").val();
    let phone = $("#userPhone").val();
    let address = $("#address").val();

    if($('.id_already').css('display') == 'block'){
        Swal.fire({
            icon: 'error',
            title: '이미 등록된 아이디입니다.',
            text: '아이디를 다시 입력해 주세요.',
          });
        return;
    }

    if(!(id && pw && pwCh && name && email && phone && address)) {
                Swal.fire({
                    icon: 'error',
                    title: '입력을 안 한 정보가 있습니다.',
                    text: '모든 입력을 완료해주세요',
                  });

        return;
    }


    if(!email.includes('@')) {
        $('.labelEm').css('display','block');
        $('.labelPh').css('display','none');
        return;
    }

    if(phone.length != 11) {
        $('.labelEm').css('display','none');
        $('.labelPh').css('display','block');
        return;
    } 
    
  $('.form').submit();

});

$('#userPasswordCh').keyup(function () {


    let pw = $("#userPassword").val();
    let pwCh = $("#userPasswordCh").val();

    if(pw != pwCh) {
        $('.labelPwCh').css('display','block');
        $('.labelEm').css('display','none');
        // $('.PwChOk').css('display', 'none');
        return;
    } else {
        // $('.PwChOk').css('display', 'block');
        $('.labelPwCh').css('display','none');
    }

});

// $(".swal2-confirm").click(function() {
//
// });

//아이디 중복확인
function checkId(){

    $('#userId').change(function() {

    var userId = $('#userId').val(); //id값이 "id"인 입력란의 값을 저장
    $.ajax({
        url:'/user/idCheck', //Controller에서 요청 받을 주소
        type:'post', //POST 방식으로 전달
        data:{userId:userId},
        success:function(idCk){ //컨트롤러에서 넘어온 cnt값을 받는다

            if(idCk == 0){ //cnt가 1이 아니면(=0일 경우) -> 사용 가능한 아이디
                $('.idOk').css("display","block");
                $('.id_already').css("display", "none");
            } else { // cnt가 1일 경우 -> 이미 존재하는 아이디
                $('.id_already').css("display","block");
                $('.idOk').css("display", "none");
                // $('#userId').val('');
            }
        },
        error:function(){
            alert("에러입니다");
        }
    });

    });

};



function checkPw() {

    let pw = $("#userPassword").val();
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_=+])(?=.*[0-9]).{8,15}$/;

    let pwCheck = reg.test(pw);

    $('#userPassword').on('keyup', function () {

        if(pwCheck) {
            $('.labelPwNo').css("display", "none");
            $('.PwChOk').css('display', 'block');
        } else {
            $('.labelPwNo').css("display", "block");
            $('.PwChOk').css('display', 'none');
        }

    });

}





function addressFind() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                } console.log(extraAddr);
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById('addressDetail').value = extraAddr;
            
            } else {
                document.getElementById('addressDetail').value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('addressPost').value = data.zonecode;
            document.getElementById('address').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('addressDetails').focus();
        }
    }).open();
    console.log("성공!");
}























      
    
        