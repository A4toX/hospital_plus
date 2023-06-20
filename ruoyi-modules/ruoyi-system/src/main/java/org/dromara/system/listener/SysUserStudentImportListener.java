package org.dromara.system.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.excel.core.ExcelListener;
import org.dromara.common.excel.core.ExcelResult;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysBase;
import org.dromara.system.domain.SysRole;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.bo.SysUserStudentBo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.system.domain.vo.SysuserStudentImportVo;
import org.dromara.system.service.*;

import java.util.List;

import static org.dromara.common.core.constant.UserConstants.IDENTITY_STUDENT;

@Slf4j
public class SysUserStudentImportListener extends AnalysisEventListener<SysuserStudentImportVo> implements ExcelListener<SysuserStudentImportVo> {
    private final ISysUserService userService;
    private final ISysUserStudentService studentService;
    private final ISysBaseService baseService;

    private final ISysRoleService roleService;

    private final String password;

    private final Boolean isUpdateSupport;

    private final Long operUserId;

    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public SysUserStudentImportListener(Boolean isUpdateSupport) {
        String initPassword = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("sys.user.initPassword");
        this.userService = SpringUtils.getBean(ISysUserService.class);
        this.studentService = SpringUtils.getBean(ISysUserStudentService.class);
        this.baseService = SpringUtils.getBean(ISysBaseService.class);
        this.roleService = SpringUtils.getBean(ISysRoleService.class);
        this.password = BCrypt.hashpw(initPassword);
        this.isUpdateSupport = isUpdateSupport;
        this.operUserId = LoginHelper.getUserId();
    }

    @Override
    public void invoke(SysuserStudentImportVo studentVo, AnalysisContext context) {
        //获取用户
        SysUserVo sysUser = this.userService.selectUserByPhonenumber(studentVo.getPhonenumber());
        try {
            // 验证是否存在这个用户
            if (ObjectUtil.isNull(sysUser)) {
                //新增用户
                SysUserBo userBo = this.initUserBo(studentVo);
                ValidatorUtils.validate(userBo);
                userService.insertUser(userBo);
                //新增学员
                SysUserStudentBo studentBo = this.initStudentBo(studentVo,userBo.getUserId());
                ValidatorUtils.validate(studentBo);
                studentService.insertByBo(studentBo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(userBo.getUserName()).append(" 导入成功");
            } else if (isUpdateSupport) {
                //更新用户信息
                Long userId = sysUser.getUserId();
                SysUserBo userBo = this.initUserBo(studentVo);
                userBo.setUserId(userId);
                ValidatorUtils.validate(userBo);
                userService.checkUserAllowed(userBo.getUserId());
                userService.checkUserDataScope(userBo.getUserId());
                userBo.setUpdateBy(operUserId);
                userService.updateUser(userBo);
                //更新学员信息
                SysUserStudentBo studentBo = this.initStudentBo(studentVo,userId);
                studentService.updateByBo(studentBo);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(studentVo.getRealName()).append(" 更新成功");
            } else {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(studentVo.getRealName()).append(" 已存在");
            }
        } catch (Exception e) {
            failureNum++;
            String msg = "<br/>" + failureNum + "、账号 " + studentVo.getRealName() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<SysuserStudentImportVo> getExcelResult() {
        return new ExcelResult<>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<SysuserStudentImportVo> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }
        };
    }


    private SysUserBo initUserBo(SysuserStudentImportVo studentVo) {
        SysUserBo userBo = new SysUserBo();
        userBo.setUserName(studentVo.getPhonenumber());//账号默认手机号
        userBo.setRealName(studentVo.getRealName());//真实姓名
        userBo.setPhonenumber(studentVo.getPhonenumber());//手机号
        userBo.setSex(studentVo.getSex());//性别
        userBo.setEmail(studentVo.getEmail());//邮箱
        userBo.setUserType("sys_user");//用户类型
        userBo.setIdcardType(studentVo.getIdcardType());//证件类型
        userBo.setIdcardNumb(studentVo.getIdcardNumb());//证件号码
        userBo.setIdentity(IDENTITY_STUDENT);//学生类型
        //获取角色id
        SysRole role = roleService.selectRoleByRoleKey("student");
        if (role!=null){
            userBo.setRoleIds(new Long[]{role.getRoleId()});
        }
        userBo.setPassword(password);//部门id
        userBo.setCreateBy(operUserId);//数据创建人
        return userBo;
    }


    private SysUserStudentBo initStudentBo(SysuserStudentImportVo studentVo, Long userId) {
        SysUserStudentBo studentBo = new SysUserStudentBo();
        studentBo.setUserId(userId);//用户id
        studentBo.setPersonType(studentVo.getPersonType());//人员类型
        studentBo.setStudentType(studentVo.getStudentType());//学员类型
        studentBo.setResidentYear(studentVo.getResidentYear());//招录年份
        //获取用户专业
        SysBase base = baseService.selectBaseByCode(studentVo.getBaseCode());
        if (base!=null){
            studentBo.setBaseId(base.getBaseId());//专业id
        }
        studentBo.setCreateBy(operUserId);//数据创建人
        return studentBo;
    }





}
