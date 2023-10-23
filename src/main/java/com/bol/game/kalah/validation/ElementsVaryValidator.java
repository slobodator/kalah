package com.bol.game.kalah.validation;

import com.bol.game.kalah.validation.constraint.ElementsVary;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.Set;

public class ElementsVaryValidator implements ConstraintValidator<ElementsVary, Collection<?>> {

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext context) {
        return collection.size() == Set.copyOf(collection).size();
    }
}
