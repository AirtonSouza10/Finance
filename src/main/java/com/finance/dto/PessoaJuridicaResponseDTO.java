package com.finance.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaJuridicaResponseDTO {

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String tpPessoa;
	private List<EnderecoResponseDTO> endereco;
	
	private String cnpj;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String nomeFantasia;
	private String razaoSocial;
	private String categoria;
	
}
