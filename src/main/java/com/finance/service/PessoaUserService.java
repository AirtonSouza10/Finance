package com.finance.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finance.ExceptionFinance;
import com.finance.dto.EnderecoRequestDTO;
import com.finance.dto.EnderecoResponseDTO;
import com.finance.dto.PessoaJuridicaRequestDTO;
import com.finance.dto.PessoaJuridicaResponseDTO;
import com.finance.model.Endereco;
import com.finance.model.PessoaJuridica;
import com.finance.model.Usuario;
import com.finance.repository.PessoaRepository;
import com.finance.repository.UsuarioRepository;

@Service
public class PessoaUserService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public PessoaJuridicaResponseDTO salvarPessoaJuridica(PessoaJuridicaRequestDTO pessoaJuridicaRequest) throws ExceptionFinance {
		if(pessoaJuridicaRequest == null) {
			throw new ExceptionFinance("Dados inválidos ou inexistentes.");
		}
		
		var pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj(pessoaJuridicaRequest.getCnpj());
		pessoaJuridica.setInscricaoEstadual(pessoaJuridicaRequest.getInscricaoEstadual());
		pessoaJuridica.setInscricaoMunicipal(pessoaJuridicaRequest.getInscricaoMunicipal());
		pessoaJuridica.setNomeFantasia(pessoaJuridicaRequest.getNomeFantasia());
		pessoaJuridica.setRazaoSocial(pessoaJuridicaRequest.getRazaoSocial());
		pessoaJuridica.setCategoria(pessoaJuridicaRequest.getCategoria());
		pessoaJuridica.setEmail(pessoaJuridicaRequest.getEmail());
		pessoaJuridica.setEmpresa(pessoaJuridica);
		pessoaJuridica.setNome(pessoaJuridicaRequest.getNome());
		pessoaJuridica.setTelefone(pessoaJuridicaRequest.getTelefone());
		pessoaJuridica.setTpPessoa(pessoaJuridicaRequest.getTpPessoa());
		pessoaJuridica.setEndereco(null);
		
		for (EnderecoRequestDTO enderecoRequest : pessoaJuridicaRequest.getEndereco()) {
			
			var endereco = new Endereco();
			endereco.setRua(enderecoRequest.getRua());
			endereco.setCep(enderecoRequest.getCep());
			endereco.setNumero(enderecoRequest.getNumero());
			endereco.setComplemento(enderecoRequest.getComplemento());
			endereco.setBairro(enderecoRequest.getBairro());
			endereco.setCidade(enderecoRequest.getCidade());
			endereco.setUf(enderecoRequest.getUf());
			endereco.setLogradouro(enderecoRequest.getLogradouro());
			endereco.setTipoEndereco(enderecoRequest.getTipoEndereco());
			endereco.setPessoa(pessoaJuridica); 
			endereco.setEmpresa(pessoaJuridica); 
			
			pessoaJuridica.getEndereco().add(endereco);
		}
		
		pessoaRepository.save(pessoaJuridica);
		var usuarioPj = usuarioRepository.findByPessoaIdOrLogin(pessoaJuridicaRequest.getId(), pessoaJuridicaRequest.getEmail());
		if(usuarioPj == null) {
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridicaRequest.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();	
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCript);
			
			usuarioPj = usuarioRepository.save(usuarioPj);
			var usuarioAcesso = usuarioRepository.findByPessoaIdOrLogin(usuarioPj.getId(),pessoaJuridicaRequest.getEmail());
			if(usuarioAcesso == null) {
				usuarioRepository.insereUserPj(usuarioPj.getId());
			}
				
		}
		
		var pessoaJuridicaResponseDTO = PessoaJuridicaResponseDTO.builder()
				.id(pessoaJuridica.getId())
				.nome(pessoaJuridica.getNome())
				.email(pessoaJuridica.getEmail())
				.telefone(pessoaJuridica.getTelefone())
				.tpPessoa(pessoaJuridica.getTpPessoa())
				.cnpj(pessoaJuridica.getCnpj())
				.inscricaoEstadual(pessoaJuridica.getInscricaoEstadual())
				.inscricaoMunicipal(pessoaJuridica.getInscricaoMunicipal())
				.nomeFantasia(pessoaJuridica.getNomeFantasia())
				.razaoSocial(pessoaJuridica.getRazaoSocial())
				.categoria(pessoaJuridica.getCategoria())
				.endereco(
						Optional.ofNullable(pessoaJuridica.getEndereco())
							.orElse(Collections.emptyList())
							.stream()
							.map(end -> EnderecoResponseDTO.builder()
								.rua(end.getRua())
								.cep(end.getCep())
								.numero(end.getNumero())
								.complemento(end.getComplemento())
								.bairro(end.getBairro())
								.cidade(end.getCidade())
								.uf(end.getUf())
								.logradouro(end.getLogradouro())
								.tipoEndereco(end.getTipoEndereco())
								.build())
							.collect(Collectors.toList())
					)
				.build();
		
		return pessoaJuridicaResponseDTO;
	}
}
