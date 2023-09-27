//이벤트 후기 게시판 이동
$('.service-review-btn').on('click', function (){
    window.location.href="/board/serviceReview";
})



function loadPage(page, callback){


    $.ajax({

        url:`/reviews/eventReview/${page}`,
        type:'get',
        dataType:'json',
        success : function (result){

            if(callback){
                callback(result)
            }
            console.log(result)

        }, error : function (a,b,c){
            console.error(c);
        }
    })
}


function eventReviewList(result){

    let text = '';


    result.forEach(r => {

        text += `
        
        
        <li>
                    <a href="/board/reviewDetail?sitterBoardNumber=${r.eventBoardNumber}">
                        <div class="review-sitter-img">
                            <img src="/common/img/보모사진1.jpg" alt="리뷰 보모사진"/>
                        </div>
                        <div class="review-sitter-content">
                            <p><strong>${e.eventName}</strong></p>
                            <div class="review-score">
                                <img src="/common/img/star.png"><span> ${r.rating} / 5</span>
                            </div>
                        </div>
                        <div class="reivew-text-content">
                            <dl>
                                <dt><strong>${r.userId}</strong></dt>
                                <dd>
                                    <p>
                                        ${r.eventBoardContent}
                                    </p>
                                </dd>
                            </dl>
                        </div>
                    </a>
                </li>
        
        
        
        
        
        `;


    })
}