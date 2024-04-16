package com.example.pr4_2.Repositories;

import com.example.pr4_2.Models.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article,Long> {
}
