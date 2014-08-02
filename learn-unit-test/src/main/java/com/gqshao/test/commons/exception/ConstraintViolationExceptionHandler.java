package com.gqshao.test.commons.exception;

import java.util.*;

import javax.validation.*;

import org.apache.commons.lang3.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import com.gqshao.test.commons.beanvalidator.*;

@ControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public final Object handleException(ConstraintViolationException cve, WebRequest request) {
        String bv = BeanValidators.extractPropertyAndMessageAsString(cve.getConstraintViolations(), " ", "--", "\n");
        logger.warn(bv);
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.isNotBlank(requestType)) {
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("success", false);
            res.put("msg", "输入字段错误");
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
        }
        return "error/500";
    }
}
