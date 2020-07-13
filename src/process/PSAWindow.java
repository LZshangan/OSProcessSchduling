package process;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.LinkedList;


public class PSAWindow {
	public PSAWindow() {
	}
    private static JFrame frame = new JFrame("高优先级调度算法");
    
    private static JPanel panel = new JPanel();
    private static JPanel panel1 = new JPanel();
    //private static JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    //菜单组件
    private static JMenuBar menuBar = new JMenuBar();
    private static JMenu processSettingsMenu = new JMenu("菜单");
    private static JMenuItem RandomcreateProcessItem = new JMenuItem("随机创建");
    private static JMenuItem startPSAItem = new JMenuItem("开始调度");
    private static JMenuItem stopPSAItem = new JMenuItem("结束调度");
    private static JMenuItem HandcreateProcessItem = new JMenuItem("手动创建");
    private static JMenuItem exitSystemItem = new JMenuItem("退出");
    private static JMenu helpMenu = new JMenu("帮助");
    private static JMenuItem aboutItem = new JMenuItem("关于");

    //设置优先级最49)的队列的时间片大小默认单位：秒
    public static double timeSlice = 1;

    public static double PCBsQueuesTimeSlice[] = new double[10];

    //多级反馈队列
    public static PCBsQueue[] PCBsQueues = new PCBsQueue[10];

    //记录已经使用的pid
    public static int[] pidsUsed = new int[101];

    //当前内存中的进程
    public static int currentPCBsNum = 0;

    //内存中能够容纳的大进程数（这里取决于可分配的pid的个数）
    public static final int PCBS_MAX_NUM = 100;

    //是否停止调度
    public static boolean isStopScheduling;
    private final JButton btnNewButton = new JButton("返回");
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private final JLabel lblNewLabel_4 = new JLabel("时钟");
    private final static JTextArea textArea = new JTextArea();
    public static  float nowTime=0;
    
    //private JEditorPane lblNewLabe_l,lblNewLabe_2,lblNewLabel_3;

	

    
    
    //很短的main函数
    public static void main(String[] args)
    {
        new PSAWindow().initWindow();
    }



    //执行窗口初始
    public void initWindow()
    {
    	
    	
        //设置窗口风格为Windows风格
        setWindowsStyle();
        processSettingsMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));

        //创建菜单
        processSettingsMenu.add(RandomcreateProcessItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(startPSAItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(stopPSAItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(HandcreateProcessItem);
        processSettingsMenu.addSeparator();
        processSettingsMenu.add(exitSystemItem);
        helpMenu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 20));

        helpMenu.add(aboutItem);

        menuBar.add(processSettingsMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        initMemory();

        //panel.setSize(1500,700);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //panel.setBounds(220,100,1500,300);
        /*Box vBox = Box.createVerticalBox();
        vBox.add(panel);
        vBox.add(panel1);
        frame.getContentPane().add(vBox);
        panel.setLayout(null);
        panel1.setLayout(null);
        */
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,panel1,panel); //这里第一个参数是控制分割线竖直，第二个参数是当你拖曳切割面版的分隔线时，窗口内的组件是否会随着分隔线的拖曳而动态改变大小，最后两个参数就是我分割完成后分割线两边各添加哪个容器。
        jSplitPane.setDividerLocation(300); //分割线的位置  也就是初始位置
        jSplitPane.setOneTouchExpandable(false); //是否可展开或收起，在这里没用
        jSplitPane.setDividerSize(2);//设置分割线的宽度 像素为单位
        jSplitPane.setEnabled(false); //设置分割线不可拖动！！
       
        frame.getContentPane().add(jSplitPane);  //加入到面板中就好了
        panel1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("手动新建进程：");
        lblNewLabel.setBounds(79, 6, 140, 24);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("1.请输入进程ID(1~100):");
        lblNewLabel_1.setBounds(38, 43, 220, 24);
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(89, 80, 106, 30);
        textField.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(textField);
        textField.setColumns(10);
        textField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped(e);
				
			}
		});
        
        JLabel lblNewLabel_2 = new JLabel("2.请输入进程优先级(0~9):");
        lblNewLabel_2.setBounds(38, 123, 240, 24);
        lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel_2);
        
        textField_1 = new JTextField();
        textField_1.setBounds(89, 160, 106, 30);
        textField_1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(textField_1);
        textField_1.setColumns(10);
        textField_1.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped1(e);
				
			}
		});
        
        JLabel lblNewLabel_3 = new JLabel("3.请输入进程服务时间:");
        lblNewLabel_3.setBounds(38, 203, 210, 24);
        lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(lblNewLabel_3);
        
        textField_2 = new JTextField();
        textField_2.setBounds(89, 240, 106, 30);
        textField_2.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(textField_2);
        textField_2.setColumns(10);
        textField_2.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped2(e);
				
			}
		});
        
        JButton btnNewButton_1 = new JButton("添加");
        btnNewButton_1.setBounds(89, 293, 106, 52);
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		toAdd();
        	}
        });
        
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 20));
        panel1.add(btnNewButton_1);
        btnNewButton.setBounds(164, 533, 114, 65);
        
        btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        	}       	       
        });
        
        panel1.add(btnNewButton);
        lblNewLabel_4.setFont(new Font("宋体", Font.PLAIN, 25));
        lblNewLabel_4.setBounds(118, 358, 89, 52);
        
        panel1.add(lblNewLabel_4);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 25));
        textArea.setBounds(89, 417, 106, 52);
        
        panel1.add(textArea);

        //frame.setContentPane(scrollPane);
        frame.setSize(2000, 700);
        frame.setBounds(100,100,1900,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
        
        frame.setLocationRelativeTo(null);
      
        frame.setVisible(true);

        
        
        //为控件绑定监听器
        setComponentsListeners();
    }
    
    

   

	protected void keyTyped2(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField_2.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField_2.setText(String.valueOf(num));
		}
	}



	protected void keyTyped1(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField_1.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField_1.setText(String.valueOf(num));
		}
	}



	protected void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		String input=textField.getText();
		if (!input.equals("")) {
			int num=Integer.parseInt(input);
			textField.setText(String.valueOf(num));
		}
	}



	protected void toAdd() {
		// TODO Auto-generated method stub
		String input1=textField.getText();
		String input2=textField_1.getText();
		String input3=textField_2.getText();
		int intNum1=0;
		if (!input1.equals("")) intNum1=Integer.parseInt(input1);
		int intNum2=0;
		if (!input2.equals("")) intNum2=Integer.parseInt(input2);
		int intNum3=0;
		if (!input3.equals("")) intNum3=Integer.parseInt(input3);
		
		if(currentPCBsNum == PCBS_MAX_NUM)
        {
            JOptionPane.showMessageDialog(frame,"The current memory space is full and cannot create a new process");
        }
        else if(pidsUsed[intNum1]==1)
        {
        	JOptionPane.showMessageDialog(null, "换个别的，搞快点！", "提示",JOptionPane.ERROR_MESSAGE);
        }
        else if(intNum2<0||intNum2>9)
        {
        	JOptionPane.showMessageDialog(null, "看不见0~9吗？", "提示",JOptionPane.ERROR_MESSAGE);
        }
        else if(intNum3<0){
        	JOptionPane.showMessageDialog(null, "服务时间为0，运行个der？", "提示",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
        	currentPCBsNum++;

            //int randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));

            /*while(pidsUsed[randomPid] == 1)
            {
                randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));
            }*/

            pidsUsed[intNum1] = 1;

            //int randomPriority = 0 + (int)(Math.random() * ((4 - 0) + 1));
            //int randomLife = 1 + (int)(Math.random() * ((5 - 1) + 1));
            //float serveTime=1+(int)(Math.random()*10);
            float serveTime=intNum3;
            float SurplusServeTime=serveTime;

            PCB pcb = new PCB(intNum1, "Ready", intNum2, SurplusServeTime);

            pcb.setSurplusServeTime(SurplusServeTime);
            LinkedList<PCB> queue = PCBsQueues[intNum2].getQueue();
            queue.offer(pcb);
            PCBsQueues[intNum2].setQueue(queue);

            showPCBQueues(PCBsQueues);
            
           
        }
	}



	



	//设置Swing的控件显示风格为Windows风格
    public static void setWindowsStyle()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }

    }

    //初始化相关内存参
    public static void initMemory()
    {
        currentPCBsNum = 0;

        Arrays.fill(pidsUsed, 1, 101, 0);

        for(int i = 0; i < PCBsQueues.length; i++)
        {
            PCBsQueues[i] = new PCBsQueue(i);
        }

        for(int i = PCBsQueuesTimeSlice.length - 1; i >= 0; i--)
        {
            //队列优先级每降一级，时间片加倍
            PCBsQueuesTimeSlice[i] = timeSlice;
            timeSlice = timeSlice+1;
        }
    }

    //给窗口中有控件绑定监听器
    public static void setComponentsListeners()
    {
        RandomcreateProcessItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
        RandomcreateProcessItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createProcess();
            }
        });


        startPSAItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
        startPSAItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startPSA();
            }
        });

        stopPSAItem.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK));
        stopPSAItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                stopPSA();
            }
        });

        HandcreateProcessItem.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_MASK));
        HandcreateProcessItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	HandcreateProcessItem();
            }
        });


        exitSystemItem.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));
        exitSystemItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        aboutItem.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
        aboutItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame, "Operation System application (1.5 version)\n\nCopyright © 2020, LZ, All Rights Reserved.");
            }
        });

    }

    //创建新进
    public static void createProcess()
    {
        if(currentPCBsNum == PCBS_MAX_NUM)
        {
            JOptionPane.showMessageDialog(frame,"The current memory space is full and cannot create a new process");
        }
        else
        {
            currentPCBsNum++;

            int randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));

            while(pidsUsed[randomPid] == 1)
            {
                randomPid = 1 + (int)(Math.random() * ((100 - 1) + 1));
            }

            pidsUsed[randomPid] = 1;

            int randomPriority = 0 + (int)(Math.random() * ((9 - 0) + 1));
            //int randomLife = 1 + (int)(Math.random() * ((5 - 1) + 1));
            float serveTime=1+(int)(Math.random()*10);
            float SurplusServeTime=serveTime;

            PCB pcb = new PCB(randomPid, "Ready", randomPriority, SurplusServeTime);

            pcb.setSurplusServeTime(SurplusServeTime);
            LinkedList<PCB> queue = PCBsQueues[randomPriority].getQueue();
            queue.offer(pcb);
            PCBsQueues[randomPriority].setQueue(queue);

            showPCBQueues(PCBsQueues);
        }
    }

    //始调
    public static void startPSA()
    {
        isStopScheduling = false;

        //更新界面操作必须借助多线程来实现
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //当前内存中还留有进程未执行
                while(currentPCBsNum!=0 && !isStopScheduling)
                {
                	textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
                    for(int i = PCBsQueues.length - 1; i >= 0; i--)//每次按优先级由高到低取队列组的队列
                    {
                        LinkedList<PCB> queue = PCBsQueues[i].getQueue();

                        if (queue.size() > 0)//队列不为空
                        {
                           
                            PCB pcb = queue.element(); //读取该队列首个PCB
                            pcb.setStatus("Running");//设置状态为Running
                            showPCBQueues(PCBsQueues);

                            int pid = pcb.getPid();//获得进程ID
                            int priority = pcb.getPriority();//获得进程优先级
                          
                            float SurplusServeTime=pcb.getSurplusServeTime();//获得进程剩余服务时间
                         
                            priority = priority-1;//优先级减1
                            
                           if(SurplusServeTime>1) {//剩余服务时间大于1个时间片
                        	   try
                               {
                                   Thread.sleep((int)(1 * 2000));//每次循环sleep一个时间片的时间
                               }
                               catch (InterruptedException e)
                               {
                                   e.printStackTrace();
                               }
                        	   SurplusServeTime--;//剩余服务时间减1
                               pcb.setSurplusServeTime(SurplusServeTime);//更新进程的剩余服务时间
                           }
                           else {//剩余服务时间不止一个时间片
                        	   try
                               {
                                   Thread.sleep((int)(SurplusServeTime * 2000));//每次循环sleep一个时间片的时间
                               }
                               catch (InterruptedException e)
                               {
                                   e.printStackTrace();
                               }
                        	   //移除该队列的首个PCB
                               queue.poll();
                               pidsUsed[pid] = 0;//更新进程使用数组
                               currentPCBsNum--;//内存中进程数减1
                           }
                            
                           nowTime++;//时钟+1，同步变化
                           textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
                            break;
                        }
                    }
                   // nowTime++;
                }

                initMemory();
                showPCBQueues(PCBsQueues);
                //有进程均执行完成，进程调度完
                JOptionPane.showMessageDialog(frame, "进程调度完成!");
                //frame.dispose();
            }
        }).start();

    }

    //强制结束进程调度
    public static void stopPSA()
    {
        isStopScheduling = true;
        nowTime=0;
        textArea.setText(String.valueOf(nowTime));//用于在时钟对应位置展示此时的nowTime，即当前时间
        initMemory();
    }

    
    public static void HandcreateProcessItem()
    {
    	Object[] options ={ "拜拜！"};  
    	int m = JOptionPane.showOptionDialog(null, "想不到吧，这个我不会！", "哈哈",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);  
       
    }

    //显示内存中的多级反馈队列
    public static void showPCBQueues(PCBsQueue[] PCBsQueues)
    {
        int queueLocationY = 0;
        JPanel queuesPanel = new JPanel();

        for(int i = PCBsQueues.length - 1; i >= 0; i--)
        {
            LinkedList<PCB> queue = PCBsQueues[i].getQueue();

            if (queue.size() > 0)
            {
                //创建个PCB队列
                JPanel PCBsQueue = new JPanel();
                PCBsQueue.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                PCBsQueue.setLayout(new FlowLayout(FlowLayout.LEFT));
                PCBsQueue.setBounds(0, queueLocationY, 800, 700);

                queueLocationY += 50;

                //创建队列前面的优先级提示
                JLabel PCBsQueuePriorityLabel = new JLabel("Priority : " + String.valueOf(i));
                PCBsQueuePriorityLabel.setOpaque(true);
                PCBsQueuePriorityLabel.setBackground(Color.YELLOW);
                PCBsQueuePriorityLabel.setForeground(Color.BLACK);

                JPanel PCBsQueuePriorityBlock = new JPanel();
                PCBsQueuePriorityBlock.add(PCBsQueuePriorityLabel);

                PCBsQueue.add(PCBsQueuePriorityBlock);

                for (PCB pcb : queue)
                {

                    //JLabel默认情况下是透明的所以直接设置背景颜色是无法显示的，必须将其设置为不透明才能显示背景

                    //设置pid标签
                    JLabel pidLabel = new JLabel("Pid: " + String.valueOf(pcb.getPid()));
                    pidLabel.setOpaque(true);
                    pidLabel.setBackground(Color.GREEN);
                    pidLabel.setForeground(Color.RED);
                    pidLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置status标签
                    JLabel statusLabel = new JLabel("Status: " + pcb.getStatus());
                    statusLabel.setOpaque(true);
                    statusLabel.setBackground(Color.GREEN);
                    statusLabel.setForeground(Color.RED);
                    statusLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置priority标签
                    JLabel priorityLabel = new JLabel("Priority: " + String.valueOf(pcb.getPriority()));
                    priorityLabel.setOpaque(true);
                    priorityLabel.setBackground(Color.GREEN);
                    priorityLabel.setForeground(Color.RED);
                    priorityLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //设置life标签
                    JLabel lifeLabel = new JLabel("SST: " + String.valueOf(pcb.getSurplusServeTime()));
                    lifeLabel.setOpaque(true);
                    lifeLabel.setBackground(Color.GREEN);
                    lifeLabel.setForeground(Color.RED);
                    lifeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    //绘制个PCB
                    JPanel PCBPanel = new JPanel();
                    PCBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    PCBPanel.setBackground(Color.BLUE);
                    PCBPanel.add(pidLabel);
                    PCBPanel.add(statusLabel);
                    PCBPanel.add(priorityLabel);
                    PCBPanel.add(lifeLabel);

                    //将PCB加入队列
                    PCBsQueue.add(new DrawLinePanel());
                    PCBsQueue.add(PCBPanel);
                }

                queuesPanel.add(PCBsQueue);
            }
        }


        //设置queuesPanel中的有PCB队列（PCBsQueue组件）按垂直方向排列
        BoxLayout boxLayout = new BoxLayout(queuesPanel, BoxLayout.Y_AXIS);
        queuesPanel.setLayout(boxLayout);

        queuesPanel.setSize(800, 700);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.removeAll();
        panel.add(queuesPanel);
        panel.updateUI();
        panel.repaint();
    }
}



