package dev.mvc.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.newscate.NewsCateProcInter;

@Controller
@RequestMapping("/news")
public class NewsCont {
  @Autowired
  @Qualifier("dev.mvc.news.NewsProc")
  private NewsCateProcInter newsProc;
  
  public NewsCont() {
    System.out.println("-> NewsCont created");
  }
  
}
