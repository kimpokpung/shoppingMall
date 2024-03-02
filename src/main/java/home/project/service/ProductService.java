package home.project.service;

import home.project.domain.Member;
import home.project.domain.Product;

import java.util.List;

public interface ProductService {
    List<String> brandList();

    void join(Product product);
    void update(Product product);
    void delete(Product product);
    void validateDuplicateProduct(Product product);
    void emptyProduct (Product product);
    void productConfirm(Product product);
}