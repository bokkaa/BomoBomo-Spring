package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.*;
import com.example.bomobomo.domain.vo.*;
import com.example.bomobomo.service.AdminService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.HtmlUtils;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

//    관리자 로그인 페이지 GET 매핑
    @GetMapping("/login")
    public String login(){
        return "admin/adminLogin";
    }
//    관리자 로그인 POST 매핑
    @PostMapping("/login")
    public RedirectView login(String adminId, String adminPassword, HttpServletRequest req, RedirectAttributes redirectAttributes){
        AdminDto adminDto = null;

        try {
            adminDto = adminService.adminLogin(adminId, adminPassword);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isLogin","0");
            return new RedirectView("/admin/login");
        }

        req.getSession().setAttribute("adminId", adminDto.getAdminId());
        req.getSession().setAttribute("adminNumber",adminDto.getAdminNumber());
        return new RedirectView("/admin/main");
    }
//    관리자 로그아웃
    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest req){
        req.getSession().invalidate();
        return new RedirectView("/common/index");
    }

//    회원 상세정보
    @GetMapping("/adminUserDetail")
    public void selectUserDetail(@RequestParam(name = "userNumber") Long userNumber, Model model) {
        UserDetailVo userDetail = adminService.selectUserDetail(userNumber);
        model.addAttribute("userDetail", userDetail);
    }
    @GetMapping("/main")
    public String Main(){
        return "admin/adminMain";
    }
    @GetMapping("/user")
    public String User(){
        return "admin/adminUser";
    }
    @GetMapping("/emp")
    public String Emp(){
        return "admin/adminEmp";
    }

    @GetMapping("/emp/regist")
    public String empRegist(Model model){
        List<ActDto> actList = adminService.selectAct();
        List<CityDto> cityList = adminService.selectAllCity();
        model.addAttribute("actList",actList);
        model.addAttribute("cityList",cityList);
        return "admin/adminEmpRegist";
    }
    @PostMapping("/emp/regist")
    public RedirectView empRegist(EmpDto empDto,@RequestParam(name = "actNumber", required = false)List<Long> actNumber
                                               ,@RequestParam("empImgFile")List<MultipartFile> empImg){
        log.info("================================actNumber{}",actNumber);
        adminService.empRegist(empDto);
        if(actNumber != null) {
            for (int i = 0; i < actNumber.size(); i++) {
                EmpActItemDto empActItemDto = new EmpActItemDto();
                empActItemDto.setEmpNumber(empDto.getEmpNumber());
                empActItemDto.setActNumber(actNumber.get(i));
                log.info("================================{}", empActItemDto);
                adminService.empActRegist(empActItemDto);
            }
        }
        try {
            adminService.empImgRegistAndSave(empImg, empDto.getEmpNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RedirectView("/admin/emp");
    }
    @PostMapping("/emp/update")
    public RedirectView empUpdate(EmpDto empDto, @RequestParam(name="empImgFile")List<MultipartFile> empImg
                                               , @RequestParam(name="actNumber",required = false)List<Long> actNumber){
        adminService.empUpdate(empDto);

        if(actNumber != null) {
            adminService.empActDelete(empDto.getEmpNumber());
            for (int i = 0; i < actNumber.size(); i++) {
                EmpActItemDto empActItemDto = new EmpActItemDto();
                empActItemDto.setEmpNumber(empDto.getEmpNumber());
                empActItemDto.setActNumber(actNumber.get(i));
                log.info("================================{}", empActItemDto);
                adminService.empActRegist(empActItemDto);
            }
        }

        try{
//            스프링에서 Mulipart 요청 처리시 파일을 선택하지않아도 객체를 생성해서 전달되므로
//            파일의 실제크기를 선택해 파일의 유무 확인
            if(empImg != null && empImg.get(0).getSize() > 0) {
                adminService.empImgUpdateAndSave(empImg, empDto.getEmpNumber());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        String url = "/admin/adminEmpDetail?empNumber=" + empDto.getEmpNumber();
        return new RedirectView(url);
    }
    @GetMapping("/admin/adminEmpDelete")
    public RedirectView empDelete(Long empNumber){
        adminService.empDelete(empNumber);
        return new RedirectView("/admin/emp");
    }
    @GetMapping(value={"/admin/adminEmpDetail"})
    public void selectEmpDetail(@RequestParam(name="empNumber")Long empNumber,
                                Model model){
        EmpVo empDetail= adminService.selectEmpDetail(empNumber);
        double avg = adminService.selectEmpAvg(empNumber);

        model.addAttribute("empDetail",empDetail);
        model.addAttribute("avg",avg);
    }
    @GetMapping("/admin/adminEmpConfig")
    public void empUpdate(@RequestParam(name="empNumber")Long empNumber, Model model){
        EmpVo empDetail= adminService.selectEmpDetail(empNumber);
        List<ActDto> actList = adminService.selectAct();
        List<EmpActItemDto> empActList = adminService.selectEmpActItem(empNumber);
        List<CityDto> cityList = adminService.selectAllCity();
        List<CountryDto> countryList = adminService.selectAllCountry();

        model.addAttribute("countryList",countryList);
        model.addAttribute("cityList", cityList);
        model.addAttribute("empDetail",empDetail);
        model.addAttribute("actList",actList);
        model.addAttribute("empActList",empActList);
    }


    @GetMapping("/match")
    public String Match(){
        return "admin/adminMatch";
    }
    @GetMapping("/match/detail")
    public String selectMatchDetail(@RequestParam(name="matchNumber")Long matchNumber,Model model){
        MatchListVo matchDetail =  adminService.selectMatchDetail(matchNumber);
        SubmitOrderDto submitOrder = adminService.selectSubmitOrder(matchNumber);
        log.info("===============================체크1");
        UserAddressVo userAddressVo = adminService.selectUserAddress(matchNumber);
        log.info("===============================체크2");
        if(submitOrder.getGenderSecond() == null){
            submitOrder.setGenderSecond("n");
        }
        model.addAttribute("userInfo",userAddressVo);
        model.addAttribute("matchDetail",matchDetail);
        model.addAttribute("submitOrder",submitOrder);
        log.info("==============================={}",submitOrder);
        return "/admin/adminMatchManage";
    }

    @GetMapping("/event")
    public String Event(){
        return "admin/adminEvent";
    }
    @GetMapping("/redirect/event")
    public RedirectView redirectEvent(){
        return new RedirectView("/admin/event");
    }
//    이벤트 조회
    @GetMapping(value={"/adminEventDetail","/adminEventConfig"})
    public void selectEventDetail(@RequestParam(name="eventNumber")Long eventNumber,Model model){
        EventVo eventDetail = adminService.selectEventDetail(eventNumber);
        log.info("=======================================바보");
        double avg = adminService.selectEventAvg(eventNumber);
        log.info("=================================바보{}",avg);
        model.addAttribute("eventDetail",eventDetail);
        model.addAttribute("avg",avg);
    }

    @GetMapping("/eventRegist")
    public String eventRegist(){
        return "admin/adminEventRegist";
    }
//    이벤트 등록
    @PostMapping("/eventRegist")
    public RedirectView eventRegist( EventDto eventDto, @RequestParam("eventImgFile")List<MultipartFile> eventImg,
                         @RequestParam("eventDetailFile")List<MultipartFile> detailImg){
        adminService.eventRegist(eventDto);
        try {
            adminService.eventImgRegistAndSave(eventImg,eventDto.getEventNumber());
            adminService.eventDetailRegistAndSave(detailImg,eventDto.getEventNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RedirectView("/admin/event");
    }
    @PostMapping("/adminEventConfig")
    public RedirectView eventUpdate(EventVo eventVo, @RequestParam("eventImgFile")List<MultipartFile> eventImg,
                                    @RequestParam("eventDetailFile")List<MultipartFile> detailImg){
        adminService.updateEvent(eventVo);
        try{
//            스프링에서 Mulipart 요청 처리시 파일을 선택하지않아도 객체를 생성해서 전달되므로
//            파일의 실제크기를 선택해 파일의 유무 확인
            if(eventImg != null && eventImg.get(0).getSize() > 0) {
                adminService.eventImgUpdateAndSave(eventImg, eventVo.getEventNumber());
            }
            if(detailImg != null && detailImg.get(0).getSize()>0) {
                adminService.eventDetailUpdateAndSave(detailImg, eventVo.getEventNumber());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return new RedirectView("/admin/event");
    }
//    이벤트 삭제
    @GetMapping("/adminEventDelete")
    public RedirectView eventDelete(Long eventNumber){
        adminService.eventDelete(eventNumber);
        return new RedirectView("/admin/event");
    }

    @GetMapping("/notice")
    public String Notice(){
        return "admin/adminNotice";
    }
    @GetMapping("/redirect/notice")
    public RedirectView redirectNotice(){
        return new RedirectView("/admin/notice");
    }
    @GetMapping("/noticeRegist")
    public String noticeRegist(){
        return "admin/adminNoticeWrite";
    }
//      공지사항 등록
    @PostMapping("/noticeRegist")
    public RedirectView noticeRegist(NoticeDto noticeDto){
        adminService.noticeRegist(noticeDto);
        return new RedirectView("/admin/notice");
    }
//    공지사항 수정
    @PostMapping("/noticeConfig")
    public RedirectView noticeUpdate(NoticeDto noticeDto){
        adminService.noticeUpdate(noticeDto);
        return  new RedirectView("/admin/notice");
    }
//    공지사항 상세정보
    @GetMapping(value={"/adminNoticeDetail","/adminNoticeConfig"})
    public void selectNoticeDetail(@RequestParam(name = "noticeNumber")Long noticeNumber, Model model){
        NoticeDto noticeDetail = adminService.selectNoticeDetail(noticeNumber);
        model.addAttribute("noticeDetail",noticeDetail);
    }
//    공지사항 삭제
    @GetMapping("/adminNoticeDelete")
    public RedirectView noticeDelete(Long noticeNumber){
        adminService.noticeDelete(noticeNumber);
        return new RedirectView("/admin/notice");
    }
}
