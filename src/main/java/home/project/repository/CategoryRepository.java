package home.project.repository;


import home.project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCode(String categoryCode);

    boolean existsByName(String name);

    boolean existsByCode(String categoryCode);
}
