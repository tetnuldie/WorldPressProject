package org.example.users;

import java.util.HashMap;
import java.util.Map;

public class UserFactory {

    private static final Map<UserType, User> userRepository;

    static {
        userRepository = new HashMap<>();
        userRepository.put(UserType.SUBSCRIBER, new User("UserOne", "OD0GVbqtXv&0UqS%yss)^M(Q", UserType.SUBSCRIBER, 4));
        userRepository.put(UserType.CONTRIBUTOR, new User("UserTwo", "8k6kTwma7Hm!UJOwzbw$KM#x", UserType.CONTRIBUTOR, 5));
        userRepository.put(UserType.AUTHOR, new User("UserThree", "#BoMb8%m&bhvoiGxBjbw89CP", UserType.AUTHOR, 6));
        userRepository.put(UserType.EDITOR, new User("UserFour", "uQIvPsh&UJl75F%IvoZRkPlC", UserType.EDITOR, 7));
        userRepository.put(UserType.ADMIN, new User("UserFive", "%wRNx8k1Xu7@bT*eK!Mr68q$", UserType.ADMIN, 8));
    }

    public static User getUser(UserType userType) {
        return userRepository.get(userType);
    }
}
