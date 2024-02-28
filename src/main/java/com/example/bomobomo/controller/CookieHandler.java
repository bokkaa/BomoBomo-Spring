package com.example.bomobomo.controller;

import com.example.bomobomo.service.NoticeService;
import com.example.bomobomo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
//조회수 쿠키 메소드 모듈화
public class CookieHandler {

    private final NoticeService noticeService;
    private final ReviewService reviewService;


    /**
     * 쿠키 처리
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @param number ID값
     * @param name 쿠키 이름값
     */
    public void handleCookies(HttpServletRequest req, HttpServletResponse resp, Long number, String name) {
        // 현재 요청에서 쿠키를 가져옵니다.
        Cookie[] cookies = req.getCookies();
        boolean updateCount = true;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 쿠키의 이름이 주어진 이름과 일치하는지 확인합니다.
                if (name.equals(cookie.getName())) {
                    // 이미 존재하는 쿠키를 처리합니다.
                    handleExistingCookie(cookie, number, req, resp, name);
                    updateCount = false;
                    break;
                }
            }
        }
        if (updateCount) {
            // 쿠키가 존재하지 않을 경우 새로운 쿠키를 생성하고 추가합니다.
            createAndAddNewCookie(req, resp, number, name);
        }
    }

    

    /**
     * 이미 존재하는 쿠키를 처리하는 메서드
     * @param cookie cookie 객체
     * @param number ID값
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @param name 쿠키 이름값
     */
    private  void handleExistingCookie(Cookie cookie, Long number, HttpServletRequest req, HttpServletResponse resp, String name) {
        String cookieValue = cookie.getValue();
        String[] values = cookieValue.split("/");
        log.info("%%%%%%%%%% {}", Arrays.toString(values));

        List<Long> valueList = Arrays.stream(values).map(Long::parseLong).collect(Collectors.toList());

        // 번호가 이미 존재하면 메서드를 즉시 종료합니다.
        if (valueList.contains(number)) {
            return;
        }

        // 번호가 존재하지 않으면 요청한 사이트의 게시물 번호를 리스트에 추가합니다.
        valueList.add(number);
        log.info("############### {}", valueList);

        // 리스트에 추가한 값들을 하나의 문자열로 결합합니다.
        String result = String.join("/", valueList.stream().map(Object::toString).collect(Collectors.toList()));
        log.info("**************************** {}", result);
        cookie.setValue(result);
        resp.addCookie(cookie);

        // 쿠키 이름(name)에 따라 이벤트 리뷰 게시판, 돌봄 서비스 리뷰 게시판 또는 공지사항 게시판을 업데이트합니다.
        if ("eventReviewDetail_count_cookie".equals(name)) {
            reviewService.updateEventReviewCount(number);
        } else if ("reviewDetail_count_cookie".equals(name)) {
            reviewService.updateCount(number);
        } else if ("notice_count_cookie".equals(name)) {
            noticeService.updateCount(number);
        }
    }


    /**
     * 새로운 쿠키를 생성하고 추가하는 메서드
     * @param req HttpServletRequest 객체
     * @param resp HttpServletResponse 객체
     * @param number ID값
     * @param name 쿠키 이름값
     */
    private  void createAndAddNewCookie(HttpServletRequest req, HttpServletResponse resp, Long number, String name) {
        Cookie newCookie = new Cookie(name, String.valueOf(number));
        newCookie.setMaxAge(24 * 60 * 60); // 쿠키의 최대 수명을 설정합니다 (24시간)
        resp.addCookie(newCookie);

        // 쿠키 이름(name)에 따라 이벤트 리뷰 게시판, 돌봄 서비스 리뷰 게시판 또는 공지사항 게시판을 업데이트합니다.
        if ("eventReviewDetail_count_cookie".equals(name)) {
            reviewService.updateEventReviewCount(number);
        } else if ("reviewDetail_count_cookie".equals(name)) {
            reviewService.updateCount(number);
        } else if ("notice_count_cookie".equals(name)) {
            noticeService.updateCount(number);
        }
    }
}