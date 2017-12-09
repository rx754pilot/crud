package com.crud.portal.rest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crud.portal.exceptions.RestException;
import com.crud.portal.exceptions.ServiceException;
import com.jedlab.framework.spring.rest.RestWriter;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException>
{

    private static Logger log = LoggerFactory.getLogger(ServiceExceptionMapper.class);

    @Override
    @RestWriter
    public Response toResponse(ServiceException se)
    {
        ValidationException ve = se.getValidationException();
        if (ve != null)
        {

            if (ve instanceof ConstraintViolationException)
            {
                ConstraintViolationException v = (ConstraintViolationException) ve;
                // List<ErrorMessage> errors =
                // JsonErrorBuilder.buildFromConstraint(v.getConstraintViolations());
                // return RestException.toResponse(errors);
            }
        }
        log.info("EXCEPTION IS : " + se.getMessage());
        return RestException.toResponse(se.getCode(), se.getMessage());
    }

}