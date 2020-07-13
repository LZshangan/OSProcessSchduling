package process;

import java.awt.*;  
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;  

//����1:ͨ����JFrame�����һ��JPanel������ͼƬ����JPanel����ʵ��
public class FirstWindow extends JFrame         //Ĭ�ϵ���BorderLayout
{  
    //����һ������  
    Container ct;  
    //����������塣  
    BackgroundPanel bgp;         //�Զ���ı����� 

    //����һ����ť������֤�����ǵ�ȷ�Ǵ����˱���ͼƬ��������һ��ͼƬ��  
     
    public static void main(String[] args)  
    {  
        new FirstWindow();  
    }  
    
    
    
    public FirstWindow()  
    {
 
    	setTitle("���̵���");
    	//this.setBounds(500,100,40,324);
        //�������κβ��ַ�ʽ��  
        ct=this.getContentPane();
        getContentPane().setLayout(null);  

        //���»��Ʊ���ͼƬ  
        bgp=new BackgroundPanel((new ImageIcon("D:\\������\\3.jpg")).getImage()); //������һ��Image����,
        bgp.setForeground(Color.CYAN);
        bgp.setBounds(0,0,474,417);  
        ct.add(bgp);
        bgp.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("\u9009\u4E0D\u4E86\u5403\u4E8F\uFF0C\u9009\u4E0D\u4E86\u4E0A\u5F53");
        lblNewLabel.setFont(new Font("����", Font.PLAIN, 25));
        lblNewLabel.setForeground(Color.CYAN);
        lblNewLabel.setBounds(98, 87, 341, 77);
        bgp.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("ʱ��Ƭ��ת�㷨");
        lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 20));
        lblNewLabel_1.setForeground(Color.YELLOW);
        lblNewLabel_1.setBounds(99, 165, 140, 27);
        bgp.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("�����ȼ��㷨");
        lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 20));
        lblNewLabel_2.setForeground(Color.YELLOW);
        lblNewLabel_2.setBounds(99, 216, 140, 27);
        bgp.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("����Ӧ���㷨");
        lblNewLabel_3.setFont(new Font("����", Font.PLAIN, 20));
        lblNewLabel_3.setForeground(Color.YELLOW);
        lblNewLabel_3.setBounds(99, 268, 140, 27);
        bgp.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("�༶���������㷨");
        lblNewLabel_4.setFont(new Font("����", Font.PLAIN, 20));
        lblNewLabel_4.setForeground(Color.YELLOW);
        lblNewLabel_4.setBounds(99, 320, 167, 27);
        bgp.add(lblNewLabel_4);
        
        JButton btnNewButton = new JButton("\u8FD9\u4E2A\uFF1F");
        btnNewButton.setFont(new Font("����", Font.PLAIN, 20));
        btnNewButton.setForeground(Color.YELLOW);
        btnNewButton.setContentAreaFilled(false); 
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		new RRWindow().initWindow();
        		
        	}
        });
        btnNewButton.setBounds(264, 165, 113, 27);
        bgp.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("\u8FD9\u4E2A\uFF1F");
        btnNewButton_1.setFont(new Font("����", Font.PLAIN, 20));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new PSAWindow().initWindow();
        	}
        });
        btnNewButton_1.setForeground(Color.YELLOW);
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBounds(264, 216, 113, 27);
        bgp.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("\u8FD9\u4E2A\uFF1F");
        btnNewButton_2.setFont(new Font("����", Font.PLAIN, 20));
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new HRRNWindow().initWindow();
        	}
        });
        btnNewButton_2.setForeground(Color.YELLOW);
        btnNewButton_2.setContentAreaFilled(false);
        btnNewButton_2.setBounds(264, 268, 113, 27);
        bgp.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("\u8FD8\u662F\u8FD9\u4E2A\uFF1F");
        btnNewButton_3.setFont(new Font("����", Font.PLAIN, 20));
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		new MFQWindow().initWindow();
        	}
        	
        });
        btnNewButton_3.setForeground(Color.YELLOW);
        btnNewButton_3.setContentAreaFilled(false);
        btnNewButton_3.setBounds(264, 320, 150, 27);
        bgp.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("\u62DC\u62DC");
        btnNewButton_4.setForeground(Color.YELLOW);
        btnNewButton_4.setFont(new Font("����", Font.PLAIN, 20));
        btnNewButton_4.setContentAreaFilled(false); 
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}

			
        });
        btnNewButton_4.setBounds(347, 360, 113, 44);
        bgp.add(btnNewButton_4);

        this.setSize(492,464);  
        this.setLocation(400,300);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true);  
    }
}  



class BackgroundPanel extends JPanel  
{  
    Image im;  
    public BackgroundPanel(Image im)  
    {  
        this.im=im;  
        this.setOpaque(true);                    //���ÿؼ���͸��,����false,��ô����͸��
    }  
    //Draw the background again,�̳���Jpanle,��Swing�ؼ���Ҫ�̳�ʵ�ֵķ���,������AWT�е�Paint()
    public void paintComponent(Graphics g)       //��ͼ��
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  //����ָ��ͼ���е�ǰ���õ�ͼ��ͼ������Ͻ�λ�ڸ�ͼ������������ռ�� (x, y)��ͼ���е�͸�����ز�Ӱ��ô��Ѵ��ڵ�����

    }  
}
