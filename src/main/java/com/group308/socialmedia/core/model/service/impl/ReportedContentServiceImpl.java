package com.group308.socialmedia.core.model.service.impl;


import com.group308.socialmedia.core.model.domain.Post;
import com.group308.socialmedia.core.model.domain.ReportedContent;
import com.group308.socialmedia.core.model.repository.PostRepository;
import com.group308.socialmedia.core.model.repository.ReportedContentRepository;
import com.group308.socialmedia.core.model.service.ReportedContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportedContentServiceImpl implements ReportedContentService {

    private final ReportedContentRepository reportedContentRepository;

    @Override
    public ReportedContent findById(Long id) {
        return reportedContentRepository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return reportedContentRepository.existsById(id);
    }

    @Override
    public ReportedContent save(ReportedContent reportedContent) {
        return reportedContentRepository.save(reportedContent);
    }

    @Override
    public void deleteById(Long id) {
        this.reportedContentRepository.deleteById(id);
    }

    @Override
    public ReportedContent findByReporterIdAndReportedPostId(long reporterId, long reportedPostId) {
           return reportedContentRepository.findByReporterIdAndReportedPostId(reporterId, reportedPostId);
    }

}
