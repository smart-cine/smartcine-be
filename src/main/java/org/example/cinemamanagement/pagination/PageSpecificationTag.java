package org.example.cinemamanagement.pagination;

import jakarta.persistence.criteria.*;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.model.Film;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageSpecificationTag<T> implements Specification<T> {
    private final transient String mainFieldName;
    private final transient Map<String, Object> paramsSearching;
    private final transient CursorBasedPageable cursorBasedPageable;

    public PageSpecificationTag(String mainFieldName,
                                CursorBasedPageable cursorBasedPageable,
                                Map<String, Object> paramsSearching
    ) {
        this.mainFieldName = mainFieldName;
        this.cursorBasedPageable = cursorBasedPageable;
        this.paramsSearching = paramsSearching;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate paginationFilter = applyPaginationFilter(root, criteriaBuilder);

        predicates.add(paginationFilter);
        if (this.paramsSearching.containsKey("filmId")) {
            predicates.add(filterBaseOnParams(root, query, criteriaBuilder));
        }

        query.orderBy(criteriaBuilder.asc(root.get(mainFieldName)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        var searchValue = this.cursorBasedPageable.getSearchValue();
        if (searchValue == null) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(mainFieldName), "");
        }

        return criteriaBuilder.greaterThan(root.get(mainFieldName), (String) searchValue);
    }

    private Predicate filterBaseOnParams(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        System.out.println("filmId: " + this.paramsSearching.get("filmId"));
        Join<T, Film> filmJoin = root.join("films");
        return criteriaBuilder.equal(filmJoin.get("id"), this.paramsSearching.get("filmId"));
    }
}
