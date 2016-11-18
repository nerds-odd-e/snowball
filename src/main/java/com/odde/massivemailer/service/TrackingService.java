package com.odde.massivemailer.service;

public interface TrackingService {

    int updateViewCount(long messageId, String userId);

    void updateViewCount(Long token);
}
