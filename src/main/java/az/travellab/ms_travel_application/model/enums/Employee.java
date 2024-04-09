package az.travellab.ms_travel_application.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum Employee {
    EMPLOYEE_1("994516413665", "Qumral", "e50dafb992e030660a9dd87207fb4e99b8", "8"),
    EMPLOYEE_2("994514753665", "Leman", "a82849063d96719c97c785b86f5f6789", "9"),
    EMPLOYEE_E("994513433665", "Xeyale", "e259acc19ede38f3c8a16b5a26941afd", "10"),
    EMPLOYEE_OTHER("others", "Others", "", "");

    @Setter
    private String phone;
    private final String name;
    private final String accessToken;
    private final String userId;

    public static Employee getEmployeeByPhone(String phone) {
        return stream(Employee.values())
                .filter(employee -> employee.getPhone().equals(phone))
                .findFirst()
                .orElseGet(() -> {
                    EMPLOYEE_OTHER.setPhone(phone);
                    return EMPLOYEE_OTHER;
                });
    }
}