package com.ourjupiter.springboot.domain.posts;

import lombok.AllArgsConstructor;

public enum FileUse {
    POSTS(1), CERTIFICATION(2), PROFILE(3);

    public int value;

    FileUse (int value) {
        this.value = value;
    }

}
