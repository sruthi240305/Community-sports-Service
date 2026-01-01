package com.club.service;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import com.club.bean.EventRegistration;
import com.club.bean.Member;
import com.club.dao.EventRegistrationDAO;
import com.club.dao.MemberDAO;
import com.club.util.DuplicateRegistrationException;
import com.club.util.ValidationException;

public class ClubService {

    private MemberDAO memberDAO = new MemberDAO();
    private EventRegistrationDAO eventDAO = new EventRegistrationDAO();

    public Member viewMemberDetails(String memberID) throws ValidationException {
        return memberDAO.findMember(memberID);
    }

    public List<Member> viewAllMembers() throws ValidationException {
        return memberDAO.viewAllMembers();
    }

    public boolean registerNewMember(Member member) throws ValidationException, SQLException {
        return memberDAO.insertMember(member);
    }

    public boolean registerMemberForEvent(
            String memberID,
            String eventName,
            String sportType,
            Date eventDate,
            String sessionSlot,
            String venue,
            Date registrationDate) throws ValidationException, DuplicateRegistrationException {

        // Check duplicate registration
        EventRegistration duplicate =
                eventDAO.findDuplicateRegistration(memberID, eventName, eventDate, sessionSlot);

        if (duplicate != null) {
            throw new ValidationException("Member already registered for this event");
        }

        try {
            int regId = eventDAO.generateRegistrationID();

            EventRegistration reg = new EventRegistration();
            reg.setRegistrationID(regId);
            reg.setMemberID(memberID);
            reg.setEventName(eventName);
            reg.setSportType(sportType);
            reg.setEventDate(eventDate);
            reg.setSessionSlot(sessionSlot);
            reg.setVenue(venue);
            reg.setRegistrationDate(registrationDate);
            reg.setAttendanceStatus("Pending");
            reg.setResultStatus("Pending");

            return eventDAO.insertRegistration(reg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean markAttendanceAndResult(
            int registrationID,
            String attendanceStatus,
            String resultStatus) throws ValidationException {

        return eventDAO.updateAttendanceAndResult(
                registrationID, attendanceStatus, resultStatus);
    }

    public List<EventRegistration> listRegistrationsByMember(String memberID)
            throws ValidationException {

        return eventDAO.findRegistrationsByMember(memberID);
    }

    public List<EventRegistration> listRegistrationsByEvent(
            String eventName,
            Date eventDate) throws ValidationException {

        return eventDAO.findRegistrationsByEvent(eventName, eventDate);
    }

    public boolean removeMember(String memberID, Date referenceDate)
            throws ValidationException {

        List<EventRegistration> active =
                eventDAO.findActiveRegistrationsForMember(memberID, referenceDate);

        if (!active.isEmpty()) {
            throw new ValidationException("Member has active registrations");
        }

        return memberDAO.deleteMember(memberID);
    }
}
