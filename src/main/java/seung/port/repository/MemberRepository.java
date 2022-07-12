package seung.port.repository;

import seung.port.domain.Member;

import java.util.List;
import java.util.Map;

public interface MemberRepository {

    Map<String, Object> select(int i);
    void insert(Map<String, Object> memberMap);
    void delete(int i);
    List<Map<String, Object>> findAll();
}
