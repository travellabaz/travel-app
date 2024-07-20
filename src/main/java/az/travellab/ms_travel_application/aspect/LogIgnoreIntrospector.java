package az.travellab.ms_travel_application.aspect;

import az.travellab.ms_travel_application.annotation.LogIgnore;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import static java.util.Objects.nonNull;

public class LogIgnoreIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public boolean hasIgnoreMarker(AnnotatedMember member) {
        var logIgnore = member.getAnnotation(LogIgnore.class);
        if (nonNull(logIgnore)) return true;
        return super.hasIgnoreMarker(member);
    }
}