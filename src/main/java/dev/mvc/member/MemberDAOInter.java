package dev.mvc.member;

import java.util.HashMap;

public interface MemberDAOInter {
  /**
   * 아이디 중복 검사
   * @param id
   * @return
   */
  public int checkID(String id);
  
  /**
   * 닉네임 중복 검사
   * @param nickname
   * @return
   */
  public int checkNICKNAME(String nickname);
  
  /**
   * 회원 가입
   * @param memberVO
   * @return
   */
  public int create(MemberVO memberVO);
  
  /**
   * memberno로 회원 정보 조회
   * @param memberno
   * @return
   */
  public MemberVO read(int memberno);
  
  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public MemberVO readByID(String id);

  /**
   * 수정 처리
   * @param memberVO
   * @return
   */
  public int update(MemberVO memberVO);
  
  /**
   * 회원 탈퇴 처리(아예 삭제가 아닌 등급을 탈퇴로 변경)
   * @param memberVO
   * @return
   */
  public int withdraw(MemberVO memberVO);
  
  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * 현재 패스워드 검사
   * @param map
   * @return 1: 일치, 0: 불일치
   */
  public int passwd_check(HashMap<String, Object> map);
}