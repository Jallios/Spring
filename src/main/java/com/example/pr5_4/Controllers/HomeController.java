package com.example.pr5_4.Controllers;

import com.example.pr5_4.Models.Article;
import com.example.pr5_4.Models.Role;
import com.example.pr5_4.Models.User;
import com.example.pr5_4.Repositories.ArticleRepository;
import com.example.pr5_4.Repositories.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {

    private UserRepository userRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public HomeController(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }
    @GetMapping("/")
    public String Index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "/Index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Article/Index")
    public String ArticleIndex(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "/Article/Index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Article/Add")
    public String ArticleAdd(@ModelAttribute("article") Article article, Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/Article/Add";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
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

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Article/Index/{id}")
    public String ArticleDelete(@PathVariable("id") long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        articleRepository.delete(article);
        return "redirect:/Article/Index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Article/Update/{id}")
    public String ArticleUpdate(@PathVariable("id") long id, Model model) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("article", article);
        model.addAttribute("users", userRepository.findAll());
        return "/Article/Update";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
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

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Favourite/Index")
    public String FavouriteIndex(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());

        return "/Favourite/Index";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Favourite/Add")
    public String FavouriteAdd(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());
        return "/Favourite/Add";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
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


    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/Favourite/Index/{id_User}/{id_Article}")
    public String FavouriteDelete(@PathVariable(value = "id_User") long id_User,@PathVariable(value = "id_Article") Long id_Article, Model model) {
        Article article = articleRepository.findById(id_Article).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id_Article));
        User user = userRepository.findById(id_User).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id_User));
        user.getArticleList().remove(article);
        userRepository.save(user);
        return "redirect:/Favourite/Index";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/User/Index")
    public String UserIndex(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/User/Index";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/User/Index/{id}")
    public String UserDelete(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        userRepository.delete(user);
        return "redirect:/User/Index";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/User/Update/{id}")
    public String updView(@PathVariable Long id, Model model)
    {
        model.addAttribute("user",userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", Role.values());
        return "/User/Update";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/User/Update/{id}")
    public String update_user(@RequestParam String login,
                              @RequestParam(name="roles[]", required = false) String[] roles,
                              @PathVariable Long id)
    {
        User user = userRepository.findById(id).orElseThrow();
        user.setLogin(login);

        user.getRoles().clear();
        if(roles != null)
        {
            for(String role: roles)
            {
                user.getRoles().add(Role.valueOf(role));
            }
        }
        userRepository.save(user);
        return "redirect:/User/Index";
    }
}
