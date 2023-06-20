package org.dromara.system.controller.system;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.system.domain.SysUser;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.system.domain.vo.SysUserStudentVo;
import org.dromara.system.domain.bo.SysUserStudentBo;
import org.dromara.system.service.ISysUserStudentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 学员
 *
 * @author Lion Li
 * @date 2023-06-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userStudent")
public class SysUserStudentController extends BaseController {

    private final ISysUserStudentService sysUserStudentService;
    private final ISysUserService userService;

    /**
     * 查询学员列表
     */
    @SaCheckPermission("system:userStudent:list")
    @GetMapping("/list")
    public TableDataInfo<SysUserStudentVo> list(SysUserStudentBo bo, PageQuery pageQuery) {
        return sysUserStudentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出学员列表
     */
    @SaCheckPermission("system:userStudent:export")
    @Log(title = "学员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserStudentBo bo, HttpServletResponse response) {
        List<SysUserStudentVo> list = sysUserStudentService.queryList(bo);
        ExcelUtil.exportExcel(list, "学员", SysUserStudentVo.class, response);
    }

    /**
     * 获取学员详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:userStudent:query")
    @GetMapping("/{userId}")
    public R<SysUserStudentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(sysUserStudentService.queryById(userId));
    }

    /**
     * 新增学员
     */
    @SaCheckPermission("system:userStudent:add")
    @Log(title = "学员", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserStudentBo bo) {
        SysUserBo user = bo.getSysUserBo();
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        userService.insertUser(user);
        return toAjax(sysUserStudentService.insertByBo(bo));
    }

    /**
     * 修改学员
     */
    @SaCheckPermission("system:userStudent:edit")
    @Log(title = "学员", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserStudentBo bo) {
        return toAjax(sysUserStudentService.updateByBo(bo));
    }

    /**
     * 删除学员
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:userStudent:remove")
    @Log(title = "学员", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(sysUserStudentService.deleteWithValidByIds(List.of(userIds), true));
    }
}
