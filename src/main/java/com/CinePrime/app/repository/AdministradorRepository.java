package com.CinePrime.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.CinePrime.app.documents.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String>{

	public Administrador findByEmail(String email);
	
}
