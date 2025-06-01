package Blockchain;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    private String previousHash;
    private String transactionHash;
    private Date timestamp;
    private ArrayList<Transaction> transactions;

    public String getPreviousHash(){
        return previousHash;
    }

    Block(String previousHash, Date timestamp, ArrayList<Transaction> transactions) {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.transactionHash = calculateTransactionHash();
    }

    private String calculateTransactionHash() {
        StringBuilder input = new StringBuilder();
        for (Transaction transaction : transactions) {
            input.append(transaction.calculateHash());
        }
        return this.transactionHash = HashUtil.calculateHash(input.toString());
    }

    // 计算块头Hash
    public String calculateHash() {
        String input = this.previousHash + this.transactionHash + this.timestamp;
        return HashUtil.calculateHash(input);
    }

    public ArrayList<Transaction> getTransactions(){
        return this.transactions;
    }

    public Boolean validate(){
        return this.transactionHash.equals(calculateTransactionHash());
    }
}
