package com.example.bomobomo.service;


import com.example.bomobomo.domain.dto.SitterCommentDto;
import com.example.bomobomo.domain.vo.Criteria;
import com.example.bomobomo.domain.vo.EventCommentVo;
import com.example.bomobomo.domain.vo.SitterCommentVo;
import com.example.bomobomo.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {


    private final ReplyMapper replyMapper;


    /**
     * 돌봄 서비스 후기 - 댓글 등록
     * @param sitterCommentDto 댓글 정보가 담긴 dto
     */
    public void register(SitterCommentDto sitterCommentDto){
       replyMapper.insert(sitterCommentDto);
    }



    /**
     * 돌봄 서비스 후기 - 댓글 목록
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     * @return 해당 게시물 ID에 등록된 댓글 목록
     */
    public List<SitterCommentVo> find(Long sitterBoardNumber){
        if (sitterBoardNumber == null) {

            throw new IllegalArgumentException("리뷰 게시번호 누락");
        }

        return replyMapper.select(sitterBoardNumber);
    }



    /**
     * 돌봄 서비스 후기 - 댓글 목록
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     * @param criteria 페이징 처리를 위한 criteria 객체
     * @return 해당 게시물 ID에 등록된 댓글 목록
     */
    public List<SitterCommentVo> findAll(Long sitterBoardNumber, Criteria criteria) {

        if (sitterBoardNumber == null) {
            throw new IllegalArgumentException("리뷰 게시 번호 누락");
        }

        return replyMapper.selectList(sitterBoardNumber, criteria);
    }


    /**
     * 돌봄 서비스 후기 - 댓글 수정
     * @param sitterCommentDto 수정 정보가 담긴 dto
     */
    public void modify(SitterCommentDto sitterCommentDto){
        replyMapper.update(sitterCommentDto);

    }


    /**
     * 돌봄 서비스 후기 - 댓글 삭제
     * @param sitterCommentNumber 댓글 ID
     */
    public void remove(Long sitterCommentNumber){
        if (sitterCommentNumber == null) {
            throw new IllegalArgumentException("댓글 번호 누락");

        }

        replyMapper.delete(sitterCommentNumber);
    }

    /**
     * 돌봄 서비스 후기 - 댓글 총개수
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     * @return
     */
    public int getTotal(Long sitterBoardNumber){
        if (sitterBoardNumber == null) {
            throw new IllegalArgumentException("리뷰게시판 번호 누락");
        }
       return replyMapper.getTotal(sitterBoardNumber);
    }


    //============================================
    //이벤트 서비스 리뷰 댓글


    /**
     * 이벤트 서비스 후기 - 댓글 등록
     * @param eventCommentVo 댓글정보가 담긴 vo
     */
    public void registerEventReply(EventCommentVo eventCommentVo){
        replyMapper.insertEventReply(eventCommentVo);

    }


    /**
     * 이벤트 서비스 후기 - 댓글 목록
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @param criteria 페이징 처리를 위한 criteria 객체
     * @return
     */
    public List<EventCommentVo> findAllEventReply(Long eventBoardNumber, Criteria criteria){

        if (eventBoardNumber == null) {
            throw new IllegalArgumentException("이벤트 보드 넘버 누락");
        }

        return replyMapper.selectListEventReply(eventBoardNumber, criteria);
    }


    /**
     * 이벤트 서비스 후기 - 댓글 수정
     * @param eventCommentVo 수정 정보가 담긴 vo
     */
    public void modifyEventReply(EventCommentVo eventCommentVo){
        replyMapper.updateEventReply(eventCommentVo);
    }



    /**
     * 이벤트 서비스 후기 - 댓글 삭제
     * @param eventCommentNumber 댓글 ID
     */
    public void removeEventReply(Long eventCommentNumber){
        if (eventCommentNumber == null) {
            throw new IllegalArgumentException("이벤트 댓글 번호 누락");
        }

        replyMapper.deleteEventReply(eventCommentNumber);
    }


    /**
     * 이벤트 서비스 후기 - 댓글 총개수
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @return
     */
    public int getTotalEventReply(Long eventBoardNumber){
        if (eventBoardNumber == null) {
            throw new IllegalArgumentException("이벤트 게시판 번호 누락");
        }

        return replyMapper.getTotalEventReply(eventBoardNumber);

    }
}
