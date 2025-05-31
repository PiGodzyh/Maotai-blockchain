import Blockchain.BlockchainManager;
import Form.Index;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UI {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            BlockchainManager manager = BlockchainManager.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        // 创建窗口
        {
            JFrame frame = new JFrame("Index");
            frame.setContentPane(new Index().main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            // 添加WindowListener监听关闭事件
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // 执行结束前的代码
                    System.out.println("窗口正在关闭，保存更新后的区块链中");

                    // 保存
                    try {
                        BlockchainManager.getInstance().serialize();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    // 然后退出程序
                    System.exit(0);
                }
            });
        }
    }
}
