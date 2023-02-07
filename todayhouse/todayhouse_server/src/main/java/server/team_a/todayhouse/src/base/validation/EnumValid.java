package server.team_a.todayhouse.src.base.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {
    String message() default "Enum에 없는 값입니다.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default false;
}
