package com.website.e_commerce.annotation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.*;
/**
 * {@code @Password} is a custom annotation for validating passwords according to specific rules.
 *
 * <p>This annotation can be applied to any field of type {@link String} within a class to enforce
 * password validation constraints such as length, presence of uppercase and lowercase characters,
 * digits, special characters, and the absence of whitespace.</p>
 *
 * <p>The validation logic is handled by the {@link PasswordConstraintsValidator} class, which
 * implements the necessary rules using the Passay library.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * public class UserRegistrationDTO {
 *
 *     @Password
 *     private String password;
 *
 *     // other fields, getters, and setters
 * }
 * }</pre>
 *
 * <p>The default error message can be overridden by providing a custom message in the annotation
 * definition, and the {@code groups} and {@code payload} elements can be used for advanced
 * validation scenarios.</p>
 *
 * @see PasswordConstraintsValidator
 * @see jakarta.validation.Constraint
 * @see org.passay.PasswordValidator
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintsValidator.class)
public @interface Password {

    /**
     * The error message that will be shown when the password is invalid.
     *
     * @return the error message
     */
    String message() default "Invalid password!";

    /**
     * Allows the specification of validation groups, to which this constraint belongs.
     *
     * @return the array of validation groups
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients to assign custom payload objects to a constraint.
     *
     * @return the array of payload classes
     */
    Class<? extends Payload>[] payload() default {};
}
