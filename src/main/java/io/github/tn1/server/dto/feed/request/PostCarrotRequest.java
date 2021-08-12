package io.github.tn1.server.dto.feed.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostCarrotRequest {

    private String title;
    private String description;
    private int price;
    private List<String> tags;

}
