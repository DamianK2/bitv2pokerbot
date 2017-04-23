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

    public Tweet()
    {

        String consumerKey = "OaARzvHyEtdpebYqRBr0R7fpn";
        String consumerKeySecret = "eKsSm18Lq2aMyUfUDUxcLDTCPSIoTetjOeCN585VGdL3KuRS2D";
        String accessToken = "851895473032105985-LwJFH6eqi80mfWeVbKE1G7HygV41Cc5";
        String accessTokenSecret = "oBWIJSFZFRyglFLyq0rjuPrWACyQPhvzJ3wYUqkRxKTqs";

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

    public void replyToTweet(String message, long messageId, String name) throws TwitterException
    {

        // Split messages that are too long into 140 characters only
        boolean fullMessage = false;
        String tempMessage = message;

        do {

            if (message.length() > 140) {
                message = message.substring(0, 140);
                //message = message.substring(130);
                tempMessage = tempMessage.substring(140);
            }
            else
                fullMessage = true;

            Twitter twitter = this.twitter;
            StatusUpdate statusUpdate = new StatusUpdate(message);
            statusUpdate.setInReplyToStatusId(messageId);
            try {
                twitter.updateStatus(statusUpdate);
            } catch (TwitterException e) {
                System.out.println("Something went wrong while posting tweet");
            }

            message = tempMessage;
        } while (!fullMessage);

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


    public void getMentions()
    {

        try {
            User user = twitter.showUser("bit2_poker");
            List<Status> statuses = twitter.getMentionsTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }

    public void getReplies(long messageId) throws TwitterException
    {
        Status status = twitter.showStatus(messageId);
        Status replyStatus = twitter.showStatus(status.getInReplyToStatusId());
        System.out.println(replyStatus.getText());
    }


    public void stream(String keyword) throws TwitterException
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

                // CREATE NEW POKER GAME
                GameOfPoker game = new GameOfPoker(status.getId(), "@" + status.getUser().getScreenName());
                game.playPoker();

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
        tweetFilterQuery.track(keyword);
        //tweetFilterQuery.language(new String[]{"en"});

        twitterStream.filter(tweetFilterQuery);
    }

    public static void main(String[] args) throws Exception
    {
        /*FileReader fileReader = new FileReader(new File("./secret.txt"));
        BufferedReader br = new BufferedReader(fileReader);

        String line;
        // if no more lines the readLine() returns null
        String[] keys = new String[4];
        int index = 0;
        while ((line = br.readLine()) != null) {
            keys[index] = line;
            index++;
        }

        Tweet tweet = new Tweet(keys[0], keys[1], keys[2], keys[3]);*/
        //tweet.stream(new String[]{"bit2_poker"});

        Tweet tweet = new Tweet();
        //tweet.post("Hello World!");
        //new Tweet().getTimelineTweets();
        //new Tweet().searchTweets("banana");
        tweet.stream("#bit2_poker");
        //tweet.replyToTweet("Reply to a tweet", 854669882297901056L);
        //tweet.getMentions();
        //tweet.getReplies(855381751207575552L);
    }

}
