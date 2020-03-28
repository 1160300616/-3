package lab;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class School {
	public static void manu()
	{
		System.out.println("You can use the following options:");
		System.out.println("1. Insert data");
		System.out.println("2. Delete data");
		System.out.println("3. Connection query");
		System.out.println("4. Group query");
		System.out.println("5. Nested query");
		System.out.println("0. exit");
		System.out.println("Please input your choice:");
	}
	public static void manu2()
	{
		System.out.println("You can shoose to delete the following data:");
		System.out.println("1. PRINCIPLE(NAME,PHONENUMBER,SCHOOL_NO)");
		System.out.println("2. SCHOOL(SCHOOL_NO,NAME,ADDRESS)");
		System.out.println("3. DEPARTMENT(DEPARTMENT_NO,NAME,SCHOOL_NO)");
		System.out.println("4. CLASS(CLASS_NO,NAME,DEPARTMENT_NO)");
		System.out.println("5. STUDENT(STUDENT_ID,NAME,AGE,CLASS_NO)");
		System.out.println("6. COURSE(COURSE_NO,NAME)");
		System.out.println("7. ELECTIVE(STUDENT_ID,COURSE_NO)");
		System.out.println("8. TEACHING(TEACHING_NO,NAME,ADDRESS,DEPARTMENT_NO)");
		System.out.println("9. TEACHER(TEACHER_ID,NAME,TEACHING_NO)");
		System.out.println("10. POSTGRADUATE(POSTGRADUATE_ID,NAME,TEACHER_ID)");
		System.out.println("You can delete data by following the above instructions.");
	}
	public static void manu1() {
		System.out.println("You can shoose to inset the following data:");
		System.out.println("1. PRINCIPLE(NAME,PHONENUMBER,SCHOOL_NO)");
		System.out.println("2. SCHOOL(SCHOOL_NO,NAME,ADDRESS)");
		System.out.println("3. DEPARTMENT(DEPARTMENT_NO,NAME,SCHOOL_NO)");
		System.out.println("4. CLASS(CLASS_NO,NAME,DEPARTMENT_NO)");
		System.out.println("5. STUDENT(STUDENT_ID,NAME,AGE,CLASS_NO)");
		System.out.println("6. COURSE(COURSE_NO,NAME)");
		System.out.println("7. ELECTIVE(STUDENT_ID,COURSE_NO)");
		System.out.println("8. TEACHING(TEACHING_NO,NAME,ADDRESS,DEPARTMENT_NO)");
		System.out.println("9. TEACHER(TEACHER_ID,NAME,TEACHING_NO)");
		System.out.println("10. POSTGRADUATE(POSTGRADUATE_ID,NAME,TEACHER_ID)");
		System.out.println("11. ELECTIVECOURSE(STUDENT_ID,STUDENT.NAME,CLASS_NO,COURSE_NO,COURSE_NAME)");
		System.out.println("You can enter data as above, please enter your data");
	}
	public static void manu3() {
		System.out.println("You can make the following queries:");
		System.out.println("1. Course selection for each student");
		System.out.println("2. Graduate students owned by each teacher");
		System.out.println("3. Students in each class");
		System.out.println("Please input your choice:");
	}
	public static void manu4()
	{
		System.out.println("You can make the following queries:");
		System.out.println("1. Number of courses per student");
		System.out.println("2. Number of students per teacher");
		System.out.println("3. Total number of students per class");
		System.out.println("Please input your choice:");
	}
	public static void manu5()
	{
		System.out.println("You can make the following queries:");
		System.out.println("1. Course selected by student with class number 1");
		System.out.println("2. The name of the graduate student of the teacher numbered 1");
		System.out.println("Please input your choice:");
	}
	public static void ParseAndInsertData(Connection connect,String input)
	{
		try {
		
		String args[] = input.split("\\(");
		String q1 = "";
		PreparedStatement Statement=null;
		if(args[0].equals("ELECTIVECOURSE"))
		{
			String temp[] = args[1].split(",");
			String q = "start transaction";
			connect.setAutoCommit(false);
			Statement=connect.prepareStatement(q);
			Statement.executeUpdate();
			if(temp.length!=5)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[4]=temp[4].replace(")", "");
			
			String arg1 = "STUDENT("+temp[0]+","+temp[1]+","+"0,"+temp[2]+")";
			String arg2 = "COURSE("+temp[3]+","+temp[4]+")";
			String arg3 = "ELECTIVE("+temp[0]+","+temp[3]+")";
			try
			{
				String query1 = "INSERT INTO "+"STUDENT"+" VALUES(?,?,?,?)";
				Statement=connect.prepareStatement(query1);
				Statement.setString(1,temp[0]);
				Statement.setString(2,temp[1]);
				Statement.setString(3,String.valueOf(0));
				Statement.setString(4,temp[2]);
		        Statement.execute();
		        
		        String query2 = "INSERT INTO "+"COURSE"+" VALUES(?,?)";
		        Statement=connect.prepareStatement(query2);
				Statement.setString(1,temp[3]);
				Statement.setString(2,temp[4]);
				Statement.execute();
				
				String query3 = "INSERT INTO "+"ELECTIVE"+" VALUES(?,?)";
		        Statement=connect.prepareStatement(query3);
				Statement.setString(1,temp[0]);
				Statement.setString(2,temp[3]);
				Statement.execute();
		        
//				ParseAndInsertData(connect,arg1);
//				ParseAndInsertData(connect,arg2);
//				ParseAndInsertData(connect,arg3);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Input error");
				q = "rollback";
				Statement=connect.prepareStatement(q);
				Statement.executeUpdate();
				q = "commit";
				Statement=connect.prepareStatement(q);
				Statement.executeUpdate();
				connect.setAutoCommit(true);
				return ;
			}
			q = "commit";
			Statement=connect.prepareStatement(q);
			Statement.executeUpdate();
			connect.setAutoCommit(true);
			System.out.println("Successfully importing data!\n");
		}
		else if(args[0].equals("PRINCIPLE")||args[0].equals("SCHOOL"))
		{
			q1 = "INSERT INTO "+args[0]+" VALUES(?,?,?)";
			if(args[1].contains("null"))
			{
				System.out.println("Input does not allow null values!");
				return;
			}
			Statement=connect.prepareStatement(q1);
			Statement.executeUpdate();
			String temp[] = args[1].split(",");
			if(temp.length!=3)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[2]=temp[2].replace(")", "");
			Statement.setString(1,temp[0]);
			Statement.setString(2,temp[1]);
			Statement.setString(3,temp[2]);
	        Statement.executeUpdate();
	        System.out.println("Successfully importing data!\n");
		}
		else if(args[0].equals("TEACHER"))
		{
			q1 = "INSERT INTO "+args[0]+" VALUES(?,?,?)";
			Statement=connect.prepareStatement(q1);
			String temp[] = args[1].split(",");
			if(temp.length!=3)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[2]=temp[2].replace(")", "");
			if(temp[2].equals("null"))
			{
				temp[2] = null;
			}
			if(temp[2]!=null)
			{
				int count = 0;
				Statement stmt = connect.createStatement();
				String q = "SELECT TEACHING_NO FROM TEACHING WHERE TEACHING_NO="+"\""+temp[2]+"\"";
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next())
				{
					count++;
				}
				if(count==0)
				{
					System.out.println("The teaching and research department where the teacher is located does not exist!");
					return ;
				}
			}
			Statement.setString(1,temp[0]);
			Statement.setString(2,temp[1]);
			Statement.setString(3,temp[2]);
	        Statement.executeUpdate();
	        System.out.println("Successfully importing data!\n");
		}
		else if(args[0].equals("DEPARTMENT")||args[0].equals("CLASS")||args[0].equals("POSTGRADUATE"))
		{
			int count = 0;
			q1 = "INSERT INTO "+args[0]+" VALUES(?,?,?)";
			if(args[1].contains("null"))
			{
				System.out.println("Input does not allow null values!");
				return;
			}
			Statement stmt = connect.createStatement();
			String temp[] = args[1].split(",");
			if(temp.length!=3)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[2]=temp[2].replace(")", "");
			String q = "";
			if(args[0].equals("DEPARTMENT"))
			{
				q = "SELECT SCHOOL_NO FROM SCHOOL WHERE SCHOOL_NO="+"\""+temp[2]+"\"";
			}
			else if(args[0].equals("CLASS"))
			{
				q = "SELECT DEPARTMENT_NO FROM DEPARTMENT WHERE DEPARTMENT_NO="+"\""+temp[2]+"\"";
			}
			else if(args[0].equals("POSTGRADUATE"))
			{
				q = "SELECT TEACHER_ID FROM TEACHER WHERE TEACHER_ID="+"\""+temp[2]+"\"";
			}
			ResultSet rs = stmt.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			while(rs.next())
			{
				count++;
			}
			if(count==0)
			{
				if(args[0].equals("DEPARTMENT"))
				{
					System.out.println("The school where the department is located dose not exist!");
				}
				else if(args[0].equals("CLASS"))
				{
					System.out.println("The department where the class is located dose not exist!");
				}
				else if(args[0].equals("POSTGRADUATE"))
				{
					System.out.println("The postgraduate's teacher does not exist!");
					return ;
				}
				return;
			}
			Statement=connect.prepareStatement(q1);
			Statement.setString(1,temp[0]);
			Statement.setString(2,temp[1]);
			Statement.setString(3,temp[2]);
	        Statement.executeUpdate();
	        System.out.println("Successfully importing data!\n");
		}
		else if(args[0].equals("STUDENT")||args[0].equals("TEACHING"))
		{
		
			q1 = "INSERT INTO "+args[0]+" VALUES(?,?,?,?)";
			if(args[1].contains("null")&&args[0].equals("TEACHING"))
			{
				System.out.println("Input does not allow null values!");
				return;
			}
			String q = "";
			Statement=connect.prepareStatement(q1);
			String temp[] = args[1].split(",");
			if(temp.length!=4)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[3]=temp[3].replace(")", "");
			if(temp[3].equals("null"))
			{
				temp[3]=null;
			}
			if(args[0].equals("STUDENT"))
			{
				q = "SELECT CLASS_NO FROM CLASS WHERE CLASS_NO="+"\""+temp[3]+"\"";
				
			}
			if(args[0].equals("TEACHING"))
			{
				q = "SELECT DEPARTMENT_NO FROM DEPARTMENT WHERE DEPARTMENT_NO="+"\""+temp[3]+"\"";
			}
			int count = 0;
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next())
			{
				count++;
			}
			if(count==0)
			{
				if(args[0].equals("STUDENT"))
				{
					System.out.println("The students's class does not exist!");
					return ;
				}
				if(args[0].equals("TEACHING"))
				{
					System.out.println("The department where the teaching and research section is located does not exist!");
					return ;
				}
			}
			
			Statement.setString(1,temp[0]);
			Statement.setString(2,temp[1]);
			Statement.setString(3,temp[2]);
			Statement.setString(4,temp[3]);
			
	        Statement.executeUpdate();
	        System.out.println("Successfully importing data!\n");
		}
		else if(args[0].equals("COURSE")||args[0].equals("ELECTIVE"))
		{
			q1 = "INSERT INTO "+args[0]+" VALUES(?,?)";
			if(args[1].contains("null"))
			{
				System.out.println("Input does not allow null values!");
				return;
			}
			String q = "";
			Statement=connect.prepareStatement(q1);
			String temp[] = args[1].split(",");
			if(temp.length!=2)
			{
				System.out.println("The number of input parameters does not meet the requirements!");
				return ;
			}
			temp[1] = temp[1].replace(")", "");
			Statement=connect.prepareStatement(q1);
			Statement.setString(1,temp[0]);
			Statement.setString(2,temp[1]);
	        Statement.executeUpdate();
	        System.out.println("Successfully importing data!\n");
		}
		
		else return;
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void deleteData(Connection connect,String input)
	{
		try
		{
			String q = "";
			String args[] = input.split("\\(");
			if(args[0].equals("PRINCIPLE")||args[0].equals("SCHOOL")||args[0].equals("CLASS")
					|| args[0].equals("POSTGRADUATE") ||args[0].equals("DEPARTMENT")||args[0].equals("TEACHER"))
			{
				String temp[] = args[1].split(",");
				if(temp.length!=3)
				{
					System.out.println("The number of input parameters does not meet the requirements!");
					return ;
				}
				temp[2]=temp[2].replace(")", "");
				if(args[0].equals("PRINCIPLE"))
				{
					q = "DELETE FROM "+args[0]+" WHERE NAME=\""+temp[0]+"\" AND PNUMBER=\""+temp[1]+"\" AND SCHOOL_NO=\""+temp[2]+"\"";
				}
				else if(args[0].equals("SCHOOL"))
				{
					q = "DELETE FROM "+args[0]+" WHERE SCHOOL_NO=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND ADDRESS=\""+temp[2]+"\"";
				}
				else if(args[0].equals("CLASS"))
				{
					q = "DELETE FROM "+args[0]+" WHERE CLASS_NO=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND DEPARTMENT_NO=\""+temp[2]+"\"";
				}
				else if(args[0].equals("TEACHER"))
				{
					q = "DELETE FROM "+args[0]+" WHERE TEACHER_ID=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND TEACHING_NO=\""+temp[2]+"\"";
				}
				else if(args[0].equals("POSTGRADUATE"))
				{
					q = "DELETE FROM "+args[0]+" WHERE POSTGRADUATE_ID=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND TEACHER_ID=\""+temp[2]+"\"";
				}
				else if(args[0].equals("DEPARTMENT"))
				{
					q = "DELETE FROM "+args[0]+" WHERE DEPARTMENT_NO=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND SCHOOL_NO=\""+temp[2]+"\"";
				}
			}
			else if(args[0].equals("COURSE"))
			{
				String temp[] = args[1].split(",");
				if(temp.length!=2)
				{
					System.out.println("The number of input parameters does not meet the requirements!");
					return ;
				}
				temp[1]=temp[1].replace(")", "");
				q = "DELETE FROM "+args[0]+" WHERE COURSE_NO=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\"";
			}
			else if(args[0].equals("TEACHING")||args[0].equals("STUDENT"))
			{
				String temp[] = args[1].split(",");
				if(temp.length!=4)
				{
					System.out.println("The number of input parameters does not meet the requirements!");
					return ;
				}
				temp[3]=temp[3].replace(")", "");
				if(args[0].equals("TEACHING"))
				{
					q = "DELETE FROM "+args[0]+" WHERE TEACHING_NO=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND ADDRESS=\""+temp[2]+"\" AND DEPARTMENT_NO=\""+temp[3]+"\"";
				
				}
				else if(args[0].equals("STUDENT"))
				{
					q = "DELETE FROM "+args[0]+" WHERE STUDENT_ID=\""+temp[0]+"\" AND NAME=\""+temp[1]+"\" AND AGE=\""+temp[2]+"\" AND CLASS_NO=\""+temp[3]+"\"";
					System.out.println(q);
				}
			}
			PreparedStatement Statement=connect.prepareStatement(q);
			int count = Statement.executeUpdate();
			System.out.println("You scuccessfully delete "+count+" records!");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void ConnectionQuery(Connection connect,String choice)
	{
		try
		{
			String q = "";
			if(choice.equals("1"))
			{
				q = "SELECT * FROM ELECTIVECOURSE";
			}
			else if(choice.equals("2"))
			{
				q = "SELECT * FROM TAS";
			}
			else if(choice.equals("3"))
			{
				q = "SELECT * FROM CAT";
			}
			else
			{
				System.out.println("Input error,please retry!");
				return ;
			}
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=columnCount;i++)
				{
					System.out.print(rs.getString(i)+"\t");
				}
				System.out.println();
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void groupQuery(Connection connect,String choice)
	{
		try
		{
			String q = "";
			if(choice.equals("1"))
			{
				q = "SELECT NAME,COUNT(*) FROM ELECTIVECOURSE GROUP BY NAME";
			}
			else if(choice.equals("2"))
			{
				q = "SELECT TEACHER,COUNT(*) FROM TAS GROUP BY TEACHER";
			}
			else if(choice.equals("3"))
			{
				q = "SELECT CLASS,COUNT(*) FROM CAT GROUP BY CLASS";
			}
			else
			{
				System.out.println("Input error,please retry!");
				return ;
			}
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=columnCount;i++)
				{
					System.out.print(rs.getString(i)+"\t");
				}
				System.out.println();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void nestedQuery(Connection connect,String choice)
	{
		try {
			String q = "";
			if(choice.equals("1"))
			{
				q = "SELECT DISTINCT STUDENT.NAME,COURSE.NAME FROM STUDENT,COURSE,ELECTIVE WHERE ELECTIVE.COURSE_NO = COURSE.COURSE_NO AND "
					+ "STUDENT.STUDENT_ID IN ( SELECT STUDENT.STUDENT_ID FROM STUDENT,CLASS WHERE CLASS.CLASS_NO = STUDENT.CLASS_NO AND CLASS.CLASS_NO =1)";
			}
			else if(choice.equals("2"))
			{
				q = "SELECT POSTGRADUATE_ID,POSTGRADUATE.NAME FROM POSTGRADUATE WHERE POSTGRADUATE.TEACHER_ID IN "
						+ "( SELECT TEACHER.TEACHER_ID FROM TEACHER,POSTGRADUATE "
						+ "WHERE TEACHER.TEACHER_ID = POSTGRADUATE.TEACHER_ID AND TEACHER.TEACHER_ID = 1)" ;
			}
			else
			{
				System.out.println("Input error,please retry!");
				return ;
			}
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=columnCount;i++)
				{
					System.out.print(rs.getString(i)+"\t");
				}
				System.out.println();
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main (String args[])
	{
		try {
	          Class.forName("com.mysql.jdbc.Driver");     //加载MYSQL JDBC驱动程序   
	          //Class.forName("org.gjt.mm.mysql.Driver");
	          System.out.println("Success loading Mysql Driver!");
	          System.out.println("\n");
	          System.out.println("Welcome to the Student Management System!");
	          Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/SCHOOL?useUnicode=true&characterEncoding=UTF-8","monty","123456") ;
	          Statement stmt = connect.createStatement();
	          while(true)
	          {
	        	  manu();
	        	  Scanner in = new Scanner(System.in);
	        	  String input = "";
	        	  String choice = in.nextLine();
	        	  switch (choice)
	        	  {
	        	  case "1":
	        		  manu1();
	        		  input = in.nextLine();
	        		  ParseAndInsertData(connect,input);
	        		  break;
	        	  case "0":
	        		  System.exit(0);
	        		  break;
	        	  case "2":
	        		  manu2();
	        		  String a = in.nextLine();
	        		  deleteData(connect,a);
	        		  break;
	        	  case "3":
	        		  manu3();
	        		  String b = in.nextLine();
	        		  ConnectionQuery(connect,b);
	        		  break;
	        	  case "4":
	        		  manu4();
	        		  String c = in.nextLine();
	        		  groupQuery(connect,c);
	        		  break;
	        	  case "5":
	        		  manu5();
	        		  String d = in.nextLine();
	        		  nestedQuery(connect,d);
	        		  break;
	        	  default :
	        		  System.out.println("Input error,please retry.");
	        		  break;
	        	  }
//	        	  String q1 = "select * from TEACHER";
	          
	        	  
	          }
	        }catch (Exception e) {
	          System.out.print("Error loading Mysql Driver!");
	          e.printStackTrace();
	        }
	        
		
	}
}
