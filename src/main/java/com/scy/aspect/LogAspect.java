package com.scy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.scy.controller.*.*(..))")
    public void log() {

    }

    /**
     * 在进行访问前获取请求信息，封装成 请求日志类
     *
     * @param joinPoint JoinPoint对象封装了SpringAop中切面方法的信息,
     *                  在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 利用 RequestContextHolder 获取request，进而获取URL和IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        // 利用 JoinPoint 获取方法签名，进而获取方法和参数
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog log = new RequestLog(url, ip, classMethod, args);

        logger.info("Request: {}", log);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("------------------执行了doAfter-----------------");
    }

    @AfterReturning(pointcut = "log()", returning = "result")
    public void doAfterReturning(Object result) {
//        logger.info("------------------执行了doAfter-----------------Returning-----------------");
        logger.info("result: {}", result);
    }

    // 将请求动作要记录的日志内容封装成实体类
    private class RequestLog {
        private String url;             // 请求的资源url
        private String ip;              // 访问者的IP
        private String classMethod;     // 调用的方法
        private Object[] args;          // 传递的参数

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
