package com.unicamp.inf332.cansei.application.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.inf332.cansei.application.dto.EmailDTO;
import com.unicamp.inf332.cansei.application.services.AuthService;
import com.unicamp.inf332.cansei.application.services.UserService;
import com.unicamp.inf332.cansei.crosscutting.security.JWTUtil;
import com.unicamp.inf332.cansei.crosscutting.security.UserSS;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api(tags="Auth")
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;

    @ApiOperation("Atualiza token do usuário.")
    @ApiResponses(value = {
	    @ApiResponse(code = 200,
	        message = "Response Headers", 
	        responseHeaders = {
	            @ResponseHeader(name = "authorization", description = "Bearer <JWT value here>"),
	        })
    })
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

    @ApiOperation("Envia e-mail com instruções para recuperação de senha.")
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
