package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.newscate.NewsCateProcInter;
import dev.mvc.newscate.NewsCateVOMenu;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  private NewsCateProcInter newsCateProc;
  
  public MemberCont() {
    System.out.println("MemberCont 생성됨");
  }
  
  /**
   * 아이디 중복 확인
   * @param id
   * @return
   */
  @GetMapping(value="/checkID") // http://localhost:9093/member/checkID?id=test
  @ResponseBody
  public String checkID(@RequestParam(name="id", defaultValue = "")String id) {
    System.out.println("-> id: " + id);
    int cnt = this.memberProc.checkID(id);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  
  /**
   * 닉네임 중복 확인
   * @param nickname
   * @return
   */
  @GetMapping(value="/checkNICKNAME") // http://localhost:9093/member/checkNICKNAME?nickname=test
  @ResponseBody
  public String checkNICKNAME(@RequestParam(name="nickname", defaultValue = "")String nickname) {
    System.out.println("-> nickname: " + nickname);
    int cnt = this.memberProc.checkNICKNAME(nickname);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  
  /**
   * 회원 가입 폼
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/create") // http://localhost:9093/member/create
  public String create_form(Model model, @ModelAttribute("memberVO") MemberVO memberVO) {
    
    //ArrayList<NewsCateVOMenu> menu = this.newsCateProc.menu();
    //model.addAttribute("menu", menu);
    
    return "/member/create";    // /template/member/create.html
  }
  
  /**
   * 회원 가입 처리
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/create")
  public String create_proc(Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());
    int checkNICKNAME_cnt = this.memberProc.checkNICKNAME(memberVO.getNickname());
    
    // id랑 닉네임 둘 다 중복 아닐 경우
    if(checkID_cnt == 0 && checkNICKNAME_cnt == 0) {
      memberVO.setGrade(11); // 11~20: 회원
      int cnt = this.memberProc.create(memberVO);
      
      if(cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("id", memberVO.getId());
        model.addAttribute("nickname", memberVO.getNickname());
      }
      else {
        model.addAttribute("code", "create_fail");
      }
      model.addAttribute("cnt", cnt);
    }
    else { // id나 닉네임 또는 둘 다 중복일 경우
      model.addAttribute("code", "duplicate_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "/member/msg";
  }
  
  /**
   * 회원 정보 조회 (내 정보 보기)
   * @param session
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value="/read")
  public String read(HttpSession session, Model model, int memberno) {
    // 회원은 회원 등급만 처리, 관리자: 1 ~ 10, 회원: 11 ~ 20
    String grade = (String)session.getAttribute("grade"); // 등급: admin, member
    
    // 회원: member && 11 ~ 20
    if (grade.equals("member") &&  memberno == (int)session.getAttribute("memberno")) {
      System.out.println("memberno: " + memberno);
      
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      
      return "member/read";  // templates/member/read.html
      
    } /*else if (grade.equals("admin")) {
      System.out.println("-> read memberno: " + memberno);
      
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      
      return "member/read";  // templates/member/read.html
    }*/ else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
    
  }
  
  /**
   * id 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_id_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_id(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_id_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("id", memberVO.getId());
      } else {
        model.addAttribute("code", "update_id_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // /templates/member/msg.html
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * 현재 passwd 확인
   * @param session
   * @param current_passwd
   * @return 1: 일치, 0: 불일치
   */
  @PostMapping(value="/read")
  @ResponseBody
  public String passwd_check(HttpSession session, @RequestBody String json_src) {
    System.out.println("json_src: " + json_src); // json_src: {"current_passwd":"1234"}
    
    JSONObject src = new JSONObject(json_src); // String -> JSON
    
    String current_passwd = (String)src.get("current_passwd"); // 값 가져오기
    System.out.println("-> current_passwd: " + current_passwd);
    
    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    map.put("passwd", current_passwd);
    //map.put("passwd", this.security.aesEncode(current_passwd));
    
    int cnt = this.memberProc.passwd_check(map); // 현재 로그인한 사용자의 패스워드 확인
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);  // 1: 패스워드 일치, 0: 불일치
    System.out.println(json.toString());
    
    return json.toString();   
  }
  
  /**
   * passwd 수정 처리
   * @param session
   * @param model
   * @param current_passwd 현재 패스워드
   * @param passwd 새로운 패스워드
   * @return
   */
  @PostMapping(value="/read")
  public String update_passwd_proc(HttpSession session, 
                                                    Model model, 
                                                    @RequestParam(value="current_passwd", defaultValue = "") String current_passwd, 
                                                    @RequestParam(value="passwd", defaultValue = "") String passwd) {
    if(this.memberProc.isMember(session)) {
      int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("memberno", memberno);
      map.put("passwd", current_passwd);
      //map.put("passwd", this.security.aesEncode(current_passwd));
      
      int cnt = this.memberProc.passwd_check(map);
      
      if (cnt == 0) { // 패스워드 불일치
        model.addAttribute("code", "passwd_not_equal");
        model.addAttribute("cnt", 0);
        
      } else { // 패스워드 일치
        map = new HashMap<String, Object>();
        map.put("memberno", memberno);
        map.put("passwd", passwd);
        //map.put("passwd", this.security.aesEncode(passwd)); // 새로운 패스워드
        
        int passwd_change_cnt = this.memberProc.update_passwd(map);
        
        if (passwd_change_cnt == 1) {
          model.addAttribute("code", "passwd_change_success");
          model.addAttribute("cnt", 1);
        } else {
          model.addAttribute("code", "passwd_change_fail");
          model.addAttribute("cnt", 0);
        }
      }

      return "redirect:/member/read";   // 수정 처리 하고 새로고침
    } else {
      return "redirect:/member/login_cookie_need";
    }
  }
  
  /**
   * name 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_name_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_name(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_name_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("name", memberVO.getName());
      } else {
        model.addAttribute("code", "update_name_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // // 수정 처리 하고 새로고침
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * nickname 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_nickname_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_nickname(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_nickname_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("nickname", memberVO.getNickname());
      } else {
        model.addAttribute("code", "update_nickname_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // // 수정 처리 하고 새로고침
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * tel 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_tel_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_tel(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_tel_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("tel", memberVO.getTel());
      } else {
        model.addAttribute("code", "update_tel_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // // 수정 처리 하고 새로고침
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * zipcode 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_zipcode_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_zipcode(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_zipcode_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("zipcode", memberVO.getZipcode());
      } else {
        model.addAttribute("code", "update_zipcode_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // // 수정 처리 하고 새로고침
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * address 수정 처리
   * @param session
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/read")
  public String update_address_proc(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
    String grade = (String)session.getAttribute("grade");
    
    // 회원 본인이거나 관리자인 경우에 처리
    if (grade.equals("member")) {
      int cnt = this.memberProc.update_address(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "update_address_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("address", memberVO.getAddress());
      } else {
        model.addAttribute("code", "update_address_fail");
      }
      
      model.addAttribute("cnt", cnt);
      
      return "redirect:/member/read"; // // 수정 처리 하고 새로고침
      } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
  
  /**
   * 회원 탈퇴 처리
   * @param model
   * @param memberno 삭제할 회원 번호
   * @return
   */
  @PostMapping(value="/read")
  public String withdraw_process(HttpSession session, Model model, @ModelAttribute("memberVO")MemberVO memberVO) {
 // 회원 본인이거나 관리자인 경우에 처리
    String grade = (String)session.getAttribute("grade");
    if (grade.equals("member")) {
      int cnt = this.memberProc.withdraw(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "withdraw_success");
        model.addAttribute("memberno", memberVO.getMemberno());
        model.addAttribute("grade", memberVO.getGrade());
        return "/index";
      } else {
        model.addAttribute("code", "withdraw_fail");
        return "redirect:/member/read"; // /templates/member/read.html
      } 
    } else {
      return "redirect:/member/login_cookie_need";  // redirect
    }
  }
}
