package Blockchain;

import java.util.ArrayList;
import java.util.Date;

public class Blockchain {
    private ArrayList<Block> blocks;
    private ArrayList<Transaction> transactionBuffer;// 交易缓冲区，存储尚进入区块链的交易
    private int bufferSizeLimit = 10;

    Blockchain() {
        blocks = new ArrayList<Block>();
        transactionBuffer = new ArrayList<>();
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    // 添加新交易
    public Boolean addTransaction(Transaction transaction) {
        // 先验证交易合法性（当前卖家是否与上任买家一致）
        Transaction previousTransaction = findLatestTransaction(transaction.getProductId());
        if (previousTransaction != null) {
            if(!previousTransaction.getTo().equals(transaction.getFrom())) {
                return false;
            }
        }

        transactionBuffer.add(transaction);
        // 如果缓冲区到达上限，则增加新区快
        if(transactionBuffer.size() == bufferSizeLimit) {
            StringBuilder hash = new StringBuilder();
            if(!blocks.isEmpty()) {
                hash.append(blocks.getLast().calculateHash());
            }
            Block newBlock = new Block(hash.toString(), new Date(), new ArrayList<Transaction>(transactionBuffer));
            addBlock(newBlock);
            transactionBuffer.clear();
        }
        return true;
    }

    // 溯源
    public ArrayList<Transaction> trace(String productId){
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Block block : blocks) {
            for (Transaction transaction : block.getTransactions()) {
                if (transaction.getProductId().equals(productId)) {
                    transactions.add(transaction);
                }
            }
        }
        // 处理未进入区块链的数据
        for (Transaction transaction : transactionBuffer) {
            if (transaction.getProductId().equals(productId)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // 找到指定编号的最近交易
    private Transaction findLatestTransaction(String productId)
    {
        // 处理未进入区块链的数据
        for (int i = transactionBuffer.size() - 1; i >= 0; i--) {
            if (transactionBuffer.get(i).getProductId().equals(productId)) {
                return transactionBuffer.get(i);
            }
        }
        for (int j = blocks.size() - 1; j >= 0; j--) {
            ArrayList<Transaction> transactions = blocks.get(j).getTransactions();
            for (int i = transactions.size() - 1; i >= 0; i--) {
                if (transactions.get(i).getProductId().equals(productId)) {
                    return transactions.get(i);
                }
            }
        }
        return null;
    }

    // 验证block
    public Boolean validateBlocks(){
        String previousHash = "";
        for(Block block : blocks){
            // 验证块内哈希
            if(!block.validate())
                return false;

            // 验证链哈希
            if(!previousHash.isEmpty() && !block.getPreviousHash().equals(previousHash))
                return false;
            previousHash = block.calculateHash();
        }
        return true;
    }
}
