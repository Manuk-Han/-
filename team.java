package team;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

// ȸ�������� ��� Ŭ���� �ۼ�
class project {
    
    private String phone,name,gender,birth,pw,due;
    
    public project(String p,String n,String g,String b,String pw,String d) {
        this.phone=p;
        this.name=n;
        this.gender=g;
        this.birth=b;
        this.pw=pw;
        this.due=d;
    }
    
    public project(){}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}
}

//������ ���̽� ���� �κ� (https://sime.tistory.com/83 ����)
class projectDao{
	private Connection con;
	
	private static final String username = "root";
	private static final String password = "a1s2d3f4@@";
	private static final String url = "jdbc:mysql://localhost:3306/team";
	
	public projectDao() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		con=DriverManager.getConnection(url, username, password);
		System.out.println("Success");
		}catch(SQLException ex) {System.out.println("SQLException"+ex);}
		catch(Exception e) {System.out.println("Exception"+e);}
    }
	
	// ������ �����ͺ��̽��� �����ϴ� �Լ� (ȸ������ - ����)
	public void insertproject(project project) { 
        String sql = "insert into project values(?,?,?,?,?,?);";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, project.getPhone());
            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getGender());
            pstmt.setString(4, project.getBirth());
            pstmt.setString(5, project.getPw());
            pstmt.setString(6, project.getDue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	// ��ȭ��ȣ�� �Է��Ͽ� �׿� �ش��ϴ� ȸ���� ���� �޴� �Լ�
	public project selectOne(String phone) {
        String sql = "select * from project where phone = ?;";
        PreparedStatement pstmt = null;
        project re = new project();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();
 
            if (rs.next()) {
            	re.setPhone(rs.getString("phone"));
            	re.setName(rs.getString("name"));
            	re.setGender(rs.getString("gender"));
            	re.setBirth(rs.getString("birth"));
            	re.setPw(rs.getString("pw"));
            	re.setDue(rs.getString("due"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return re;
    }
	
	// ��ȭ��ȣ�� ��������� �Է��Ͽ� ��й�ȣ�� �޴� �Լ� (pwã��)
	String pw(project p,String id,String b) {
		if(id.equals(p.getPhone())==true) {
			if (b.equals(p.getBirth())==true)
				// �����ͺ��̽��� ��ȭ��ȣ�� ��������� ��ġ��
				return p.getPw();
			else
				// �����ͺ��̽��� ��ȭ��ȣ�� ��������� ����ġ��
				return "������� ��ġ x";
		}
		else
			// �����ͺ��̽��� ���� ��ȣ��
			return "���� ��ȣ �Դϴ�.";
	}
	
	// ��ȭ��ȣ�� �Է��Ͽ� ��й�ȣ�� �޴� �Լ� (�α���)
	String in(project p,String id) {
		if(id.equals(p.getPhone())==true)
			return p.getPw();
		else
			return "���� ��ȣ �Դϴ�.";
	}
	
	// ������ ���̽��� ����(due)�� ������Ʈ ��Ű�� �Լ� (�α���-����-����)
	public void update(String due,String phone) {
        String sql = "update project set due = ? where phone = ? ;";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, due);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

// ������Ʈ�� ����� �ձ۰� ���ش� Ŭ���� (https://pythonq.com/so/java/302211 ����)
class RoundedBorder implements Border {

    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}

// gui ������
public class team extends JFrame{ // ���� ȭ�� ������(�α��� â)
	Font f = new Font("����ǹ��� �־�",Font.PLAIN,20);
	
	Image img1 = null;
	Image img2 = null;
	
	// �̹��� ���� �Լ�
	JLabel img1() {
		try {
			File intput1 = new File(
					"C:/Users/samsung/eclipse-workspace/1/Project/src/img11.gif");
			img1=ImageIO.read(intput1);
		}catch(IOException e) {
			System.out.print("�̹���1 ����");
		}
		JLabel i1=new JLabel(new ImageIcon(img1));
		
		i1.setLocation(45,45*4+20);
		i1.setSize(45*4,60);
		
		return i1;
	}
	
	// �̹��� ���� �Լ�
	JLabel img2() {
		try {
			File intput2 = new File(
					"C:/Users/samsung/eclipse-workspace/1/Project/src/img12.gif");
			img2=ImageIO.read(intput2);
		}catch(IOException e) {
			System.out.print("�̹���2 ����");
		}
		JLabel i2=new JLabel(new ImageIcon(img2));
		
		i2.setLocation(45*5,45*4+20);
		i2.setSize(45*4+30,60);
		
		return i2;
	}
	
	public team() {
		
		projectDao pD=new projectDao();
		
		setTitle("Log-in");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		loginput li = new loginput();
		c.add(li.phone);
		c.add(li.phonein);
		c.add(li.pw);
		c.add(li.pwin);
		c.add(li.text);
		
		logbut lb = new logbut(li);
		c.add(lb.logfind);
		c.add(lb.login);
		c.add(lb.logmake);
		
		numbut nb = new numbut();
		nb.set1(nb.nb, li,lb);
		
		c.add(img1());
		c.add(img2());
		
		setSize(495,765+5);
		setVisible(true);
	}
	
	//id pw �Է�ĭ
	class loginput extends Panel{
		JLabel phone = new JLabel("�޴�����ȣ (11)");
		JTextField phonein = new JTextField("010-");
		JLabel pw = new JLabel("��й�ȣ (4)");
		JTextField pwin = new JTextField("");
		JLabel text = new JLabel("��� ����!");
		
		public loginput() {
			phone.setLocation(45,90);
			phone.setSize(45*3,45);
			phone.setFont(f);
			phone.setForeground(new Color(255,160,122));
            phone.setBorder(new RoundedBorder(10));
            phone.setBackground(new Color(253,245,230));
            phone.setOpaque(true);
			
			phonein.setLocation(225,90);
			phonein.setSize(45*5-7,45);
			phonein.setFont(f);
			phonein.setBorder(new RoundedBorder(10));
			phonein.setBackground(new Color(253,245,230));
			phonein.setSelectionStart(phonein.getText().length());

			pw.setLocation(45,138);
			pw.setSize(45*3,45);
			pw.setFont(f);
			pw.setForeground(new Color(255,160,122));
			pw.setBorder(new RoundedBorder(10));
			pw.setBackground(new Color(253,245,230));
			pw.setOpaque(true);
			
			pwin.setLocation(225,138);
			pwin.setSize(45*5-7,45);
			pwin.setFont(f);
			pwin.setBorder(new RoundedBorder(10));
			pwin.setBackground(new Color(253,245,230));
			
			text.setLocation(0,30);
	        text.setSize(495,30);
	        text.setFont(new Font("����ǹ��� �־�",Font.PLAIN,30));
	        text.setHorizontalAlignment(JLabel.CENTER);
		}
	}
	
	class logbut extends Panel{ // �α���â�� �ִ� ��ư��
		JButton logmake = new JButton("ȸ������");
		JButton login = new JButton("�α���");
		JButton logfind = new JButton("PWã��");
		String pass=""; // ��й�ȣ ����
		
		public logbut(loginput li) {
			logmake.setLocation(15,675);
			logmake.setSize(45*3,45);
			logmake.setFont(f);
			logmake.setBackground(new Color(255,228,225));
            logmake.setForeground(new Color(244,164,96));
            logmake.setBorder(new RoundedBorder(10));
			
			login.setLocation(45*5-35,675);
			login.setSize(45*2+10,45);
			login.setFont(f);
			login.setBackground(new Color(255,228,225));
			login.setForeground(new Color(244,164,96));
            login.setBorder(new RoundedBorder(10));
			
			logfind.setLocation(45*8-25,675);
			logfind.setSize(45*3,45);
			logfind.setFont(f);
			logfind.setBackground(new Color(255,228,225));
			logfind.setForeground(new Color(244,164,96));
            logfind.setBorder(new RoundedBorder(10));
			
            // ��ȭ��ȣâ �������� '-'�� ������ ��ü�� ������ db�� �������Ѽ�
            // �α��� ���θ� ����
			login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					projectDao pd=new projectDao();
					
					project p = pd.selectOne(li.phonein.getText().replace("-", ""));
					
					if(pd.in(p, li.phonein.getText().replace("-", "")).length()==4) {
						if(pd.in(p, li.phonein.getText().replace("-", "")).equals(pass)) {
							new my(pd.selectOne(li.phonein.getText().replace("-", "")));
							// �α��� ������ ����â �˾� & �α��� â �ʱ�ȭ
							li.phonein.setText("010-");
							li.pwin.setText("");
							pass="";
						}
						else if(pd.in(p, li.phonein.getText().replace("-", "")).
								equals(li.pwin.getText())){
							new my(pd.selectOne(li.phonein.getText().replace("-", "")));
							// �α��� ������ ����â �˾� & �α��� â �ʱ�ȭ
							li.phonein.setText("010-");
							li.pwin.setText("");
							pass="";
						}
						else {
							// pw ����ġ �� �˾� & ��й�ȣ ĭ �ʱ�ȭ
							new logx("��й�ȣ ��ġ x");
							li.pwin.setText("");
							pass="";
						}
					}
				
					else {
						// db�� ���� ��ȭ��ȣ�� �˾� & �α��� â �ʱ�ȭ
						new logx("���� ��ȣ�Դϴ�.");
						li.phonein.setText("010-");
						li.pwin.setText("");
						pass="";
					}
				}
			});
			
			logmake.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new lmb();
				}
			});
			logfind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new pwf();
				}
			});
		}
	}
	
	class numbut extends Panel{ // �α��� â ���� ��ư��
		Container c = getContentPane();
		String []n= {"1","2","3","4","5","6","7","8","9","AC","0","Del"};
		JButton []nb=new JButton[12];
		
		JButton[] make() {
			int j=0;
			for(int i=0;i<12;i++) {
				nb[i]=new JButton(n[i]);
				nb[i].setSize(44*3,44*2);
				nb[i].setFont(f);
				if((i+1)%3==1)
					j+=1;
				nb[i].setLocation(45*3*(1+(i%3))-96,45*(4+2*j));
				nb[i].setBorder(new RoundedBorder(20));
				nb[i].setBackground(new Color(255,240,245));
				nb[i].setForeground(new Color(188,143,143));
			}
			return nb;
		}
		void makenbt(JButton []a) {
			for(int i=0;i<12;i++) {
				c.add(a[i]);
			}
		}
		// ��ȣ�� ac, del ��� ����
		void set1(JButton []a,loginput b,logbut lb) {           
			for(int i=0;i<12;i++) {
				String num=a[i].getText();
				if(num.length()==1) {
					a[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(b.phonein.getText().length()<13) {
								b.phonein.setText(b.phonein.getText()+num);
								if(b.phonein.getText().length()==8||b.phonein.getText().length()==3)
									b.phonein.setText(b.phonein.getText()+"-");
							}
							else {
								if(b.pwin.getText().length()<4) {
									b.pwin.setText(b.pwin.getText()+"*");
									lb.pass+=num;
								}
								else
									b.pwin.setText(b.pwin.getText());
							}
						}
					});
				}
				else if(num=="Del") {
					a[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int b1=b.phonein.getText().length();
							int b2=lb.pass.length();
							
							if(b2>0) {
								b.pwin.setText(b.pwin.getText().substring(0,b2-1));
								lb.pass=lb.pass.substring(0,b2-1);
							}
							else
								b.phonein.setText(b.phonein.getText().substring(0,b1-1));
						}
					});
				}
				else {
					a[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							b.phonein.setText("010-");
							b.pwin.setText("");
							lb.pass="";
						}
					});
				}
			}
		}
	
		public numbut() {
			make();
			makenbt(nb);
		}
	}

	public static void main(String[] args) {
		new team();
	}
}

// ��ȭ��ȣ or ��й�ȣ ����ġ�� �˾�
class logx extends JFrame{
	JLabel a = new JLabel();
	JButton x = new JButton("Ȯ��");
	Font F = new Font("����ǹ��� �־�",Font.PLAIN,20);
	
	public logx(String t) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		a.setText(t);
		a.setHorizontalAlignment(JLabel.CENTER);
		a.setLocation(22,30);
		a.setSize(45*6,45);
		a.setFont(new Font("����ǹ��� �־�",Font.PLAIN,25));
		
		x.setLocation(45*2,100);
		x.setSize(45*3,45);
		x.setFont(F);
		x.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		x.setBackground(new Color(255,228,225));
		x.setForeground(new Color(244,164,96));
        x.setBorder(new RoundedBorder(10));
        
		c.add(a);
		c.add(x);
		
		setTitle("Error");
		setLocation(90,300);
		setSize(45*7,200);
		setVisible(true);
	}
}

// �� �ٹ̴� Ŭ����1 
class b{
	JLabel set(JLabel a) {
		a.setBackground(new Color(253,245,230));
        a.setOpaque(true);
        
        return a;
	}
}

//�� �ٹ̴� Ŭ����2 
class bb{
	JTextField set(JTextField a) {
		a.setBorder(new RoundedBorder(10));
		a.setBackground(new Color(253,245,230));
		
		return a;
	}
	JLabel set(JLabel a) {
		a.setBorder(new RoundedBorder(10));
		a.setBackground(new Color(253,245,230));
		a.setOpaque(true);
		
		return a;
	}
}

class lmb extends JFrame{
	JLabel hi = new JLabel("ȸ������");
	JLabel name = new JLabel("�̸�");
	JTextField namein = new JTextField("");
	JLabel phone = new JLabel("�޴�����ȣ");
	JTextField phonein = new JTextField("");
	JLabel pw = new JLabel("��й�ȣ(4)");
	JTextField pwin = new JTextField("");
	JLabel gen = new JLabel("����");
	JRadioButton m = new JRadioButton("��");
	JRadioButton f = new JRadioButton("��");
	JLabel birth = new JLabel("�������");
	JTextField birthin = new JTextField("");
	JButton cancle = new JButton("���");
	JButton save = new JButton("����");
	Font F = new Font("����ǹ��� �־�",Font.PLAIN,20);
	String g;
	b b=new b();
	bb bb= new bb();
	
	public lmb() {
		setTitle("Make ID/PW");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		hi.setLocation(160,15);
		hi.setSize(45*3,45);
		hi.setFont(new Font("����ǹ��� �־�",Font.PLAIN,30));
		hi.setForeground(new Color(255,160,122));
        hi.setBorder(new RoundedBorder(10));
        hi.setHorizontalAlignment(JLabel.CENTER);
        b.set(hi);
		
		name.setLocation(45,70);
		name.setSize(45*3,45);
		name.setFont(F);
		name.setForeground(new Color(255,160,122));
        name.setBorder(new RoundedBorder(20));
        b.set(name);
		
		namein.setLocation(225,70);
		namein.setSize(45*5,45);
		namein.setFont(F);
		bb.set(namein);
		
		phone.setLocation(45,90+45);
		phone.setSize(45*3,45);
		phone.setFont(F);
		phone.setForeground(new Color(255,160,122));
        phone.setBorder(new RoundedBorder(20));
        b.set(phone);
		
		phonein.setLocation(225,90+45);
		phonein.setSize(45*5,45);
		phonein.setFont(F);
		bb.set(phonein);

		pw.setLocation(45,155+45);
		pw.setSize(45*3,45);
		pw.setFont(F);
		pw.setForeground(new Color(255,160,122));
        pw.setBorder(new RoundedBorder(20));
        b.set(pw);
		
		pwin.setLocation(225,155+45);
		pwin.setSize(45*5,45);
		pwin.setFont(F);
		bb.set(pwin);
		
		gen.setLocation(45,220+45);
		gen.setSize(45*3,45);
		gen.setFont(F);
		gen.setForeground(new Color(255,160,122));
        gen.setBorder(new RoundedBorder(20));
        b.set(gen);
		
		m.setLocation(225+45,220+45);
		m.setSize(45,45);
		m.setFont(F);
		m.addItemListener(new ItemListener()
		   {
		   public void itemStateChanged(ItemEvent e) {
			   if(e.getStateChange() == ItemEvent.SELECTED)
				   g="m";
		   }
		   });
		m.setForeground(new Color(255,160,122));
		m.setBackground(new Color(255,250,240));
		m.setOpaque(true);
		
		f.setLocation(225+45*3,220+45);
		f.setSize(45,45);
		f.setFont(F);
		f.addItemListener(new ItemListener()
		   {
		   public void itemStateChanged(ItemEvent e) {
			   if(e.getStateChange() == ItemEvent.SELECTED)
				   g="f";
		   }
		   });
		f.setForeground(new Color(255,160,122));
		f.setBackground(new Color(255,250,240));
		f.setOpaque(true);
		
		birth.setLocation(45,285+45);
		birth.setSize(45*3,45);
		birth.setFont(F);
		birth.setForeground(new Color(255,160,122));
        birth.setBorder(new RoundedBorder(20));
        b.set(birth);
		
		birthin.setLocation(225,285+45);
		birthin.setSize(45*5,45);
		birthin.setFont(F);
		bb.set(birthin);
		
		cancle.setLocation(45,385+45);
		cancle.setSize(45*3,45);
		cancle.setFont(F);
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancle.setBackground(new Color(255,228,225));
        cancle.setForeground(new Color(244,164,96));
        cancle.setBorder(new RoundedBorder(10));
		
		String pattern="yyyy-MM-dd";
		SimpleDateFormat sm = new SimpleDateFormat(pattern);
		
		String date = sm.format(new Date());
		
		save.setLocation(300,385+45);
		save.setSize(45*3,45);
		save.setFont(F);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectDao pd=new projectDao();
				project p = new project(phonein.getText(),namein.getText(),g,
						birthin.getText(),pwin.getText(),date);				
				pd.insertproject(p);
				dispose();
			}
		});
		save.setBackground(new Color(255,228,225));
        save.setForeground(new Color(244,164,96));
        save.setBorder(new RoundedBorder(10));
		
		c.add(hi);
		c.add(name);
		c.add(namein);
		c.add(phone);
		c.add(phonein);
		c.add(pw);
		c.add(pwin);
		c.add(gen);
		c.add(m);
		c.add(f);
		c.add(birth);
		c.add(birthin);	
		c.add(cancle);
		c.add(save);
		
		setSize(495,495+45);
		setVisible(true);
	}
}

class pwf extends JFrame{ // pw ã�� â
	JLabel hi = new JLabel("PWã��");
	JLabel phone = new JLabel("�޴�����ȣ");
	JTextField phonein = new JTextField("");
	JLabel pw = new JLabel("��й�ȣ");
	JLabel pwin = new JLabel("");
	JLabel birth = new JLabel("�������");
	JTextField birthin = new JTextField("");
	JButton cancle = new JButton("���");
	JButton save = new JButton("ã��");
	Font F = new Font("����ǹ��� �־�",Font.PLAIN,20);
	b b= new b();
	bb bb= new bb();
		
	public pwf() {
		setTitle("Find PW");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		hi.setLocation(160,15);
		hi.setSize(45*3,45);
		hi.setFont(new Font("����ǹ��� �־�",Font.PLAIN,30));
		hi.setBackground(new Color(255,228,225));
        hi.setForeground(new Color(255,160,122));
        hi.setBorder(new RoundedBorder(20));
        b.set(hi);
		
		phone.setLocation(45,90);
		phone.setSize(45*3,45);
		phone.setFont(F);
		phone.setForeground(new Color(255,160,122));
        phone.setBorder(new RoundedBorder(20));
        b.set(phone);
		
		phonein.setLocation(225,90);
		phonein.setSize(45*5,45);
		phonein.setFont(F);
		bb.set(phonein);

		pw.setLocation(45,285);
		pw.setSize(45*3,45);
		pw.setFont(F);
		pw.setForeground(new Color(255,160,122));
        pw.setBorder(new RoundedBorder(20));
        b.set(pw);
		
		pwin.setLocation(225,285);
		pwin.setSize(45*5,45);
		pwin.setFont(F);
		bb.set(pwin);
		
		birth.setLocation(45,155);
		birth.setSize(45*3,45);
		birth.setFont(F);
		birth.setForeground(new Color(255,160,122));
        birth.setBorder(new RoundedBorder(20));
        b.set(birth);
		
		birthin.setLocation(225,155);
		birthin.setSize(45*5,45);
		birthin.setFont(F);
		bb.set(birthin);
		
		cancle.setLocation(45,385);
		cancle.setSize(45*3,45);
		cancle.setFont(F);
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancle.setBackground(new Color(255,228,225));
        cancle.setForeground(new Color(244,164,96));
        cancle.setBorder(new RoundedBorder(10));
		
		save.setLocation(300,385);
		save.setSize(45*3,45);
		save.setFont(F);
		// �Է��� ��ȭ��ȣ�� ��������� ���� db�� pw���� �޾ƿ� ǥ��
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectDao pd=new projectDao();
				
				project p = pd.selectOne(phonein.getText());
				
				String a = pd.pw(p,phonein.getText(),birthin.getText());
				pwin.setText(a);
				cancle.setText("�ݱ�");
			}
		});
		save.setBackground(new Color(255,228,225));
        save.setForeground(new Color(244,164,96));
        save.setBorder(new RoundedBorder(10));
		
		c.add(hi);
		c.add(phone);
		c.add(phonein);
		c.add(pw);
		c.add(pwin);
		c.add(birth);
		c.add(birthin);
		c.add(cancle);
		c.add(save);
		
		setSize(495,495);
		setVisible(true);
	}
}

class my extends JFrame{
	Font F = new Font("����ǹ��� �־�",Font.PLAIN,20);
	bb bb= new bb();
	projectDao pd=new projectDao();
	//�޷�
	Calendar cal = Calendar.getInstance();
	
	int year=cal.get(Calendar.YEAR);
	int mon=cal.get(Calendar.MONTH)+1;
	int date=cal.get(Calendar.DATE);
	int week=cal.get(Calendar.DAY_OF_WEEK);
	
	String [] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	
	JLabel[] callb = new JLabel[49];
	
	JLabel[] calMake() {
		for(int i = 0 ; i < days.length;i++)
			callb[i] = new JLabel(days[i]);                   
		for(int i = days.length ; i < 49;i++){                
			callb[i] = new JLabel("");                   
		}
		return callb;
	}
	
	JLabel[] calSet() {//https://digiconfactory.tistory.com ����
		
		calMake();
		
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,(mon-1));
        cal.set(Calendar.DATE,1);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        int j=0;
        // �޷� ���ۿ��� ������ �κ� ���
        for(int i=cal.getFirstDayOfWeek();i<dayOfWeek;i++){  j++;  }
        for(int kk=0;kk<j;kk++){
            callb[kk+7]=new JLabel("");
        }
        // �޷� ���� ���� �ۼ�
        for(int i=cal.getMinimum(Calendar.DAY_OF_MONTH);
                      i<=cal.getMaximum(Calendar.DAY_OF_MONTH);i++){
            cal.set(Calendar.DATE,i);
               if(cal.get(Calendar.MONTH) !=mon-1){
                      break;
               }
               callb[i+6+j]=new JLabel(Integer.toString(i));
        }
        return callb;
    }
	
	JLabel due = new JLabel("�Ⱓ : ");
	JLabel day = new JLabel();	
	JLabel name = new JLabel();
	
	JButton cancle = new JButton("�ݱ�");
	JButton buy = new JButton("�̿�� ����");
	JButton enter = new JButton("����");
	
	public my(project p) {
		setTitle("My Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		calSet();
		
		due.setLocation(30,15);
		due.setSize(45*4+5,45);
		due.setFont(F);
		due.setText(due.getText()+p.getDue());
		due.setForeground(Color.black);
		bb.set(due);
		
		day.setLocation(235,15);
		day.setSize(45*4-5,45);
		day.setFont(F);
		String pattern="yyyy-MM-dd";
		SimpleDateFormat sm = new SimpleDateFormat(pattern);
		String date = sm.format(new Date());
		day.setText(date);
		day.setHorizontalAlignment(JLabel.CENTER);
		day.setForeground(Color.black);
		bb.set(day);
		
		name.setLocation(250+45*4,15);
		name.setSize(45*4,45);
		name.setText(p.getName()+" ��");
		name.setFont(F); 
		name.setForeground(Color.black);
		name.setHorizontalAlignment(JLabel.CENTER);
		bb.set(name);
		
		for(int i = 0; i < 49;i++){
			callb[i].setLocation(30+90*(i%7),45+40*(1+i/7));
			callb[i].setSize(45*2,40);
			callb[i].setFont(F);
            callb[i].setForeground(Color.black);
            if(i%7==0)
            	callb[i].setForeground(new Color(255,0,0));
            if(i%7==6)
            	callb[i].setForeground(new Color(0,0,255));
		}
		
		cancle.setLocation(30,385);
		cancle.setSize(45*4,45);
		cancle.setFont(F);
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancle.setBackground(new Color(255,228,225));
        cancle.setForeground(new Color(244,164,96));
        cancle.setBorder(new RoundedBorder(10));
		
		buy.setLocation(45+185,385);
		buy.setSize(45*4,45);
		buy.setFont(F);
		// ����(due)�� ���ó�¥�� �������� �ٲپ� �� �� �� �̷��� ������ ����â�� ���
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(date.replace("-", ""))>=
				Integer.parseInt(p.getDue().replace("-", "")))
					new buy(date,p,due);
				else
					new buy(p.getDue(),p,due);
			}
		});
		buy.setBackground(new Color(255,228,225));
        buy.setForeground(new Color(244,164,96));
        buy.setBorder(new RoundedBorder(10));
		
		enter.setLocation(435,385);
		enter.setSize(45*4,45);
		enter.setFont(F);
		// ����(due)�� ���ó�¥�� �������� �ٲپ� �� �� ������ ���������� ����, �ƴϸ� �˾�
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(date.replace("-", ""))>=
				Integer.parseInt(due.getText().substring(5,due.getText().length()
						).replace("-", "")))
					new logx("�ܿ��� x");
				else
					dispose();
			}
		});
		enter.setBackground(new Color(255,228,225));
        enter.setForeground(new Color(244,164,96));
        enter.setBorder(new RoundedBorder(10));
		
		c.add(due);
		c.add(day);
		c.add(name);
		
		for(int i = 0 ; i <49;i++)
            c.add(callb[i]);                   

		c.add(cancle);
		c.add(buy);
		c.add(enter);
		
		setSize(660,495);
		setVisible(true);
	}
}

class buy extends JFrame{
	
	JLabel mon = new JLabel("����");
	JLabel fee = new JLabel("����");

	JLabel []m = new JLabel[4];
	int []ml= {1,3,6,12};
	void mm() {
		for(int i=0;i<4;i++) {
			m[i]=new JLabel(Integer.toString(ml[i]));
		}
	}
	void mmk() {
		mm();
		for(int i=0;i<4;i++) {
			m[i].setLocation(45,70+45*(i+1));
			m[i].setSize(45*2,45);
			m[i].setFont(F);
		}
	}
	
	JLabel []f = new JLabel[4];
	String []fl= {"50,000��","120,000��","200,000��","350,000��"};
	void mf() {
		for(int i=0;i<4;i++) {
			f[i]=new JLabel(fl[i]);
		}
	}
	void mfk() {
		mf();
		for(int i=0;i<4;i++) {
			f[i].setLocation(15+45*4,70+45*(i+1));
			f[i].setSize(45*4,45);
			f[i].setFont(F);
		}
	}
	
	JRadioButton []cb = new JRadioButton[4];
	void mc() {
		for(int i=0;i<4;i++) {
			cb[i]=new JRadioButton();
		}
	}
	void mck(String due) {
		mc();
		ButtonGroup g = new ButtonGroup();
		
		int y=Integer.parseInt(due.replace("-","").substring(0,4));
		int mon=Integer.parseInt(due.replace("-","").substring(4,6));
		int d=Integer.parseInt(due.replace("-","").substring(6,8));
		
		for(int i=0;i<4;i++) {
			int j=i;
			g.add(cb[i]);
			cb[i].setLocation(45+45*8,70+45*(i+1));
			cb[i].setSize(45,45);
			cb[i].setForeground(new Color(255,160,122));
			cb[i].setBackground(new Color(255,250,240));
			cb[i].setOpaque(true);
			// ����(due)�� ������ �ٲپ� �� ��ư�� �°� ������ �� �ٽ� ���ڿ��� �ٲپ�
			// �þ ������ ǥ��
			cb[i].addItemListener(new ItemListener()
			   {
			   public void itemStateChanged(ItemEvent e) {
				   if(e.getStateChange() == ItemEvent.SELECTED) {
					 
					   if(mon+Integer.parseInt(m[j].getText())<=12) {
							dued.setText(due+" ~ "+String.format("%04d-%02d-%02d",
									y,mon+Integer.parseInt(m[j].getText()),d));
						}
						else{
							dued.setText(due+" ~ "+String.format("%04d-%02d-%02d",
									y+1,mon-12+Integer.parseInt(m[j].getText()),d));
						}
					   won.setText(f[j].getText());
				   }
			   }
			   });
		}
	}
	
	JLabel dued = new JLabel("");
	void md(JLabel dued,String due) {
		dued.setText(due+" ~ ");
	}
	
	JButton cancle = new JButton("���");
	JLabel won = new JLabel("0��");
	JButton pur = new JButton("����");
	JLabel text = new JLabel("����");
	
	Font F = new Font("����ǹ��� �־�",Font.PLAIN,20);
	bb bb= new bb();
	
	public buy(String due,project p,JLabel duedd) {
		setTitle("Buy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(255,250,240));
		
		text.setLocation(45*4,15);
		text.setSize(45*3,45);
        text.setFont(new Font("����ǹ��� �־�",Font.PLAIN,30));
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setBackground(new Color(253,245,230));
        text.setForeground(new Color(244,164,96));
        text.setBorder(new RoundedBorder(10));
        text.setOpaque(true);
		
		mon.setLocation(45,70);
		mon.setSize(45*2,45);
		mon.setFont(F);
		
		fee.setLocation(15+45*4,70);
		fee.setSize(45*4,45);
		fee.setFont(F);
		
		mmk();
		mfk();
		mck(due);
		md(dued,due);
		
		dued.setLocation(45*2+22,385-45);
		dued.setSize(45*6,45);
		dued.setFont(F);
		bb.set(dued);
		
		cancle.setLocation(45-5,385+45);
		cancle.setSize(45*3,45);
		cancle.setFont(F);
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancle.setBackground(new Color(255,228,225));
        cancle.setForeground(new Color(244,164,96));
        cancle.setBorder(new RoundedBorder(10));
		
		won.setLocation(45*4,385+45);
		won.setSize(45*3-20,45);
		won.setFont(F);
		won.setHorizontalAlignment(JLabel.CENTER);
		bb.set(won);
		
		pur.setLocation(300,385+45);
		pur.setSize(45*3,45);
		pur.setFont(F);
		// �þ�� ����(due) ������ �����ͺ��̽��� ������Ʈ
		pur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectDao pd=new projectDao();
				pd.update(dued.getText().substring(12,dued.getText().length()),p.getPhone());
				duedd.setText("�Ⱓ : "+pd.selectOne(p.getPhone()).getDue());
				dispose();
			}
		});
		pur.setBackground(new Color(255,228,225));
        pur.setForeground(new Color(244,164,96));
        pur.setBorder(new RoundedBorder(10));
		
		c.add(mon);
		c.add(fee);
		for(int i=0;i<4;i++) {
			c.add(m[i]);
		}
		for(int i=0;i<4;i++) {
			c.add(f[i]);
		}
		for(int i=0;i<4;i++) {
			c.add(cb[i]);
		}
		c.add(text);
		c.add(dued);
		c.add(won);
		c.add(cancle);
		c.add(pur);
		
		setSize(495,495+45);
		setVisible(true);
	}
}















