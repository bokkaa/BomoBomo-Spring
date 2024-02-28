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
- 댓글 기
#### 에러 페이지 <a href="https://github.com/bokkaa/SpringBoot-BomoBomo/wiki/%EC%97%90%EB%9F%AC-%ED%8E%98%EC%9D%B4%EC%A7%80" >상세보기 - WIKI 이동</a>
- 에러 페이지


<hr>

## 📌코드 수정 

<details><summary>1. 무한 증식 DTO</summary>

<img width="467" alt="제목 없음" src="https://github.com/bokkaa/JPA-DW/assets/77730779/8ebbaafe-7333-4178-8bde-49104b332e1c">

- 조회 정보 로직을 주로 담당하는 관리자 페이지에서 조회용 DTO를 마구잡이로 만들다보니 .java 파일 자체가 너무 많아졌다.
- 그래서 이걸 어떻게 하면 가독성도 좋고 유지보수를 쉽게 할 수 있을까 해서 생각해낸 것이 내부 클래스의 활용이었다.
- 내부 클래스를 활용하니 관리자 페이지에서 상품 관련 DTO를 약 12개에서 4개로 줄일 수 있었다.


<img width="352" alt="제목 없음1" src="https://github.com/bokkaa/JPA-DW/assets/77730779/43337bab-5c48-4db5-aab0-2af78ef683af">

<details><summary>Goods/AdminGoods.java </summary>
 
```java
package com.example.dw.domain.dto.admin.goods;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminGoods extends AdminGoodsStan {


    private Long goodsMainImgId;
    private String goodsMainImgPath;
    private String goodsMainImgUuid;
    private String goodsMainImgName;

    private Long goodsDetailImgId;
    private String goodsDetailImgPath;
    private String goodsDetailImgUuid;
    private String goodsDetailImgName;


    @QueryProjection
    public AdminGoods(Long goodsId, String goodsName, String goodsCategory, Integer goodsQuantity, Integer goodsPrice, Integer goodsSaleCount, String goodsDetailContent, String goodsMate, String goodsCertify, LocalDateTime goodsRd, LocalDateTime goodsMd, Long goodsMainImgId, String goodsMainImgPath, String goodsMainImgUuid, String goodsMainImgName, Long goodsDetailImgId, String goodsDetailImgPath, String goodsDetailImgUuid, String goodsDetailImgName) {
        super(goodsId, goodsName, goodsCategory, goodsQuantity, goodsPrice, goodsSaleCount, goodsDetailContent, goodsMate, goodsCertify, goodsRd, goodsMd);
        this.goodsMainImgId = goodsMainImgId;
        this.goodsMainImgPath = goodsMainImgPath;
        this.goodsMainImgUuid = goodsMainImgUuid;
        this.goodsMainImgName = goodsMainImgName;
        this.goodsDetailImgId = goodsDetailImgId;
        this.goodsDetailImgPath = goodsDetailImgPath;
        this.goodsDetailImgUuid = goodsDetailImgUuid;
        this.goodsDetailImgName = goodsDetailImgName;
    }




    //제품 메인 사진
    @Data
    @NoArgsConstructor
    public static class AdminGoodsMainImg {

        private Long goodsMainImgId;
        private String goodsMainImgPath;
        private String goodsMainImgUuid;
        private String goodsMainImgName;


        public AdminGoodsMainImg(Long goodsMainImgId, String goodsMainImgPath, String goodsMainImgUuid, String goodsMainImgName) {
            this.goodsMainImgId = goodsMainImgId;
            this.goodsMainImgPath = goodsMainImgPath;
            this.goodsMainImgUuid = goodsMainImgUuid;
            this.goodsMainImgName = goodsMainImgName;
        }
    }

    //제품 상세 사진
    @Data
    public static class AdminGoodsDetailImg {
        private Long goodsDetailImgId;
        private String goodsDetailImgPath;
        private String goodsDetailImgUuid;
        private String goodsDetailImgName;


        public AdminGoodsDetailImg(Long goodsDetailImgId, String goodsDetailImgPath, String goodsDetailImgUuid, String goodsDetailImgName) {
            this.goodsDetailImgId = goodsDetailImgId;
            this.goodsDetailImgPath = goodsDetailImgPath;
            this.goodsDetailImgUuid = goodsDetailImgUuid;
            this.goodsDetailImgName = goodsDetailImgName;
        }
    }


    //관리자 페이지 상품 리스트
    @Data
    public static class AdminGoodsList {

        private Long goodsId;
        private String goodsCategory;
        private String goodsName;
        private Integer goodsQuantity;
        private Integer goodsSaleCount;
        private Integer goodsPrice;

        private LocalDateTime goodsRd;
        private LocalDateTime goodsMd;

        @QueryProjection
        public AdminGoodsList(Long goodsId, String goodsCategory, String goodsName, Integer goodsQuantity, Integer goodsSaleCount, Integer goodsPrice, LocalDateTime goodsRd, LocalDateTime goodsMd) {
            this.goodsId = goodsId;
            this.goodsCategory = goodsCategory;
            this.goodsName = goodsName;
            this.goodsQuantity = goodsQuantity;
            this.goodsSaleCount = goodsSaleCount;
            this.goodsPrice = goodsPrice;
            this.goodsRd = goodsRd;
            this.goodsMd = goodsMd;
        }
    }


    //관리자 페이지 상품 상세보기
    @Data
    public static class AdminGoodsDetail extends AdminGoodsStan {

        private Double ratingAvg;

        private String goodsMainImgPath;
        private String goodsMainImgUuid;
        private String goodsMainImgName;
        private List<AdminGoods.AdminGoodsDetailImg> adminGoodsDetailImg;

        public AdminGoodsDetail(Long goodsId, String goodsName, String goodsCategory, Integer goodsQuantity, Integer goodsPrice, Integer goodsSaleCount, String goodsDetailContent, String goodsMate, String goodsCertify, LocalDateTime goodsRd, LocalDateTime goodsMd, Double ratingAvg, String goodsMainImgPath, String goodsMainImgUuid, String goodsMainImgName) {
            super(goodsId, goodsName, goodsCategory, goodsQuantity, goodsPrice, goodsSaleCount, goodsDetailContent, goodsMate, goodsCertify, goodsRd, goodsMd);
            this.ratingAvg = ratingAvg;
            this.goodsMainImgPath = goodsMainImgPath;
            this.goodsMainImgUuid = goodsMainImgUuid;
            this.goodsMainImgName = goodsMainImgName;
        }

        public AdminGoodsDetail setGoodsDetailImg(List<AdminGoods.AdminGoodsDetailImg> adminGoodsDetailImg) {
            this.adminGoodsDetailImg = adminGoodsDetailImg;
            return this;
        }

    }


}

```
  
</details>
</details>

<br>

<details><summary>2. 페이징 처리 기준</summary>

```java

@Override
    public Page<AdminOrderListResultDto> orderList(Pageable pageable, AdminSearchOrderForm adminSearchOrderForm) {


        SearchForm searchForm = new SearchForm(adminSearchOrderForm.getCate(), adminSearchOrderForm.getKeyword());

        List<AdminOrderListDto> list = jpaQueryFactory.select(new QAdminOrderListDto(
                orderList.id,
                orders.id,
                orders.users.id,
                orders.users.userAccount,
                orders.orderUserAddressNumber,
                orders.orderAddressNormal,
                orders.orderAddressDetail,
                orders.orderUserEmail,
                orders.orderUserName,
                orders.orderUserPhoneNumber,
                orders.orderRegisterDate,
                goods.id,
                goods.goodsName,
                orderItem.orderPrice,
                orderItem.orderQuantity,
                orderList.orderDate
        ))
                .from(orderList)
                .leftJoin(orderList.orders, orders)
                .leftJoin(orders.users, users)
                .leftJoin(orders.orderItems, orderItem)
                .leftJoin(orderItem.goods, goods)
                .where(
                        cateKeywordEq(searchForm),
                        dateEq(adminSearchOrderForm.getPrev(), adminSearchOrderForm.getNext())

                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long getTotal = jpaQueryFactory.select(
                orderList.count()
        )
                .from(orderList)
                .where(
                        cateKeywordEq(searchForm),
                        dateEq(adminSearchOrderForm.getPrev(), adminSearchOrderForm.getNext())

                )
                .fetchOne();


        return new PageImpl<>(convertOrderList(list),pageable, getTotal);


    }



    // AdminOrderListDto 목록을 AdminOrderListResultDto로 변환하는 메서드
    private List<AdminOrderListResultDto> convertOrderList(List<AdminOrderListDto> orderList) {

       ....
    }


```

- 한 페이지당 보이는 게시글을 15개로 고정해놓았다. 하지만 위 방식으로 쿼리를 돌려서 뽑아보니 <br>
  주문 목록 id 기준으로 페이징처리가 되는 것이 아니라 주문 내역에 들어가 있는 상품들 수로 페이징 기준이 잡혀버렸다.
- 따라서 이를 해결하기 위해 주문ID 조회를 루트로 하는 쿼리를 따로 뽑았고 그 결과값을 본 쿼리의 where in절에 조건으로 넣었다.

<details><summary>수정 코드
</summary>

```java

 @Override
    public Page<AdminOrderList.AdminOrdersListDto.AdminOrderListResultDto> orderLists(
            Pageable pageable, AdminSearchOrderForm adminSearchOrderForm) {

        SearchForm searchForm = new SearchForm(adminSearchOrderForm.getCate(), adminSearchOrderForm.getKeyword());

        //orderListId로 페이징처리하기 위한 루트쿼리
        List<Long> orderListIds = jpaQueryFactory
                .selectDistinct(orderList.id)
                .from(orderList)
                .where(
                        cateKeywordEq(searchForm),
                        dateEq(adminSearchOrderForm.getPrev(), adminSearchOrderForm.getNext())
                )

                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        System.out.println(orderListIds);

        List<AdminOrderList.AdminOrdersListDto> list = jpaQueryFactory.select(new QAdminOrderList_AdminOrdersListDto(
                orderList.id,
                orders.id,
                users.id,
                users.userAccount,
                orders.orderUserAddressNumber,
                orders.orderAddressNormal,
                orders.orderAddressDetails,
                orders.orderUserEmail,
                orders.orderUserName,
                orders.orderUserPhoneNumber,
                orders.orderRegisterDate,
                goods.id,
                goods.goodsName,
                orderItem.orderPrice,
                orderItem.orderQuantity,
                orders.orderRegisterDate
        ))
                .from(orderList)
                .leftJoin(orderList.orders, orders)
                .leftJoin(orders.users, users)
                .leftJoin(orders.orderItems, orderItem)
                .leftJoin(orderItem.goods, goods)
                .where(orderList.id.in(orderListIds))
                .fetch();

        Long getTotal = jpaQueryFactory.select(
                orderList.count()
        )
                .from(orderList)
                .where(
                        cateKeywordEq(searchForm),
                        dateEq(adminSearchOrderForm.getPrev(), adminSearchOrderForm.getNext())
                )
                .fetchOne();

        return new PageImpl<>(convertOrderList(list), pageable, getTotal);
    }

 private List<AdminOrderList.AdminOrdersListDto.AdminOrderListResultDto> convertOrderList(
            List<AdminOrderList.AdminOrdersListDto> orderList) {

...
}

```



</details>

</details>

<hr>

## 📌느낀점

정식으로 JPA 수업도 들었던 것이 아니고 인프런에서 JPA 관련 강의 두 개를 들으며 시작했던 프로젝트였다. 그 상태로 처음 프로젝트를 시작했을 때, 빌더 패턴에서 막히기 시작했다. 그래서 빌더 패턴은 무엇이고 빌더 패턴은 왜 사용하는지 등을 이런 저런 자료를 찾아보며 프로젝트에 적용하기 시작했다.  

프로젝트를 진행하며 JPA에서 경계해야하는 N+1 문제가 일어난 적이 한 번 있었다. 보통 N+1이 터지는 이유는 특히 ForEach(빠른 for문) 안에서 쿼리가 개수만큼 더 돌아가는 경우였고, 이를 해결하기 위해 정보를 하나의 쿼리로 데이터를 조회하거나 여러 쿼리로 조회하여 스트림을 통해 매핑을 하였다. 

그런데 한 가지 문제가 있었는데 엔티티 관계가 OneToOne인 경우 자동적으로 유니크 키가 설정되어 삭제 후 수정 로직이 들어가는 기능을 구현했을 시 에러가 발생했다. 때문에 어쩔 수 없이 OneToOne 관계지만 OneToMany , ManyToOne으로 관계를 설정해야만 했다.

까다롭다면 까다로운 이 ORM 기술을 한 번 사용해보니 장점은 명확했다. 일단 JAVA가 일종의 주인이 되어 JAVA 코드로 데이터베이스를 관리할 수 있다는 점이 가장 큰 장점이 아닐까 싶다. 

