package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.ReportedContent;

public interface ReportedContentService extends BaseService<ReportedContent>{

    ReportedContent findByReporterIdAndReportedPostId(long reporterId, long reportedPostId);

}
