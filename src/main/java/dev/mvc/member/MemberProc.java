package dev.mvc.member;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;

@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {
  @Autowired
  private MemberDAOInter memberDAO;
  
  @Autowired
  Security security;
  
  public MemberProc(){
    System.out.println("-> MemberProc 생성됨.");
  }
  
  /**
   * 아이디(이메일) 중복 확인
   */
  @Override
  public int checkID(String id) {
    int cnt = this.memberDAO.checkID(id);
    return cnt;
  }

  /**
   * 닉네임 중복 확인
   */
  @Override
  public int checkNICKNAME(String nickname) {
    int cnt = this.memberDAO.checkNICKNAME(nickname);
    return cnt;
  }

  /**
   * 비밀번호 암호화 후 회원가입 처리
   */
  @Override
  public int create(MemberVO memberVO) {
    // 비밀번호 암호화
    String passwd = memberVO.getPasswd();
    String passwd_encoded = this.security.aesEncode(passwd);
    memberVO.setPasswd(passwd_encoded);
    
    int cnt = this.memberDAO.create(memberVO);
    return cnt;
  }

  /**
   * memberno로 회원 정보 조회
   */
  @Override
  public MemberVO read(int memberno) {
    MemberVO memberVO = this.memberDAO.read(memberno);
    return memberVO;
  }
  
  /**
   * id로 회원 정보 조회
   */
  @Override
  public MemberVO readByID(String id) {
    MemberVO memberVO = this.memberDAO.readByID(id);
    return memberVO;
  }

  /**
   * 회원인지 검사
   */
  @Override
  public boolean isMember(HttpSession session) {
    boolean examine = false;
    String grade = (String)session.getAttribute("grade");
    
    if(grade != null) {
      if(grade.equals("member")) {
        examine = true;
      }
    }
    
    return examine;
  }

  /**
   * 회원 정보 수정(아이디, 비번, 닉, 이름, 번호, 우편번호, 상세 주소)
   */
  @Override
  public int update(MemberVO memberVO) {
    int cnt = this.memberDAO.update(memberVO);
    return cnt;
  }

  /**
   * 회원 탈퇴 처리(grade 값을 탈퇴로 지정한 99로 변경)
   */
  @Override
  public int withdraw(MemberVO memberVO) {
    int cnt = this.memberDAO.withdraw(memberVO);
    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    String passwd = (String)map.get("passwd");
    passwd = this.security.aesEncode(passwd);
    map.put("passwd", passwd);
    
    int cnt = this.memberDAO.login(map);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    String passwd = (String)map.get("passwd");
    passwd = this.security.aesEncode(passwd);
    map.put("passwd", passwd);
    
    int cnt = this.memberDAO.passwd_check(map);
    return cnt;
  }
  
 
}