package cn.wx.ycloudtech.service;

import cn.wx.ycloudtech.domain.Organization;

import java.util.List;

public interface OrganizationService {
    String getOrgNameByUserId(String userId);

    Integer addNewOrgan(Organization organizationNew);

    boolean ifUserHasOrgan(String userId);

    List<Organization> getAllSubmitOrgan();

    List<Organization> getOrganByUserId(String userId);
}
