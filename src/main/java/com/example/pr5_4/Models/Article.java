package com.example.pr5_4.Models;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdArticle;
    @Size(min = 4, max = 15, message = "Заголовок не может быть меньше 4 и больше 15")
    @NotBlank(message = "Heading is required")
    private String Heading;
    @NotBlank(message = "Text is required")
    private String Text;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToMany(mappedBy = "articleList")
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Article() {
    }

    public Article(Long idArticle, String heading, String text, User user) {
        IdArticle = idArticle;
        Heading = heading;
        Text = text;
        this.user = user;
    }

    public Long getIdArticle() {
        return IdArticle;
    }

    public void setIdArticle(Long idArticle) {
        IdArticle = idArticle;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
