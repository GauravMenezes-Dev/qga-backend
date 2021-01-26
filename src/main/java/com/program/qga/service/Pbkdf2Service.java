package com.program.qga.service;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

@Service
public class Pbkdf2Service {

	public static byte[] salt = "test-salt-string".getBytes();
	
	public String encodeIt(String password) {	
		try {
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 10, 512);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
	        byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
	        return Base64.getMimeEncoder().encodeToString(hash);
		}
		catch(Exception e) {
			System.out.println("\nError occured while trying to encode "+password+": \n"+e);
			return "";
		}
	}
	
	public boolean compare(String inputPass, String encPass){
		return this.encodeIt(inputPass).equals(encPass);
	}
}