package Blockchain;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BlockchainManager {

    static private String filename = "src/main/data/blockchain.dat";
    static private BlockchainManager instance;

    public static BlockchainManager getInstance() throws IOException, ClassNotFoundException {
        if(instance == null) {
            instance = new BlockchainManager(filename);
        }
        return instance;
    }

    private Blockchain blockchain;

    BlockchainManager(String filename) throws IOException, ClassNotFoundException {
        Path path = Paths.get(filename);

        try{
            if(!Files.exists(path)){
                Files.createFile(path);
                System.out.println("File created");
            }
            else System.out.println("File already exists");
        }catch(IOException e){
            e.printStackTrace();
        }

        // 如果无数据则新建
        if(Files.size(path) == 0){
            this.blockchain = new Blockchain();
        }
        else this.blockchain = deserialize();
    }

    public void serialize() throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(blockchain); // 对象 → JSON 字符串

        // 写入文件（可指定编码，如 UTF-8）
        try (FileWriter writer = new FileWriter(filename, StandardCharsets.UTF_8)) {
            writer.write(json);
        }
    }

    public Blockchain deserialize() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();

        // 读取 JSON 文件
        try (FileReader reader = new FileReader(filename, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Blockchain.class); // JSON → 对象
        }
    }

    public Boolean addTransaction(Transaction transaction) throws IOException, ClassNotFoundException {
        return blockchain.addTransaction(transaction);
    }

    public ArrayList<Transaction> trace(String productId){
        return blockchain.trace(productId);
    }
}
