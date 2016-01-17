package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import vo.Member;

public class MemberDao {
	DataSource ds;

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public void createTable() throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"CREATE TABLE MEMBERS (" +
					"	MNO INTEGER NOT NULL," +
					"	EMAIL    VARCHAR(40)  NOT NULL," +
					"	PWD      VARCHAR(100) NOT NULL," +
					"	MNAME    VARCHAR(50)  NOT NULL," +
					"	CRE_DATE DATETIME     NOT NULL," +
					"	MOD_DATE DATETIME     NOT NULL " +
					")"
					);
			
			stmt.execute();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			}
			catch(Exception e) {}
			
			try {
				if (connection != null) {
					connection.close();
				}
			}
			catch(Exception e) {}
		}
	}
	
	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"SELECT MNAME,EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?"
					);
			
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				return new Member()
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"));
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}
			catch(Exception e) {}
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			}
			catch(Exception e) {}
			
			try {
				if (connection != null) {
					connection.close();
				}
			}
			catch(Exception e) {}
		}
	}
	
	public int insert(Member member) throws Exception  {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS (EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE) VALUES (?, ?, ?, NOW(), NOW())"
					);
			
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			
			return stmt.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			}
			catch(Exception e) {}
			
			try {
				if (connection != null) {
					connection.close();
				}
			}
			catch(Exception e) {}
		}
	}
	
}
