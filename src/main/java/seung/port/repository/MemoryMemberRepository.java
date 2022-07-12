package seung.port.repository;

import org.springframework.stereotype.Repository;
import seung.port.domain.Member;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static List<Map<String, Object>> memberListMap = new ArrayList<Map<String, Object>>();


    @Override
    public void insert(Map<String, Object> memberMap) {

        memberListMap.add(memberMap);
    }

    @Override
    public void delete(int i) {
        memberListMap.remove(i);
    }

    @Override
    public Map<String, Object> select(int i) {
        return memberListMap.get(i);
    }

    @Override
    public List<Map<String, Object>> findAll() {

        return memberListMap;
    }

}
