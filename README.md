# SpringBoot-MyBatis-Project-BomoBomo
스프링부트-마이바티스 프로젝트
<br>


## 🖥️ 프로젝트 소개
베이비시터 매칭 플랫폼, 아이들을 위한 이벤트 서비스 제공 플랫폼
<br>


## 🕰️ 개발 기간
* 2023.9 - 2023.10

### 🧑‍🤝‍🧑 맴버구성
 - 팀장: 김성찬 : 관리자 페이지
 - 부팀장: 복영헌 : 메인페이지, 커뮤니티, 에러페이지 
 - 팀원1: 임형준 : 마이페이지
 - 팀원2: 공병문 : 회사소개, 이벤트 페이지
 - 팀원3: 이중근 : 로그인, 회원가입, 계정찾기, 씨터찾기


### ⚙️ 개발 환경
- **IDE** : IntelliJ IDEA
- **Framework** : Springboot(3.x), MyBatis
- **Database** : Oracle DB(11xe)

### 📌포트폴리오 

[BOMOBOMO 포트폴리오.pdf](https://github.com/bokkaa/SpringBoot-BomoBomo/files/14415618/BOMOBOMO.pdf)

## 📌 내가 맡은 기능
#### 메인 페이지 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 베스트 돌봄 후기 Top6
- 베스트 이벤트 후기 Top6

#### FAQ / 공지사항 게시판 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD-%EA%B2%8C%EC%8B%9C%ED%8C%90" >상세보기 - WIKI 이동</a>
- FAQ 목록
- 공지사항 목록
- 공지사항 상세보기

#### 돌봄 후기 게시판 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EB%8F%8C%EB%B4%84-%ED%9B%84%EA%B8%B0-%EA%B2%8C%EC%8B%9C%ED%8C%90" >상세보기 - WIKI 이동</a>
- 돌봄 후기 목록
- 돌봄 후기 상세
- 돌봄 후기 수정 삭제
- 댓글 기능

#### 이벤트 후기 게시판 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EC%9D%B4%EB%B2%A4%ED%8A%B8-%ED%9B%84%EA%B8%B0-%EA%B2%8C%EC%8B%9C%ED%8C%90" >상세보기 - WIKI 이동</a>
- 이벤트 후기 목록
- 이벤트 후기 상세
- 이벤트 후기 수정 삭제
- 댓글 기능
#### 에러 페이지 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EC%97%90%EB%9F%AC-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 에러 페이지


<hr>

## 📌코드 수정 

<details><summary>조회수( 쿠키값 이용 ) - 모듈화</summary>


<img width="811" alt="제목 없음" src="https://github.com/bokkaa/SpringBoot-BomoBomo/assets/77730779/5398ee8d-b7ef-4207-b403-d47504ea8fd9">


```java

//돌봄후기 상세보기
    @GetMapping("/reviewDetail")
    public String showServiceReviewDetailPage(@RequestParam("sitterBoardNumber") Long sitterBoardNumber,
                                               Model model, HttpServletRequest req, HttpServletResponse resp){



        SitterBoardVo sitterBoardVo = reviewService.selectOne(sitterBoardNumber);
        List<SitterBoardVo> sitterBoardVoList = reviewService.findReviewDetail(sitterBoardVo.getEmpNumber());
        double getAvg = reviewService.getAvgRating(sitterBoardVo.getEmpNumber());


        //조회수 쿠키 이용
        Cookie[] cookies = req.getCookies();
        boolean updateCount = true;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("reviewDetail_count_cookie".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();

                    String[] values = cookieValue.split("/");
                    log.info("%%%%%%%%%% {}", Arrays.toString(values));

                    List<Long> valueList = Arrays.stream(values).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

                    if(valueList.contains(sitterBoardNumber)){
                        updateCount = false;
                        break;
                    }

                    valueList.add(sitterBoardNumber);
                    log.info("##############3 {}", valueList);

                    String result = String.join("/", valueList.stream().map(ele -> ele+"").collect(Collectors.toList()));

                    log.info("**************************** {}", result);
                    cookie.setValue(result);
                    resp.addCookie(cookie);
                    updateCount = false;
                    reviewService.updateCount(sitterBoardNumber);

                }

            }
        }

        if (updateCount) {
            Cookie newCookie = new Cookie("reviewDetail_count_cookie", req.getParameter("sitterBoardNumber"));
            newCookie.setMaxAge(24 * 60 * 60);
            resp.addCookie(newCookie);

            reviewService.updateCount(sitterBoardNumber);
        }


        model.addAttribute("sitterReviewList", sitterBoardVoList);
        model.addAttribute("serviceReviewDetail", sitterBoardVo);
        model.addAttribute("getAvg", (Math.round(getAvg*100) / 100.0));

        log.info(String.valueOf(getAvg));
        log.info(sitterBoardVo.toString());
        log.info(sitterBoardVoList.toString());
        return "board/serviceReviewDetail";
    }

```
- 기존에는 모듈화 없이 컨트롤러에 직접 쿠키값을 이용하여 게시글 상세 페이지에 진입했을 시 24시간마다 한 번 조회수 1이 올라가도록 설정해놓았다.
- 작동 원리는 게시물 상세 페이지에 진입할 때마다 그 게시물의 ID값을 쿠키값으로 이어붙이는 형식으로 해당 쿠키값에 게시물ID가 존재한다면 조회수가 올라가지 않는다.
- 문제는 게시물 상세 페이지로 진입하는 매핑이 3-4개는 되었고 이 코드를 그대로 각각의 매핑 컨트롤러에 반복적으로 사용하기에는 가독성은 물론 유지보수에도 안 좋을듯하여 모듈화를 시도해보았다.


<details><summary>수정코드</summary>

```java

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

.
.
.

// 결과적으로 각각의 매핑 컨트롤러에는 
// 다음과 같은 코드만 추가하면 같은 기능을 구현할 수 있게 되었다.

       //조회수 증가
        CookieHandler cookieHandler = new CookieHandler(noticeService, reviewService);
        cookieHandler.handleCookies(req, resp, eventBoardNumber, "eventReviewDetail_count_cookie");

```

</details> 

</details>

<hr>

## 📌느낀점

정식으로 JPA 수업도 들었던 것이 아니고 인프런에서 JPA 관련 강의 두 개를 들으며 시작했던 프로젝트였다. 그 상태로 처음 프로젝트를 시작했을 때, 빌더 패턴에서 막히기 시작했다. 그래서 빌더 패턴은 무엇이고 빌더 패턴은 왜 사용하는지 등을 이런 저런 자료를 찾아보며 프로젝트에 적용하기 시작했다.  

프로젝트를 진행하며 JPA에서 경계해야하는 N+1 문제가 일어난 적이 한 번 있었다. 보통 N+1이 터지는 이유는 특히 ForEach(빠른 for문) 안에서 쿼리가 개수만큼 더 돌아가는 경우였고, 이를 해결하기 위해 정보를 하나의 쿼리로 데이터를 조회하거나 여러 쿼리로 조회하여 스트림을 통해 매핑을 하였다. 

그런데 한 가지 문제가 있었는데 엔티티 관계가 OneToOne인 경우 자동적으로 유니크 키가 설정되어 삭제 후 수정 로직이 들어가는 기능을 구현했을 시 에러가 발생했다. 때문에 어쩔 수 없이 OneToOne 관계지만 OneToMany , ManyToOne으로 관계를 설정해야만 했다.

까다롭다면 까다로운 이 ORM 기술을 한 번 사용해보니 장점은 명확했다. 일단 JAVA가 일종의 주인이 되어 JAVA 코드로 데이터베이스를 관리할 수 있다는 점이 가장 큰 장점이 아닐까 싶다. 

