package com.finance.security;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.finance.ApplicationContextLoad;
import com.finance.model.Usuario;
import com.finance.repository.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
@Component
public class JWTTokenAutenticacaoService {
	private static final long EXPIRATION_TIME = 864000000;
	private static final String SECRET = "teste";
	private static final String PREFIX_TOKEN = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username) throws Exception {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = PREFIX_TOKEN + " " + JWT;

		response.setContentType("application/json");
		response.addHeader(HEADER_STRING, token);

		liberacaoCors(response);

		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}

	private void liberacaoCors(HttpServletResponse response) {
		if (Objects.isNull(response.getHeader("Access-Control-Allow-Origin"))) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (Objects.isNull(response.getHeader("Access-Control-Allow-Headers"))) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (Objects.isNull(response.getHeader("Access-Control-Request-Headers"))) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (Objects.isNull(response.getHeader("Access-Control-Allow-Methods"))) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String token = request.getHeader(HEADER_STRING);

		try {

			if (Objects.nonNull(token)) {
				var tokenLimpo = token.replace(PREFIX_TOKEN, "").trim();

				var user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(tokenLimpo).getBody().getSubject();

				if (Objects.nonNull(user)) {
					Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
							.findUserByLogin(user);
					if (Objects.nonNull(usuario)) {
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
								usuario.getAuthorities());
					}
				}
			}

		} catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");
		} catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado. Efetue o login novamente");
		} finally {
			liberacaoCors(response);
		}

		return null;
	}
}
