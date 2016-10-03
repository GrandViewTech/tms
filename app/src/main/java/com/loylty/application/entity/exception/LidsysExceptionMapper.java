package com.loylty.application.entity.exception;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.common.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.loylty.application.entity.bo.master.Tenant;
import com.loylty.application.service.util.SpringContext;

public class LidsysExceptionMapper implements ExceptionMapper<LidsysWebServiceException>
	{
		private static org.apache.log4j.Logger	logger			= org.apache.log4j.Logger.getLogger(LidsysExceptionMapper.class);
		
		private static ObjectMapper				objectMapper	= new ObjectMapper();
		private static ObjectWriter				objectWriter	= objectMapper.writerWithDefaultPrettyPrinter();
		
		private String							logPath;
		
		public String getLogPath()
			{
				return logPath;
			}
			
		public void setLogPath(String logPath)
			{
				this.logPath = logPath;
			}
			
		@Override
		public Response toResponse(LidsysWebServiceException lidsysWebServiceException)
			{
				String tenantId = lidsysWebServiceException.getTenantId();
				String userId = lidsysWebServiceException.getUserId();
				Tenant tenant = SpringContext.getTenant(tenantId);
				String tenantName = (tenant == null) ? tenantId : tenant.getCorporateName();
				try
					{
						if ( userId.contains("@") )
							{
								userId = StringUtils.split(userId, "@")[0];
							}
						userId = userId.trim().toLowerCase();
						tenantName = tenantName.trim().toLowerCase();
						tenantName = tenantName.replaceAll("\\s", "_");
						String date = (new SimpleDateFormat("yyyy_M_dd.hh_mm_ss")).format(new Date());
						String fileName = "error.json";
						String path = logPath + File.separator + tenantName + File.separator + StringUtils.split(date, "\\.")[0] + File.separator + StringUtils.split(date, "\\.")[1] + File.separator + userId;
						File file = new File(path);
						if ( file.exists() == false )
							{
								FileUtils.forceMkdir(file);
							}
						FileWriter fileWriter = new FileWriter(new File(path + File.separator + fileName));
						fileWriter.write(objectWriter.writeValueAsString(lidsysWebServiceException));
						fileWriter.flush();
						fileWriter.close();
					}
				catch (Exception exception)
					{
						logger.error(exception.getLocalizedMessage(), exception);
					}
				ErrorResponse errorResponse = new ErrorResponse(lidsysWebServiceException);
				return Response.serverError().header("exception", lidsysWebServiceException.getMessage()).encoding("utf-8").entity(errorResponse).status(lidsysWebServiceException.getStatus()).build();
			}
			
	}
