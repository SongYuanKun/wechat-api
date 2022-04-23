package com.songyuankun.wechat.timeline.service;

import com.songyuankun.wechat.entity.Timeline;
import com.songyuankun.wechat.entity.TimelineMonth;
import com.songyuankun.wechat.entity.TimelinePost;
import com.songyuankun.wechat.timeline.dao.TimeLineRepository;
import com.songyuankun.wechat.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author songyuankun
 */
@Service
@Slf4j
public class TimelineServiceImpl {

    private final TimeLineRepository timeLineRepository;

    public TimelineServiceImpl(TimeLineRepository timeLineRepository) {
        this.timeLineRepository = timeLineRepository;
    }


    public List<Timeline> listTimeLine() {
        List<Object[]> objects = timeLineRepository.listTimeline();
        List<Timeline> timelineList = new ArrayList<>();
        objects.forEach(objects1 -> timelineList.add(new Timeline(Integer.parseInt(objects1[0].toString()), Integer.parseInt(objects1[1].toString()))));
        genTimelineMonth(timelineList);
        return timelineList;
    }

    private void genTimelineMonth(List<Timeline> timelineList) {
        for (Timeline timeline : timelineList) {
            List<TimelineMonth> timelineMonthList = new ArrayList<>();
            for (int i = Calendar.DECEMBER + 1; i > 0; i--) {
                List<Object[]> objects = timeLineRepository.listTimelinePost(timeline.getYear(), i);
                List<TimelinePost> postList = new ArrayList<>();
                objects.forEach(objects1 -> postList.add(new TimelinePost(Integer.parseInt(objects1[0].toString()), objects1[1].toString(), objects1[2].toString(), DateUtil.String2Date(objects1[3].toString()))));

                if (CollectionUtils.isEmpty(postList)) {
                    continue;
                }
                TimelineMonth month = new TimelineMonth();
                month.setCount(postList.size());
                month.setMonth(i);
                month.setPosts(postList);
                timelineMonthList.add(month);
            }
            timeline.setMonths(timelineMonthList);
        }
    }

}
