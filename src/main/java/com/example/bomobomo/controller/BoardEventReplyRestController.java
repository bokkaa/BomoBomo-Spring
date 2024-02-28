package com.example.bomobomo.controller;

import com.example.bomobomo.domain.vo.Criteria;
import com.example.bomobomo.domain.vo.EventCommentVo;
import com.example.bomobomo.domain.vo.PageVo;
import com.example.bomobomo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/eventReplies")
public class BoardEventReplyRestController {

    private final ReplyService replyService;

    //이벤트 서비스 리뷰


    /**
     * 이벤트 서비스 후기 - 댓글 등록
     * @param eventCommentVo 이벤트 서비스 - 댓글 vo
     */
    @PostMapping("")
    public void eventReviewReply(@RequestBody EventCommentVo eventCommentVo){
        replyService.registerEventReply(eventCommentVo);
    }


    /**
     * 이벤트 서비스 후기- 댓글 리스트
     * @param eventBoardNumber 이벤트 후기 게시글  ID
     * @param page page 변수
     * @return 해당 게시글 ID와 일치하는 이벤트 게시글에 등록된 댓글 리스트
     */
    @GetMapping("/list/{eventBoardNumber}/{page}")
    public Map<String, Object> showEventReplyList(@PathVariable("eventBoardNumber") Long eventBoardNumber,
                                                  @PathVariable("page") int page){
        Criteria criteria = new Criteria();
        criteria.setAmount(6);
        criteria.setPage(page);

        PageVo pageReplyVo=new PageVo(replyService.getTotalEventReply(eventBoardNumber), criteria);
        List<EventCommentVo> replyList = replyService.findAllEventReply(eventBoardNumber, criteria);

        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("pageReplyVo", pageReplyVo);
        replyMap.put("replyList", replyList);
        replyMap.put("totalReply", replyService.getTotalEventReply(eventBoardNumber));


        log.info(replyList.toString()+"=======================**************");

        return replyMap;
    }


    /**
     * 이벤트 서비스 후기  - 댓글 삭제
     * @param eventCommentNumber 이벤트 서비스 후기 댓글ID
     */
    @DeleteMapping("/{eventCommentNumber}")
    public void removeReply(@PathVariable("eventCommentNumber") Long eventCommentNumber){
        if (eventCommentNumber == null) {
            throw new IllegalArgumentException("이벤트 댓글번호 누락");
        }

        replyService.removeEventReply(eventCommentNumber);
    }


    /**
     * 이벤트 서비스 후기 - 댓글 수정
     * @param eventCommentNumber 이벤트 후기 게시글 ID
     * @param eventCommentVo 이벤트 후기 댓글 vo
     */
    @PatchMapping("{eventCommentNumber}")
    public void modifyEventReply(@PathVariable("eventCommentNumber") Long eventCommentNumber,
                                 @RequestBody EventCommentVo eventCommentVo){

        eventCommentVo.setEventCommentNumber(eventCommentNumber);
        replyService.modifyEventReply(eventCommentVo);

    }


}
