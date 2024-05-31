package com.CinePrime.app.repository;

import com.CinePrime.app.documents.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeliculaRepository extends MongoRepository<Pelicula, String> {
	
}