package com.twq.parser.configuration.service;

import com.twq.metadata.model.TargetPage;
import com.twq.parser.configuration.TargetConfigMatcher;
import com.twq.parser.matches.MatchType;
import com.twq.parser.matches.UrlStringMatcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  和profile相关的目标页面配置的服务类
 */
public class TargetPageConfigService {

    private Map<Integer, List<TargetPage>> targetPageMap = null;

    public TargetPageConfigService(Map<Integer, List<TargetPage>> targetPageMap) {
        this.targetPageMap = targetPageMap;
    }

    /**
     *  获取指定profileId的所有的目标页面的配置
     *  并且将每一个目标页面配置转化成可以匹配的实体
     * @param profileId
     * @return
     */
    public List<TargetConfigMatcher> getByProfileId(int profileId) {
        //获取指定profileId相对应的所有目标页面配置
        List<TargetPage> targetPages = targetPageMap.getOrDefault(profileId, Collections.<TargetPage>emptyList());
        //将目标页面配置转换成目标页面匹配类
        List<TargetConfigMatcher> targetConfigMatchers = targetPages.stream().map(targetPage -> {
            return new TargetConfigMatcher(targetPage.getId(), targetPage.getName(), targetPage.isEnable(),
                    new UrlStringMatcher(MatchType.valueOf(targetPage.getMatchType()),
                            targetPage.getMatchPattern(), targetPage.isMatchWithoutQueryString()));
        }).collect(Collectors.toList());
        return targetConfigMatchers;
    }

}
