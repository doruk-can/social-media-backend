package com.group308.socialmedia.core.model.repository;

import com.group308.socialmedia.core.model.domain.ReportedContent;
import com.group308.socialmedia.core.model.domain.Subscription;

public interface ReportedContentRepository extends BaseRepository<ReportedContent, Long> {

    ReportedContent findByReporterIdAndReportedPostId(long reporterId, long reportedPostId);
}
