package com.practice.app.specification;

import com.practice.app.dto.BookDTO;
import com.practice.app.entity.Books;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class BookSpecification {

    public static Specification<Books> dynamicQuery(BookDTO filters){
      return (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<>();

          if(filters.getTitle() != null){
              predicates.add(cb.like(root.get("title"), "%" + filters.getTitle().toLowerCase() + "%"));
          }

          if (filters.getAuthor() != null) {

              String pattern = "%" + filters.getAuthor().toLowerCase() + "%";

              predicates.add(cb.like(cb.lower(root.get("author")), pattern));
          }

          if (filters.getPageCount() != null){
              predicates.add(cb.equal(root.get("pageCount"), filters.getPageCount()));
          }

          if(filters.getGenre() != null){
              predicates.add(cb.equal(root.get("genre"), filters.getGenre()));
          }

          if(filters.getStartDate() != null && filters.getEndDate() != null){
              predicates.add(cb.between(root.get("publishedAt"), filters.getStartDate(), filters.getEndDate()));
          } else if (filters.getStartDate() != null) {
              predicates.add(cb.greaterThanOrEqualTo(root.get("publishedAt"), filters.getStartDate()));
          } else if (filters.getEndDate() != null) {
              predicates.add(cb.lessThanOrEqualTo(root.get("publishedAt"), filters.getEndDate()));
          }

          if(filters.getIsAvailable() != null){
              predicates.add(cb.equal(root.get("isAvailable"), filters.getIsAvailable()));
          }

          // Converting the List into a single Predicate
          return cb.and(predicates.toArray(new Predicate[0]));
      };

    }
}
