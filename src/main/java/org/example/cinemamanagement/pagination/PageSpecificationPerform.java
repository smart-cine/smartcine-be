package org.example.cinemamanagement.pagination;

import jakarta.persistence.criteria.*;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.utils.DateConverter;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PageSpecificationPerform<T> implements Specification<T> {

    private final transient String mainFieldName;
    private final transient Map<String, Object> paramsSearching;
    private final transient CursorBasedPageable cursorBasedPageable;

    public PageSpecificationPerform(String mainFieldName,
                                    CursorBasedPageable cursorBasedPageable,
                                    Map<String, Object> paramsSearching
    ) {
        this.mainFieldName = mainFieldName;
        this.cursorBasedPageable = cursorBasedPageable;
        this.paramsSearching = paramsSearching;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();
        predicates.add(this.applyPaginationFilter(root, criteriaBuilder));
        predicates.add(this.filterBaseOnParams(root, query, criteriaBuilder));
        query.orderBy(criteriaBuilder.asc(root.get(mainFieldName)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        var searchValue = cursorBasedPageable.getSearchValue();

        if (searchValue == null)
            return criteriaBuilder.greaterThan(root.get(mainFieldName), Timestamp.valueOf("1970-01-01 00:00:00"));

        return criteriaBuilder.greaterThan(root.get(mainFieldName),
                DateConverter.convertToTimestamp(
                        String.valueOf(searchValue),
                        "yyyy-MM-dd hh:mm:ss.SSS"
                )
        );
    }

    private Predicate filterBaseOnParams(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();
        if (this.paramsSearching.containsKey("cinema_id")) {
            UUID cinemaId = UUID.fromString(String.valueOf(paramsSearching.get("cinema_id")));
            predicates.add(criteriaBuilder.equal(root.get("cinemaRoom").get("cinema").get("id"), cinemaId));
        }

        if (this.paramsSearching.containsKey("start_time")) {
            String dateStr = String.valueOf(this.paramsSearching.get("start_time"));

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"),
                            DateConverter.convertToTimestamp(dateStr, "yyyy-MM-dd")
                    )
            );
        }

        if (this.paramsSearching.containsKey("film_id")) {
            try {
                UUID filmId = UUID.fromString(String.valueOf(this.paramsSearching.get("film_id")));
                predicates.add(criteriaBuilder.equal(root.get("film").get("id"), filmId));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}