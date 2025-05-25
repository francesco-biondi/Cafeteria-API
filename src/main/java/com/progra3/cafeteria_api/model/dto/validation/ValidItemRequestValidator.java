package com.progra3.cafeteria_api.model.dto.validation;

import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.SelectedProductOptionRequestDTO;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductGroup;
import com.progra3.cafeteria_api.model.entity.ProductOption;
import com.progra3.cafeteria_api.service.helper.ProductFinderService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidItemRequestValidator implements ConstraintValidator<ValidItemRequest, ItemRequestDTO> {

    private final ProductFinderService productFinderService;

    @Override
    public boolean isValid(ItemRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.productId() == null) return false;

        Product product = productFinderService.getEntityById(dto.productId());

        if (!product.getComposite()) return true;

        List<ProductGroup> groups = product.getProductGroups();

        boolean allGroupsOptional = groups.stream()
                .allMatch(group -> group.getMinQuantity() == 0);

        boolean hasSelectedOptions = dto.selectedOptions() != null && !dto.selectedOptions().isEmpty();

        if (!hasSelectedOptions && !allGroupsOptional) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("This product requires at least one option to be selected.")
                    .addPropertyNode("selectedOptions").addConstraintViolation();
            return false;
        }

        if (!hasSelectedOptions) return true;

        Map<Long, Long> optionToGroupMap = groups.stream()
                .flatMap(group -> group.getOptions().stream()
                        .map(option -> Map.entry(option.getId(), group.getId())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<Long, Integer> selectedCountPerGroup = new HashMap<>();
        Map<Long, Integer> selectedCountPerOption = new HashMap<>();

        for (SelectedProductOptionRequestDTO optionDto : dto.selectedOptions()) {
            Long optionId = optionDto.productOptionId();
            Integer quantity = optionDto.quantity();

            if (!optionToGroupMap.containsKey(optionId)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid option ID: " + optionId)
                        .addPropertyNode("selectedOptions").addConstraintViolation();
                return false;
            }

            Long groupId = optionToGroupMap.get(optionId);
            selectedCountPerGroup.merge(groupId, quantity, Integer::sum);
            selectedCountPerOption.merge(optionId, quantity, Integer::sum);
        }

        for (ProductGroup group : groups) {
            int count = selectedCountPerGroup.getOrDefault(group.getId(), 0);
            if (count < group.getMinQuantity() || count > group.getMaxQuantity()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Group '" + group.getName() +
                                "' requires between " + group.getMinQuantity() + " and " + group.getMaxQuantity() + " selections.")
                        .addPropertyNode("selectedOptions").addConstraintViolation();
                return false;
            }

            for (ProductOption option : group.getOptions()) {
                int countPerOption = selectedCountPerOption.getOrDefault(option.getId(), 0);
                if (countPerOption > option.getMaxQuantity()) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Option '" + option.getProduct().getName() +
                                    "' in group '" + group.getName() + "' can be selected at most " + option.getMaxQuantity() + " times.")
                            .addPropertyNode("selectedOptions").addConstraintViolation();
                    return false;
                }
            }
        }

        return true;
    }
}

