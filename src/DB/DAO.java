package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO
{
	private static DAO getDao = null;

	public static DAO getInstance()
	{
		if (getDao == null)
		{
			getDao = new DAO();
		}
		return getDao;
	}

	private DAO()
	{
	}
	
	private Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "1111");

		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("Connection Faile");
		}

		return conn;
	}
	
	public int insert(DTO dto) {
		
		Connection conn = getConnection();
		PreparedStatement ppst = null;
		
		int res = 0;
		try
		{
			ppst = conn.prepareStatement("insert into Bomber values(?,?)");
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getPwd());
			
			res = ppst.executeUpdate();
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			try
			{
				if(ppst!=null) ppst.close();
				if(conn!=null) conn.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		
		return res;
		
	}
	
	public DTO selAcc(DTO dto) {
		Connection conn = getConnection();
		PreparedStatement ppst = null;
		ResultSet rs = null;
		
		DTO getdto = null;
		
		try
		{
			ppst = conn.prepareStatement("select * from Bomber where id = ? and pwd = ?");
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getPwd());
			
			rs = ppst.executeQuery();
			
			if (rs.next())
			{
				getdto = new DTO(null);
				getdto.setId(rs.getString(1));
				getdto.setPwd(rs.getString(2));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			try
			{
				if(rs!=null) rs.close();
				if(ppst!=null) ppst.close();
				if(conn!=null) conn.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return getdto;
	}
	
}
