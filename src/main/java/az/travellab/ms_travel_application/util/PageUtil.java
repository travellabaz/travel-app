package az.travellab.ms_travel_application.util;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static az.travellab.ms_travel_application.util.HttpContextUtil.HTTP_CONTEXT_UTIL;
import static java.lang.Integer.parseInt;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public enum PageUtil {
    PAGE_UTIL;

    public Pageable getPageRequest() {
            var page = HTTP_CONTEXT_UTIL.getParamValueByName("page");
            var size = HTTP_CONTEXT_UTIL.getParamValueByName("count");

            if (page.isPresent()) {
                var intSize = size.map(Integer::parseInt).orElse(10);
                return PageRequest.of(parseInt(page.get()) - 1, intSize);
            }
            return PageRequest.of(0, 10);
        }
}