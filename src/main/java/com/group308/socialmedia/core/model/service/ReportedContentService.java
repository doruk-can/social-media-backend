package com.group308.socialmedia.core.model.service;

import com.group308.socialmedia.core.model.domain.ReportedContent;

import java.util.List;

public interface ReportedContentService extends BaseService<ReportedContent>{

    ReportedContent findByReporterIdAndReportedPostId(long reporterId, long reportedPostId);

    ReportedContent findByReporterIdAndReportedUserId(long reporterId, long reportedUserId);

    List<ReportedContent> findAllByReportType(String reportType);


}
