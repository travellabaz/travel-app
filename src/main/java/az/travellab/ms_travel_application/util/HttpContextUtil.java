package az.travellab.ms_travel_application.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.list;
import static java.util.Optional.ofNullable;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;


@NoArgsConstructor(access = PRIVATE)
public enum HttpContextUtil {
    HTTP_CONTEXT_UTIL;

    public Optional<ServletRequestAttributes> getServletRequestAttributes() {
        return ofNullable((ServletRequestAttributes) getRequestAttributes());
    }

    public Optional<HttpServletRequest> getHttpServletRequest() {
        var requestAttributes = getServletRequestAttributes();
        return requestAttributes.map(ServletRequestAttributes::getRequest);
    }

    public Optional<String> getParamValueByName(String paramName) {
        return getHttpServletRequest().map(httpServletRequest -> httpServletRequest.getParameter(paramName));
    }

    public List<String> getParamNames() {
        return getHttpServletRequest()
                .map(httpServletRequest -> list(httpServletRequest.getParameterNames()))
                .orElseGet(ArrayList::new);
    }

    public Map<String, String> getNameValueParams() {
        return getParamNames().stream()
                .collect(toMap(
                        identity(),
                        paramName -> getParamValueByName(paramName).orElseGet(String::new),
                        (first, second) -> first
                ));
    }
}