/*우편번호 찾기 코드*/
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
                document.getElementById('address_extra').value = extraAddr;

            } else {
                document.getElementById('address_extra').value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('address-num').value = data.zonecode;
            document.getElementById('address').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('address_detail').focus();
        }
    }).open();
    console.log("성공!");
}

//수정후 버튼 클릭시 이동




//비밀번호 특수 문자 및 비밀번호 확인
function checkPw() {

    let pw = $("#userPassword").val();
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_=+])(?=.*[0-9]).{8,15}$/;

    let pwCheck = reg.test(pw);
    console.log(pwCheck);
    $('#userPassword').on('keyup',function () {

        if(pwCheck) {
            $('.checkPw').css("display", "none");
            console.log("실행!");
        } else {
            $('.checkPw').css("display", "block");
            console.log("altlfgod!")
        }

    });
}

function checkPassword() {
    let pw = $("#userPassword").val();
    let up = $('#userPassword1').val();
    console.log(up);
    $('#userPassword1').on('keyup', function () {

        if (up == pw || !up) {
            $('.checkPws').css("display", "none");
        } else {
            $('.checkPws').css("display", "block");
        }

    });
}



$('.removebtn').on('click',function (){
    let userRemoveBtn=$(this).data('number');
    console.log(userRemoveBtn);
    if(confirm("정말로 탈퇴하시겠습니까?")){
        console.log(userRemoveBtn);
        window.location.href='/mypage/userInfoDelete?userNumber=' + userRemoveBtn;
    }else{

    }
})

