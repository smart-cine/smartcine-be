package org.example.cinemamanagement.pagination;

import jakarta.persistence.criteria.*;
import org.example.cinemamanagement.model.CinemaLayout;
import org.example.cinemamanagement.model.CinemaProvider;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PageSpecificationCinemaLayout<T> implements Specification<T> {
    private final transient String mainFieldName;
    private final transient Map<String, Object> paramsSearching;
    private final transient CursorBasedPageable cursorBasedPageable;

    public PageSpecificationCinemaLayout(String mainFieldName,
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

        if (paramsSearching.containsKey("providerId")) {
            predicates.add(filterBaseOnParams(root, query, criteriaBuilder));
        }

        query.orderBy(criteriaBuilder.asc(root.get(mainFieldName)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        if (!cursorBasedPageable.hasCursors()) {
            return criteriaBuilder.greaterThan(root.get(mainFieldName), UUID.fromString("00000000-0000-0000-0000-000000000000"));
        }

        return criteriaBuilder.greaterThan(root.get(mainFieldName), UUID.fromString((String)cursorBasedPageable.getSearchValue()));
    }

    private Predicate filterBaseOnParams(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (this.paramsSearching.containsKey("providerId")) {
            // get list perform by cinemaProviderId

            Join<T, CinemaProvider> join = root.join("cinemaProvider");
            return criteriaBuilder.equal(join.get("id"), this.paramsSearching.get("providerId"));
        }
        return null;
    }
}
