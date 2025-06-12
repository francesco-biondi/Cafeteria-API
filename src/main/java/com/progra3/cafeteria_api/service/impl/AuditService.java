package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.audit.AuditInProgressException;
import com.progra3.cafeteria_api.exception.audit.AuditNotFoundException;
import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.Audit;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.enums.AuditStatus;
import com.progra3.cafeteria_api.model.mapper.AuditMapper;
import com.progra3.cafeteria_api.repository.AuditRepository;
import com.progra3.cafeteria_api.security.BusinessContext;
import com.progra3.cafeteria_api.service.port.IAuditService;
import com.progra3.cafeteria_api.service.helper.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final AuditRepository auditRepository;

    private final BusinessContext businessContext;
    private final ExpenseService expenseService;
    private final OrderService orderService;

    private final AuditMapper auditMapper;

    private final Clock clock;

    @Override
    public AuditResponseDTO create(AuditRequestDTO dto) {
        verifyAuditStatus(businessContext.getCurrentBusinessId());

        Audit audit = auditMapper.toEntity(dto);

        audit.setBusiness(businessContext.getCurrentBusiness());
        audit.setStartTime(LocalDateTime.now(clock));
        audit.setAuditStatus(AuditStatus.IN_PROGRESS);
        audit.setRealCash(Constant.ZERO_AMOUNT);
        audit.setTotal(Constant.ZERO_AMOUNT);
        audit.setTotalExpensed(Constant.ZERO_AMOUNT);
        audit.setDeleted(false);
        audit.setBalanceGap(calculateBalanceGap(audit));

        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public List<AuditResponseDTO> getAll() {
        return auditRepository.findByBusiness_Id(businessContext.getCurrentBusinessId())
                .stream()
                .map(auditMapper::toDTO)
                .toList();
    }

    @Override
    public AuditResponseDTO getById(Long auditId){
        return auditMapper.toDTO(getEntityById(auditId));
    }

    @Override
    public AuditResponseDTO finalize(Long auditId) {
        Audit audit = getEntityById(auditId);
        audit.setCloseTime(LocalDateTime.now(clock));

        List<Expense> expenses = expenseService.getByDateTimeBetween(audit.getStartTime(), audit.getCloseTime());
        List<Order> orders = orderService.getByDateTimeBetween(audit.getStartTime(), audit.getCloseTime());

        audit.setExpenses(expenses);
        audit.setOrders(orders);

        audit.setTotalExpensed(calculateExpenseTotal(audit));
        audit.setTotal(calculateTotal(audit));
        audit.setBalanceGap(calculateBalanceGap(audit));

        audit.setAuditStatus(AuditStatus.FINALIZED);

        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public AuditResponseDTO cancel(Long auditId) {
        Audit audit = getEntityById(auditId);
        audit.setDeleted(true);
        audit.setAuditStatus(AuditStatus.CANCELED);
        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public Audit getEntityById(Long auditId) {
        return auditRepository.findByIdAndBusiness_Id(auditId, businessContext.getCurrentBusinessId())
                .orElseThrow(() -> new AuditNotFoundException(auditId));
    }

    private void verifyAuditStatus(Long businessId) {
        if (auditRepository.existsByBusiness_Id(businessId)) {
            auditRepository.findTopByBusiness_IdOrderByIdDesc(businessId)
                    .filter(audit -> audit.getAuditStatus().equals(AuditStatus.IN_PROGRESS))
                    .ifPresent(audit -> {
                        throw new AuditInProgressException();
                    });
        }
    }

    private Double calculateExpenseTotal(Audit audit) {
        return audit.getExpenses().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    private Double calculateTotal(Audit audit) {
        return audit.getOrders().stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    private Double calculateBalanceGap(Audit audit) {
        return audit.getRealCash() - (audit.getTotal() + audit.getInitialCash() - audit.getTotalExpensed());
    }
}