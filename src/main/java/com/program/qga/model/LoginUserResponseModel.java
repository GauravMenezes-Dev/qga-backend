package com.program.qga.model;

import lombok.Data;

@Data
public class LoginUserResponseModel {
	private String username;
	private String role;
	private String message;
}