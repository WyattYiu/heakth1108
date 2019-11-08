package com.itheima.health.dao;

import com.itheima.health.pojo.Member;

public interface MemberDao {


    Member isExistByTelephone(String telephone);

    void add(Member newmember);

    Integer getMemberReport(String month);

    Integer dayNewMember(String today);

    Integer findMemberCount();

    Integer weekNewMember(String monday);

    Integer monthNewMember(String first);
}
