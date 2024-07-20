package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.dao.entity.ExpensesEntity;
import az.travellab.ms_travel_application.model.request.ExpensesDto;
import org.springframework.beans.BeanUtils;

public enum ExpensesMapper {
    EXPENSES_MAPPER;

    public ExpensesEntity generateExpensesEntity(ExpensesDto expensesDto) {
        return ExpensesEntity.builder()
                .amount(expensesDto.getAmount())
                .type(expensesDto.getType())
                .note(expensesDto.getNote())
                .build();
    }

    public ExpensesDto generateExpensesDto(ExpensesEntity expensesEntity) {
        var expensesDto = new ExpensesDto();
        BeanUtils.copyProperties(expensesEntity, expensesDto);
        return expensesDto;
    }
}
