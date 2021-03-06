package io.github.tn1.server.global.utils.fcm;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.github.tn1.server.global.utils.fcm.dto.SendDto;
import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.notification.domain.NotificationEntity;
import io.github.tn1.server.domain.notification.domain.repository.NotificationRepository;
import io.github.tn1.server.domain.question.domain.Question;
import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.tag_notification.TagNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmUtil {

	@Value("${firebase.path}")
    private String path;

    private final NotificationRepository notificationRepository;
    private final TagNotificationRepository tagNotificationRepository;

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(path).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendTagNotification(String tag, Feed feed) {
        tagNotificationRepository.findByTag(tag)
                .stream().filter(value -> value.getUser().isNotification())
                .forEach(value ->
                        send(
                                SendDto.builder()
										.user(value.getUser())
                                        .title("??????")
                                        .message("#" + tag + " ????????? ????????? ???????????? ???????????????!")
                                        .key("feed_id")
                                        .data(feed.getId().toString())
                                        .build()
                        )
                );
    }

    public void sendUserResultNotification(Report report, boolean black) {
    	String message;
    	if(black) {
    		message = "??? ???????????? ???????????????.";
		} else {
    		message = "??? ???????????? ???????????????.";
		}

		send(
				SendDto.builder()
				.user(report.getReporter())
				.title("??????")
				.message(report.getDefendant().getName() + message)
				.key("report_id")
				.data(report.getId().toString())
				.build()
		);
	}

	public void sendFeedResultNotification(Report report, boolean delete) {
    	String message;
    	if(delete){
    		message = "???????????? ???????????? ???????????????.";
		} else {
    		message = "???????????? ???????????? ?????? ???????????????.";
		}

		send(
				SendDto.builder()
						.user(report.getReporter())
						.title("??????")
						.message(report.getDefendant().getName() + message)
						.key("report_id")
						.data(report.getId().toString())
						.build()
		);
	}

	public void sendQuestionResultNotification(Question question) {
		send(
				SendDto.builder()
						.user(question.getUser())
						.title("??????")
						.message("[ " + question.getTitle() + " ] ?????? ????????? ???????????????.")
						.key("question_id")
						.data(question.getId().toString())
						.build()
		);
	}

    private void send(SendDto sendDto) {
    	Long notificationId = notificationRepository.save(
				NotificationEntity.builder()
				.user(sendDto.getUser())
				.title(sendDto.getTitle())
				.message(sendDto.getMessage())
				.content(sendDto.getData())
				.build()
		).getId();
    	if(sendDto.getUser().haveDeviceToken()) {
			Message message = Message.builder()
					.setToken(sendDto.getUser().getDeviceToken())
					.putData(sendDto.getKey(), sendDto.getData())
					.putData("notification_id", notificationId.toString())
					.setNotification(
							Notification.builder()
									.setTitle(sendDto.getTitle())
									.setBody(sendDto.getMessage())
									.build()
					)
					.build();
			FirebaseMessaging.getInstance().sendAsync(message);
		}
    }

}
