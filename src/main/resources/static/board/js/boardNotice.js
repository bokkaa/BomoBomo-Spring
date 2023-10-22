import * as boardNotify from './module/boardNotify.js';
import * as paging from './module/boardPagination.js';


let keywordTest = '';

//input에서 받은 결과를 넘긴다.
function getSearchVo(){
    let cate = $('.cate').val();
    let keyword = $('.keyword').val();

    console.log(keyword);

    return {
        cate : cate,
        keyword : keywordTest
    };
}

//page 변수를 처음부터 1로 초기화
//첫화면이 1페이지
$(document).ready(function () {
    boardNotify.loadPage(1, getSearchVo(), noticeList);

});

//페이징처리된 숫자 클릭 시 해당 데이터를 가져와서 비동기 페이징처리
$(document).on('click', '.page-num a', function (e) {
    e.preventDefault();
    $('.keyword').val('');
    const page = $(this).data('pagenum');
    boardNotify.loadPage(page, getSearchVo(), noticeList);


});


//검색 버튼 클릭 시 검색결과 화면에 표시를하며 동시에 페이징처리
$('.notice-search-btn').on('click', function (){
    keywordTest = $('.keyword').val();
    boardNotify.loadPage(1, getSearchVo(), noticeList);
})

$(document).on('click', '.back-to-notice-btn', function (){
    $('.keyword').val('');
    keywordTest = '';
    boardNotify.loadPage(1, getSearchVo(), noticeList);
})


function noticeList(result) {
    let text = '';


    if(result.boardNoticeList.length != 0){
        result.boardNoticeList.forEach(r => {

            text += `
                <tr class="notice-content">
                    <td class="notice-number">${r.noticeNumber}</td>
                    <td class="notice-title"><a href="/board/detail?noticeNumber=${r.noticeNumber}"><strong>${r.noticeTitle}</strong></a></td>
                    <td class="notice-writer">관리자</td>
                    <td class="notice-register-date">${r.noticeRegisterDate}</td>
                    <td class="notice-count">${r.noticeCount}</td>
                </tr>
            `;
        });

    }else {
        text = `
            <tr class="non-notice-search-result">
                <td colspan="5" > 
                    <img src="/common/img/non-search-result.png" alt="검색결과 없음"/>
                </td>
            </tr>
            <tr class="non-notice-search-result">
                <td colspan="5" class="non-search-result"> 
                    <p><strong>검색 결과가 없습니다. 검색 정보를 다시 확인해주세요.</strong></p>
                    <button class="back-to-notice-btn" data-pagenum="1">목록으로 돌아가기</button>
                </td>
            </tr>  
          
           
        `;
    }

    $('.content').html(text);
    //동시에 페이징처리
    let $pagenation = $('.notice-pagination-container ul');

    paging.updatePagination(result.pageVo, $pagenation);
}






