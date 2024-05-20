package az.travellab.ms_travel_application.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static az.travellab.ms_travel_application.model.enums.FieldsQueryExpressions.getExpression;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class QueryGeneratorUtil {
    private final StringBuilder query;
    private final static List<String> fields = List.of("nameTo", "nameFrom", "phoneTo", "phoneFrom", "message", "mail");

    public static QueryGeneratorUtil buildBaseQuery(String baseQuery) {
        return new QueryGeneratorUtil(new StringBuilder(baseQuery));
    }

    public QueryGeneratorUtil generateFilter(Set<String> paramNames) {
        paramNames.stream()
                .filter(paramName -> !(paramName.equals("page") || paramName.equals("count")))
                .forEach(paramName -> query.append(getExpression(paramName)));
        return this;
    }

    public QueryGeneratorUtil endWith(String endOfQuery) {
        query.append(endOfQuery);
        return this;
    }

    public Query getTypedQuery(EntityManager entityManager, Map<String, String> paramKeyValue, Pageable pageable) {
        var nativeQuery = entityManager.createNativeQuery(query.toString());
        paramKeyValue.forEach((key, value) -> {
            if (value == null) return;
            if (key.equals("page") || key.equals("count")) return;
            if (fields.contains(key)) {
                nativeQuery.setParameter(key, "%" + value.toUpperCase() + "%");
            } else if (key.equals("isMarried") || key.equals("isParent")) {
                nativeQuery.setParameter(key, Boolean.valueOf(value));
            } else if (key.equals("cityId") || key.equals("countryId")) {
                nativeQuery.setParameter(key, Long.valueOf(value));
            } else {
                nativeQuery.setParameter(key, value);
            }
        });
        makePageable(nativeQuery, pageable);
        return nativeQuery;
    }

    private void makePageable(Query query, Pageable pageable) {
        if (pageable.isUnpaged()) return;
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
    }

}