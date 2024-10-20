package home.project.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import home.project.domain.*;
import home.project.dto.responseDTO.ProductResponse;
import home.project.dto.responseDTO.ProductResponseForManager;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProduct product = QProduct.product;
    private final QCategory category = QCategory.category;
    private final QProductOrder productOrder = QProductOrder.productOrder;
    private final QOrders orders = QOrders.orders;
    private final QShipping shipping = QShipping.shipping;


    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Product> findProducts(String brand, String categoryCode, String productName, String content, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (brand != null && !brand.isEmpty()) {
            builder.and(product.brand.toLowerCase().like("%" + brand.toLowerCase() + "%"));
        }
        if (categoryCode != null && !categoryCode.isEmpty()) {
            builder.and(product.category.code.toLowerCase().like(categoryCode.toLowerCase() + "%"));
        }
        if (productName != null && !productName.isEmpty()) {
            builder.and(product.name.toLowerCase().like("%" + productName.toLowerCase() + "%"));
        }
        if (content != null && !content.isEmpty()) {
            builder.or(product.brand.lower().like("%" + content.toLowerCase() + "%"))
                    .or(product.category.code.lower().like("%" + content.toLowerCase() + "%"))
                    .or(product.name.lower().like("%" + content.toLowerCase() + "%"));
        }

        List<Product> results = queryFactory
                .selectFrom(product)
                .leftJoin(product.category, category).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(product)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Product> findSoldProducts(String brand, String categoryCode, String productName, String content, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (brand != null && !brand.isEmpty()) {
            builder.and(product.brand.toLowerCase().like("%" + brand.toLowerCase() + "%"));
        }
        if (categoryCode != null && !categoryCode.isEmpty()) {
            builder.and(product.category.code.toLowerCase().like(categoryCode.toLowerCase() + "%"));
        }
        if (productName != null && !productName.isEmpty()) {
            builder.and(product.name.toLowerCase().like("%" + productName.toLowerCase() + "%"));
        }
        if (content != null && !content.isEmpty()) {
            builder.or(product.brand.lower().like("%" + content.toLowerCase() + "%"))
                    .or(product.category.code.lower().like("%" + content.toLowerCase() + "%"))
                    .or(product.name.lower().like("%" + content.toLowerCase() + "%"));
        }

        // PURCHASE_CONFIRMED 상태의 주문만 필터링
        builder.and(product.productOrder.any().orders.shipping.deliveryStatus.eq(DeliveryStatusType.PURCHASE_CONFIRMED));

        List<Product> results = queryFactory
                .selectFrom(product)
                .leftJoin(product.category, category).fetchJoin()
                .leftJoin(product.productOrder, productOrder).fetchJoin()
                .leftJoin(productOrder.orders, orders).fetchJoin()
                .leftJoin(orders.shipping, shipping).fetchJoin()
                .where(builder)
                .distinct()
                .orderBy(product.soldQuantity.desc()) // 정렬 추가
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(product)
                .leftJoin(product.productOrder, productOrder)
                .leftJoin(productOrder.orders, orders)
                .leftJoin(orders.shipping, shipping)
                .where(builder)
                .distinct()
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    public Page<Product> findAllBySoldQuantity(Pageable pageable){
        // 판매 갯수(soldQuantity)가 많은 순으로 정렬하여 상품 조회
        List<Product> results = queryFactory
                .selectFrom(product)
                .orderBy(product.soldQuantity.desc())  // soldQuantity 기준 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 상품 수 계산
        long total = queryFactory
                .selectFrom(product)
                .fetchCount();

        // 결과를 PageImpl로 감싸서 반환
        return new PageImpl<>(results, pageable, total);
    };

    @Override
    public Page<Product> findAllByOrderByBrandAsc(Pageable pageable) {
        List<Product> results = queryFactory
                .selectFrom(product)
                .orderBy(product.brand.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(product)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<Product> findAllByCategory(Category category) {
        return queryFactory
                .selectFrom(product)
                .where(product.category.id.eq(category.getId()))
                .fetch();
    }

    @Override
    public Page<Product> findTop20LatestProducts(Pageable pageable) {
        List<Product> results = queryFactory
                .selectFrom(product)
                .orderBy(product.createAt.desc())
                .offset(pageable.getOffset())
                .limit(Math.min(20L, pageable.getPageSize()))
                .fetch();

        long total = Math.min(20L, queryFactory
                .selectFrom(product)
                .fetchCount());

        return new PageImpl<>(results, pageable, total);
    }

}