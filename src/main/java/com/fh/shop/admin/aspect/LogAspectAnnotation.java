package com.fh.shop.admin.aspect;

import com.fh.shop.admin.annotation.LogAnnotation;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.common.WebContext;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.po.user.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Component
@Aspect
public class LogAspectAnnotation {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Resource(name = "logService")
    private ILogService logService;

    @Pointcut("execution(* com.fh.shop.admin.controller..*.add*(..))" +
            "|| execution(* com.fh.shop.admin.controller..*.del*(..))" +
            "|| execution(* com.fh.shop.admin.controller..*.update*(..))" +
            "|| execution(* com.fh.shop.admin.controller.user.UserController.login(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object doLog(ProceedingJoinPoint pjp) {

        Object proceed = null;

        HttpServletRequest request = WebContext.get();
        User user = (User) request.getSession().getAttribute(SystemConstant.USER_LOGIN);

        String info = "";

        //获取类名
        String className = pjp.getTarget().getClass().getCanonicalName();


        MethodSignature signature = (MethodSignature) pjp.getSignature();

        Method method = signature.getMethod();
        //获取方法名
        String methodName = signature.getName();

        if (method.isAnnotationPresent(LogAnnotation.class)) {

            LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);

            info = annotation.info();
        }

        Map<String, String[]> map = request.getParameterMap();

        Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();

        StringBuilder builder = new StringBuilder();

        while (iterator.hasNext()) {

            Map.Entry<String, String[]> next = iterator.next();

            String key = next.getKey();

            String[] value = next.getValue();

            builder.append(key).append("=").append(StringUtils.join(value, ",")).append("<-->");
        }

        try {

            proceed = pjp.proceed();
            if (methodName.equals("login")) {

                user = (User) request.getSession().getAttribute(SystemConstant.USER_LOGIN);

                if (user == null) {
                    return proceed;
                }
            }

            Log log = new Log();
            log.setParamInfo(builder.toString());
            log.setContent(info);
            log.setUserName(user.getUserName());
            log.setRealName(user.getRealName());
            log.setInsertTime(new Date());
            log.setStutas(1);
            log.setInfo("执行了" + className + "类的" + methodName + "方法成功");
            logService.addLog(log);

            LOGGER.info(user.getRealName() + "执行了" + className + "类的" + methodName + "方法成功");

        } catch (Throwable throwable) {

            throwable.printStackTrace();

            LOGGER.error(user.getRealName() + "执行了" + className + "类的" + methodName + "方法失败" + throwable.getMessage());

            Log log = new Log();
            log.setContent(info);
            log.setParamInfo(builder.toString());
            log.setUserName(user.getUserName());
            log.setRealName(user.getRealName());
            log.setInsertTime(new Date());
            log.setStutas(0);
            log.setInfo("执行了" + className + "类的" + methodName + "方法失败");
            logService.addLog(log);
            throw new RuntimeException(throwable);
        }

        return proceed;
    }
}
