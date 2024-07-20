package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.repository.ExpensesRepository;
import az.travellab.ms_travel_application.model.request.ExpensesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.factory.ExpensesMapper.EXPENSES_MAPPER;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

    private final ExpensesRepository expensesRepository;

    public void addExpenses(ExpensesDto expensesDto) {
        expensesRepository.save(EXPENSES_MAPPER.generateExpensesEntity(expensesDto));
    }

    public List<ExpensesDto> getExpenses() {
        var expensesEntities = expensesRepository.getCurrentMonthExpenses();
        return expensesEntities.stream().map(EXPENSES_MAPPER::generateExpensesDto).toList();
    }

    public void deleteExpenses(Long id) {
        expensesRepository.deleteById(id);
    }
}
