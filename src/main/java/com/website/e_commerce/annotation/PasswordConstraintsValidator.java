package com.website.e_commerce.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;
/**
 * {@code PasswordConstraintsValidator} is a custom constraint validator that implements
 * the password validation logic for the {@link Password} annotation.
 *
 * <p>This class uses the Passay library to apply a series of validation rules on the
 * password, including:</p>
 * <ul>
 *   <li>Minimum and maximum length</li>
 *   <li>At least one uppercase letter</li>
 *   <li>At least one lowercase letter</li>
 *   <li>At least one digit</li>
 *   <li>At least one special character</li>
 *   <li>No whitespace allowed</li>
 * </ul>
 *
 * <p>The {@code isValid} method evaluates the password against these rules and returns
 * {@code true} if the password meets all criteria, otherwise {@code false}. If the validation
 * fails, a custom error message is generated based on the failed rule.</p>
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
 * @see Password
 * @see ConstraintValidator
 * @see org.passay.PasswordValidator
 * @see org.passay.RuleResult
 */

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    /**
     * Initializes the validator in preparation for isValid calls.
     *
     * @param constraintAnnotation the annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(Password constraintAnnotation) {
        // Initialization code, if necessary, goes here.
    }

    /**
     * Validates the password based on the rules specified.
     *
     * @param password the password to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return true if the password is valid, false otherwise
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator passwordValidator = new PasswordValidator(
                Arrays.asList(
                        // Length rule: Minimum 10, maximum 128 characters
                        new LengthRule(10, 128),

                        // At least one upper case letter
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),

                        // At least one lower case letter
                        new CharacterRule(EnglishCharacterData.LowerCase, 1),

                        // At least one digit
                        new CharacterRule(EnglishCharacterData.Digit, 1),

                        // At least one special character
                        new CharacterRule(EnglishCharacterData.Special, 1),

                        // No whitespace allowed
                        new WhitespaceRule()
                )
        );

        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        // Send custom error message based on failed validation rule
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                passwordValidator.getMessages(result).stream().findFirst().orElse("Invalid password!")
        ).addConstraintViolation().disableDefaultConstraintViolation();

        return false;
    }
}
