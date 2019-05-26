package Sources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class Window extends JFrame {
    public static void main(String[] args) {

        Window window=new Window();

    }
    private Window(){
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //选择文件夹
        jFileChooser.showOpenDialog(this);
        String filePath=String.valueOf(jFileChooser.getSelectedFile());//获得到的文件夹路径
        if(filePath.equals("null")){
            filePath="src/images"; //使用默认文件夹
        }
        File file= new File(filePath); //创建图片目录的文件对象
        String [] fileName = file.list();//获得文件列表
        assert fileName != null;
        List<String> list = new ArrayList<String>();
        for (String s : fileName){
            if (!Pattern.matches(".+(.JPEG|.jpeg|.JPG|.jpg|.png)$", s)) {//正则表达式
                continue;
            }
            list.add(s);
        }//筛选图片
        fileName=  list.toArray(new String[1]);
        final int[] now = {0};//当前页面图片编号
        final boolean[] flag = {false};//循环播放是否打开标志

        this.setLocation(150, 60);
        this.setLayout(new BorderLayout(0,0));

        JLabel ImagArea = new JLabel();

        final ImageIcon[] image = {new ImageIcon(filePath + "/" + fileName[now[0]])};
        ImagArea.setIcon(image[0]);
        ImagArea.setBounds(0, 0, 960, 540);

        ImageIcon lefticon = new ImageIcon("src/icons/left.png");
        ImageIcon righticon = new ImageIcon("src/icons/right.png");
        ImageIcon starticon = new ImageIcon("src/icons/start.png");
        JButton left = new JButton(lefticon);
        JButton right = new JButton(righticon);
        JButton start = new JButton(starticon);
        left.setContentAreaFilled(false);
        right.setContentAreaFilled(false);
        start.setContentAreaFilled(false);

        String[] finalFileName = fileName;
        String finalFilePath = filePath;

        right.addActionListener(e -> {
            flag[0] =false;
            Random random =new Random();
            now[0]=random.nextInt(finalFileName.length);
            ImagArea.setIcon(new ImageIcon(finalFilePath +"/"+ finalFileName[now[0]]));
            System.out.println(now[0]);
        });

        left.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                flag[0] =false;
                Random random =new Random();
                now[0]=random.nextInt(finalFileName.length);
                ImagArea.setIcon(new ImageIcon(finalFilePath +"/"+ finalFileName[now[0]]));
                System.out.println(now[0]);
            }
        });
       start.addActionListener(e -> new Thread(() -> {//创建线程
           flag[0] = !flag[0];//重复点击　切换循环/暂停
           System.out.println("线程开始");
           Random random = new Random();
           while (flag[0]) {
               now[0] = random.nextInt(finalFileName.length);
               ImagArea.setIcon(new ImageIcon(finalFilePath + "/" + finalFileName[now[0]]));
               System.out.println(now[0]);
               try {
                   Thread.sleep(500);
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
           }
           System.out.println("线程结束");
       }).start());

        this.add(BorderLayout.CENTER,ImagArea);
        this.add(BorderLayout.WEST,left);
        this.add(BorderLayout.EAST,right);
        this.add(BorderLayout.SOUTH,start);
        this.setSize(1680, 1020);
        this.setTitle("图片浏览器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
