package com.hb.cmd;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hb.dao.EmployeesDAO;

public class SendEmailCommand implements Command{

	@Override
	public String exec(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id").trim();
		String email = request.getParameter("email").trim();
		String ranPwd = (int)(Math.random()*10000000)+"";
		
		EmployeesDAO edao = new EmployeesDAO();
		boolean updateOk = false;
		boolean sendOk = false;
		String result = null;
		
		try {
			new MailExam(email,ranPwd);
			updateOk = edao.pwdUpdate(id,ranPwd);
				
			sendOk = true;

		} catch (Exception e) {
			sendOk = false;		
		} finally {
			if(sendOk && updateOk){
				result = "success";
			}else{
				result = "fail";
			}
		}
		System.out.println(String.valueOf(sendOk)+String.valueOf(updateOk));
		return result;
	}

}
class MailExam {
	   String mail = null;
	   String pass=null;
	   public MailExam() {
	   }

	   public MailExam(String mail,String pass) throws Exception {
	      this.mail = mail;
	      this.pass=pass;
	      Properties prop = new Properties();
	      prop.setProperty("mail.transport.protocol", "smtp");
	      prop.setProperty("mail.host", "smtp.gmail.com");// 바꿔야댐
	      prop.put("mail.smtp.auth", "true");
	      prop.put("mail.smtp.port", "465");
	      prop.put("mail.smtp.socketFactory.port", "465");
	      prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      prop.put("mail.smtp.socketFactory.fallback", "false");

	      Authenticator auth = new Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("kheehyun1100@gmail.com", "tpuvmawjucuccieu");
	         }
	      };
	      Session session = Session.getDefaultInstance(prop, auth);

	      MimeMessage message = new MimeMessage(session);

	      message.setSender(new InternetAddress("lsg0814k@gmail.com"));
	      message.setSubject("사원비밀번호 확인");

	      message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
	      Multipart mp = new MimeMultipart();
	      MimeBodyPart mbp1 = new MimeBodyPart();
	      mbp1.setText("새로운 비밀번호는" + pass + "입니다.");
	      mp.addBodyPart(mbp1);
	      MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	      mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	      mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	      mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	      mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	      mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	      CommandMap.setDefaultCommandMap(mc);
	      message.setContent(mp);
	      Transport.send(message);
	   }
	}