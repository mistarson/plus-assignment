package plus.plusassignment.domain.comment.repository.custom;

import static plus.plusassignment.domain.comment.entity.QComment.comment;
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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import plus.plusassignment.domain.comment.dao.CommentWithPostAndUser;
import plus.plusassignment.domain.comment.entity.Comment;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<CommentWithPostAndUser> findByIdWithPostAndUser(Long commentId) {

        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(CommentWithPostAndUser.class,
                        comment.id, comment.content, comment.createdTime,
                        post.id, post.title, post.content, post.createdTime,
                        socialUser.username.coalesce(normalUser.username),
                        socialUser.email.coalesce(normalUser.email)
                ))
                .from(comment)
                .join(post).on(comment.post.id.eq(post.id))
                .leftJoin(socialUser).on(socialUser.id.eq(comment.userId))
                .leftJoin(normalUser).on(normalUser.id.eq(comment.userId))
                .where(comment.id.eq(commentId))
                .fetchOne());
    }

    public Page<CommentWithPostAndUser> findAllByIdWithPostAndUser(Long postId, Pageable pageable) {

        List<CommentWithPostAndUser> content = jpaQueryFactory
                .select(Projections.constructor(CommentWithPostAndUser.class,
                        comment.id, comment.content, comment.createdTime,
                        post.id, post.title, post.content, post.createdTime,
                        socialUser.username.coalesce(normalUser.username),
                        socialUser.email.coalesce(normalUser.email)
                ))
                .from(comment)
                .join(post).on(comment.post.id.eq(post.id))
                .leftJoin(socialUser).on(socialUser.id.eq(comment.userId))
                .leftJoin(normalUser).on(normalUser.id.eq(comment.userId))
                .where(post.id.eq(postId))
                .orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.post.id.eq(postId));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder<>(Comment.class, "post");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        return orders;
    }

}
