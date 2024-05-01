package com.finance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finance.controller.AcessoController;
import com.finance.model.Acesso;
import com.finance.service.AcessoService;

@SpringBootTest(classes = FinanceApplication.class)
class FinanceApplicationTests {

	@Autowired
	private AcessoService acessoServie;
	@Autowired
	private AcessoController acessoController;

	@Test
	void testCadastraAcesso() {
		var acesso = new Acesso(null, "ROLE_ADMIN");
		acessoServie.save(acesso);
	}
	
	@Test
	void testCadastraAcessoController() {
		var acesso = new Acesso(null, "ROLE_ADMIN");
		acessoController.salvarAcesso(acesso);
	}
}
