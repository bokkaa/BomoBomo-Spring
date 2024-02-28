package com.example.bomobomo.service;


import com.example.bomobomo.domain.dto.EventBoardDto;
import com.example.bomobomo.domain.dto.EventBoardImgDto;
import com.example.bomobomo.domain.dto.SitterBoardDto;
import com.example.bomobomo.domain.vo.Criteria;
import com.example.bomobomo.domain.vo.EventBoardVo;
import com.example.bomobomo.domain.vo.SearchReviewVo;
import com.example.bomobomo.domain.vo.SitterBoardVo;
import com.example.bomobomo.mapper.EventBoardFileMapper;
import com.example.bomobomo.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {


    private final ReviewMapper reviewMapper;
    private final EventBoardFileService eventBoardFileService;



    /**
     * 돌봄 서비스 후기 목록
     * @param criteria 페이징 처리 criteria 객체
     * @param searchReviewVo 검색 정보가 담긴 vo
     * @return
     */
      public List<SitterBoardVo> selectAll(Criteria criteria,SearchReviewVo searchReviewVo){

      List<SitterBoardVo> selectListAll = reviewMapper.selectServiceReviewAll(criteria, searchReviewVo);

      return selectListAll;

  }



    /**
     * 페이징 처리를 위한 게시물 총 개수
     * @param searchReviewVo 검색 정보가 담긴 vo
     * @return
     */
    public int getTotal(SearchReviewVo searchReviewVo){


          return reviewMapper.getTotal(searchReviewVo);


    }

    /**
     * 돌봄 서비스 후기 상세
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     * @return 해당 후기 ID와 일치하는 게시물 정보
     */
    public SitterBoardVo selectOne(Long sitterBoardNumber){
        if (sitterBoardNumber == null) {
            throw new IllegalArgumentException("리뷰게시번호 누락");
        }

        return Optional.ofNullable(reviewMapper.selectOne(sitterBoardNumber))
                .orElseThrow(()->{throw new IllegalArgumentException("존재하지 않는 게시 번호");});
    }




    /**
     * 돌봄 서비스 후기 평점
     * @param empNumber 직원 ID
     * @return 해당 직원 ID의 돌봄 서비스 평점
     */
    public double getAvgRating(Long empNumber){

        if (empNumber == null) {
            throw new IllegalArgumentException("직원 번호 누락");
        }


         return reviewMapper.getAvgRating(empNumber);
    }


    /**
     * 조회수 증가 로직
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     */
    public void updateCount(Long sitterBoardNumber){
        if (sitterBoardNumber == null) {
            throw new IllegalArgumentException("리뷰 게시 번호 누락");

        }

        reviewMapper.updateCount(sitterBoardNumber);

    }

    /**
     * 돌봄 서비스 후기 수정
     * @param sitterBoardDto 수정 정보가 담긴 dto
     */
    public void modifyServiceReview(SitterBoardDto sitterBoardDto){

          reviewMapper.updateServiceReview(sitterBoardDto);

    }

    /**
     * 돌봄 서비스 후기 삭제
     * @param sitterBoardNumber 돌봄 서비스 후기 게시물 ID
     */
    public void delete(Long sitterBoardNumber){
        if (sitterBoardNumber == null) {
            throw new IllegalArgumentException("리뷰 게시 번호 누락");
        }

        reviewMapper.delete(sitterBoardNumber);

    }

    /**
     * 메인페이지 : 돌봄 서비스 후기 - 조회수 기준 Top6
     * @return
     */
    public List<SitterBoardVo> findTopCount(){
          return reviewMapper.selectTopCount();
    }

    /**
     * 돌봄 후기 상세 - 동일 직원 다른 후기 목록
     * @param empNumber 직원 ID
     * @return
     */
    public List<SitterBoardVo> findReviewDetail(Long empNumber){
        if (empNumber == null) {
            throw new IllegalArgumentException("직원 번호 누락");
        }

       return reviewMapper.serviceReviewDetailTopCount(empNumber);
    }



    //=================================================================


    /**
     * 이벤트 서비스 후기 목록
     * @param criteria 페이징 처리를 위한 criteria 객체
     * @param searchReviewVo 검색 정보가 담긴 vo
     * @return
     */
    public List<EventBoardVo> findEventReview(Criteria criteria, SearchReviewVo searchReviewVo){
          return reviewMapper.selectER(criteria, searchReviewVo);
    }

    /**
     * 이벤트 서비스 후기 총개수
     * @param searchReviewVo 검색 정보가 담긴 vo
     * @return
     */
    public int getTotalER(SearchReviewVo searchReviewVo){
          return reviewMapper.getTotalER(searchReviewVo);
    }

    /**
     * 이벤트 서비스 후기 상세
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @return
     */
    public EventBoardVo showEReviewDetail(Long eventBoardNumber){

          return Optional.ofNullable(reviewMapper.selectOne2(eventBoardNumber))
                  .orElseThrow(()->{throw new IllegalArgumentException("존재하지 않는 정보");});
    }



    /**
     * 이벤트 서비스 평점
     * @param eventNumber 이벤트 서비스 ID
     * @return
     */
    public double getAvgEventReviewRating(Long eventNumber){

        if (eventNumber == null) {
            throw new IllegalArgumentException("이벤트 번호 누락");
        }

        return reviewMapper.selectAvgEventReviewRating(eventNumber);
    }

    /**
     * 이벤트 서비스 후기 삭제
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     */
    public void removeEventReview(Long eventBoardNumber){
        if (eventBoardNumber == null) {
            throw new IllegalArgumentException("이벤트 리뷰 게시판 번호 누락");
        }
        eventBoardFileService.remove(eventBoardNumber);
        reviewMapper.deleteEventReview(eventBoardNumber);
    }

    /**
     * 이벤트 서비스 후기 조회수 증가
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     */
    public void updateEventReviewCount(Long eventBoardNumber){
        if (eventBoardNumber == null) {
            throw new IllegalArgumentException("이벤트 리뷰 번호 누락");
        }
        reviewMapper.updateEventReviewCount(eventBoardNumber);
    }

    /**
     * 메인페이지 : 이벤트 서비스 후기 조회수 기준 TOp6
     * @return
     */
    public List<EventBoardVo> findEventTopCount(){
          return reviewMapper.selectTopEventCount();
    }


    /**
     * 이벤트 서비스 후기 수정
     * @param eventBoardDto 수정 정보가 담긴 dto
     * @param files 사진 파일
     */
    public void modifyEventReview(EventBoardDto eventBoardDto, MultipartFile files){

            if(!files.isEmpty()){
                eventBoardFileService.remove(eventBoardDto.getEventBoardNumber());
                try{
                    eventBoardFileService.registerAndSaveFile(files, eventBoardDto.getEventBoardNumber());
                }catch(IOException e){
                    e.printStackTrace();
                }
                reviewMapper.updateEventReview(eventBoardDto);
            }
    }


    /**
     * 이벤트 서비스 후기 - 동일 이벤트 다른 후기 목록 조회
     * @param eventNumber 이벤트 ID
     * @return
     */
    public List<EventBoardVo> findEventReviewTopCount(Long eventNumber){

        if (eventNumber == null) {
            throw new IllegalArgumentException("이벤트 번호 누락");
        }
         return reviewMapper.eventReviewDetailTopCount(eventNumber);
    }


}
