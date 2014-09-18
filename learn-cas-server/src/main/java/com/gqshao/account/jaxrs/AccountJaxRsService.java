package com.gqshao.account.jaxrs;

import com.gqshao.account.entity.ResultDTO;
import com.gqshao.commons.web.MediaTypes;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/user")
public class AccountJaxRsService {

    @GET
    @Path("/{loginName}/{custom}")
    @Consumes(MediaTypes.JSON_UTF_8)
    @Produces(MediaTypes.APPLICATION_XML_UTF_8)
    public ResultDTO query(@PathParam("loginName") String loginName, @PathParam("custom") String custom) {
        if (StringUtils.isBlank(custom)) {
            String message = "用户不存在(id:" + loginName + ")";
            throw buildException(Response.Status.NOT_FOUND, message);
        }
        ResultDTO res = new ResultDTO();
        res.setSuccess(true);
        res.setMsg("用户存在(id:" + loginName + ")");
        return res;
    }

    @POST
    @Path("/{loginName}/{custom}")
    @Consumes(MediaTypes.JSON_UTF_8)
    @Produces(MediaTypes.APPLICATION_XML_UTF_8)
    public ResultDTO add(@PathParam("loginName") String loginName, @PathParam("custom") String custom, Map<String, Object> body) {
        if (StringUtils.isBlank(custom)) {
            String message = "用户不存在(id:" + loginName + ")";
            throw buildException(Response.Status.NOT_FOUND, message);
        }
        ResultDTO res = new ResultDTO();
        res.setSuccess(true);
        res.setMsg("用户存在(id:" + loginName + ")");
        return res;
    }

    private WebApplicationException buildException(Response.Status status, String message) {
        return new WebApplicationException(Response.status(status).entity(message)
                .type(MediaTypes.TEXT_PLAIN_UTF_8).build());
    }
}
