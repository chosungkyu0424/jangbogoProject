package backend.jangbogoProject.member.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import backend.jangbogoProject.member.domain.Member;
import backend.jangbogoProject.member.dto.MemberDto;
import backend.jangbogoProject.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // http://localhost:8080/member/login
    @GetMapping("/member/login")
    public ModelAndView loginForm(@RequestParam(value = "error", required = false) String error,
                                  @RequestParam(value = "exception", required = false) String exception,
                                  Model model) {
        ModelAndView mav = new ModelAndView("member/login");
        mav.addObject("error", error);
        mav.addObject("exception", exception);

        return mav;
    }

    @PostMapping("/member/login")
    public String login(@RequestBody MemberDto memberDto) {
        JsonObject data = new JsonObject();
        Member member = memberDto.toEntity();

        if(memberService.login(member))
        {
            System.out.println("로그인 성공");
            data.addProperty("token", "123");
        }
        else
        {
            System.out.println("존재하지 않는 이메일이거나 비밀번호가 일치하지 않습니다.");
            data.addProperty("res", "존재하지 않는 이메일이거나 비밀번호가 일치하지 않습니다.");
        }

        return data.toString();
    }

    // http://localhost:8080/member/register
    @GetMapping("/member/register")
    public ModelAndView registerForm(){
        ModelAndView mav = new ModelAndView("member/register");
        return mav;
    }

    // http://localhost:8080/member/register
    @PostMapping("/member/register")
    public ModelAndView create(@RequestBody MemberDto MemberDto){
        memberService.save(MemberDto.toEntity());
        //존재하는 이메일이면
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("res", "회원가입 성공");
        return mav;
    }

    // http://localhost:8080/member/emailCheck
    @GetMapping("/member/emailCheck")
    @ResponseBody
    public Map<String, Object> emailCheck(@RequestParam("email") String email)
    {
        System.out.println("이메일 체크");
        // 존재하는 이메일이면 1, 아니면 0 반환
        int result = memberService.isCheckEmail(email);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        if(result == 0)
        {
            System.out.println(email + " 사용 가능 이메일입니다.");
            resultMap.put("result", 0);
        }
        else
        {
            System.out.println(email + " 이미 존재하는 이메일입니다.");
            resultMap.put("result", 1);
        }

        return resultMap;
    }

    @GetMapping("/getLoginMemberName")
    public String getLoginMemberName(@RequestParam String email){
        Optional<Member> member = memberService.findEmail(email);

        Gson gson = new Gson();
        String json = gson.toJson(member.get().getName()).toString();

        return json;
    }

    @GetMapping("/mypage")
    public ModelAndView mypageForm() {

        ModelAndView mav = new ModelAndView("mypage/mypage");
        return mav;
    }

    @GetMapping("/mypage/editInfo")
    public ModelAndView editInfo(){

        ModelAndView mav = new ModelAndView("mypage/edit-info");
        return mav;
    }

    @PutMapping("/member/updatePassword")
    public Map<String, Object> updatePassword(@RequestBody MemberDto memberDto){

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("res", memberService.updatePassword(memberDto.toEntity()));
        return resultMap;
    }

    @PutMapping("/member/updateOtherInfo")
    public Map<String, Object> updateOtherInfo(@RequestBody MemberDto memberDto){

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("res", memberService.updateOtherInfo(memberDto.toEntity()));
        return resultMap;
    }

    /*
    // http://localhost:8080/member/mypage
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/mypage")
    public ModelAndView mypageForm(Principal principal, Model model) {
        Optional<Member> member = memberService.findEmail(principal.getName());
        model.addAttribute("member", member);

        ModelAndView mav = new ModelAndView("member/mypage");
        return mav;
    }

    // http://localhost:8080/member/editInfo
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/member/editInfo")
    public ModelAndView editInfo(Principal principal, Model model){
        Optional<Member> member = memberService.findEmail(principal.getName());
        model.addAttribute("member", member);

        ModelAndView mav = new ModelAndView("member/edit-info");
        return mav;
    }
*/
}
