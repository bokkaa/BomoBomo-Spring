package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.SitterCommentDto;
import com.example.bomobomo.domain.vo.*;
import com.example.bomobomo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/replies")
public class BoardReplyRestController {


    private final ReplyService replyService;

    /**
     * 돌봄 서비스 후기 - 댓글 등록
     * @param sitterCommentDto 댓글 정보가 담긴 dto
     */
    @PostMapping("")
    public void serviceReviewReply(@RequestBody SitterCommentDto sitterCommentDto){
        replyService.register(sitterCommentDto);

    }


    /**
     * 돌봄 서비스 후기 - 댓글 목록 조회
     * @param sitterBoardNumber 돌봄 후기 게시물 ID
     * @return 해당 ID와 일치하는 돌봄 후기에 등록된 댓글 목록
     */
    @GetMapping("/list/{sitterBoardNumber}")
    public List<SitterCommentVo> showReply(@PathVariable("sitterBoardNumber") Long sitterBoardNumber){
        return replyService.find(sitterBoardNumber);
    }

    /**
     * 돌봄 서비스 후기 - 댓글 목록 조회
     * @param sitterBoardNumber 돌봄 후기 게시물 ID
     * @param page page 변수
     * @return 해당 ID와 일치하는 돌봄 후기에 등록된 댓글 목록
     */
    @GetMapping("/list/{sitterBoardNumber}/{page}")
    public Map<String, Object> showReplyList(@PathVariable("sitterBoardNumber") Long sitterBoardNumber,
                                           @PathVariable("page") int page){

        Criteria criteria = new Criteria();
        criteria.setAmount(6);
        criteria.setPage(page);

        PageVo pageReplyVo = new PageVo(replyService.getTotal(sitterBoardNumber), criteria);
        List<SitterCommentVo> replyList = replyService.findAll(sitterBoardNumber, criteria);

        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("pageReplyVo", pageReplyVo);
        replyMap.put("replyList", replyList);
        replyMap.put("totalReply", replyService.getTotal(sitterBoardNumber));

        return replyMap;

    }


    /**
     * 돌봄 서비스 후기 - 댓글 수정
     * @param sitterCommentNumber 댓글 ID
     * @param sitterCommentDto 수정 정보를 담고 있는 dto
     */
    @PatchMapping("{sitterCommentNumber}")
    public void modifyReply(@PathVariable("sitterCommentNumber") Long sitterCommentNumber,
                            @RequestBody SitterCommentDto sitterCommentDto){
        sitterCommentDto.setSitterCommentNumber(sitterCommentNumber);
        replyService.modify(sitterCommentDto);
    }

    /**
     * 돌봄 서비스 후기 - 댓글 삭제
     * @param sitterCommentNumber 댓글 ID
     */
    @DeleteMapping("/{sitterCommentNumber}")
    public void removeReply(@PathVariable("sitterCommentNumber") Long sitterCommentNumber){
        if (sitterCommentNumber == null) {
            throw new IllegalArgumentException("댓글 번호 누락");
        }

        replyService.remove(sitterCommentNumber);
    }


    
    


}
