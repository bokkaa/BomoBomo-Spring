// 페이징 처리

// 검색어 저장
let searchKeyword='';

// 첫 화면
$(document).ready(function () {
    loadPage(1,getSearchVo())
});

// 페이지 숫자 클릭
$(document).on('click', '.bt', function (e) {
    e.preventDefault();
    const page = $(this).data('num');
    $('.keyword').val('');
    loadPage(page, getSearchVo());
});

// 검색 버튼 클릭
$('.submit').on('click', function (){
    searchKeyword = $('.keyword').val();
    loadPage(1,getSearchVo());
})

// input 데이터
function getSearchVo(){
    let cate = $('.cate').val();
    let keyword = $('.keyword').val();
    return {
        cate : cate,
        keyword : searchKeyword
    };
}
// ajax
function loadPage(page, searchVo) {
    $.ajax({
        url: `/admin/rest/event/list/${page}`,
        type: 'get',
        data : searchVo,
        dataType: 'json',
        success: function (result) {
            console.log(result.pageVo);
            console.log(result.eventList);
            loadEventList(result);
        },
        error: function (a, b, c) {
            console.log("실패");
            console.error(c);
        }
    });
}
// // ajax로 가져온 데이터를 사용해 페이징처리
function loadEventList(result){
    if(result.eventList!=0){
        let list = $('.event-list');
        list.empty();
        $.each(result.eventList, function (index, event) {
            let avg = "";
            if(event.avg<0.5){
                avg="☆☆☆☆☆";
            }else if(event.avg>=0.5 && event.avg < 1.5){
                avg="★☆☆☆☆";
            }
            else if(event.avg>=1.5 && event.avg < 2.5){
                avg="★★☆☆☆";
            }
            else if(event.avg>=2.5 && event.avg < 3.5){
                avg="★★★☆☆";
            }
            else if(event.avg>=3.5 && event.avg < 4.5){
                avg="★★★★☆";
            }
            else if(event.avg>=4.5){
                avg="★★★★★";
            }

            let eventDiv = $('<div class="event-item">');
            let imagePath =event.eventImgUploadPath + '/th_' + event.eventImgUuid + '_' + event.eventImgName;
            eventDiv.append('<div class="event-img">'+
                `<img class = "thumbnail" src="/admin/rest/displayEventImg?fileName=${imagePath}" alt="썸네일"/>`+
                '</div>');
            eventDiv.append('<div class ="event-title">' + event.eventName+'</div>');
            eventDiv.append('<span class="event-price">'+ event.eventPrice+'<span class="won">원</span></span>');
            eventDiv.append('<div class="event-rating">'+avg+ '</div>');
            eventDiv.on('click', function(){
               let url = `/admin/adminEventDetail?eventNumber=${event.eventNumber}`
                window.location.href=url;
            });
            list.append(eventDiv);
        });
    }else{
        let list = $('.event-list');
        list.empty();
        console.log("비어있음");
    }
    pagination(result.pageVo);
}
// 각 페이징 버튼 처리
function pagination(pageVo) {
    let $pagenation = $('.pagination-container');
    $pagenation.empty();

    if (pageVo.prev) {
        $pagenation.append(`
        <a href="#" data-num="${pageVo.startPage-1}" class="bt prev">&lt;</a>
        `);
        }
    if(pageVo.realEnd!=0){
        for (let page = pageVo.startPage; page <= pageVo.endPage; page++) {
            if(page == pageVo.criteria.page){
                $pagenation.append(`
                    <a href="#" class="bt num on" data-num="${page}">${page}</a>
                `
                );

            }else {
                $pagenation.append(`
                    <a href="#" class="bt num" data-num="${page}">${page}</a>
                `
                );
            }

        }
    }else{

    }
    if (pageVo.next) {
        $pagenation.append(`
                <a href="#" data-num="${pageVo.endPage+1}"  class="bt next">&gt;</a>
            `);
    }
}


// 인풋박스에 엔터키를 누르면 search버튼 클릭 처리
function inputEnter(event) {
    if (event.key === "Enter") {
        document.getElementById("submit").click();
    }
}
document.getElementById("search").addEventListener("keyup", inputEnter);

//이벤트 등록 버튼 처리
let eventRegist = $('.regist-btn');
eventRegist.on('click', function() {
    let url = `/admin/eventRegist`;
    window.location.href = url;
});