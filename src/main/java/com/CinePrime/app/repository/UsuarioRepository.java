package com.CinePrime.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.CinePrime.app.documents.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	public Usuario findByEmail(String email);
	
}
