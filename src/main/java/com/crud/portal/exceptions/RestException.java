package com.crud.portal.exceptions;

import java.util.List;

import javax.ws.rs.core.Response;

import org.omidbiz.core.axon.Axon;
import org.omidbiz.core.axon.AxonBuilder;

public class RestException
{

    public static Response toResponse(int code, String msg)
    {
        StringBuilder sb = buildError(code, msg);
        return Response.status(Response.Status.BAD_REQUEST).entity(sb.toString()).build();
    }

    
    private static StringBuilder buildError(int code, String msg)
    {
        ErrorMessage errorMessage = new ErrorMessage(code, msg);
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"errors\" : [");
        String jsonError = new AxonBuilder().useWithPrettyWriter().create().toJson(errorMessage);
        sb.append(jsonError);
        sb.append("]}");
        return sb;
    }

    public static Response toResponse(List<ErrorMessage> errors)
    {
        // FIXME : use JAXB to convert errorList to json
        if (errors != null && errors.size() > 0)
        {
            StringBuilder sb = new StringBuilder("{");
            sb.append("\"errors\" : [");
            Axon axon = new AxonBuilder().useWithPrettyWriter().create();
            int i = 0;
            for (ErrorMessage errorMessage : errors)
            {
                if (i > 0)
                    sb.append(",");
                sb.append(axon.toJson(errorMessage));
                i++;
            }
            sb.append("]}");
            return Response.status(Response.Status.BAD_REQUEST).entity(sb.toString()).build();
        }
        return Response.status(Response.Status.SEE_OTHER).build();
    }
    
}
