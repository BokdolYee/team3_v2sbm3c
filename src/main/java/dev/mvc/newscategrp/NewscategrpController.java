package dev.mvc.newscategrp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/newscategrp")
public class NewscategrpController {

    @Autowired
    @Qualifier("dev.mvc.newscategrp.NewscategrpProc")
    private NewscategrpProcInter newscategrpProc;

    @GetMapping("/list")
    public String list(Model model) {
        List<NewscategrpVO> list = newscategrpProc.list();
        model.addAttribute("list", list);
        return "/newscategrp/list";
    }

    @GetMapping("/create")
    public String createForm() {
        return "/newscategrp/create";
    }

    @PostMapping("/create")
    public String create(NewscategrpVO newscategrpVO) {
        newscategrpProc.create(newscategrpVO);
        return "redirect:/newscategrp/list";
    }

    @GetMapping("/read/{newscategrpno}")
    public String read(@PathVariable int newscategrpno, Model model) {
        NewscategrpVO newscategrpVO = newscategrpProc.read(newscategrpno);
        model.addAttribute("newscategrpVO", newscategrpVO);
        return "/newscategrp/read";
    }

    @PostMapping("/update")
    public String update(NewscategrpVO newscategrpVO) {
        newscategrpProc.update(newscategrpVO);
        return "redirect:/newscategrp/list";
    }

    @GetMapping("/delete/{newscategrpno}")
    public String delete(@PathVariable int newscategrpno) {
        newscategrpProc.delete(newscategrpno);
        return "redirect:/newscategrp/list";
    }
}
