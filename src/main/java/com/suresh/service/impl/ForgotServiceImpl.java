package com.suresh.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suresh.constants.AppConstants;
import com.suresh.models.UserEntity;
import com.suresh.props.AppsProps;
import com.suresh.repositories.UserDetailsRepository;
import com.suresh.service.ForgotService;
import com.suresh.utils.EmailUtils;

@Service
public class ForgotServiceImpl implements ForgotService {

	@Autowired
	private UserDetailsRepository repository;
	@Autowired
	private AppsProps props;
	@Autowired
	private EmailUtils emailUtil;

	@Override
	public String forgotPassword(String email) {
		UserEntity entity = repository.findByUserEmail("sureshkumarreddyraina@gmail.com");
		if (entity == null) {
			return props.getMessages().get(AppConstants.INVALID_EMAIL);
		}

		if (sendEmail(entity)) {
			return props.getMessages().get(AppConstants.PASSWORD_SENT);
		}

		return AppConstants.ERROR;
	}

	private boolean sendEmail(UserEntity entity) {

		Map<String, String> messages = props.getMessages();

		String subject = messages.get(AppConstants.FORGOT_PWD_MAIL_SUBJECT);
		String to = entity.getUserEmail();
		String bodyFileName = messages.get(AppConstants.FORGOT_PWD_BODY_MAIL_TEMPLATE);
		String body = readMailBody(bodyFileName, entity);

		return emailUtil.sendEmail(subject, body, to);

	}

	public String readMailBody(String fileName, UserEntity user) {
		String body = null;
		StringBuffer buffer = new StringBuffer();

		Path path = Paths.get(fileName);

		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> buffer.append(line));

			body = buffer.toString();
			body = body.replace(AppConstants.FNAME, user.getFirstName());
			body = body.replace(AppConstants.PWD, user.getUserPassword());

		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return body;
	}

}
