package plus.plusassignment.domain.post.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import plus.plusassignment.domain.post.service.PostService;

@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final PostService postService;

    // 초 분 시 일 월 요일 연도
    @Scheduled(cron = "* 0 0 * * *", zone = "Asia/Seoul")
    public void scheduleDelete90DaysOldPost() {
        postService.delete90DaysOldData(LocalDateTime.now().minusDays(90));
    }

}
