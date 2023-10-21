package com.example.bomobomo.mapper;

import com.example.bomobomo.domain.dto.AddressDto;
import com.example.bomobomo.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Mapper
public interface UserMapper {

    //로그인
    public UserDto select(@Param("userId")String userId, @Param("userPassword")String userPassword);

    //회원가입 유저 정보
    public void insertUser(UserDto userDto);
    //회원가입 주소 정보
    public void insertAddr(AddressDto addressDto);

    //등록된 아이디인지 확인
    public int idCheck(String userId);
    //등록된 이름인지 확인
    public int nameCheck(String userName);


    //등록된 이름,이메일인지인지 확인
    public int emailCheck(@Param("userEmail")String userEmail, @Param("userName")String userName);
    //등록된 아이디, 이름, 이메일인지인지 확인
    public int pwEmailCheck(@Param("userEmail")String userEmail, @Param("userName")String userName, @Param("userId")String userId);

    public void insert(UserDto userDto);

    // 마이 페이지에서 회원정보 수정
    public void update(UserDto userDto);

    //
    public UserDto idFindSelect(@Param("userName")String userName, @Param("userEmail")String userEmail);

    public void rePw(@Param("rePassword") String rePassword, @Param("userId")String userId);
}
