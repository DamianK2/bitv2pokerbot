package poker;

import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.List;


public class Tweet {

    public static final int TWEET_CHARACTER_LIMIT = 140, SLEEP_PERIOD = 30000;
    public static final String BOT_NAME = "bit2_poker";
    private Twitter twitter;
    private Configuration build;

    public Tweet()
    {
        String[] keys = new String[4];
        
        try {
            FileReader fileReader = new FileReader(new File("./secret.txt"));
            BufferedReader br = new BufferedReader(fileReader);


            String line;
            // if no more lines the readLine() returns null
            int index = 0;

            try {
                while ((line = br.readLine()) != null) {
                    keys[index] = line;
                    index++;
                }
            } catch (IOException e) {
                System.out.println("Couldn't find keys.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find keys.");
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(keys[0])
                .setOAuthConsumerSecret(keys[1])
                .setOAuthAccessToken(keys[2])
                .setOAuthAccessTokenSecret(keys[3]);
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

    public synchronized long replyToTweet(String message, long messageId, String name) throws TwitterException
    {

        /* Always add user's name to the start of the tweet, so take away that amount of characters
         * Split messages that are too long into 140 characters only */
        boolean fullMessage = false;
        String tempMessage = message;
        Status status = null;
        int availableMessageLength = TWEET_CHARACTER_LIMIT - name.length() - 1;

        do {

            if (message.length() > availableMessageLength) {
                message = message.substring(0, availableMessageLength);
                //message = message.substring(130);
                tempMessage = tempMessage.substring(availableMessageLength);
            }
            else
                fullMessage = true;

            Twitter twitter = this.twitter;
            StatusUpdate statusUpdate = new StatusUpdate(name + "\n" + message);
            statusUpdate.setInReplyToStatusId(messageId);
            try {
                status = twitter.updateStatus(statusUpdate);
            } catch (TwitterException e) {
                System.out.println("Something went wrong while posting tweet");
            }

            message = tempMessage;
        } while (!fullMessage);
        
        System.out.println("Last tweet ID: " + status.getId());
	
        return status.getId();
    }
    
    public String getUserReply(long replyToId, String name) throws TwitterException {
    	
    	String userReply = "";
    	
    	try {
			Thread.sleep(Tweet.SLEEP_PERIOD);
		} catch (InterruptedException e) {
			System.out.println("Something went wrong while posting tweet Ask discard");
		}
    	
    	for (Status status : this.getTimelineTweets(name)) {
    		if (replyToId == status.getInReplyToStatusId())
    			userReply = status.getText();
        }

        // Each reply has mentioned username in the front. Remove it with the space after the name and @ symbol in the front
    	if (!userReply.equals("")) {
            userReply = userReply.substring(BOT_NAME.length() + 2);
            System.out.println("Returning: " + userReply);
        }
    	
    	return userReply;
    }

    public List<Status> getTimelineTweets(String name) throws TwitterException
    {
        Twitter twitter = this.twitter;
        Paging paging = new Paging(1, 20);
        List<Status> statuses = twitter.getUserTimeline(name, paging);
        return statuses;
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
        //tweet.getReplies(856914103268515840L);
    }

    
}
