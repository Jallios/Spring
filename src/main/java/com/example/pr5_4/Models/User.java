package com.example.pr5_4.Models;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdUser;
    @Size(min = 5, max = 20, message = "Логин не может быть меньше 8 и больше 20")
    @NotBlank(message = "Login is required")
    private String Username;
    @Size(min = 8, message = "Пароль не может быть меньше 8 и больше 25")
    @Pattern(regexp = "^.*(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$" , message = "Пароль должен содержать заглавные и прописные буквы, цифры и специальные символы")
    @NotBlank(message = "Password is required")
    private String Password;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy="user")
    private List<Article> articles;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Favourites",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "article_id") }
    )
    private List<Article> articleList;

    public User() {
    }

    public User(Long idUser, String username, String password, Set<Role> roles) {
        IdUser = idUser;
        Username = username;
        Password = password;
        this.roles = roles;
    }

    public Long getIdUser() {
        return IdUser;
    }

    public void setIdUser(Long idUser) {
        IdUser = idUser;
    }

    public String getLogin() {
        return Username;
    }

    public void setLogin(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

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
}
