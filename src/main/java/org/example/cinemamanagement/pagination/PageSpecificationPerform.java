package org.example.cinemamanagement.pagination;

import jakarta.persistence.criteria.*;
import org.example.cinemamanagement.model.Cinema;
import org.example.cinemamanagement.model.CinemaRoom;
import org.example.cinemamanagement.model.Perform;
import org.example.cinemamanagement.utils.CursorBasedPageable;
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
        List<Predicate> predicates = new ArrayList<>();
        Predicate paginationFilter = applyPaginationFilter(root, criteriaBuilder);
        predicates.add(paginationFilter);

        predicates.add(filterBaseOnParams(root, criteriaBuilder));

        query.orderBy(criteriaBuilder.asc(root.get(mainFieldName)));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate applyPaginationFilter(Root<T> root, CriteriaBuilder criteriaBuilder) {
        var searchValue = cursorBasedPageable.getSearchValue();

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
    }

    private Predicate filterBaseOnParams(Root<T> root, CriteriaBuilder criteriaBuilder) {

//        if (!this.paramsSearching.containsKey("featured")) {
//            Join<Perform, CinemaRoom>  performCinemaRoomJoin = root.join("cinemaRoom");
//            Join<CinemaRoom, Cinema> cinemaRoomCinemaJoin = root.
//        }
        return null;
    }
}