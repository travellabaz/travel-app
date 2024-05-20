package az.travellab.ms_travel_application.util;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static az.travellab.ms_travel_application.util.HttpContextUtil.HTTP_CONTEXT_UTIL;
import static java.lang.Integer.parseInt;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public enum PageUtil {
    PAGE_UTIL;

    public PageRequest getPageRequest() {
        var page = HTTP_CONTEXT_UTIL.getParamValueByName("page");
        var count = HTTP_CONTEXT_UTIL.getParamValueByName("count");

        return page
                .map(
                        s -> PageRequest.of(
                                parseInt(s) - 1,
                                parseInt(count.get()),
                                Sort.by("createdAt").descending()
                        )
                )
                .orElseGet(() -> PageRequest.of(0, 10));
    }
}