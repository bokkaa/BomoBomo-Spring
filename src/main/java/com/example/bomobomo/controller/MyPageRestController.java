package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.MatchDto;
import com.example.bomobomo.domain.vo.*;
import com.example.bomobomo.service.EventBoardService;
import com.example.bomobomo.service.MyPageService;
import com.example.bomobomo.service.SitterBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myPages")
@RequiredArgsConstructor
public class MyPageRestController {
    private final MyPageService myPageService;
    private final SitterBoardService sitterBoardService;
    private final EventBoardService eventBoardService;
//     파일경로 확인 , 시터 결제내역 이미지
    @Value("${file.empImg}")
    private String fileSitterBuyImg;

    @GetMapping("/img")
    public byte[] getPfpImg(String fileFullPath) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(fileSitterBuyImg, fileFullPath));
    }
// 이벤트 결제 내역 이미지
    @Value("${file.eventImg}")
    private String fileeventImg;

    @GetMapping("/eventimg")
    public byte[] getEventImg(String fileFullPath) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(fileeventImg, fileFullPath));
    }

    // 마이페이지 메인에서 데이터 정보 출력
    @Value("${file.empImg}")
    private String fileEmpImg;

    @GetMapping("/empimg")
    public byte[] getEmpImg(String fileFullPath) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(fileEmpImg, fileFullPath));
    }

    //마이페이지 메인헤서 활동 이미지 출력
    @Value("${actImg.dir}")
    private String fileEmpActImg;

    @GetMapping("/empActimg")
    public byte[] getEmpActImg(String fileFullPath) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(fileEmpActImg, fileFullPath));
    }
    // 마이페이지 시터 이용후기 사용자가 작성한 내용만 출력
    @GetMapping("/sitterReviewList/{page}")
    public Map<String, Object> showEventReviewList(@PathVariable("page") int page,HttpServletRequest req, Criteria criteria){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
        criteria.setPage(page);

        PageVo pageVo = new PageVo(sitterBoardService.findSitterReviewTotal(userNumber), criteria);
        List<SitterBoardVo> sitterBoardVoList = sitterBoardService.findAll(criteria,userNumber);

        Map<String, Object> map = new HashMap<>();
        map.put("pageVo", pageVo);
        map.put("list", sitterBoardVoList);
        return map;
    }

    // 마이페이지 이벤트 이용후기 사용자가 작성한 내용만 출력
    @GetMapping("/eventReviewList/{pages}")
    public Map<String, Object> showSitterReviewList(@PathVariable("pages") int pages,HttpServletRequest req, Criteria criteria){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
        criteria.setPage(pages);

        PageVo pageVO = new PageVo(eventBoardService.findEventReviewTotal(userNumber), criteria);
        List<EventBoardVo> eventBoardVoList = eventBoardService.findEventAll(criteria,userNumber);

        Map<String, Object> map = new HashMap<>();
        map.put("pageVO", pageVO);
        map.put("eventlist", eventBoardVoList);
        return map;
    }


    @PostMapping("/purchase")
    public Map<String, Object> purchasePage(HttpServletRequest req){
        Long userNumber = (Long)req.getSession().getAttribute("userNumber");
        System.out.println(userNumber);
        MatchUserInfoVo matchUserInfoVo=myPageService.findMatchUserInfo(userNumber);
        List<MatchBuyInfoVo> matchBuyInfoVo=myPageService.findMatchBuyInfo(userNumber);

        Map<String, Object> map =new HashMap<>();
        map.put("userInfo",matchUserInfoVo);
        map.put("buyInfo",matchBuyInfoVo);

        return map;
    }

    @PatchMapping("/{matchNumber}")
    public void modify(@PathVariable("matchNumber") Long matchNumber,
                       @RequestBody MatchDto matchDto){
        //json형식으로 받을거야
        System.out.println(matchNumber);


        myPageService.statusUpdate(matchDto.getMatchNumber());


    }

}









