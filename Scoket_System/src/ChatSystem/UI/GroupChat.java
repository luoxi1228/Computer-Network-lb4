package ChatSystem.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;


public class GroupChat {

    private JButton sendToAll = new JButton("发送群聊消息");
    public JTextField  text = new JTextField(40);
    private JButton openFileBtn = new JButton("发送文件");
    private JButton openImageBtn = new JButton("发送图片");
    public static JLabel imageLabel2 = new JLabel();
    String userName;  //由客户端登录时设置
    String des;
    String src;
    public static JTextArea textArea;
    public static JTextArea textArea1;
    JFrame jf = new JFrame("组聊");

    // 构造函数
    public GroupChat(String userName) {
        this.userName = userName;
        init();
    }

    // 初始化函数
    void init() {
        jf.setBounds(500,200,1000,700);  //设置坐标和大小
        jf.setResizable(false);  // 缩放为不能缩放

        JPanel jp1 = new JPanel();//文本
        jp1.setPreferredSize(new Dimension(600, 400));
        JLabel lable = new JLabel("用户：" + userName);
        textArea = new JTextArea("************************************进入成功，欢迎来到组聊室！*************************************\n*******当前组聊室用户：\n****罗皙\n****刘倬宇\n****100\n", 15, 50);
        textArea.setEditable(false);  // 设置为不可修改

        JScrollPane scroll = new JScrollPane(textArea);  // 设置滚动面板（装入textArea）
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  // 显示垂直条
        jp1.add(lable);
        jp1.add(scroll);

        jp1.add(text);
        jp1.add(sendToAll);
        jp1.add(openFileBtn);
        jp1.add(openImageBtn );

        //显示图片

        JPanel jp2=new JPanel();//显示图片
        textArea1=new JTextArea("\n*******===============================图片显示=========================\n", 1, 50);
        jp2.setPreferredSize(new Dimension(400, 300));
        imageLabel2.setIcon(null);
        jp2.add(textArea1);
        jp2.add(imageLabel2);


        //创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(jp1, BorderLayout.EAST);
        mainPanel.add(jp2, BorderLayout.WEST);

        // 将主面板添加到窗口中
        jf.getContentPane().add(mainPanel);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String closeNotice="！！！用户【"+userName+"】退出聊天室！！！" ;
                login.messageSend.sendNotice(closeNotice,userName);
                login.userClient.exit();
            }
        });
        // 调整窗口大小并显示窗口
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 设置右上角关闭图标的作用
        jf.setVisible(true);  // 设置可见
        listerner();
    }

    // “打开文件”调用函数
    String showFileOpenDialog(JFrame parent) {
        String src="";
        // 创建一个默认的文件选择器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹
        fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\send"));
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
        // 设置默认使用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.setFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
        // 打开文件选择框（线程将被堵塞，知道选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);  // 对话框将会尽量显示在靠近 parent 的中心
        // 点击确定
        if(result == JFileChooser.APPROVE_OPTION) {
            // 获取路径
            File file = fileChooser.getSelectedFile();
            src = file.getAbsolutePath();
        }
        return src;
    }

    //
    String showImageOpenDialog(JFrame parent) {
        String src="";
        // 创建一个默认的文件选择器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹
        fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\send"));
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("(txt)", "txt"));
        // 设置默认使用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.setFileFilter(new FileNameExtensionFilter("(jpg)", "jpg"));
        // 打开文件选择框（线程将被堵塞，知道选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);  // 对话框将会尽量显示在靠近 parent 的中心
        // 点击确定
        if(result == JFileChooser.APPROVE_OPTION) {
            // 获取路径
            File file = fileChooser.getSelectedFile();
            src = file.getAbsolutePath();
        }
        return src;
    }

    public void listerner() {
        sendToAll.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String msg = text.getText();
                        login.messageSend.sendPublicGroupMsg(msg,userName);
                        text.setText(" ");
                    }
                });
        //设置打开图片监听
        openImageBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String gettid=JOptionPane.showInputDialog(null,"请输入你想传送图片的用户：","传输图片",JOptionPane.PLAIN_MESSAGE);
                src=showImageOpenDialog(jf);
                des="C:\\Users\\ASUS\\Desktop\\accept\\2.jpg";
                login.file.sendGroupImage(src,des,userName,"100");
                login.file.sendGroupImage(src,des,userName,"刘倬宇");
                login.file.sendGroupImage(src,des,userName,"罗皙");
            }
        });
        // 设置“打开文件”监听
        openFileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String gettid=JOptionPane.showInputDialog(null,"请输入你想传送文件的用户：","传输文件",JOptionPane.PLAIN_MESSAGE);
                src=showFileOpenDialog(jf);
                des="C:\\Users\\ASUS\\Desktop\\accept\\1.txt";
                login.file.sendFileToAll(src,des,userName,"100");
                login.file.sendFileToAll(src,des,userName,"刘倬宇");
            }
        });
    }

}