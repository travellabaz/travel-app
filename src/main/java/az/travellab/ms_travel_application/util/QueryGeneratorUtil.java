package az.travellab.ms_travel_application.util;

import az.travellab.ms_travel_application.model.enums.ClientFieldsQueryExpressions;
import az.travellab.ms_travel_application.model.enums.FilterType;
import az.travellab.ms_travel_application.model.enums.SalesFieldsQueryExpressions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public final class QueryGeneratorUtil {
    private final StringBuilder query;
    private static final List<String> fields = List.of("nameTo", "nameFrom", "phoneTo", "phoneFrom", "message", "mail");

    public static QueryGeneratorUtil buildBaseQuery(String baseQuery) {
        return new QueryGeneratorUtil(new StringBuilder(baseQuery));
    }

    public QueryGeneratorUtil generateFilter(Set<String> paramNames, FilterType type) {

        final var count = "count";
        final var page = "page";

        var stream = paramNames.stream()
                .filter(paramName -> !(paramName.equals(page) || paramName.equals(count)));

        if (type.equals(FilterType.CLIENTS)) {
            stream.forEach(paramName -> query.append(ClientFieldsQueryExpressions.getExpression(paramName)));
        } else {
            stream.forEach(paramName -> query.append(SalesFieldsQueryExpressions.getExpression(paramName)));
        }
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
            } else if (
                    key.equals("isMarried") || key.equals("isParent") || key.equals("isOfficial")
                    || key.equals("hasClientRelationship") || key.equals("isEmployeeBonusPaid")
            ) {
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
