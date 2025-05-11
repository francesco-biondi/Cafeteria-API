package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.AuditInProgressException;
import com.progra3.cafeteria_api.exception.AuditNotFoundException;
import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.dto.mapper.AuditMapper;
import com.progra3.cafeteria_api.model.entity.Audit;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.enums.AuditStatus;
import com.progra3.cafeteria_api.repository.AuditRepository;
import com.progra3.cafeteria_api.service.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService implements IAuditService {

    private final AuditRepository auditRepository;

    private final ExpenseService expenseService;
    private final OrderService orderService;

    private final AuditMapper auditMapper;

    private final Clock clock;

    @Override
    public AuditResponseDTO createAudit(AuditRequestDTO dto) {
        AuditStatus auditStatus = findTop().getAuditStatus();

        if (auditStatus.equals(AuditStatus.IN_PROGRESS)) {
            throw new AuditInProgressException();
        }

        Audit audit = auditMapper.toEntity(dto);
        audit.setStartTime(LocalDateTime.now(clock));

        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public List<AuditResponseDTO> listAudits() {
        return auditRepository.findAll()
                .stream()
                .map(auditMapper::toDTO)
                .toList();
    }

    @Override
    public AuditResponseDTO finalizeAudit(Long auditId) {
        Audit audit = getEntityById(auditId);
        audit.setCloseTime(LocalDateTime.now(clock));

        List<Expense> expenses = expenseService.getByDateTimeBetween(audit.getStartTime(), audit.getStartTime());
        List<Order> orders = orderService.getByDateTimeBetween(audit.getStartTime(), audit.getCloseTime());

        audit.setExpenses(expenses);
        audit.setOrders(orders);

        audit.setTotalExpensed(calculateExpenseTotal(audit));
        audit.setTotal(calculateTotal(audit));

        audit.setAuditStatus(AuditStatus.FINALIZED);

        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public AuditResponseDTO cancelAudit(Long auditId) {
        Audit audit = getEntityById(auditId);
        audit.setDeleted(true);
        audit.setAuditStatus(AuditStatus.CANCELED);
        return auditMapper.toDTO(auditRepository.save(audit));
    }

    @Override
    public Audit getEntityById(Long auditId) {
        return auditRepository.findById(auditId).orElseThrow(() -> new AuditNotFoundException(auditId));
    }

    @Override
    public Audit findTop() {
        return auditRepository.findTopByOrderByIdDesc();
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
}