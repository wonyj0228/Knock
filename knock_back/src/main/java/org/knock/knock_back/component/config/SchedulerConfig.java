package org.knock.knock_back.component.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knock.knock_back.service.crawling.movie.CGV;
import org.knock.knock_back.service.crawling.movie.LOTTE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.knock.knock_back.service.crawling.movie.KOFIC;
import org.knock.knock_back.service.crawling.movie.MegaBox;

/**
 * @author nks
 * @apiNote Scheduler 로 제어되는 설정들
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final KOFIC kofic;
    private final MegaBox megaBox;
    private final CGV cgv;
    private final LOTTE lotte;

    @Value("${schedule.kofic.use}")
    private boolean useScheduleKOFIC;

    @Value("${schedule.megabox.use}")
    private boolean useScheduleMegaBox;

    @Value("${schedule.cgv.use}")
    private boolean useScheduleCGV;

    @Value("${schedule.lotte.use}")
    private boolean useScheduleLotte;

    /**
     * 주기적으로 KOFIC 에서 영화 정보를 받아온다.
     * @apiNote cronTab = 1시간에 1번, 정시
     */
    @Async
    @Scheduled(cron = "${schedule.kofic.cron}")
    public void koficJob() {

        try
        {
            if (useScheduleKOFIC)
            {
                kofic.requestAPI();
            }
        }
        catch (Exception e)
        {
            logger.debug("[{}] 예기치 않은 종료 : ", e.getMessage());
        }
    }

    /**
     * 주기적으로 MegaBox 에서 상영 예정작 정보를 받아온다.
     * @apiNote cronTab = 매일 오전 3시
     */
    @Async
    @Scheduled(cron = "${schedule.megabox.cron}")
    public void megaBoxJob() {

        try
        {
            if (useScheduleMegaBox)
            {
                megaBox.addNewIndex();
            }
        }
        catch (Exception e)
        {
            logger.debug("[{}] 예기치 않은 종료 : ", e.getMessage());
        }
    }

    /**
     * 주기적으로 CGV 에서 상영 예정작 정보를 받아온다.
     * @apiNote cronTab = 매일 오전 4시
     */
    @Async
    @Scheduled(cron = "${schedule.cgv.cron}")
    public void CGVJob() {

        try
        {
            if (useScheduleCGV)
            {
                cgv.addNewIndex();
            }
        }
        catch (Exception e)
        {
            logger.debug("[{}] 예기치 않은 종료 : ", e.getMessage());
        }
    }

    /**
     * 주기적으로 Lotte 에서 상영 예정작 정보를 받아온다.
     * @apiNote cronTab = 매일 오전 4시
     */
    @Async
    @Scheduled(cron = "${schedule.lotte.cron}")
    public void LotteJob() {

        try
        {
            if (useScheduleLotte)
            {
                lotte.addNewIndex();
            }
        }
        catch (Exception e)
        {
            logger.debug("[{}] 예기치 않은 종료 : ", e.getMessage());
        }
    }
}
