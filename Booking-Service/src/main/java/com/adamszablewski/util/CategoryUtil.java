package com.adamszablewski.util;

import com.adamszablewski.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CategoryUtil {
    private final CategoryRepository categoryRepository;

    public boolean checkIfExists(String text){
        return categoryRepository.existsByName(text);
    }
}
