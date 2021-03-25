package com.ecommerce.store.repository;

import com.ecommerce.store.model.CommandItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommandItemRepository extends JpaRepository<CommandItem, Long> {

}
