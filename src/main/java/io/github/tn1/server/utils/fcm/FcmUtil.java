package io.github.tn1.server.utils.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.github.tn1.server.dto.fcm.SendDto;
import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.tag_notification.TagNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmUtil {

    private static final String FIREBASE_CONFIG_PATH = "tn1-server-firebase-adminsdk.json";

    private final TagNotificationRepository tagNotificationRepository;

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
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
                .stream().filter(value -> value.getUser().haveDeviceToken())
                .forEach(value ->
                        send(
                                SendDto.builder()
                                        .token(value.getUser().getDeviceToken())
                                        .title("태그")
                                        .message("#" + tag + " 태그가 포함한 게시물이 올라왔어요!")
                                        .key("feed_id")
                                        .data(feed.getId().toString())
                                        .build()
                        )
                );
    }

    private void send(SendDto sendDto) {
        Message message = Message.builder()
                .setToken(sendDto.getToken())
                .putData(sendDto.getKey(), sendDto.getData())
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
