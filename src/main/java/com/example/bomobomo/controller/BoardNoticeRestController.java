package com.example.bomobomo.controller;



import com.example.bomobomo.domain.dto.NoticeDto;
import com.example.bomobomo.domain.vo.Criteria;
import com.example.bomobomo.domain.vo.PageVo;
import com.example.bomobomo.domain.vo.SearchVo;
import com.example.bomobomo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class BoardNoticeRestController {

    private final NoticeService noticeService;


    /**
     * 공지사항 목록
     * @param page page 변수
     * @param searchVo 검색 정보가 담긴 vo
     * @return
     */
    @GetMapping("/list/{page}")
    public Map<String, Object> findAll(@PathVariable("page")int page, SearchVo searchVo) {

        //@Pathvariable로 받아온 page를 criteria 즉 현재 페이지 정보로 가져온다.
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        PageVo pageVo = new PageVo(noticeService.getTotal(searchVo), criteria);
        List<NoticeDto> boardDtoList = noticeService.selectAll(criteria, searchVo);


        //Object 타입으로 감싸서 리턴값으로 내보낸다.
        Map<String, Object> boardMap = new HashMap<>();
        boardMap.put("pageVo", pageVo);
        boardMap.put("boardNoticeList", boardDtoList);


        return boardMap;
    }


}
