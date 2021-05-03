package cn.wx.ycloudtech.service;

import cn.wx.ycloudtech.domain.Organization;

public interface OrganizationService {
    String getOrgNameByUserId(String userId);

    Integer addNewOrgan(Organization organizationNew);

    boolean ifUserHasOrgan(String userId);
}
