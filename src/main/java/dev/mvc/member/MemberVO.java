package dev.mvc.member;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MemberVO {
  /*
  memberno   NUMBER(7)      NOT NULL, -- 회원 번호, 레코드를 구분하는 컬럼, 기본키 
  id         VARCHAR(30)    NOT NULL UNIQUE, -- 이메일(아이디), 중복 안됨, 레코드를 구분 
  passwd     VARCHAR(200)   NOT NULL, -- 비밀번호, 영숫자 조합, 암호화
  name       VARCHAR(30)    NOT NULL, -- 성명, 한글 10자 저장 가능
  birth      VARCHAR(30)    NOT NULL, -- 생년월일 ex) 20241205
  nickname   VARCHAR(36)    NOT NULL,
  tel        VARCHAR(14)    NOT NULL, -- 전화번호
  gender     VARCHAR(6)     NOT NULL, -- 성별, 남성 or 여성
  zipcode    VARCHAR(5)     NOT NULL, -- 우편번호, ex) 03153
  address    VARCHAR(80)        NULL, -- 상세 주소
  replycnt   NUMBER(6)      DEFAULT 0 NOT NULL, -- 작성한 댓글 수 가입 시 기본적으로 0
  rdate      DATE           NOT NULL, -- 가입일    
  grade      NUMBER(2)      NOT NULL, -- 등급(1~10: 관리자, 11~20: 회원, 40~49: 정지 회원, 99: 탈퇴 회원)
   */
  
  /** 회원 번호 */
  private int memberno;
  /** 아이디(이메일) */
  private String id = "";
  /** 비밀번호 */
  private String passwd = "";
  /** 성명 */
  private String name = "";
  /** 생년월일 */
  private String birth = "";
  /** 닉네임 */
  private String nickname = "";
  /** 전화 번호 */
  private String tel = "";
  /** 성별 */
  private String gender = "";
  /** 우편 번호 */
  private String zipcode = "";
  /** 상세 주소 */
  private String address = "";
  /** 작성 댓글수 */
  private int replycnt = 0;
  /** 가입일 */
  private String mdate = "";
  /** 등급 */
  private int grade = 0;


  /** id 저장 여부 */
  private String id_save = "";
  /** passwd 저장 여부 */
  private String passwd_save = "";
  /** 이동할 주소 저장 */
  private String url_address = "";
}