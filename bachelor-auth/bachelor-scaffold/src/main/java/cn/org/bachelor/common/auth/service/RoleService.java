package cn.org.bachelor.common.auth.service;

import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.common.auth.AuthValueHolderService;
import cn.org.bachelor.common.auth.dao.RoleMapper;
import cn.org.bachelor.common.auth.dao.RoleMenuMapper;
import cn.org.bachelor.common.auth.dao.RolePermissionMapper;
import cn.org.bachelor.common.auth.dao.UserRoleMapper;
import cn.org.bachelor.common.auth.domain.Role;
import cn.org.bachelor.common.auth.domain.RoleMenu;
import cn.org.bachelor.common.auth.domain.RolePermission;
import cn.org.bachelor.common.auth.domain.UserRole;
import cn.org.bachelor.common.auth.vo.UserSysParam;
import cn.org.bachelor.common.auth.vo.UserVo;
import cn.org.bachelor.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2018/10/27
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private AuthValueHolderService valueHolder;

    /**
     * @return
     */
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    /**
     * @param orgCode
     * @param keyWord
     * @return
     */
    public List<Role> findViaOrg(String orgCode, String keyWord) {
        Example ex = new Example(Role.class);
        ex.setOrderByClause("NAME ASC");
        ex.createCriteria()
                .andEqualTo("orgCode", orgCode);
        if (StringUtils.isNotEmpty(keyWord)) {
            keyWord = "%" + keyWord + "%";
            ex.or().orLike("name", keyWord)
                    .orLike("code", keyWord);
        }
        return roleMapper.selectByExample(ex);
    }

    /**
     * @param role
     * @return
     */
    public Role createRole(Role role) {
        role.setId(UUID.randomUUID().toString());
        role.setUpdateTime(new Date());
        role.setUpdateUser("system");
        Role r = new Role();
        r.setCode(role.getCode());
        r = roleMapper.selectOne(r);
        if (r != null) {
            throw new BusinessException("duplicate_role_code");
        }
        roleMapper.insert(role);
        return role;
    }

    public List<UserVo> findUserByClientID(UserSysParam param) {
        param.setClientId(valueHolder.getClientID());
        return null;
    }

    /**
     * @param roleID
     */
    public void deleteRole(String roleID) {
        Role role = roleMapper.selectByPrimaryKey(roleID);
        if (role == null) {
            return;
        }
        Example ex = new Example(RolePermission.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        rolePermissionMapper.deleteByExample(ex);
        ex = new Example(UserRole.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        userRoleMapper.deleteByExample(ex);
        ex = new Example(RoleMenu.class);
        ex.createCriteria().andEqualTo("roleCode", role.getCode());
        roleMenuMapper.deleteByExample(ex);
        roleMapper.delete(role);
    }

    /**
     * @param role
     */
    public void modifyRole(Role role) {
        if (StringUtils.isEmpty(role.getId())) {
            throw new BusinessException("role_id_must_be_exist");
        }
        Role roleDB = roleMapper.selectByPrimaryKey(role.getId());
        //
        roleDB.setName(role.getName());
        roleDB.setOrgCode(role.getOrgCode());
        roleDB.setUpdateTime(new Date());
        roleDB.setUpdateUser("system");
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * 获得角色下的用户
     *
     * @param roleCode
     * @return
     */
    public List<UserVo> getRoleUsers(String roleCode) {
        return null;
    }

    /**
     * 获得角色下的用户
     *
     * @param userCode
     * @return roleCodes
     */
    public List<String> getUserRoles(String userCode) {
        UserRole ur = new UserRole();
        ur.setUserCode(userCode);
        List<UserRole> userRoles = userRoleMapper.select(ur);
        List<String> result = new ArrayList<>(userRoles.size());
        userRoles.forEach(userRole -> {
            result.add(userRole.getRoleCode());
        });
        return result;
    }

    /**
     * @param roleCode
     * @param users
     */
    public void addUsersToRole(String roleCode, List<UserVo> users) {
        if (StringUtils.isEmpty(roleCode)) {
            throw new BusinessException("role_code_must_be_exist");
        }
        for (UserVo user : users) {
            UserRole ur = new UserRole();
            ur.setRoleCode(roleCode);
            ur.setUserCode(user.getCode());
            ur.setUserId(user.getId());
//            ur.setUserOrgId(user.getOrgId());
//            ur.setUserDeptName(user.getDeptName());
//            ur.setUserDeptPath(user.getDeptPath());
            if (userRoleMapper.selectOne(ur) != null) {
                throw new BusinessException("user_already_in_role", roleCode, user.getCode());
            }
            ur.setId(UUID.randomUUID().toString());
            ur.setUpdateTime(new Date());
            ur.setUpdateUser("system");
            userRoleMapper.insert(ur);
        }
    }

    /**
     * @param roleCode
     * @param users
     */
    public void deleteUsersFromRole(String roleCode, List<String> users) {
        Example ex = new Example(UserRole.class);
        ex.createCriteria()
                .andEqualTo("roleCode", roleCode)
                .andIn("userCode", users);
        userRoleMapper.deleteByExample(ex);
    }


    /**
     * @param roleID
     * @return
     */
    public Role selectByPrimaryKey(String roleID) {
        return roleMapper.selectByPrimaryKey(roleID);
    }
}
