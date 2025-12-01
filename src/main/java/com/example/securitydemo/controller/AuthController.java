package com.example.securitydemo.controller;

import com.example.securitydemo.dto.SignupDto;
import com.example.securitydemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        if (userDetails != null) {
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("authorities",userDetails.getAuthorities());
        model.addAttribute("password",userDetails.getPassword());
        }
        return "dashboard";
    }

    @GetMapping("/signup")     // 빈종이
    public String signupForm(Model model){
        model.addAttribute("signupDto",new SignupDto());   // SignupDto 빈 dto
        return "signup";
    }

    @PostMapping("/signup")   // 데이터베이스에 저장,처리 하는 공간
    public String signup(
            @Valid @ModelAttribute SignupDto signupDto,  // html 에 사용자가 입력한 dto
            BindingResult bindingResult   // Binding vali 체크한결과를 넣어줌  안에 hasErrors 가있음
    ){
        if(!signupDto.getPassword().equals(signupDto.getPasswordConfirm())){  // 밑에 field html 에 있는 passwordConfirm = ${#fields.hasErrors('passwordConfirm')
            bindingResult.rejectValue("passwordConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
        }
        // 검증 실패
        if (bindingResult.hasErrors()){
            return "signup";
        }

        // DB 조회가 필요한 검증
        // 아이디 중복 체크
        if (userService.existsByUsername(signupDto.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디입니다.");
            return "signup";
        }

        // 이메일 중복 체크
        if (userService.existsByEmail(signupDto.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "이미 사용중인 이메일입니다.");
            return "signup";
        }



        // 검증 성공 => 회원가입 처리
        userService.register(signupDto);
        return "redirect:/login";
    }




}
