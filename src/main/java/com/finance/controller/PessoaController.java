package com.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.finance.ExceptionFinance;
import com.finance.dto.PessoaJuridicaRequestDTO;
import com.finance.dto.PessoaJuridicaResponseDTO;
import com.finance.service.PessoaUserService;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@ResponseBody
	@PostMapping(value = "**/salvar-pessoa-juridica")
	public ResponseEntity<PessoaJuridicaResponseDTO> salvarPessoaJuridica(@RequestBody PessoaJuridicaRequestDTO pessoaJuridica) throws ExceptionFinance{
		var pessoaJuridicaReponse = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		return new ResponseEntity<PessoaJuridicaResponseDTO>(pessoaJuridicaReponse, HttpStatus.OK);
	}

}
