package com.example.pr4_2.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdUser;
    @Size(min = 5, max = 20, message = "Логин не может быть меньше 8 и больше 20")
    @NotBlank(message = "Login is required")
    private String Login;
    @Size(min = 8, max = 25, message = "Пароль не может быть меньше 8 и больше 25")
    @Pattern(regexp = "^.*(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$" , message = "Пароль должен содержать заглавные и прописные буквы, цифры и специальные символы")
    @NotBlank(message = "Password is required")
    private String Password;
    @OneToOne(fetch = FetchType.LAZY)
    private Role role;

    @OneToMany(mappedBy="user")
    private List<Article> articles;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Favourites",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "article_id") }
    )
    List<Article> articleList;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public User() {
    }

    public User(Long idUser, String login, String password, Role role) {
        IdUser = idUser;
        Login = login;
        Password = password;
        this.role = role;
    }

    public Long getIdUser() {
        return IdUser;
    }

    public void setIdUser(Long idUser) {
        IdUser = idUser;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
