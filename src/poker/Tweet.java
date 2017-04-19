package poker;

import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;


public class Tweet {

    private Twitter twitter;
    private Configuration build;

    public Tweet(String consumerKey, String consumerKeySecret, String accessToken, String accessTokenSecret)
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerKeySecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        this.build = cb.build();
        TwitterFactory tf = new TwitterFactory(this.build);
        this.twitter = tf.getInstance();
    }

    public void post(String message) throws TwitterException
    {
        Twitter twitter = this.twitter;
        Status status = twitter.updateStatus(message);
        System.out.println("Successfully updated the status to [" + status.getText() + "].");
    }

    public void replyToTweet(String message, long messageId) throws TwitterException
    {
        Twitter twitter = this.twitter;
        StatusUpdate statusUpdate = new StatusUpdate(message);
        statusUpdate.setInReplyToStatusId(messageId);
        try {
            twitter.updateStatus(statusUpdate);
        } catch (TwitterException e) {
            System.out.println("Something went wrong while posting tweet");
        }

    }

    public void getTimelineTweets() throws TwitterException
    {
        Twitter twitter = this.twitter;
        List<Status> statuses = twitter.getHomeTimeline();
        System.out.println("Showing home timeline.");
        for (Status status : statuses) {
            System.out.println(status.getUser().getName() + ":" +
                    status.getText());
        }
    }

    // Be careful, this can exceed limit of tweets
    public void searchTweets(String search) throws TwitterException
    {
        Twitter twitter = this.twitter;

        try {
            Query query = new Query(search);
            QueryResult result;

            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }


    }

    public void stream(String[] keywords) throws TwitterException
    {
//        ConfigurationBuilder cb = new ConfigurationBuilder();
//        cb.setDebugEnabled(true)
//                .setOAuthConsumerKey(CONSUMER_KEY)
//                .setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
//                .setOAuthAccessToken(ACCESS_TOKEN)
//                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        //TwitterFactory tf = new TwitterFactory(cb.build());
        //Twitter twitter = tf.getInstance();


        TwitterStream twitterStream = new TwitterStreamFactory(this.build).getInstance();

        twitterStream.addListener(new StatusListener () {
            public void onStatus(Status status) {
                System.out.println("ID: " + status.getId() + " @" + status.getUser().getScreenName() + " " + status.getText()); // print tweet text to console
            }

            @Override
            public void onException(Exception e) {
                System.out.println("Exception occured:" + e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onTrackLimitationNotice(int n) {
                System.out.println("Track limitation notice for " + n);
            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                System.out.println("Stall warning");
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                System.out.println("Scrub geo with:" + arg0 + ":" + arg1);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                System.out.println("Status deletion notice");
            }
        });

        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(keywords);
        tweetFilterQuery.language(new String[]{"en"});

        twitterStream.filter(tweetFilterQuery);
    }

    public static void main(String[] args) throws Exception
    {
        FileReader fileReader = new FileReader(new File("./secret.txt"));
        BufferedReader br = new BufferedReader(fileReader);

        String line;
        // if no more lines the readLine() returns null
        String[] keys = new String[4];
        int index = 0;
        while ((line = br.readLine()) != null) {
            keys[index] = line;
            index++;
        }

        Tweet tweet = new Tweet(keys[0], keys[1], keys[2], keys[3]);
        //tweet.stream(new String[]{"bit2_poker"});

        //tweet.post("Hello World!");
        //new Tweet().getTimelineTweets();
        //new Tweet().searchTweets("banana");
        //new Tweet().stream();
        tweet.replyToTweet("Reply to a tweet", 854669882297901056L);
    }

}
