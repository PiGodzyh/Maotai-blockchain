package Blockchain;

import java.util.Date;

public class Transaction {
    private String productId;
    private String from;
    private String to;
    private Date timestamp;

    // 添加无参构造函数供Gson使用
    public Transaction() {}

    public Transaction(String productId, String from, String to) {
        this.productId = productId;
        this.from = from;
        this.to = to;
        this.timestamp = new Date();
    }

    public String calculateHash() {
        String input = productId + from + to + timestamp;
        return HashUtil.calculateHash(input);
    }

    public String getProductId() {
        return productId;
    }

    public String getFromAndTo(){
        return from + " --> " + to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo(){
        return to;
    }
}