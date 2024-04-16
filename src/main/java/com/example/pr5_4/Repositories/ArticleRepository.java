package com.example.pr5_4.Repositories;

import com.example.pr5_4.Models.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article,Long> {
}
