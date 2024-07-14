package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.SalesComponentsEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.dao.entity.SalesPaymentsEntity;
import az.travellab.ms_travel_application.dao.repository.SalesRepository;
import az.travellab.ms_travel_application.exception.NotFoundException;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import az.travellab.ms_travel_application.util.PercentageCalcUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.SALES_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesCalculationService {

    private final SalesRepository salesRepository;

    public void calculate(String salesNumber, Optional<SalesEntity> salesEntityOptional) {

        var salesEntity = salesEntityOptional.orElseGet(() -> salesRepository.findByNumber(salesNumber)
                .orElseThrow(() -> new NotFoundException(SALES_NOT_FOUND.getMessage().formatted(salesNumber))));

        var components = salesEntity.getComponents();
        var payments = salesEntity.getPayments();

        var calculationResult = calculateAmounts(components);

        var soldAmount = calculationResult.soldAmount;
        var salesProfit = calculateSalesProfit(soldAmount, calculationResult.purchasedAmount);
        var employeeBonus = calculateEmployeeBonus(salesProfit);
        var companyProfit = calculateCompanyProfit(salesProfit, employeeBonus);

        var status = SalesStatus.PENDING_FOR_APPROVE;
        if (checkIfSalesCompleted(components, payments))
            status = SalesStatus.COMPLETED;

        updateSalesEntity(salesEntity, calculationResult.purchasedAmount, calculationResult.soldAmount, employeeBonus, companyProfit, status);

        salesRepository.save(salesEntity);

        logCalculationResults(calculationResult.purchasedAmount, calculationResult.systemProfit, salesProfit, employeeBonus, companyProfit);
    }

    private boolean checkIfSalesCompleted(List<SalesComponentsEntity> componentsEntities, List<SalesPaymentsEntity> paymentsEntities) {
        var totalComponentsRemaining = componentsEntities.stream()
                .map(SalesComponentsEntity::getRemainedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalComponentsRemaining.compareTo(BigDecimal.ZERO) > 0) return false;

        var lastPaymentsRemaining = paymentsEntities.isEmpty() ? BigDecimal.ZERO : paymentsEntities.get(paymentsEntities.size() - 1).getRemaining();

        return lastPaymentsRemaining.compareTo(BigDecimal.ZERO) <= 0;
    }

    private CalculationResult calculateAmounts(List<SalesComponentsEntity> components) {
        var systemProfit = BigDecimal.ZERO;
        var purchasedAmount = BigDecimal.ZERO;
        var soldAmount = BigDecimal.ZERO;

        for (SalesComponentsEntity entity : components) {
            if (entity.getType().equals(ServiceType.SYSTEM)) {
                var profit = calculateSystemProfit(entity);
                systemProfit = systemProfit.add(profit);
                purchasedAmount = purchasedAmount.add(entity.getPurchasedAmount().subtract(profit));
            } else {
                purchasedAmount = purchasedAmount.add(entity.getPurchasedAmount());
            }

            purchasedAmount = purchasedAmount.add(entity.getTransferCommission());
            soldAmount = soldAmount.add(entity.getSoldAmount());
        }

        return new CalculationResult(purchasedAmount, systemProfit, soldAmount);
    }

    private BigDecimal calculateSystemProfit(SalesComponentsEntity entity) {
        return PercentageCalcUtil.calculatePercentage(entity.getPurchasedAmount(), entity.getPercentage());
    }

    private BigDecimal calculateSalesProfit(BigDecimal soldAmount, BigDecimal purchasedAmount) {
        return soldAmount.subtract(purchasedAmount);
    }

    private BigDecimal calculateEmployeeBonus(BigDecimal salesProfit) {
        return PercentageCalcUtil.calculatePercentage(salesProfit, BigDecimal.TEN);
    }

    private BigDecimal calculateCompanyProfit(BigDecimal salesProfit, BigDecimal employeeBonus) {
        return salesProfit.subtract(employeeBonus);
    }

    private void updateSalesEntity(SalesEntity saleEntity, BigDecimal purchasedAmount, BigDecimal soldAmount,
                                   BigDecimal employeeBonus, BigDecimal companyProfit, SalesStatus salesStatus) {
        saleEntity
                .setPurchasedAmount(purchasedAmount)
                .setEmployeeBonus(employeeBonus)
                .setSoldAmount(soldAmount)
                .setProfit(companyProfit)
                .setStatus(salesStatus);
    }

    private record CalculationResult(BigDecimal purchasedAmount, BigDecimal systemProfit, BigDecimal soldAmount) {
    }

    private void logCalculationResults(BigDecimal purchasedAmount, BigDecimal systemProfit, BigDecimal salesProfit, BigDecimal employeeBonus, BigDecimal companyProfit) {
        log.info("Purchased amount: " + purchasedAmount);
        log.info("System profit: " + systemProfit);
        log.info("Sales profit: " + salesProfit);
        log.info("Employee profit: " + employeeBonus);
        log.info("Company profit: " + companyProfit);
    }
}
