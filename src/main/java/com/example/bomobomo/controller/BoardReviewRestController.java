package com.example.bomobomo.controller;


import com.example.bomobomo.domain.vo.*;
import com.example.bomobomo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class BoardReviewRestController {
    private final ReviewService reviewService;

    
   
    @Value("${file.empImg}")
    String empImg;

    //직원 사진 경로
    @GetMapping("/empPic")
    public byte[] getEmpPic(String empPicFullName) throws IOException{
        System.out.println("========================");
        System.out.println(empPicFullName);
        return FileCopyUtils.copyToByteArray(new File(empImg, empPicFullName));
    }


    /**
     * 돌봄 서비스 후기 목록
     * @param page page 변수
     * @param searchReviewVo 검색 정보를 담고 있는 vo
     * @return
     */
    @GetMapping("/service/{page}")
    public Map<String, Object> findListAll(@PathVariable("page") int page, SearchReviewVo searchReviewVo){

        //페이징
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        //한 페이지당 게시물 수 설정
        criteria.setAmount(8);


        PageVo pageReviewVo = new PageVo(reviewService.getTotal(searchReviewVo), criteria);
        List<SitterBoardVo> serviceReviewList = reviewService.selectAll(criteria, searchReviewVo);

        Map<String, Object> reviewMap = new HashMap<>();
        reviewMap.put("pageReviewVo", pageReviewVo);
        reviewMap.put("serviceReviewList", serviceReviewList);

        log.info(pageReviewVo+"===============================");
        log.info(serviceReviewList+"=====================");

        return reviewMap;

    }




    //====================================================================
    
    //이벤트 서비스 후기 사진 저장경로
    @Value("${file.dir}")
    String fileEventImg;

    //이벤트 서비스 후기 사진
    @GetMapping("/img")
    public byte[] getImg(String fileFullName) throws IOException{
        System.out.println("============================================================");
        System.out.println(fileFullName);
        return FileCopyUtils.copyToByteArray(new File(fileEventImg, fileFullName));
    }


    /**
     * 이벤트 서비스 후기 목록
     * @param page page 변수
     * @param searchReviewVo 검색 정보를 담고있는 vo
     * @return
     */
    @GetMapping("/eventReview/{page}")
    public Map<String, Object> findEventReviewList(@PathVariable("page") int page,
                                                   SearchReviewVo searchReviewVo){
        //페이징
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(8);

        PageVo pageEventReviewVo = new PageVo(reviewService.getTotalER(searchReviewVo), criteria);
        List<EventBoardVo> eventReviewList = reviewService.findEventReview(criteria, searchReviewVo);

        Map<String, Object> eventReviewMap = new HashMap<>();
        eventReviewMap.put("pageEventReviewVo", pageEventReviewVo);
        eventReviewMap.put("eventReviewList", eventReviewList);

        log.info(pageEventReviewVo+"===============");
        log.info(eventReviewList+"==============================");

        return eventReviewMap;
    }
}
