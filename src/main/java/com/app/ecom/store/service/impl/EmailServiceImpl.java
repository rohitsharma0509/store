package com.app.ecom.store.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.app.ecom.store.constants.Constants;
import com.app.ecom.store.dto.Email;
import com.app.ecom.store.model.EmailAccount;
import com.app.ecom.store.repository.EmailAccountRepository;
import com.app.ecom.store.service.EmailService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.util.EmailUtil;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Inject
	private EmailAccountRepository emailAccountRepository;
	
	@Inject
	private CommonUtil commonUtil;
	
	@Inject
	private EmailUtil emailUtil;

	@Override
	public EmailAccount getEmailAccountById(Long id) {
		return emailAccountRepository.findById(id).get();
	}
	
	public EmailAccount getEmailAccount() {
		List<EmailAccount> emailAccounts = emailAccountRepository.findAll();
		if(CollectionUtils.isEmpty(emailAccounts)){
			return new EmailAccount();
		} else {
			return emailAccounts.get(0);
		}
	}

	@Override
	public EmailAccount addEmailAccount(EmailAccount emailAccount) {
		return emailAccountRepository.save(emailAccount);
	}

	@Override
	public void sendEmail(Email email) {
		String[] to = commonUtil.convertStringToArray(email.getTo(), Constants.COMMA);
		String[] cc = commonUtil.convertStringToArray(email.getCc(), Constants.COMMA);
		String[] bcc = commonUtil.convertStringToArray(email.getBcc(), Constants.COMMA);
		emailUtil.sendEmail(to, cc, bcc, email.getSubject(), email.getBody());
	}
}
