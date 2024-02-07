/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.apimgt.keymgt.model.entity;

import org.wso2.carbon.apimgt.api.model.subscription.CacheableEntity;
import org.wso2.carbon.apimgt.keymgt.model.util.SubscriptionDataStoreUtil;

/**
 * Entity for representing a SubscriptionDTO in APIM
 */
public class Subscription implements CacheableEntity<String> {

    private String subscriptionUUID;
    private String subscriptionId = null;
    private String policyId = null;
    private Integer apiId = null;
    private String apiUUID;
    private Integer appId = null;
    private String applicationUUID;
    private String subscriptionState = null;
    private long timeStamp;
    private String apiName;

    public String getSubscriptionUUID() {

        return subscriptionUUID;
    }

    public void setSubscriptionUUID(String subscriptionUUID) {

        this.subscriptionUUID = subscriptionUUID;
    }

    public String getApiName() {

        return apiName;
    }

    public void setApiName(String apiName) {

        this.apiName = apiName;
    }

    public String getApiVersion() {

        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {

        this.apiVersion = apiVersion;
    }

    private String apiVersion;

    public String getSubscriptionUUId() {

        return subscriptionUUID;
    }

    public void setSubscriptionUUId(String subscriptionUUId) {

        this.subscriptionUUID = subscriptionUUId;
    }

    public String getApiUUID() {

        return apiUUID;
    }

    public void setApiUUID(String apiUUID) {

        this.apiUUID = apiUUID;
    }

    public String getApplicationUUID() {

        return applicationUUID;
    }

    public void setApplicationUUID(String applicationUUID) {

        this.applicationUUID = applicationUUID;
    }

    public String getSubscriptionId() {

        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {

        this.subscriptionId = subscriptionId;
    }

    public String getPolicyId() {

        return policyId;
    }

    public void setPolicyId(String policyId) {

        this.policyId = policyId;
    }

    public Integer getApiId() {

        return apiId;
    }

    public void setApiId(Integer apiId) {

        this.apiId = apiId;
    }

    public Integer getAppId() {

        return appId;
    }

    public void setAppId(Integer appId) {

        this.appId = appId;
    }

    public String getSubscriptionState() {

        return subscriptionState;
    }

    public void setSubscriptionState(String subscriptionState) {

        this.subscriptionState = subscriptionState;
    }

    @Override
    public String getCacheKey() {

        return SubscriptionDataStoreUtil.getSubscriptionCacheKey(getAppId(), getApiId());
    }

    public long getTimeStamp() {

        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {

        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {

        return "Subscription{" +
                "subscriptionUUID='" + subscriptionUUID + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", policyId='" + policyId + '\'' +
                ", apiId=" + apiId +
                ", apiUUId='" + apiUUID + '\'' +
                ", appId=" + appId +
                ", appUUID='" + applicationUUID + '\'' +
                ", subscriptionState='" + subscriptionState + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
