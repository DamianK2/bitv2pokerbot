package poker;

public class TwitterInformation {

    private String gameMessage;
    private long originalMessageId;
    private long currentMessageId;
    private String playerName;

    public TwitterInformation(long messageId, String name) {
        this.originalMessageId = messageId;
        this.gameMessage = "";
        this.playerName = name;
    }

    public void updateGameMessage(String message) {
        System.out.println(message);
        this.gameMessage += message + "\n";
    }

    public long getOriginalMessageId() {
        return this.originalMessageId;
    }

    public long getCurrentMessageId() {
        return this.currentMessageId;
    }

    public void updateCurrentMessageId(long lastTweetId) {
        this.currentMessageId = lastTweetId;
    }

    public String getGameMessage() {
        return this.gameMessage;
    }

    public void clearGameMessage() {
        this.gameMessage = "";
    }

    public String getPlayerName() { return this.playerName; }


}
