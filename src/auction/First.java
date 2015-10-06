package auction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class First extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 299228424963664361L;
	JPanel FirstLayer;
	JPanel SecondLayerT;
	JPanel SecondLayerC;
	JPanel ThirdLayerT_L;
	JPanel ThirdLayerT_R;
	JPanel FourthLayerT_L_C;
	JPanel FourthLayerT_R_C;
	JPanel ThirdLayerC_L;
	JPanel ThirdLayerC_R;
	JTextField tNumOfBuyers;
	JTextField tNumOfSellers;
	Second s;
	boolean def;
	
	int numOfBuyers;
	int numOfSellers;
	ArrayList<person> sellers;
	ArrayList<person> buyers;
	
	class buttonBM extends JButton
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3461308971829280560L;
		public buttonBM(String name, JTextField textFild)
		{
			
		}
		
	}
	
	class person implements Comparable<Object>
	{
		int id;
		int price;
		int offer;
		int notMore;
		
		person(int id, int price, int offer)
		{
			this.id = id;
			this.price = price;
			this.offer = offer;	
			this.notMore = offer;
		}
		
		@Override
		public int compareTo(Object arg0) 
		{			
			person p = (person)arg0;
			return this.price - p.price;
		}
		
	}
	
	public First()
	{
		super("Auction");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);

		this.def = JOptionPane.showConfirmDialog(new JButton(), "Использовать значения по умолчанию?") == JOptionPane.YES_OPTION;
		
		this.initComponents();
		
		pack();
	}
	
	void initComponents()
	{
		this.numOfBuyers = -1;
		this.numOfSellers = -1;
		this.sellers = new ArrayList<First.person>();
		this.buyers = new ArrayList<First.person>();
		//Первый уровень
		this.FirstLayer = new JPanel();
		this.FirstLayer.setLayout(new BorderLayout());
		
		JPanel bufJpanelB = new JPanel();
		bufJpanelB.setLayout(new FlowLayout());	
		
		//Второй уровень
		this.SecondLayerT = new JPanel();
		this.SecondLayerT.setLayout(new GridLayout(1, 2));
		
		this.SecondLayerC = new JPanel();
		
		JButton bufJButtonB = new JButton("Ok");
		bufJButtonB.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				First.this.checkAll();
				super.mouseClicked(e);
			}
		});
		
		//Третий уровень
		this.ThirdLayerT_L = new JPanel();
		this.ThirdLayerT_L.setLayout(new BorderLayout());
		this.ThirdLayerT_L.setBorder(new LineBorder(Color.BLACK, 1));
		
		this.ThirdLayerT_R = new JPanel();
		this.ThirdLayerT_R.setLayout(new BorderLayout());
		this.ThirdLayerT_R.setBorder(new LineBorder(Color.BLACK, 1));
		
		//Четвертый уровень
		this.FourthLayerT_L_C = new JPanel();
		this.FourthLayerT_L_C.setLayout(new GridLayout(1, 3));
		
		this.FourthLayerT_R_C = new JPanel();
		this.FourthLayerT_R_C.setLayout(new GridLayout(1, 3));
		
		//Пятый уровень
		this.tNumOfBuyers = new JTextField(2);
		JButton bufButtonOkTL = new JButton("Ok");
		bufButtonOkTL.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				First.this.checkNums();
				super.mouseClicked(e);
			}
		});
		
		this.tNumOfSellers = new JTextField(2);
		JButton bufButtonOkTR = new JButton("Ok");
		bufButtonOkTR.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				First.this.checkNums();
				super.mouseClicked(e);
			}
		});


		
		//Компоновка четвертый уровень
		this.FourthLayerT_L_C.add(new JLabel("Кол:"));
		this.FourthLayerT_L_C.add(this.tNumOfBuyers);
		this.FourthLayerT_L_C.add(bufButtonOkTL);
		
		this.FourthLayerT_R_C.add(new JLabel("Кол:"));
		this.FourthLayerT_R_C.add(this.tNumOfSellers);
		this.FourthLayerT_R_C.add(bufButtonOkTL);
		
		//Компоновка третий уровень
		this.ThirdLayerT_L.add(this.FourthLayerT_L_C, BorderLayout.CENTER);
		this.ThirdLayerT_L.add(new JLabel("Покупатели"), BorderLayout.NORTH);
		
		this.ThirdLayerT_R.add(this.FourthLayerT_R_C, BorderLayout.CENTER);
		this.ThirdLayerT_R.add(new JLabel("Продавцы"), BorderLayout.NORTH);
		
		//Компоновка второй уровень
		this.SecondLayerT.add(this.ThirdLayerT_L);
		this.SecondLayerT.add(this.ThirdLayerT_R);
		bufJpanelB.add(bufJButtonB);
		
		//Компоновка первый уровень
		this.FirstLayer.add(this.SecondLayerT, BorderLayout.NORTH);
		this.FirstLayer.add(this.SecondLayerC, BorderLayout.CENTER);
		this.FirstLayer.add(bufJpanelB, BorderLayout.SOUTH);
		
		//Компоновка нулевой уровень
		getContentPane().add(this.FirstLayer);

		if (this.def)
		{
			this.tNumOfBuyers.setText("4");
			this.tNumOfSellers.setText("4");
			this.checkNums();
		}

	}
	
	void checkNums()
	{
		try
		{
			this.numOfBuyers = Integer.parseInt(tNumOfBuyers.getText());
			this.numOfSellers = Integer.parseInt(tNumOfSellers.getText());
			
			setSecondLayerC();
			
			if (this.numOfBuyers > 10 || this.numOfBuyers < 2 || this.numOfSellers < 2 || this.numOfSellers >10)
				throw new Exception();			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Введите целые числа в отрезке [2, 10]");
		}
	}
	
	void setSecondLayerC()
	{
		this.FirstLayer.remove(this.SecondLayerC);
		
		//Второй уровень
		this.SecondLayerC = new JPanel();
		this.SecondLayerC.setLayout(new GridLayout(1, 2));
		
		//Третий уровень
		this.ThirdLayerC_L = new JPanel();
		this.ThirdLayerC_L.setLayout(new GridLayout(this.numOfBuyers, 1));
		
		this.ThirdLayerC_R = new JPanel();
		this.ThirdLayerC_R.setLayout(new GridLayout(this.numOfSellers, 1));
		
		//Компоновка третий уровень
		for(int i = 0; i < this.numOfBuyers; i++)
		{
			JPanel bufJPanel = new JPanel();
			bufJPanel.setLayout(new GridLayout(1, 4));
			bufJPanel.setBorder(new LineBorder(Color.BLACK, 1));
			JTextField pBufTextField = new JTextField(2);
			JTextField cBufTextField = new JTextField(2);

			if (this.def)
			{
				pBufTextField.setText("" + (2 + i * 7));
				cBufTextField.setText("22");
			}
			bufJPanel.add(new JLabel("цена:"));
			bufJPanel.add(pBufTextField);
			bufJPanel.add(new JLabel("кол-во:"));
			bufJPanel.add(cBufTextField);
			this.ThirdLayerC_L.add(bufJPanel);
		}
		
		for(int i = 0; i < this.numOfSellers; i++)
		{
			JPanel bufJPanel = new JPanel();
			bufJPanel.setLayout(new GridLayout(1, 4));
			bufJPanel.setBorder(new LineBorder(Color.BLACK, 1));
			JTextField pBufTextField = new JTextField(2);
			JTextField cBufTextField = new JTextField(2);

			if (this.def)
			{
				pBufTextField.setText("" + (3 + i * 6));
				cBufTextField.setText("21");
			}
			bufJPanel.add(new JLabel("цена:"));
			bufJPanel.add(pBufTextField);
			bufJPanel.add(new JLabel("кол-во:"));
			bufJPanel.add(cBufTextField);
			this.ThirdLayerC_R.add(bufJPanel);
		}
		
		//Компоновка второй уровень
		this.SecondLayerC.add(this.ThirdLayerC_L);
		this.SecondLayerC.add(this.ThirdLayerC_R);
		
		//компоновка первый уровень
		this.FirstLayer.add(this.SecondLayerC);
		this.getContentPane().validate();
		this.pack();
		this.getContentPane().repaint();
		//this.def = false;
	}
	
	void checkAll()
	{
		try
		{
			this.numOfBuyers = Integer.parseInt(tNumOfBuyers.getText());
			this.numOfSellers = Integer.parseInt(tNumOfSellers.getText());
			
			//setSecondLayerC();
			
			if (this.numOfBuyers > 10 || this.numOfBuyers < 2 || this.numOfSellers < 2 || this.numOfSellers >10)
				throw new Exception();		
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Введите целые числа в отрезке [2, 10]");
			return;
		}
		try
		{
		//	if (this.numOfBuyers == -1 || this.numOfSellers == -1)
		//		throw new Exception();
			this.sellers = new ArrayList<First.person>();
			this.buyers = new ArrayList<First.person>();
			for (int i = 0; i < this.numOfBuyers; i++)
			{
				int price = Integer.parseInt(((JTextField)((JPanel)this.ThirdLayerC_L.getComponent(i)).getComponent(1)).getText());
				int offer = Integer.parseInt(((JTextField)((JPanel)this.ThirdLayerC_L.getComponent(i)).getComponent(3)).getText());
				this.buyers.add(new person(i + 1, price, offer));				
			}
			for (int i = 0; i < this.numOfSellers; i++)
			{
				int price = Integer.parseInt(((JTextField)((JPanel)this.ThirdLayerC_R.getComponent(i)).getComponent(1)).getText());
				int offer = Integer.parseInt(((JTextField)((JPanel)this.ThirdLayerC_R.getComponent(i)).getComponent(3)).getText());
				this.sellers.add(new person(i + 1, price, offer));				
			}
			this.s = new Second(this.sellers, this.buyers);
			this.s.setVisible(true);
			
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Введите целые числа в строки \"цена\" и \"количество\"");
		}
	}
}
