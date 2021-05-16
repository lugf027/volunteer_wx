package cn.wx.ycloudtech.implement;

import cn.wx.ycloudtech.domain.Organization;
import cn.wx.ycloudtech.mapper.OrganizationMapper;
import cn.wx.ycloudtech.service.OrganizationService;
import cn.wx.ycloudtech.util.MyConstants;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrganizationService")
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;


    @Override
    public String getOrgNameByUserId(String userId) {
        List<Organization> organizationList = organizationMapper.selectList(new EntityWrapper<Organization>()
                .eq("USER_ID", userId));
        if (organizationList.isEmpty())
            return null;
        else
            return organizationList.get(0).getOrganName();
    }

    @Override
    public Integer addNewOrgan(Organization organizationNew) {
        return organizationMapper.insert(organizationNew);
    }

    @Override
    public boolean ifUserHasOrgan(String userId) {
        List<Organization> organizationList = organizationMapper.selectList(new EntityWrapper<Organization>()
                .eq("USER_ID", userId));
        // 记录为空，用户还没有成为招募者
        return !organizationList.isEmpty();
    }

    @Override
    public List<Organization> getAllSubmitOrgan() {
        return organizationMapper.selectList(new EntityWrapper<Organization>()
                .eq("ORGAN_STATUS", MyConstants.ORGAN_SUBMIT));
    }

    @Override
    public List<Organization> getOrganByUserId(String userId) {
        return organizationMapper.selectList(new EntityWrapper<Organization>()
                .eq("USER_ID", userId));
    }
}
