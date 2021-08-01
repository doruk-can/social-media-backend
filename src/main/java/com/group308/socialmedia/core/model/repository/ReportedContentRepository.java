package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.ReportedContent;
import com.group308.socialmedia.core.model.domain.Subscription;

import java.util.List;

public interface ReportedContentRepository extends BaseRepository<ReportedContent, Long> {

    ReportedContent findByReporterIdAndReportedPostId(long reporterId, long reportedPostId);

    ReportedContent findByReporterIdAndReportedUserId(long reporterId, long reportedUserId);

    List<ReportedContent> findAllByReportType(String reportType);

}
