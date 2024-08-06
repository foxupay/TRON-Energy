package com.fox.energy.framework.config;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.fox.energy.common.utils.email.EmailThreadFactory;
import com.fox.energy.common.utils.spring.SpringUtils;
import com.fox.energy.conf.domain.AppConfMail;
import com.fox.energy.conf.service.IAppConfMailService;

import java.io.File;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MailUtils {
    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 500, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new EmailThreadFactory("email-"));

    private static final IAppConfMailService appConfMailService = SpringUtils.getBean(IAppConfMailService.class);

    public static MailAccount getMailAccount() {
        List<AppConfMail> appConfMails = appConfMailService.selectList(null);
        MailAccount account = new MailAccount();
        account.setHost(appConfMails.get(0).getHost());
        account.setPort(appConfMails.get(0).getPort());
        account.setFrom(appConfMails.get(0).getFrom());
        account.setUser(appConfMails.get(0).getUser());
        account.setPass(appConfMails.get(0).getPass());
        account.setSslEnable(appConfMails.get(0).getSslEnable() == 1);
        return account;
    }

    public static void send(String to, String subject, String content, boolean sync, File... files) {
        MailAccount account = getMailAccount();
        if (sync) {
            MailUtil.send(account, to, subject, content, false, files);
        } else {
            pool.submit(() -> {
                MailUtil.send(account, to, subject, content, false, files);
            });
        }
    }
}
