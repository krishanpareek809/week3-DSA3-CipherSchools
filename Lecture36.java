import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Twitter {

    static int timestamp = 0;
    public Map<Integer, User> userMap;

    class TweetNode {
        public int tweetId;
        public int time;
        public TweetNode next;

        public TweetNode(int tweetId) {
            this.tweetId = tweetId;
            time = timestamp;
            timestamp++;
            next = null;
        }
    }

    class User {

        public int userId;
        public Set<Integer> followed;
        public TweetNode tweetHead;

        public User(int userId) {
            this.userId = userId;
            this.followed = new HashSet<>();
            followed.add(userId);
            this.tweetHead = null;
        }

        public void post(int tweetId) {
            TweetNode tweetNode = new TweetNode(tweetId);
            tweetNode.next = tweetHead;
            tweetHead = tweetNode;
        }

        public void follow(int followeeId) {
            followed.add(followeeId);
        }

        public void unfollow(int followeeId) {
            followed.remove(followeeId);
        }

    }

    public Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {

        if (!userMap.containsKey(userId)) {
            User user = new User(userId);
            userMap.put(userId, user);
        }
        User user = userMap.get(userId);
        user.post(tweetId);
    }

    public List<Integer> getNewsFeed(int userId) {

        List<Integer> result = new LinkedList<>();

        if (!userMap.containsKey(userId)) {
            return result;
        }

        User user = userMap.get(userId);
        Set<Integer> followedUsers = user.followed;

        PriorityQueue<TweetNode> maxHeap = new PriorityQueue<>(followedUsers.size(),
                (tweet1, tweet2) -> (tweet2.time - tweet1.time));

        for (int id : followedUsers) {
            User u = userMap.get(id);
            TweetNode head = u.tweetHead;

            if (head != null) {
                maxHeap.add(head);
            }
        }

        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            TweetNode tweetNode = maxHeap.poll();
            result.add(tweetNode.tweetId);
            count++;
            if (tweetNode.next != null) {
                maxHeap.add(tweetNode.next);
            }
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User user = new User(followerId);
            userMap.put(followerId, user);
        }

        if (!userMap.containsKey(followeeId)) {
            User user = new User(followeeId);
            userMap.put(followeeId, user);
        }
        User user = userMap.get(followeeId);
        user.follow(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User user = new User(followerId);
            userMap.put(followerId, user);
        }

        if (!userMap.containsKey(followeeId)) {
            User user = new User(followeeId);
            userMap.put(followeeId, user);
        }
        User user = userMap.get(followeeId);
        user.unfollow(followeeId);
    }
}