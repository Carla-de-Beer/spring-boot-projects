package com.cadebe.github_reader.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    String userName;
    String htmlUrl;
    String avatarUrl;
    String yearCreated;
    int numFollowers;
}
