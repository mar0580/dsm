package com.diginet.springmvc.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.digicon.helper.GenericFunctionHelper;
import br.com.digicon.logs.LogDeleter;
import br.com.digicon.logs.LogReader;

import com.digicon.util.MsgTypeEnum;
import com.digicon.util.Util;

@Service("logService")
public class LogService {

	protected ResponseEntity<String> getMessage(String msg, MsgTypeEnum msgType) throws UnsupportedEncodingException {
		return new ResponseEntity<String>(Util.msg(msg, msgType), HttpStatus.OK);
	}
	 
	public ResponseEntity<String> getLog(String name){
		String separator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		
		try {
			List<String> lines = LogReader.read(name, null);
			for (String line : lines) {
				sb.append(line).append(separator);
			}
		} catch (Exception e) {
			System.out.println("Não foi possível recupurar informações do arquivo");
		}
		 
		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	}
	
	public ResponseEntity<String> deleteLog(String name) throws UnsupportedEncodingException{
		String msg = new String();

		try {
			msg = LogDeleter.mainDeleteLog(name, null);
			if (msg.startsWith("error")) {
				return getMessage("Não foi possível excluir arquivo de log", MsgTypeEnum.MSG_ERR);
			}

		} catch (Exception e) {
			return getMessage ("Não foi possível excluir arquivo de log", MsgTypeEnum.MSG_ERR);
		}

		return getMessage("Arquivo excluído com Êxito", MsgTypeEnum.MSG_INFO);
	}

	public ResponseEntity<String> sendLog(String name) throws UnsupportedEncodingException{
		
		String str_path = new String();
		
		try {
			str_path = GenericFunctionHelper.zipLog(name);
			if (str_path.toString().startsWith("error")) {
				return getMessage(str_path.toString(), MsgTypeEnum.MSG_ERR);
			}
		} catch (Exception e) {
			return getMessage("Não foi enviar o arquivo de log", MsgTypeEnum.MSG_ERR);
		}

		str_path = str_path.replace(":", "TWODOTS");
		str_path = str_path.replace("\\", "SLASH");
		
		return getMessage("Log salvo em BEGINSTRONG" + str_path + "ENDSTRONG", MsgTypeEnum.MSG_INFO);
	}
}
