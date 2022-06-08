package com.diginet.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digicon.util.Util;
import com.digicon.util.MsgTypeEnum;

import br.com.digicon.helper.GenericFunctionHelper;
import br.com.digicon.logs.Log;
 

@Controller
@CrossOrigin(origins = "localhost:8081", maxAge = 3600)
@RequestMapping("/downloads") 
public class DownloadController extends AbstractController { 
	
	@RequestMapping(value="/log/{name}", method=RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody 
	public String download(HttpServletResponse response, @PathVariable String name, HttpSession session,
			HttpServletResponse httpServletResponse ) throws Exception {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		response.setContentType("application/force-download");  
	    response.setHeader("Content-Transfer-Encoding", "binary");    
	    response.setHeader("Content-Disposition","attachment; filename=\"" + name+".zip\"");//fileName);
	    InputStream inputStream = new FileInputStream(createLognGetPath(name)); 
	    IOUtils.copy(inputStream, response.getOutputStream()); 
	    response.getOutputStream().flush();      
	    return IOUtils.toString(inputStream);      
	}
	
	@RequestMapping(value="/check/{name}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody 
	public ResponseEntity<String> download(@PathVariable String name,  HttpSession session,
			HttpServletResponse httpServletResponse) throws Exception {
		
		configureHttpResponse(httpServletResponse);
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		
		String msg = ""; 
		String filePath = Log.getPath(name, null);
		File file = new File(filePath);  
		if (file.exists()){
			msg = Util.msg("true", MsgTypeEnum.MSG_INFO);	  
		} else { 
			msg = Util.msg("false", MsgTypeEnum.MSG_INFO); 
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);  
	}   
	    
	public String createLognGetPath(String name ) {  
		String str_path = new String();
		try { 
			str_path = GenericFunctionHelper.zipLog(name); 
			if (str_path.startsWith("error")) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return str_path;
	}
}
