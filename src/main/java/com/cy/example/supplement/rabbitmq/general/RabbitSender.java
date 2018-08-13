package com.cy.example.supplement.rabbitmq.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cy.example.vo.IpAnalysisVo_Sina;
import com.cy.example.vo.IpAnalysisVo_Tb;
import com.cy.example.config.RabbitConfig;
import com.cy.example.entity.system.LoginRecordEntity;
import com.cy.example.entity.system.MailEntity;
import com.cy.example.util.HttpRequestUtil;
import com.cy.example.util.JsonUtil;
import com.cy.example.util.StringUtil;


@Component
public class RabbitSender {
	
	private static final Logger logger = LoggerFactory
			.getLogger(RabbitSender.class);

//	@Autowired
//	private AmqpTemplate rabbitTemplate;
//
//	@Value("${swallow.ip.analysis}")
//	private String ipAnalysis;
//
//	public void sendLoginRecord(LoginRecordEntity loginRecord) {
//		String tbUrl = ipAnalysis.split(",")[0] + loginRecord.getC_loginIp();
//		try {
//			String addr = HttpRequestUtil.get(tbUrl);
//			IpAnalysisVo_Tb ipAnalusisTb = JsonUtil.toBean(addr, IpAnalysisVo_Tb.class);
//			if(0 == ipAnalusisTb.getCode()){
//				if(StringUtil.IsEqual("XX", ipAnalusisTb.getData().getRegion())){
//					loginRecord.setC_province(ipAnalusisTb.getData().getCity());
//					loginRecord.setC_city(ipAnalusisTb.getData().getCity());
//				}else{
//					loginRecord.setC_province(ipAnalusisTb.getData().getRegion());
//					loginRecord.setC_city(ipAnalusisTb.getData().getCity());
//				}
//
//			}else{
//				String sinaUrl = ipAnalysis.split(",")[1] + loginRecord.getC_loginIp();
//				addr = HttpRequestUtil.get(sinaUrl);
//				IpAnalysisVo_Sina ipAnalusisSina = JsonUtil.toBean(addr, IpAnalysisVo_Sina.class);
//				if(1 == ipAnalusisSina.getRet()){
//					loginRecord.setC_province(ipAnalusisSina.getProvince());
//					loginRecord.setC_city(ipAnalusisSina.getCity());
//				}else{
//					loginRecord.setC_province("未知");
//					loginRecord.setC_city("未知");
//					loginRecord.toString();
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.info("解析IP出错！");
//			loginRecord.setC_province("未知");
//			loginRecord.setC_city("未知");
//		}
//		logger.info("发送对象信息: " + loginRecord.toString());
//		this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_LOGINRECORD, loginRecord);
//	}
//
//	public void sendMail(MailEntity mail) {
//		logger.info("发送对象信息: " + mail.toString());
//		this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_MAIL, mail);
//	}
	
}