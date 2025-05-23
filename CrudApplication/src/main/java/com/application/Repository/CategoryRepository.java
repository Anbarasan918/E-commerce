package com.application.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.Pojo.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findById(Long id);
}
