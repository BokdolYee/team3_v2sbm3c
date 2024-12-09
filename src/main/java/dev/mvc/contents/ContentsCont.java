package dev.mvc.contents;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/contents")
@Controller
public class ContentsCont {
  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;
  
  public ContentsCont() {
    System.out.println("-> ContentsCont created.");
  }
  
  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   * 
   * @return
   */
  @GetMapping(value = "/post2get")
  public String msg(Model model, 
      @RequestParam(name="url", defaultValue = "")String url) {
    ArrayList<NewsCateVOMenu> menu = this.newscateProc.menu();
    model.addAttribute("menu", menu);

    return url; // forward, /templates/...
  }
  
  @GetMapping(value = "/create")
  public String create(HttpSession session,Model model, 
      @ModelAttribute("contentsVO") ContentsVO contentsVO, 
      @RequestParam(name="newscateno", defaultValue="0") int newscateno,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    ArrayList<NewsCateVOMenu> menu = this.newscateProc.menu();
    model.addAttribute("menu", menu);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    if (this.memberProc.isMemberAdmin(session)) {
      NewsCateVO newscateVO = this.newscateProc.read(newscateno);//  카테고리 정보를 출력하기위한 목적
      model.addAttribute("newscateVO", newscateVO);

      return "/contents/create"; // /templates/contents/create.html
    }else {
      return "redirect:/member/login_cookie_need";
    }
  }
  
  
}
