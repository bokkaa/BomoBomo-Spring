package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.ActDto;
import com.example.bomobomo.domain.dto.ActImgDto;
import com.example.bomobomo.service.ActImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@Transactional
@RequestMapping("/sample/*")
public class SampleController {


    private final ActImgService actImgService;

    @GetMapping("/checkbox")
    public String move(){
        return "/board/sample";
    }

    @PostMapping("/choices")
    public String sample(ActImgDto actImgDto, ActDto actDto,
                         @RequestParam("actType") String act,
                         @RequestParam("actImg") List<MultipartFile> files){

        //활동명 가져와서 넣기
        actDto.setActName(act);
        log.info(act.toString());
        //활동 테이블에 등록
        actImgService.registerAct(actDto);


        //활동 이미지 테이블에 넣을 actNumber 가져오기
        actImgDto.setActNumber(actDto.getActNumber());


        //활동이미지 테이블에 등록
        actImgService.registerAndFileProc(actImgDto, files);



        return "common/index";
    }


}
