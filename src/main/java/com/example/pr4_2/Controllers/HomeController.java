package com.example.pr4_2.Controllers;

import com.example.pr4_2.Models.Article;
import com.example.pr4_2.Models.Role;
import com.example.pr4_2.Models.User;
import com.example.pr4_2.Repositories.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController implements WebMvcConfigurer {

    public HomeController(RoleRepository roleRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/")
    public String Index(Model model) {
        model.addAttribute("title", "Главная");
        return "Index";
    }

    private final RoleRepository roleRepository;

    @GetMapping("/Role/Index")
    public String RoleIndex(Model model) {
        model.addAttribute("title", "Роли");
        model.addAttribute("roles", roleRepository.findAll());
        return "/Role/Index";
    }

    @GetMapping("/Role/Add")
    public String RoleAdd(@ModelAttribute("role") Role role, Model model) {
        model.addAttribute("title", "Роли");
        return "/Role/Add";
    }
    @PostMapping("/Role/Add")
    public String RoleAdd(@ModelAttribute("role") @Valid Role role,
                          BindingResult bindingResult, Model model) {
        model.addAttribute("title", "Роли");
        if (bindingResult.hasErrors())
        {
            return "/Role/Add";
        }
        roleRepository.save(role);
        return "redirect:/Role/Index";
    }

    @GetMapping("/Role/Index/{id}")
    public String RoleDelete(@PathVariable("id") long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        roleRepository.delete(role);
        return "redirect:/Role/Index";
    }

    @GetMapping("/Role/Update/{id}")
    public String RoleUpdate(@PathVariable(value = "id") long id, Model model) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("role", role);
        model.addAttribute("title", "Роли");
        return "/Role/Update";
    }

    @PostMapping("/Role/Update/{id}")
    public String RoleUpdate(@PathVariable(value = "id") long id, @Valid Role role,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
        {
            return "/Role/Update";
        }
        role.setIdRole(id);
        roleRepository.save(role);
        return "redirect:/Role/Index";
    }

    private final UserRepository userRepository;

    @GetMapping("/User/Index")
    public String UserIndex(Model model) {
        model.addAttribute("title", "Пользователи");
        model.addAttribute("users", userRepository.findAll());
        return "/User/Index";
    }

    @GetMapping("/User/Add")
    public String UserAdd(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("title", "Пользователи");
        model.addAttribute("roles", roleRepository.findAll());
        return "/User/Add";
    }
    @PostMapping("/User/Add")
    public String UserAdd(@ModelAttribute("user") @Valid User user,
                          @RequestParam Long idRole, BindingResult bindingResult, Model model) {
        model.addAttribute("title", "Пользователи");
        if (bindingResult.hasErrors())
        {
            model.addAttribute("roles", roleRepository.findAll());
            return "/User/Add";
        }
        Role role = roleRepository.findById(idRole).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idRole));
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/User/Index";
    }

    @GetMapping("/User/Index/{id}")
    public String UserDelete(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        userRepository.delete(user);
        return "redirect:/User/Index";
    }

    @GetMapping("/User/Update/{id}")
    public String UserUpdate(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("title", "Пользователи");
        return "/User/Update";
    }

    @PostMapping("/User/Update/{id}")
    public String UserUpdate(@PathVariable("id") long id, @Valid User user,
                             BindingResult bindingResult,@RequestParam Long idRole, Model model) {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("roles", roleRepository.findAll());
            return "/User/Update";
        }
        Role role = roleRepository.findById(idRole).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idRole));
        user.setRole(role);
        user.setIdUser(id);
        userRepository.save(user);
        return "redirect:/User/Index";
    }

    private final ArticleRepository articleRepository;

    @GetMapping("/Article/Index")
    public String ArticleIndex(Model model) {
        model.addAttribute("title", "Статьи");
        model.addAttribute("articles", articleRepository.findAll());
        return "/Article/Index";
    }

    @GetMapping("/Article/Add")
    public String ArticleAdd(@ModelAttribute("article") Article article, Model model) {
        model.addAttribute("title", "Статьи");
        model.addAttribute("users", userRepository.findAll());
        return "/Article/Add";
    }
    @PostMapping("/Article/Add")
    public String ArticleAdd(@ModelAttribute("article") @Valid Article article,
                          @RequestParam Long idUser, BindingResult bindingResult, Model model) {
        model.addAttribute("title", "Статьи");
        if (bindingResult.hasErrors())
        {
            model.addAttribute("users", userRepository.findAll());
            return "/Article/Add";
        }
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idUser));
        article.setUser(user);
        articleRepository.save(article);
        return "redirect:/Article/Index";
    }

    @GetMapping("/Article/Index/{id}")
    public String ArticleDelete(@PathVariable("id") long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        articleRepository.delete(article);
        return "redirect:/Article/Index";
    }

    @GetMapping("/Article/Update/{id}")
    public String ArticleUpdate(@PathVariable("id") long id, Model model) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("title", "Статья");
        return "/Article/Update";
    }

    @PostMapping("/Article/Update/{id}")
    public String ArticleUpdate(@PathVariable("id") long id, @Valid Article article,
                             BindingResult bindingResult,@RequestParam Long idUser, Model model) {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("users", userRepository.findAll());
            return "/Article/Update";
        }
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idUser));
        article.setUser(user);
        article.setIdArticle(id);
        articleRepository.save(article);
        return "redirect:/Article/Index";
    }





    @GetMapping("/Favourite/Index")
    public String FavouriteIndex(Model model) {
        model.addAttribute("title", "Избранное");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());

        return "/Favourite/Index";
    }


    @GetMapping("/Favourite/Add")
    public String FavouriteAdd(Model model) {
        model.addAttribute("title", "Избранное");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());
        return "/Favourite/Add";
    }

    @PostMapping("/Favourite/Add")
    public String FavouriteAdd(@RequestParam Long idArticle,
                             @RequestParam Long idUser, Model model) {
        model.addAttribute("title", "Статьи");
        User user = userRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idUser));
        Article article = articleRepository.findById(idArticle).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + idArticle));
        user.getArticleList().add(article);
        userRepository.save(user);
        return "redirect:/Favourite/Index";
    }

    @GetMapping("/Favourite/Index/{id_User}/{id_Article}")
    public String FavouriteDelete(@PathVariable(value = "id_User") long id_User,@PathVariable(value = "id_Article") Long id_Article, Model model) {
        Article article = articleRepository.findById(id_Article).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id_Article));
        User user = userRepository.findById(id_User).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id_User));
        user.getArticleList().remove(article);
        userRepository.save(user);
        return "redirect:/Favourite/Index";
    }
}
