package com.finance.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finance.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	@Query(value = "select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
	
	Usuario findByPessoaIdOrLogin(Long id, String login);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value = "insert into usuarios_acesso(usuario_id, acesso_id) values (?1,(select id from acesso where descricao = 'ROLE_USER')) ")
	void insereUserPj(Long id);
}
