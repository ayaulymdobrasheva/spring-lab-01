package kz.iitu.springlab.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoverUrlValidator.class)
public @interface ValidCoverUrl {

    String message() default "{book.coverUrl.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}