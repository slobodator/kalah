package com.bol.game.kalah.validation.constraint;

import com.bol.game.kalah.validation.ElementsVaryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = ElementsVaryValidator.class)
@Documented
public @interface ElementsVary {
    String message() default "{constraint.ElementsVary.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
