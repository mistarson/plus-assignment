package plus.plusassignment.domain.post.repository.custom;

import static plus.plusassignment.domain.post.entity.QPost.post;
import static plus.plusassignment.domain.user.entity.QNormalUser.normalUser;
import static plus.plusassignment.domain.user.entity.QSocialUser.socialUser;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import plus.plusassignment.domain.post.dao.PostWithUser;
import plus.plusassignment.domain.post.entity.Post;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostWithUser> findAllPostsWithUser(Pageable pageable) {
        List<PostWithUser> content = jpaQueryFactory
                .select(Projections.constructor(PostWithUser.class, post.id, post.title,
                        post.content, post.createdTime, socialUser.id.coalesce(normalUser.id),
                        socialUser.username.coalesce(normalUser.username),
                        socialUser.email.coalesce(normalUser.email)))
                .from(post)
                .leftJoin(socialUser).on(socialUser.id.eq(post.userId))
                .leftJoin(normalUser).on(normalUser.id.eq(post.userId))
                .where(post.deleted.isFalse())
                .orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(post.count())
                .from(post);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder<>(Post.class, "post");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        return orders;
    }
}
