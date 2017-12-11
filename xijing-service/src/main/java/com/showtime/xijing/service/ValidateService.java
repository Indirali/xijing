package com.showtime.xijing.service;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class ValidateService {


    public <T> void validateObject(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            Assert.isTrue(constraintViolation.getPropertyPath() == null && constraintViolation.getMessage() == null,
                    constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
        }
    }

    public <T> void validateObjectList(List<T> object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        for (T obj : object) {
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                Assert.isTrue(constraintViolation.getPropertyPath() == null && constraintViolation.getMessage() == null,
                        constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage());
            }
        }
    }

}
