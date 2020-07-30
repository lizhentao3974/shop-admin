package com.fh.shop.admin.controller.user;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.param.user.UserSearchParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.ExcelUtils;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.MD5Util;
import com.fh.shop.admin.vo.user.UserVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping("/toUserList")
    public String toUserList() {
        return "user/list";
    }

    @RequestMapping("/queryUserList")
    @ResponseBody
    public DataTableResult queryUserList(UserSearchParam userSearchParam) {
        return userService.queryUserList(userSearchParam);
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/add";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public ServerResponse addUser(User user) {
        String password = user.getPassword();
        String s = MD5Util.MD5Encode(password, "utf-8");
        user.setPassword(s);
        userService.addUser(user);
        return ServerResponse.success();
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    @LogAnnotation(info = "单个删除用户")
    public ServerResponse deleteUser(Long id) {
        userService.deleteUser(id);
        return ServerResponse.success();
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "/user/edit";
    }

    @RequestMapping("/editUser")
    @ResponseBody
    public ServerResponse editUser(Long id) {
        User user = userService.editUser(id);

        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setRealName(user.getRealName());
        userVo.setSex(user.getSex());
        userVo.setEmail(user.getEmail());
        userVo.setBirthday(DateUtil.date2str(user.getBirthday(), DateUtil.Y_M_D));
        userVo.setTelePhone(user.getTelePhone());
        userVo.setUserImage(user.getUserImage());
        userVo.setAreaName(user.getProvincialName() + "---->" + user.getCityName() + "---->" + user.getCountyName());
        userVo.setProvincial(user.getProvincial());
        userVo.setCity(user.getCity());
        userVo.setCounty(user.getCounty());
        return ServerResponse.success(userVo);
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    @LogAnnotation(info = "修改用户")
    public ServerResponse updateUser(User user, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(user.getUserImage())) {
            if (StringUtils.isNotEmpty(user.getOldImgFile())) {
                String oldImgFile = user.getOldImgFile();
                String realPath = request.getServletContext().getRealPath(oldImgFile);
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            user.setUserImage(user.getOldImgFile());
        }
        userService.updateUser(user);
        return ServerResponse.success();
    }

    @RequestMapping("/deleteUsers")
    @ResponseBody
    @LogAnnotation(info = "批量删除用户")
    public ServerResponse deleteUsers(@RequestParam("ids[]") Long[] ids) {
        return userService.deleteUsers(ids);
    }

    @RequestMapping("/emportPdf")
    @LogAnnotation(info = "导出pdf")
    public void emportPdf(UserSearchParam userSearchParam, HttpServletResponse response) {
        List<User> userList = userService.findUserParam(userSearchParam);

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/temp");
        try {
            Template userTemp = configuration.getTemplate("userTemp.html");

            Map result = new HashMap();
            result.put("userList", userList);

            StringWriter sw = new StringWriter();
            userTemp.process(result, sw);

            FileUtil.pdfDownloadFile(response, sw.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    @LogAnnotation(info = "用户登录")
    public ServerResponse login(User user, HttpServletRequest request) {
        String userName = user.getUserName();
        String password = user.getPassword();
        String s = MD5Util.MD5Encode(password, "utf-8");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ServerResponse.error(ResponseEnum.USERNAME_PASSWORD_IS_NULL);
        }
        User userDB = userService.findUserByName(userName);

        Long userId = userDB.getId();

        if (userDB == null) {
            return ServerResponse.error(ResponseEnum.USER_IS_NOT);
        }
        String userPassword = userDB.getPassword();

        if (!userPassword.equals(s)) {
            return ServerResponse.error(ResponseEnum.USER_PASSWORD_IS_NOT);
        }
        //先把当前时间转换成年月日格式的字符串，然后再把字符串格式的日期转换成Date类型
        Date date = DateUtil.str2date(DateUtil.date2str(new Date(), DateUtil.Y_M_D), DateUtil.Y_M_D);
        //获取当前用户的登陆时间
        Date loginDate1 = userDB.getLoginDate();

        //判断当前用户登录时间是否为null
        if (loginDate1 == null) {
            //如果为null,证明第一次登陆
            userDB.setLoginCount(1);
            //数据库重置登陆次数为 1
            userService.updateLoginCount(userId);
        } else {
            Date loginDate = DateUtil.str2date(DateUtil.date2str(loginDate1, DateUtil.Y_M_D), DateUtil.Y_M_D);
            //判断是否在当前当前时间之前
            if (date.after(loginDate)) {
                //如果两个时间不在同一天，就证明不是同一天登录，前端显示当天第一次登陆
                userDB.setLoginCount(1);
                //数据库重置登陆次数为 1
                userService.updateLoginCount(userId);
            } else {
                //如果在同一天登录的，就让前端显示的登陆次数加1
                Integer loginCount = userDB.getLoginCount();
                userDB.setLoginCount(loginCount + 1);
                //数据库中登陆次数每次加1
                userService.incrLoginCount(userId);
            }
        }

        request.getSession().setAttribute(SystemConstant.USER_LOGIN, userDB);

        //在user放入session中 后 ，把登录时间修改为当前时间
        userService.updateUserLoginDate(userId, new Date());
        return ServerResponse.success();

    }

    @RequestMapping("/importWord")
    @LogAnnotation(info = "导出用户信息word")
    public void importWord(UserSearchParam userSearchParam, HttpServletResponse response, HttpServletRequest request) {
        List<User> userList = userService.findUserParam(userSearchParam);
        List<UserVo> userVoList = toUserVoList(userList);
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), SystemConstant.TEMP_PATH);
        try {
            Template template = configuration.getTemplate("qqqq.xml");
            Map map = new HashMap();
            map.put("userList", userVoList);
            String path = SystemConstant.IMPORT_WORD_DOWN_PATH + UUID.randomUUID().toString() + SystemConstant.WORD_DOC;
            fileOutputStream = new FileOutputStream(path);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            template.process(map, outputStreamWriter);
            FileUtil.downloadFile(request, response, new File(path));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<UserVo> toUserVoList(List<User> userList) {
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : userList) {
            UserVo userVo = new UserVo();
            userVo.setUserName(user.getUserName());
            userVo.setRealName(user.getRealName());
            userVo.setEmail(user.getEmail());
            userVo.setTelePhone(user.getTelePhone());
            userVoList.add(userVo);
        }

        return userVoList;
    }

    @RequestMapping("/exportExcel")
    @LogAnnotation(info = "导出用户的Excel")
    public void exportExcel(UserSearchParam userSearchParam, HttpServletResponse response) {
        List<User> userList = userService.findUserParam(userSearchParam);
        List<UserVo> userVoList = userService.getUserVoList(userList);
        String[] titles = {"ID", "用户名", "真实姓名", "密码", "性别", "邮箱", "生日", "电话", "地区"};

        String[] props = {"id", "userName", "realName", "password", "sex", "email", "birthday", "telePhone", "areaName"};

        XSSFWorkbook workbook = ExcelUtils.titleRow(userVoList, props, titles, "用户列表");
        FileUtil.excelDownload(workbook, response);
    }

    @RequestMapping("/removeUser")
    public String removeUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/loginInfo.jsp";
    }


}
