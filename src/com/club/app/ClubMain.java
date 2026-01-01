package com.club.app;

import java.sql.Date;
import java.util.Scanner;

import com.club.bean.Member;
import com.club.service.ClubService;
import com.club.util.ValidationException;
import com.club.util.DuplicateRegistrationException;
import com.club.util.ActiveRegistrationsExistException;

public class ClubMain {

    private static ClubService service = new ClubService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("--- Community Sports Club Console ---");

        // ===================== DEMO 1: Register a new member =====================
        Member m = new Member();
        m.setFullName("Meenakshi Rao");
        m.setAge(27);
        m.setGender("FEMALE");
        m.setPrimarySport("BADMINTON");
        m.setMobile("9998887771");
        m.setEmail("meenakshi.rao@example.com");
        m.setStatus("ACTIVE");

        try {
            boolean ok = service.registerNewMember(m); // DAO generates unique memberID
            if (ok) {
                System.out.println("MEMBER REGISTERED, ID: " + m.getMemberID());
            } else {
                System.out.println("MEMBER REGISTRATION FAILED");
            }
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // ===================== DEMO 2: Register the member for an event =====================
        try {
            Date eventDate = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000); // 3 days later
            Date regDate = new Date(System.currentTimeMillis());

            boolean ok = service.registerMemberForEvent(
                    m.getMemberID(),               // use generated memberID
                    "Weekend Badminton Doubles",
                    "BADMINTON",
                    eventDate,
                    "09:00-10:30",
                    "Indoor Court 1",
                    regDate
            );

            System.out.println(ok
                    ? "EVENT REGISTRATION SUCCESS"
                    : "EVENT REGISTRATION FAILED");

        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // ===================== DEMO 3: List all members =====================
        try {
            System.out.println("\n--- All Members ---");
            for (Member mem : service.viewAllMembers()) {
                System.out.println(mem.getMemberID() + " | " + mem.getFullName() + " | " + mem.getPrimarySport() + " | " + mem.getStatus());
            }
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // ===================== DEMO 4: Attempt safe removal =====================
        try {
            boolean removed = service.removeMember(m.getMemberID(), new Date(System.currentTimeMillis()));
            System.out.println(removed
                    ? "MEMBER REMOVED SUCCESSFULLY"
                    : "MEMBER REMOVAL FAILED");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        sc.close();
    }
}
