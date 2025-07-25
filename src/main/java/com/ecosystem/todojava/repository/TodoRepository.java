package com.ecosystem.todojava.repository;

import com.ecosystem.todojava.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}
