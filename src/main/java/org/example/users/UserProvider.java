package org.example.users;

import java.util.HashMap;
import java.util.Map;

public class UserProvider {

    private static final Map<UserType, User> userRepository;

    static {
        userRepository = new HashMap<>();
        userRepository.put(UserType.SUBSCRIBER, new User("UserOne", "OD0GVbqtXv&0UqS%yss)^M(Q", UserType.SUBSCRIBER));
        userRepository.put(UserType.CONTRIBUTOR, new User("UserTwo", "8k6kTwma7Hm!UJOwzbw$KM#x", UserType.CONTRIBUTOR));
        userRepository.put(UserType.AUTHOR, new User("UserThree", "#BoMb8%m&bhvoiGxBjbw89CP", UserType.AUTHOR));
        userRepository.put(UserType.EDITOR, new User("UserFour", "uQIvPsh&UJl75F%IvoZRkPlC", UserType.EDITOR));
        userRepository.put(UserType.ADMIN, new User("UserFive", "%wRNx8k1Xu7@bT*eK!Mr68q$", UserType.ADMIN));
    }

    public static User getUser(UserType userType) {
        return userRepository.get(userType);
    }
}
