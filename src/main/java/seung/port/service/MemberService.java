package seung.port.service;

import org.springframework.stereotype.Service;
import seung.port.domain.Member;
import seung.port.repository.MemberRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private int i;
    private boolean findExist = false;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Map<String, Object> join(Member member) {

        List<Map<String, Object>> memberList = memberRepository.findAll();
        Map<String, Object> rtnMap = new HashMap<>();
        boolean isExist = false;
        String message = "";

        for (int i = 0; i < memberList.size(); i++) {
            Map<String, Object> exMemberMap = new HashMap<>();
            exMemberMap = memberList.get(i);

            if (member.getId().equals(exMemberMap.get("id"))) {
                isExist = true;
            }
        }

        if (isExist) {
            message = "이미 존재하는 ID 입니다.";
            rtnMap.put("message", message);
            rtnMap.put("success", "N");
            return rtnMap;
        }

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("userName", member.getUserName());
        memberMap.put("id", member.getId());
        memberMap.put("password", member.getPassword());
        memberMap.put("phoneNumber", member.getPhoneNumber());

        memberRepository.insert(memberMap);

        message = "회원가입이 완료되었습니다.";

        rtnMap.put("message", message);
        rtnMap.put("success", "Y");

        return rtnMap;
    }

    //회원 삭제
    public Map<String, Object> deleteMember(Member member) {

        List<Map<String, Object>> memberList = memberRepository.findAll();
        Map<String, Object> rtnMap = new HashMap<>();
        boolean isExist = false;
        String message = "";

        for (int a = 0; a < memberList.size(); a++) {
            Map<String, Object> exMemberMap = new HashMap<>();
            exMemberMap = memberList.get(a);

            if (member.getPassword().equals(exMemberMap.get("password"))) {
                i = a;
                isExist = true;
            }
        }

        if (!isExist) {
            message = "삭제 실패";
            rtnMap.put("message", message);
            rtnMap.put("success", "N");
            return rtnMap;
        }

        memberRepository.delete(i);

        message = "삭제 완료.";
        rtnMap.put("message", message);
        rtnMap.put("success", "Y");
        return rtnMap;
    }

    //아이디 찾기
    public boolean findId(Member member) {

        List<Map<String, Object>> memberList = memberRepository.findAll();

        for (int a = 0; a < memberList.size(); a++) {
            Map<String, Object> exMemberMap = memberList.get(a);

            if (member.getPhoneNumber().equals(exMemberMap.get("phoneNumber")) &&
                    member.getUserName().equals(exMemberMap.get("userName"))) {
                i = a;
                return findExist = true;
            }
        }
        return findExist;
    }

    // 비밀번호 찾기
    public boolean findPw(Member member) {

        List<Map<String, Object>> memberList = memberRepository.findAll();

        for (int a = 0; a < memberList.size(); a++) {
            Map<String, Object> exMemberMap = memberList.get(a);

            if (member.getPhoneNumber().equals(exMemberMap.get("phoneNumber")) &&
                    member.getUserName().equals(exMemberMap.get("userName")) &&
                    member.getId().equals(exMemberMap.get("id"))) {
                i = a;
                return findExist = true;
            }
        }
        return findExist;
    }

    //아디 비번 찾기 맵 생성
    public Map<String, Object> findMemberMap(boolean isExist) {

        String message = "";
        Member member = new Member();
        Map<String, Object> rtnMap = new HashMap<>();

        if (!findExist) {
            message = "실패";
            rtnMap.put("message", message);
            rtnMap.put("success", "N");
            return rtnMap;
        }

        message = "성공";
        rtnMap.put("password", memberRepository.select(i).get("password"));
        rtnMap.put("id", memberRepository.select(i).get("id"));
        rtnMap.put("message", message);
        rtnMap.put("success", "Y");
        return rtnMap;
    }

    public Map<String, Object> loginMember(Member member) {

        List<Map<String, Object>> memberList = memberRepository.findAll();
        Map<String, Object> rtnMap = new HashMap<>();
        boolean isExist = false;
        String message = "";

        for (int a = 0; a < memberList.size(); a++) {
            Map<String, Object>  exMemberMap = memberList.get(a);

            if (member.getId().equals(exMemberMap.get("id")) &&
            member.getPassword().equals(exMemberMap.get("password"))) {

                i = a;
                isExist = true;
            }
        }

        if (!isExist) {
            message = "실패";
            rtnMap.put("message", message);
            rtnMap.put("success", "N");
            return rtnMap;
        }

        message = "성공";
        rtnMap.put("id", memberRepository.select(i).get("id"));
        rtnMap.put("userName", memberRepository.select(i).get("userName"));
        rtnMap.put("phoneNumber", memberRepository.select(i).get("phoneNumber"));
        rtnMap.put("message", message);
        rtnMap.put("success", "Y");

        return rtnMap;
    }
}
