package seung.port.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import seung.port.domain.Member;
import seung.port.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원가입 버튼
    @GetMapping("/new")
    public String join() {
        return "join";
    }

    //회원가입창에서 회원가입 버튼
    @PostMapping("/new")
    public String joinHome(Member form, HttpSession session) {

        Map<String, Object> rtnMap = memberService.join(form);
       System.out.println(rtnMap.get("message"));

        if ("Y".equals(rtnMap.get("success"))) {
            return "home";

       } return "existMember";
    }

    //로그인 버튼
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //로그인에서 로그인 버튼
    @PostMapping("/loginHome")
    public String UserLogin(Member form, Model model, HttpSession session) {

        Map<String, Object> rtnMap = memberService.loginMember(form);
        System.out.println(rtnMap.get("message"));

        session.setAttribute("id",rtnMap.get("id"));
        session.setAttribute("userName",rtnMap.get("userName"));
        session.setAttribute("phoneNumber",rtnMap.get("phoneNumber"));

        if ("Y".equals(rtnMap.get("success"))) {
            model.addAttribute("id",rtnMap.get("id"));
            return "loginHome";

        } return "wrongForm";
    }

    //로그인에서 아이디찾기 버튼
    @PostMapping("/login/FindId")
    public String FindId() {
        return "FindId";
    }

    //아이디찾기에서 확인 버튼
    @PostMapping("/login/FindId/FindIdCon")
    public String FindIdCon(Member form, Model model) {

        Map<String, Object> rtnMap = memberService.findMemberMap(memberService.findId(form));
        System.out.println(rtnMap.get("message"));

        if ("Y".equals(rtnMap.get("success"))) {
            model.addAttribute("id", rtnMap.get("id"));
            return "FindIdCon";
        }
        return "wrongForm";
    }

    //로그인에서 비밀번호찾기 버튼
    @PostMapping("/login/FindPw")
    public String FindPw() {
        return "FindPw";
    }

    //비밀번호 찾기에서 확인 버튼
    @PostMapping("/login/FindPw/FindPwCon")
    public String FindPwCon(Member form, Model model) {

        Map<String, Object> rtnMap = memberService.findMemberMap(memberService.findPw(form));
        System.out.println(rtnMap.get("message"));

        if ("Y".equals(rtnMap.get("success"))) {
            model.addAttribute("password", rtnMap.get("password"));
            return "FindPwCon";
        }
        return "wrongForm";
    }

    //로그인 한 화면에서 내정보 버튼
    @GetMapping("/MyPage")
    public String MyPage(Model model, Member form, HttpSession session) {

        model.addAttribute("id",session.getAttribute("id"));
        model.addAttribute("userName",session.getAttribute("userName"));
        model.addAttribute("phoneNumber",session.getAttribute("phoneNumber"));

        return "MyPage";
    }

    //마이페이지에서 회원탈퇴 버튼
    @PostMapping("/MyPage/deleteMemberPwCon")
    public String deleteMemberPwCon(){
        return "deleteMemberPwCon";
    }

    //비밀번호 확인하고 회원탈퇴버튼
    @PostMapping("/home")
    public String deleteMemberPwConHome(Member form){

        Map<String, Object> rtnMap = memberService.deleteMember(form);
        System.out.println(rtnMap.get("message"));

        if ("Y".equals(rtnMap.get("success"))) {
            System.out.println("탈퇴 완료");
            return "home";
        }
        return "wrongForm";
    }

    //로그인 한 화면에서 로그아웃 버튼
    @GetMapping("/logOut")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "home";
    }
}