package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.Content;
import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.PostInteraction;

import java.util.List;

public interface OptionalMessageService extends BaseService<OptionalMessage> {

    List<OptionalMessage> findAllByMessageFromAndMessageTo(String messageFrom, String messageTo);
}
