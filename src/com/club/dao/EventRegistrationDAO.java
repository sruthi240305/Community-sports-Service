package com.club.dao;
import com.club.util.ValidationException;

import com.club.bean.EventRegistration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.club.util.DBUtil;
import com.club.util.DuplicateRegistrationException;
import com.club.util.ValidationException;
public class EventRegistrationDAO {
	public int generateRegistrationID() throws SQLException {
		int regid=1;
		Connection con=DBUtil.getDBConnection();
		java.sql.Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs=st.executeQuery("select  max(registrationId) from event_reg");
		if(rs.next()) {
			regid=rs.getInt(1)+1;
			
		}
		rs.close();
		st.close();
		con.close();
		return regid;
	}
	public boolean insertRegistration(EventRegistration reg) throws ValidationException {
        if (reg == null) {
            throw new ValidationException("The registration details cannot be null");
        }
        boolean done = false;
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement st = con.prepareStatement("insert into event_reg values(?,?,?,?,?,?,?,?,?,?)");
            st.setInt(1, reg.getRegistrationID());
            st.setString(2, reg.getMemberID());
            st.setString(3, reg.getEventName());
            st.setString(4, reg.getSportType());
            st.setDate(5, reg.getEventDate());
            st.setString(6, reg.getSessionSlot());
            st.setString(7, reg.getVenue());
            st.setDate(8, reg.getRegistrationDate());
            st.setString(9, reg.getAttendanceStatus());
            st.setString(10, reg.getResultStatus());

            int row = st.executeUpdate();
            if (row > 0) {
                done = true;
            }
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Failed to insert registration: " + e.getMessage());
        }
        return done;
    }

	public EventRegistration findRegistration(int registrationID)throws ValidationException {
		EventRegistration reg=null;
		if(registrationID<=0) {
			throw new ValidationException("the reg details cannot be null");
		}try {
			Connection con=DBUtil.getDBConnection();
		
			PreparedStatement st=con.prepareStatement("select * from event_reg where registrationId=?");
			st.setInt(1,registrationID);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				reg=new EventRegistration();
				reg.setRegistrationID(rs.getInt("registrationId"));
				reg.setMemberID(rs.getString("memberId"));
	            reg.setEventName(rs.getString("eventName"));
	            reg.setSportType(rs.getString("sportType"));
	            reg.setEventDate(rs.getDate("eventDate"));
	            reg.setSessionSlot(rs.getString("sessionSlot"));
	            reg.setVenue(rs.getString("venue"));
	            reg.setRegistrationDate(rs.getDate("registrationDate"));
	            reg.setAttendanceStatus(rs.getString("attendanceStatus"));
	            reg.setResultStatus(rs.getString("resultStatus"));
			}
			rs.close();
	        st.close();
	        con.close();
			}catch(Exception e) {
				e.printStackTrace();
			}return reg;
	}
	public java.util.List<EventRegistration> findRegistrationsByMember(String  memberID) throws ValidationException{
		List<EventRegistration> helothere=new ArrayList<>();
		if(memberID==null || memberID=="") {
			throw new ValidationException("something is wrong buddy");
		}
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement st=con.prepareStatement("select * from event_reg where memberID=?");
			st.setString(1,memberID);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				EventRegistration eventreg=new EventRegistration();
				 eventreg.setRegistrationID(rs.getInt("registrationId"));
		            eventreg.setMemberID(rs.getString("memberId"));
		            eventreg.setEventName(rs.getString("eventName"));
		            eventreg.setSportType(rs.getString("sportType"));
		            eventreg.setEventDate(rs.getDate("eventDate"));
		            eventreg.setSessionSlot(rs.getString("sessionSlot"));
		            eventreg.setVenue(rs.getString("venue"));
		            eventreg.setRegistrationDate(rs.getDate("registrationDate"));
		            eventreg.setAttendanceStatus(rs.getString("attendanceStatus"));
		            eventreg.setResultStatus(rs.getString("resultStatus"));
		            helothere.add(eventreg);

			}
			rs.close();
			st.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	return helothere;}
	public java.util.List<EventRegistration> findRegistrationsByEvent(String eventName,java.sql.Date eventDate) throws ValidationException{
		List<EventRegistration> helothere=new ArrayList<>();
		if(eventName==null || eventName=="") {
			throw new ValidationException("something is wrong buddy");
		}
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement st=con.prepareStatement("select * from event_reg where eventName=? and eventDate=?");
			st.setString(1,eventName);
			st.setDate(2,eventDate);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				EventRegistration eventreg=new EventRegistration();
				 eventreg.setRegistrationID(rs.getInt("registrationId"));
		            eventreg.setMemberID(rs.getString("memberId"));
		            eventreg.setEventName(rs.getString("eventName"));
		            eventreg.setSportType(rs.getString("sportType"));
		            eventreg.setEventDate(rs.getDate("eventDate"));
		            eventreg.setSessionSlot(rs.getString("sessionSlot"));
		            eventreg.setVenue(rs.getString("venue"));
		            eventreg.setRegistrationDate(rs.getDate("registrationDate"));
		            eventreg.setAttendanceStatus(rs.getString("attendanceStatus"));
		            eventreg.setResultStatus(rs.getString("resultStatus"));
		            helothere.add(eventreg);

			}
			rs.close();
			st.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	return helothere;
	}
	public EventRegistration findDuplicateRegistration(
            String memberID,
            String eventName,
            java.sql.Date eventDate,
            String sessionSlot) throws ValidationException, DuplicateRegistrationException {

        if (memberID == null || eventName == null || sessionSlot == null || eventDate == null) {
            throw new ValidationException("Invalid input for checking duplicate registration");
        }

        EventRegistration reg = null;
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM event_reg WHERE memberId=? AND eventName=? AND eventDate=? AND sessionSlot=?"
            );
            ps.setString(1, memberID);
            ps.setString(2, eventName);
            ps.setDate(3, eventDate);
            ps.setString(4, sessionSlot);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reg = new EventRegistration();
                reg.setRegistrationID(rs.getInt("registrationId"));
                reg.setMemberID(rs.getString("memberId"));
                reg.setEventName(rs.getString("eventName"));
                reg.setSportType(rs.getString("sportType"));
                reg.setEventDate(rs.getDate("eventDate"));
                reg.setSessionSlot(rs.getString("sessionSlot"));
                reg.setVenue(rs.getString("venue"));
                reg.setRegistrationDate(rs.getDate("registrationDate"));
                reg.setAttendanceStatus(rs.getString("attendanceStatus"));
                reg.setResultStatus(rs.getString("resultStatus"));

                throw new DuplicateRegistrationException("Member already registered for this event/session/date");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ValidationException("Error checking duplicate registration: " + e.getMessage());
        }

        return reg;
    }
    public boolean updateAttendanceAndResult(
            int registrationID,
            String attendanceStatus,
            String resultStatus) throws ValidationException {

        if (registrationID <= 0 || attendanceStatus == null) {
            throw new ValidationException("Invalid input for updating attendance/result");
        }

        boolean updated = false;
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE event_reg SET attendanceStatus=?, resultStatus=? WHERE registrationId=?"
            );
            ps.setString(1, attendanceStatus);
            ps.setString(2, resultStatus);
            ps.setInt(3, registrationID);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                updated = true;
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Failed to update attendance/result: " + e.getMessage());
        }

        return updated;
    }
	public List<EventRegistration> findActiveRegistrationsForMember(
	        String memberID,
	        java.sql.Date referenceDate) throws ValidationException {

	    List<EventRegistration> list = new ArrayList<>();

	    if (memberID == null || referenceDate == null) {
	        throw new ValidationException("Invalid input");
	    }

	    try {
	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps = con.prepareStatement(
	            "SELECT * FROM event_reg WHERE memberId=? AND eventDate >= ?"
	        );

	        ps.setString(1, memberID);
	        ps.setDate(2, referenceDate);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            EventRegistration reg = new EventRegistration();
	            reg.setRegistrationID(rs.getInt("registrationId"));
	            reg.setMemberID(rs.getString("memberId"));
	            reg.setEventName(rs.getString("eventName"));
	            reg.setSportType(rs.getString("sportType"));
	            reg.setEventDate(rs.getDate("eventDate"));
	            reg.setSessionSlot(rs.getString("sessionSlot"));
	            reg.setVenue(rs.getString("venue"));
	            reg.setRegistrationDate(rs.getDate("registrationDate"));
	            reg.setAttendanceStatus(rs.getString("attendanceStatus"));
	            reg.setResultStatus(rs.getString("resultStatus"));

	            list.add(reg);
	        }

	        rs.close();
	        ps.close();
	        con.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
}
