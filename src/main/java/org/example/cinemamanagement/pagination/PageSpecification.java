package org.example.cinemamanagement.pagination;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PageSpecification<T> implements Specification<T> {
    private final transient String mainFieldName;
    private final transient String subFieldName;
    private final transient Object searchValue;
    private final transient CursorBasedPageable cursorBasedPageable;

    public PageSpecification(String mainFieldName,
                             String subFieldName,
                             Object searchValue,
                             CursorBasedPageable cursorBasedPageable
    ) {

        this.mainFieldName = mainFieldName;
        this.subFieldName = subFieldName;
        this.searchValue = searchValue;
        this.cursorBasedPageable = cursorBasedPageable;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate paginationFilter = applyPaginationFilter(root, criteriaBuilder);
        predicates.add(paginationFilter);

        if (subFieldName != null && searchValue != null && !subFieldName.isEmpty() && !searchValue.toString().isEmpty() ) {
            Predicate filterBaseOnParams = filterBaseOnParams(root, criteriaBuilder);
            predicates.add(filterBaseOnParams);
        }

        query.orderBy(criteriaBuilder.asc(root.get(mainFieldName)));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        var searchValue = cursorBasedPageable.getSearchValue();

        if (mainFieldName.contains("Time")) {
            if (searchValue == null)
                return criteriaBuilder.greaterThanOrEqualTo(root.get(mainFieldName), Timestamp.valueOf("1970-01-01 00:00:00"));
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                Date parsedDate = dateFormat.parse(String.valueOf(searchValue));
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                return criteriaBuilder.greaterThan(root.get(mainFieldName), timestamp);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        } else {
            if (searchValue == null)
                return criteriaBuilder.greaterThan(root.get(mainFieldName), "");
            return criteriaBuilder.greaterThan(root.get(mainFieldName), String.valueOf(searchValue));
        }
    }

    private Predicate filterBaseOnParams(Root<T> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;

        if(searchValue instanceof UUID) {
            predicate = criteriaBuilder.equal(root.get(subFieldName).get("id"), searchValue);
        } else {
            predicate = criteriaBuilder.equal(root.get(subFieldName), searchValue);
        }

        return predicate;
    }
}
