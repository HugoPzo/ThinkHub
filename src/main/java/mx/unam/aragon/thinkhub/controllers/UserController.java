package mx.unam.aragon.thinkhub.controllers;

import jakarta.servlet.http.HttpSession;
import mx.unam.aragon.thinkhub.entities.User;
import mx.unam.aragon.thinkhub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Página de registro
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/login";
    }

    // Página de login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        var result = userService.login(user.getEmail(), user.getPassword());
        if (result.isPresent()) {
            session.setAttribute("user", result.get());
            return "redirect:/posts";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

}
