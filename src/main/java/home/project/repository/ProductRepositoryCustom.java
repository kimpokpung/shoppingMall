package home.project.repository;

import home.project.domain.Category;
import home.project.domain.Product;
import home.project.dto.responseDTO.ProductResponse;
import home.project.dto.responseDTO.ProductResponseForManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> findProducts(String brand, String category, String productName, String content, Pageable pageable);

    Page<Product> findSoldProducts(String brand, String category, String productName, String content, Pageable pageable);

    Page<Product> findAllBySoldQuantity(Pageable pageable);

    Page<Product> findAllByOrderByBrandAsc(Pageable pageable);
    List<Product> findAllByCategory(Category category);
    Page<Product> findTop20LatestProducts(Pageable pageable);


}
