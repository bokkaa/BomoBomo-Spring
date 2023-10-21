package com.example.bomobomo.mapper;

import com.example.bomobomo.domain.dto.*;
import com.example.bomobomo.domain.vo.*;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.List;

@Mapper
public interface AdminMapper {

//    관리자 로그인
    public AdminDto login(@Param("adminId")String adminId, @Param("adminPassword")String adminPassword);
//    주간 가입수
    public List<WeeklyRegisterVo> weeklyRegister();
//    최신 매칭 5건조회
    public List<MatchListVo> selectNewMatch();

//    유저 리스트
    public List<UserListVo> selectAllUsers(@Param("criteria") Criteria criteria, @Param("searchVo")SearchVo searchVo);
//    검색별 유저 수
    public int getTotalUsers(SearchVo searchVo);
//    회원 상세정보
    public UserDetailVo selectUserDetail(Long userNumber);

//    직원 리스트
    public List<EmpDto> selectAllEmp(@Param("criteria")Criteria criteria, @Param("searchVo")SearchVo searchVo);
//    검색별 직원 수
    public int getTotalEmp(SearchVo searchVo);
//    직원 활동 조회
    public List<ActDto> selectAct();
//    직원 등록
    public void empRegist(EmpDto empDto);
//    직원 수정
    public void empUpdate(EmpDto empDto);
//    직원 이미지 등록
    public void empImgRegist(EmpImgDto empImgDto);
//    직원 이미지 수정
    public void empImgUpdate(EmpImgDto empImgDto);
//    직원 활동 등록
    public void empActRegist(EmpActItemDto empActItemDto);
//    직원 활동 삭제
    public void empActDelete(Long empNumber);
//    직원 상세
    public EmpVo selectEmpDetail(Long empNumber);
//    직원 활동 이미지
    public List<ActVo> selectEmpAct(Long empNumber);
//    직원 활동 조회
    public List<EmpActItemDto> selectEmpActItem(Long empNumber);
//    직원 삭제
    public void empDelete(Long empNumber);
//    직원 평점
    public double selectEmpAvg(Long empNumber);

//    공지사항 리스트
    public List<NoticeDto> selectAllNotice(@Param("criteria")Criteria criteria, @Param("searchVo")SearchVo searchVo);
//    검색별 공지 수
    public int getTotalNotice(SearchVo searchVo);
//    공지사항 상세정보
    public NoticeDto selectNoticeDetail(Long noticeNumber);
//    공지 등록
    public void noticeRegist(NoticeDto noticeDto);
//    공지 수정
    public void noticeUpdate(NoticeDto noticeDto);
//    공지 삭제
    public void noticeDelete(Long noticeNumber);

//    매칭 리스트
    public List<MatchListVo> selectAllMatchs(@Param("criteria")Criteria criteria, @Param("searchVo")SearchVo searchVo);
//    매칭 관리
    public MatchListVo selectMatchDetail(Long matchNumber);
//    검색별 매칭수
    public int getTotalMatchs(SearchVo searchVo);
//    매칭 status 업데이트
    public void updateStatus(@Param("matchNumber")Long matchNumber,@Param("status")String status);

//    이벤트 리스트
    public List<AdminEventVo> selectAllEvents(@Param("criteria")Criteria criteria, @Param("searchVo")SearchVo searchVo);
//    검색별 이벤트수
    public int getTotalEvents(SearchVo searchVo);
//    이벤트 조회
    public EventVo selectEventDetail(Long eventNumber);
//    이벤트 등록
    public void eventRegist(EventDto eventDto);
//    이벤트 이미지 등록
    public void eventImgRegist(EventImgDto eventImgDto);
//    이벤트 상세정보 등록
    public void eventDetailRegist(EventDetailDto eventDetailDto);
//    이벤트 삭제
    public void eventDelete(Long eventNumber);
//    이벤트 업데이트
    public void updateEvent(EventVo eventVo);
//    이벤트 이미지 업데이트
    public void updateEventImg(EventImgDto eventImgDto);
//    이벤트 상세정보 업데이트
    public void updateEventDetail(EventDetailDto eventDetailDto);
//    이벤트 평균 별점 조회
    public double selectEventAvg(Long eventNumber);

//    city 전체조회
    public List<CityDto> selectAllCity();
//    country 전체조회
    public List<CountryDto> selectAllCountry();
//    country 조회
    public List<CountryDto> selectCountry(Long cityNumber);
//    직원 region 조회
    public RegionVo selectEmpRegion(Long empNumber);
//    제출 신청서 조회
    public SubmitOrderDto selectSubmitOrder(Long matchNumber);
//    매칭관리 유저주소조회
    public UserAddressVo selectUserAddress(Long matchNumber);
//    견적서 내용 조회
    public List<EstContentDto> selectEst(Long matchNumber);
//    견적서 내용추가
    public void insertEst(EstContentDto estContentDto);
//    견적서 내용 삭제
    public void deleteEst(Long matchNumber);
//    시터 매출
    public Integer sitterTotal();
//    이벤트 매출
    public Integer eventTotal();
}
