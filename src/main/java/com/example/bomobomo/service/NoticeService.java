package com.example.bomobomo.service;



import com.example.bomobomo.domain.dto.NoticeDto;
import com.example.bomobomo.domain.vo.Criteria;
import com.example.bomobomo.domain.vo.SearchVo;
import com.example.bomobomo.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {


    private final NoticeMapper noticeMapper;




    /**
     * 공지사항 목록
     * @param criteria 페이징 처리를 위한 criteria 객체
     * @param searchVo 검색정보가 담긴 vo
     * @return
     */
    public List<NoticeDto> selectAll(Criteria criteria, SearchVo searchVo){
        return noticeMapper.selectAll(criteria, searchVo);
    }

    /**
     * 공지사항 상세
     * @param noticeNumber 공지사항 ID
     * @return
     */
    public NoticeDto selectOne(Long noticeNumber){

        if (noticeNumber == null) {
            throw new IllegalArgumentException("공지사항 번호 누락");
        }

        return Optional.ofNullable(noticeMapper.selectOne(noticeNumber))
                .orElseThrow(()->{throw new IllegalArgumentException("존재하지 않는 공지 게시 번호");});
    }



    /**
     * 공지사항 게시물 총 개수
     * @param searchVo 검색정보가 담긴 vo
     * @return
     */
    public int getTotal(SearchVo searchVo){
        return noticeMapper.getTotal(searchVo);
    }


    /**
     * 공지사항 조회수 증가
     * @param noticeNumber 공지사항 ID
     */
    public void updateCount(Long noticeNumber){


        if (noticeNumber == null) {
            throw new IllegalArgumentException("공지사항 번호 누락!");
        }

        noticeMapper.updateCount(noticeNumber);
    }



}
