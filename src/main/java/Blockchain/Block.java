package Blockchain;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    private String previousHash;
    private String transactionHash;
    private Date timestamp;
    private ArrayList<Transaction> transactions;

    Block(String previousHash, Date timestamp, ArrayList<Transaction> transactions) {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.transactions = transactions;
        calculateTransactionHash();
    }

    private void calculateTransactionHash() {
        StringBuilder input = new StringBuilder();
        for (Transaction transaction : transactions) {
            input.append(transaction.calculateHash());
        }
        this.transactionHash = HashUtil.calculateHash(input.toString());
    }

    public String calculateHash() {
        String input = this.previousHash + this.transactionHash + this.timestamp;
        return HashUtil.calculateHash(input);
    }

    public ArrayList<Transaction> getTransactions(){
        return this.transactions;
    }
}
