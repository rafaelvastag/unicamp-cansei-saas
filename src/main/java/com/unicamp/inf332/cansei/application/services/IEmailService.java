package com.unicamp.inf332.cansei.application.services;

import org.springframework.mail.SimpleMailMessage;

import com.unicamp.inf332.cansei.domain.entities.Cliente;
import com.unicamp.inf332.cansei.domain.entities.Pedido;

public interface IEmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
