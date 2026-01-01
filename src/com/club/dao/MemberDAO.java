package com.club.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.club.bean.Member;
import com.club.util.DBUtil;
import com.club.util.ValidationException;

public class MemberDAO {
	public String generateMemberID() throws SQLException {
	    Connection con = DBUtil.getDBConnection();
	    String newID = "MB2001"; 
	    PreparedStatement st = con.prepareStatement("SELECT memberId FROM member ORDER BY memberId DESC LIMIT 1");
	    ResultSet rs = st.executeQuery();
	    if (rs.next()) {
	        String lastID = rs.getString("memberId"); 
	        int num = Integer.parseInt(lastID.substring(2)) + 1; 
	        newID = "MB" + num;
	    }
	    rs.close();
	    st.close();
	    con.close();
	    return newID;
	}

	public Member findMember(String memberID) throws ValidationException {
		Member mem=null;
		if(memberID==null) {
			throw new ValidationException("the reg details cannot be null");
		}try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("select * from member where  memberId =?");
			ps.setString(1, memberID);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				mem=new Member();
				 mem.setMemberID(rs.getString("memberId"));
		            mem.setFullName(rs.getString("fullName"));
		            mem.setAge(rs.getInt("age"));
		            mem.setGender(rs.getString("gender"));
		            mem.setPrimarySport(rs.getString("primarySport"));
		            mem.setMobile(rs.getString("mobile"));
		            mem.setEmail(rs.getString("email"));
		            mem.setStatus(rs.getString("status"));
			}
			rs.close();
			ps.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
			
		}return mem;
	}
	public java.util.List<Member> viewAllMembers() {
		List<Member> mysolution=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("select * from member");
			ResultSet rs=ps.executeQuery();

			while(rs.next()) {
				Member mem=new Member();
	            mem.setMemberID(rs.getString("memberId"));
	            mem.setFullName(rs.getString("fullName"));
	            mem.setAge(rs.getInt("age"));
	            mem.setGender(rs.getString("gender"));
	            mem.setPrimarySport(rs.getString("primarySport"));
	            mem.setMobile(rs.getString("mobile"));
	            mem.setEmail(rs.getString("email"));
	            mem.setStatus(rs.getString("status"));
	            mysolution.add(mem);
			}
			rs.close();
			ps.close();
			con.close();

		}catch(Exception e) {
			e.printStackTrace();
		}return mysolution;
	}
	public boolean insertMember(Member member) throws ValidationException, SQLException {
	    if (member == null) {
	        throw new ValidationException("The registration details cannot be null");
	    }

	    Connection con = DBUtil.getDBConnection();

	    // Generate unique member ID
	    String uniqueID = generateMemberID();
	    member.setMemberID(uniqueID);

	    PreparedStatement ps = con.prepareStatement(
	        "INSERT INTO member VALUES(?,?,?,?,?,?,?,?)"
	    );
	    ps.setString(1, member.getMemberID());
	    ps.setString(2, member.getFullName());
	    ps.setInt(3, member.getAge());
	    ps.setString(4, member.getGender());
	    ps.setString(5, member.getPrimarySport());
	    ps.setString(6, member.getMobile());
	    ps.setString(7, member.getEmail());
	    ps.setString(8, member.getStatus());

	    int row = ps.executeUpdate();

	    ps.close();
	    con.close();

	    return row > 0;
	}

	public boolean updateMemberStatus(String memberID,String status) throws ValidationException {
		boolean done= false;
		if(memberID==null || status==null) {
			throw new ValidationException("the reg details cannot be null");
		}
		try{
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("UPDATE member SET status = ? WHERE memberId = ?");
			ps.setString(1,status);
			ps.setString(2, memberID);
			int row=ps.executeUpdate();
			if(row>0) {
				done=true;
			}
			ps.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return done;}
	public boolean deleteMember(String memberID) throws ValidationException{
		boolean done=false;
		if(memberID==null) {
			throw new ValidationException("problem dude");
		}
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("DELETE FROM member WHERE memberId = ?");
			ps.setString(1, memberID);
			int row=ps.executeUpdate();
			if(row>0) {
				done=true;
			}ps.close();
			con.close();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}
	
}
