package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.EventBoardDto;
import com.example.bomobomo.domain.dto.SitterBoardDto;
import com.example.bomobomo.domain.vo.EventBoardVo;
import com.example.bomobomo.domain.vo.SitterBoardVo;
import com.example.bomobomo.service.NoticeService;
import com.example.bomobomo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {


    private final NoticeService noticeService;
    private final ReviewService reviewService;



    //FAQ 게시판
    @GetMapping("/faq")
    public String showFaqPage(){
        return "board/boardFaq";
    }

    //공지사항 게시판
    @GetMapping("/notice")
    public String showNoticePage(){
        return "board/boardNotice";
    }


    /**
     * 공지사항 상세
     * @param noticeNumber 공지사항 ID
     * @param model model 객체
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @return 해당 공지사항 ID와 일치하는 게시물 정보
     */
    @GetMapping("/detail")
    public String showNoticeDetailPage(@RequestParam(name = "noticeNumber") Long noticeNumber, Model model, HttpServletRequest req, HttpServletResponse resp){


        //모델 객체를 통해 detail페이지로 해당 공지사항 세부내역 전달
        model.addAttribute("noticeDetail",  noticeService.selectOne(noticeNumber));

        //조회수 증가
        CookieHandler cookieHandler = new CookieHandler(noticeService, reviewService);
        cookieHandler.handleCookies(req, resp, noticeNumber, "notice_count_cookie");

        return "board/detail";
    }


    //=========================================================


    //돌봄후기 게시판
    @GetMapping("/serviceReview")
    public String showServiceReviewPage(){
        return "board/serviceReview";
    }


    /**
     * 돌봄 서비스 후기 상세
     * @param sitterBoardNumber 돌봄 후기 게시물ID
     * @param model model 객체
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @return 해당 Id와 일치하는 게시물 정보
     */
    @GetMapping("/reviewDetail")
    public String showServiceReviewDetailPage(@RequestParam("sitterBoardNumber") Long sitterBoardNumber,
                                               Model model, HttpServletRequest req, HttpServletResponse resp){



        SitterBoardVo sitterBoardVo = reviewService.selectOne(sitterBoardNumber);
        List<SitterBoardVo> sitterBoardVoList = reviewService.findReviewDetail(sitterBoardVo.getEmpNumber());
        double getAvg = reviewService.getAvgRating(sitterBoardVo.getEmpNumber());

        //조회수 증가
        CookieHandler cookieHandler = new CookieHandler(noticeService, reviewService);
        cookieHandler.handleCookies(req, resp, sitterBoardNumber, "reviewDetail_count_cookie");


        model.addAttribute("sitterReviewList", sitterBoardVoList);
        model.addAttribute("serviceReviewDetail", sitterBoardVo);
        model.addAttribute("getAvg", (Math.round(getAvg*100) / 100.0));

        log.info(String.valueOf(getAvg));
        log.info(sitterBoardVo.toString());
        log.info(sitterBoardVoList.toString());
        return "board/serviceReviewDetail";
    }

    /**
     * 돌봄 서비스 후기 수정 페이지 이동
     * @param sitterBoardNumber 돌봄 후기 게시물 ID
     * @param model model 객체
     * @return
     */
    @GetMapping("/modifyServiceReview")
    public String modifyServiceReview(@ModelAttribute("sitterBoardNumber") Long sitterBoardNumber,
                                      Model model){


        model.addAttribute("sitter",  reviewService.selectOne(sitterBoardNumber));
        return "board/serviceReviewModify";
    }

    /**
     * 돌봄 서비스 후기 수정 완료
     * @param sitterBoardDto 수정 정보가 담긴 dto
     * @param sitterBoardNumber 돌봄 후기 게시물ID
     * @param redirectAttributes RedirectAttributes 객체
     * @return
     */
    @PostMapping("/modifySR")
    public RedirectView modifySR(SitterBoardDto sitterBoardDto,
                                 @RequestParam("sitterBoardNumber") Long sitterBoardNumber,
                                 RedirectAttributes redirectAttributes)
        {

        sitterBoardDto.setSitterBoardNumber(sitterBoardNumber);
        log.info(sitterBoardDto.toString()+"*******************===========================");
        reviewService.modifyServiceReview(sitterBoardDto);

            redirectAttributes.addAttribute("sitterBoardNumber", sitterBoardDto.getSitterBoardNumber());

            return new RedirectView("/board/reviewDetail");
    }


    /**
     * 돌봄 서비스 후기 삭제
     * @param sitterBoardNumber 돌봄 후기 게시물 ID
     * @return 돌봄 후기 목록 페이지로 이동
     */
    @GetMapping("/removeSReview")
    public RedirectView removeServiceReview(Long sitterBoardNumber){
        reviewService.delete(sitterBoardNumber);

        return new RedirectView("serviceReview");
    }

    
    //=========================================================================


    //이벤트 게시판 이동
    @GetMapping("/eventReview")
    public String showEventReviewPage(){
        return "board/eventReview";
    }



    /**
     * 이벤트 서비스 후기 상세
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @param model model 객체
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @return 해당 ID와 일치하는 게시물 정보
     */
    //이벤트 후기 상세보기
    @GetMapping("/reviewEventDetail")
    public String showEventReviewDetailPage(@RequestParam("eventBoardNumber")Long eventBoardNumber
            , Model model, HttpServletRequest req, HttpServletResponse resp){



        EventBoardVo eventBoardVo = reviewService.showEReviewDetail(eventBoardNumber);
        List<EventBoardVo> eventBoardVoList = reviewService.findEventReviewTopCount(eventBoardVo.getEventNumber());
        double getAvgEventReview = reviewService.getAvgEventReviewRating(eventBoardVo.getEventNumber());

        log.info(String.valueOf(getAvgEventReview) +"===============================================");
        log.info(eventBoardVo.toString()+"====================******************");

        model.addAttribute("eventReviewList", eventBoardVoList);
        model.addAttribute("eventReviewDetail", eventBoardVo);
        model.addAttribute("avgEventReview", (Math.round(getAvgEventReview*100) / 100.0));

        //조회수 증가
        CookieHandler cookieHandler = new CookieHandler(noticeService, reviewService);
        cookieHandler.handleCookies(req, resp, eventBoardNumber, "eventReviewDetail_count_cookie");

        return "board/eventReviewDetail";

    }

    /**
     * 이벤트 서비스 후기 수정 페이지 이동
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @param model model 객체
     * @return
     */
    @GetMapping("/modifyEventReview")
    public String modifyEventReview(@ModelAttribute("eventBoardNumber") Long eventBoardNumber,
                                    Model model){

        model.addAttribute("eventReview", reviewService.showEReviewDetail(eventBoardNumber));
        return "/board/eventReviewModify";
    }


    /**
     * 이벤트 서비스 후기 수정 완료
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @param userNumber 회원 ID
     * @param eventNumber 이벤트 ID
     * @param eventBoardDto 수정 정보가 담긴 dto
     * @param redirectAttributes RedirectAttributes 객체
     * @param files 사진파일
     * @return
     */
    @PostMapping("/modifyER")
    public RedirectView modifyER(@RequestParam("eventBoardNumber")Long eventBoardNumber,
                                 @RequestParam("userNumber")Long userNumber,
                                 @RequestParam("eventNumber")Long eventNumber,
                                 EventBoardDto eventBoardDto,
                                 RedirectAttributes redirectAttributes, @RequestParam("eventBoardImg") MultipartFile files){


            eventBoardDto.setEventBoardNumber(eventBoardNumber);
            eventBoardDto.setUserNumber(userNumber);
            eventBoardDto.setEventNumber(eventNumber);

        log.info(eventBoardDto.toString()+"*******************===========================");

        reviewService.modifyEventReview(eventBoardDto, files);
        redirectAttributes.addAttribute("eventBoardNumber", eventBoardDto.getEventBoardNumber());

        return new RedirectView("/board/reviewEventDetail");

    }

    /**
     * 이벤트 서비스 후기 삭제
     * @param eventBoardNumber 이벤트 후기 게시물 ID
     * @return 이벤트 후기 목록 페이지로 이동
     */
    @GetMapping("/removeEReview")
    public RedirectView removeEventReview(Long eventBoardNumber){
        reviewService.removeEventReview(eventBoardNumber);
        return new RedirectView("eventReview");
    }


}