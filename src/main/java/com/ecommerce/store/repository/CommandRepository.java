package com.ecommerce.store.repository;

import com.ecommerce.store.model.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

}
