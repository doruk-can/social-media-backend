package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.OptionalMessage;
import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.domain.ReportedContent;

import java.util.List;

public interface OptionalMessageRepository extends BaseRepository<OptionalMessage, Long>{

    List<OptionalMessage> findAllByMessageFromAndMessageTo(String messageFrom, String messageTo);

}
