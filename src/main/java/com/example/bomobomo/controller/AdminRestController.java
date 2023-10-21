package com.example.bomobomo.controller;

import com.example.bomobomo.domain.dto.CountryDto;
import com.example.bomobomo.domain.dto.EmpDto;
import com.example.bomobomo.domain.dto.EstContentDto;
import com.example.bomobomo.domain.dto.NoticeDto;
import com.example.bomobomo.domain.vo.UserListVo;
import com.example.bomobomo.domain.vo.*;
import com.example.bomobomo.service.AdminService;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/rest/*")
@Slf4j
public class AdminRestController {
    private final AdminService adminService;

    @Value("${file.eventImg}")
    private String eventImgPath;
    @Value("${file.eventDetail}")
    private String eventDetailPath;
    @Value("${file.empImg}")
    private String empImgPath;
    @Value("${actImg.dir}")
    private String actImgPath;

    @GetMapping("/weeklyRegister")
    public List<WeeklyRegisterVo> weeklyRegister(){
        List<WeeklyRegisterVo> weeklyList = adminService.weeklyRegister();
        return weeklyList;
    }

    @GetMapping("/sitterTotal")
    public Map<String,Integer> total(){
        Map<String,Integer> data = new HashMap<>();
        data.put("sitterTotal", adminService.sitterTotal());
        data.put("eventTotal", adminService.eventTotal());
        return data;
    }

    @GetMapping("/newMatch")
    public List<MatchListVo> selectNewMatch(){
        log.info("=====================뉴매치{}",adminService.selectNewMatch());
        List<MatchListVo> matchList = adminService.selectNewMatch();
        return matchList;
    }


    @GetMapping("/user/list/{page}")
    public Map<String, Object> selectAllUsers(@PathVariable("page")int page, SearchVo searchVo) {
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(adminService.getTotalUsers(searchVo), criteria);
        List<UserListVo> adminUserList = adminService.selectAllUsers(criteria, searchVo);

        Map<String, Object> adminUserMap = new HashMap<>();
        adminUserMap.put("pageVo", pageVo);
        adminUserMap.put("adminUserList", adminUserList);

        return adminUserMap;
    }

    @GetMapping("/emp/list/{page}")
    public Map<String, Object> selectAllEmp(@PathVariable("page")int page, SearchVo searchVo) {
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(adminService.getTotalEmp(searchVo), criteria);
        List<EmpDto> adminEmpList = adminService.selectAllEmp(criteria, searchVo);

        Map<String, Object> adminEmpMap = new HashMap<>();
        adminEmpMap.put("pageVo", pageVo);
        adminEmpMap.put("adminEmpList", adminEmpList);
        return adminEmpMap;
    }


    @GetMapping("/notice/list/{page}")
    public Map<String, Object> selectAllNotice(@PathVariable("page")int page, SearchVo searchVo){
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(adminService.getTotalNotice(searchVo), criteria);
        List<NoticeDto> adminNoticeList = adminService.selectAllNotice(criteria, searchVo);

        Map<String, Object> adminNoticeMap = new HashMap<>();
        adminNoticeMap.put("pageVo", pageVo);
        adminNoticeMap.put("adminNoticeList",adminNoticeList);
        return adminNoticeMap;
    }

    @GetMapping("/match/list/{page}")
    public Map<String,Object> selectAllMatchs(@PathVariable("page")int page, SearchVo searchVo){
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(5);
        PageVo pageVo = new PageVo(adminService.getTotalMatchs(searchVo),criteria);
        List<MatchListVo> matchList = adminService.selectAllMatchs(criteria,searchVo);

        Map<String, Object> matchListMap = new HashMap<>();
        matchListMap.put("pageVo",pageVo);
        matchListMap.put("matchList",matchList);
        return matchListMap;
    }
    @GetMapping("/event/list/{page}")
    public Map<String,Object> selectAllEvents(@PathVariable("page")int page, SearchVo searchVo){
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(9);
        PageVo pageVo = new PageVo(adminService.getTotalEvents(searchVo), criteria);
        List<AdminEventVo> eventList = adminService.selectAllEvents(criteria,searchVo);

        Map<String,Object> eventListMap = new HashMap<>();
        eventListMap.put("pageVo",pageVo);
        eventListMap.put("eventList",eventList);
        return eventListMap;
    }
    @GetMapping("/match/status")
    public String updateStatus(@RequestParam(name="matchNumber")Long matchNumber,@RequestParam(name="status") String status){
        adminService.updateStatus(matchNumber,status);
        log.info("============================matchNumber={}",matchNumber);
        log.info("============================status={}",status);
        return status;
    }
//    country 조회
    @GetMapping("/country")
    public List<CountryDto> selectCountry(@RequestParam(name="cityNumber")Long cityNumber){
        List<CountryDto> countryList = adminService.selectCountry(cityNumber);
        return countryList;
    }


    @GetMapping("/displayEventImg")
    public byte[] display(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(eventImgPath, fileName));
    }
    @GetMapping("/displayEventDetail")
    public byte[] displayDetail(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(eventDetailPath, fileName));
    }
    @GetMapping("/displayEmpImg")
    public byte[] displayEmpImg(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(empImgPath, fileName));
    }


    @GetMapping("/actImg")
    public List<ActVo> actImgList(Long empNumber){
        log.info("===============================여기야empNumber{}",empNumber);
        return adminService.selectEmpAct(empNumber);
    }
    @GetMapping("/displayActImg")
    public byte[] displayActImg(@RequestParam("fileName")String fileName) throws IOException {
        log.info("==================================여기야fileName{}",fileName);
        return FileCopyUtils.copyToByteArray(new File(actImgPath, fileName));
    }
    @GetMapping("/est/select")
    public List<EstContentDto> selectEst(Long matchNumber){
        List<EstContentDto> estList = adminService.selectEst(matchNumber);
        return estList;
    }

    @PostMapping("/est")
    public void insertEst(@RequestBody List<EstContentDto> list,@RequestParam(name = "matchNumber")Long matchNumber){
        log.info("======================바보야{}",matchNumber);
        adminService.deleteEst(matchNumber);
        
        list.forEach(ele -> System.out.println(ele));
        for(EstContentDto estContentDto : list){
            adminService.insertEst(estContentDto);
            log.info("=========================추가한거{}",estContentDto);
        }
    }
}
