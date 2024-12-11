package dev.mvc.member;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.newscate.NewsCateProcInter;
import dev.mvc.newscate.NewsCateVOMenu;

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
      memberVO.setGrade(11);
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
    else { // id랑 닉네임 둘 다 중복일 경우
      model.addAttribute("code", "duplicate_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "/member/msg";
  }
  
  
}
