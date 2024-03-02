package com.kodakro.mapshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodakro.mapshop.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	@Query(value = """
			select t from Token t inner join Customer c \s
			on t.customer.id = c.id\s
			where c.id = :id and (t.expired = false or t.revoked = false)\s
			""")
	List<Token> findAllValidTokenByUser(Integer id);

	Optional<Token> findByToken(String token);
}
